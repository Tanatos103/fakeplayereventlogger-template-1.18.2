package com.fakeplayereventlogger.aurum;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClient implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger("SocketClient:FakePlayerLogger");
    private static final int QUEUE_CAPACITY = 1000;
    private static final int BUFFER_SIZE = 8192;

    private final String host;
    private final int port;
    private final BlockingQueue<String> messageQueue;
    private volatile boolean isRunning; // Usamos un booleano simple
    private final ExecutorService executorService;
    private final Thread senderThread;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.messageQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.isRunning = true;
        this.executorService = Executors.newSingleThreadExecutor();
        this.senderThread = initSenderThread();
    }

    public void sendPlayerInfoAsync(String json) {
        if (!messageQueue.offer(json)) {
            try {
                messageQueue.put(json); // Esto bloqueará si la cola está llena
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Thread initSenderThread() {
        Thread thread = new Thread(() -> {
            while (isRunning && !Thread.currentThread().isInterrupted()) {
                try {
                    String json = messageQueue.poll(1, TimeUnit.SECONDS);
                    if (json != null) {
                        sendMessageAsync(json);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        return thread;
    }

    private void sendMessageAsync(String json) {
        try {
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            InetSocketAddress serverAddress = new InetSocketAddress(host, port);

            channel.connect(serverAddress, null, new CompletionHandler<Void, Void>() {
                @Override
                public void completed(Void result, Void attachment) {
                    ByteBuffer buffer = ByteBuffer.wrap((json + "\n").getBytes(StandardCharsets.UTF_8));
                    channel.write(buffer, null, new CompletionHandler<Integer, Void>() {
                        @Override
                        public void completed(Integer result, Void attachment) {
                            closeQuietly(channel);
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) { 
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
        isRunning = false; // Detenemos el hilo
        senderThread.interrupt();
        try {
            senderThread.join(5000);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for sender thread to stop", e);
            Thread.currentThread().interrupt();
        }
        shutdownExecutor();
    }

    private void shutdownExecutor() {
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                LOGGER.warn("ExecutorService did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for ExecutorService to terminate", e);
            Thread.currentThread().interrupt();
        }
    }

}
