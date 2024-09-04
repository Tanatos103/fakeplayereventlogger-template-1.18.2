package com.fakeplayereventlogger.aurum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketClient implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger("EventLogs");
    private static final int QUEUE_CAPACITY = 1000;
    private static final int BUFFER_SIZE = 8192;

    private final String host;
    private final int port;
    private final BlockingQueue<String> messageQueue;
    private final AtomicBoolean isRunning;
    private final ExecutorService executorService;
    private final AsynchronousChannelGroup channelGroup;
    private final Thread senderThread;

    public SocketClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.messageQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.isRunning = new AtomicBoolean(true);
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
        this.senderThread = initSenderThread();
    }

    public void sendPlayerInfoAsync(String json) {
        if (!messageQueue.offer(json)) {
            LOGGER.warn("Message queue is full. Applying backpressure.");
            try {
                messageQueue.put(json); // This will block if the queue is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("Interrupted while waiting to add message to queue", e);
            }
        }
    }

    private Thread initSenderThread() {
        Thread thread = new Thread(() -> {
            while (isRunning.get() && !Thread.currentThread().isInterrupted()) {
                try {
                    String json = messageQueue.poll(1, TimeUnit.SECONDS);
                    if (json != null) {
                        sendMessageAsync(json);
                    }
                } catch (InterruptedException e) {
                    LOGGER.info("Sender thread interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        return thread;
    }

    private void sendMessageAsync(String json) {
        try {
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open(channelGroup);
            InetSocketAddress serverAddress = new InetSocketAddress(host, port);
            
            channel.connect(serverAddress, null, new CompletionHandler<Void, Void>() {
                @Override
                public void completed(Void result, Void attachment) {
                    ByteBuffer buffer = ByteBuffer.wrap((json + "\n").getBytes(StandardCharsets.UTF_8));
                    channel.write(buffer, null, new CompletionHandler<Integer, Void>() {
                        @Override
                        public void completed(Integer result, Void attachment) {
                            closeQuietly(channel);
                            LOGGER.debug("JSON sent successfully: {}", json);
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                            LOGGER.error("Failed to send message", exc);
                            closeQuietly(channel);
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    LOGGER.error("Failed to connect", exc);
                    closeQuietly(channel);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Error creating channel", e);
        }
    }

    private void closeQuietly(AsynchronousSocketChannel channel) {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            LOGGER.warn("Error closing channel", e);
        }
    }

    @Override
    public void close() {
        isRunning.set(false);
        senderThread.interrupt();
        try {
            senderThread.join(5000);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for sender thread to stop", e);
            Thread.currentThread().interrupt();
        }
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                LOGGER.warn("ExecutorService did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for ExecutorService to terminate", e);
            Thread.currentThread().interrupt();
        }
        try {
            channelGroup.shutdownNow();
            channelGroup.awaitTermination(5, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error shutting down channel group", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        try (SocketClient client = new SocketClient(Config.getServerHost(), Config.getServerPort())) {
            // Use the client here
        } catch (IOException e) {
            LOGGER.error("Error initializing SocketClient", e);
        }
    }
}