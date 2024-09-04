package com.fakeplayereventlogger.mixin;

import carpet.commands.PlayerCommand;
import com.fakeplayereventlogger.aurum.PlayerInfo;
import com.fakeplayereventlogger.aurum.SocketClient;
import com.fakeplayereventlogger.aurum.Config;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin(PlayerCommand.class)
public abstract class FakePlayerLoggerMixin {
    private static final SocketClient SOCKET_CLIENT;

    static {
        SOCKET_CLIENT = new SocketClient(Config.getServerHost(), Config.getServerPort());
    }

    @Inject(method = "spawn", at = @At("HEAD"), remap = false)
    private static void logFakePlayerSpawn(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        String playerName = context.getArgument("player", String.class);
        Vec3d pos = source.getPosition(); // Usamos la posición del ejecutor como valor predeterminado



        ServerPlayerEntity executorPlayer = source.getPlayer();
        
        if (executorPlayer != null) {
            PlayerInfo playerInfo = new PlayerInfo(
                playerName,
                source.getPlayer().getUuidAsString(),
                new double[] {pos.x, pos.y, pos.z},
                "spawn",
                source.getWorld().getRegistryKey().getValue().toString(),
                executorPlayer.getName().asString(),
                executorPlayer.getUuidAsString()
            );
            
            String json = playerInfo.toJson();
            SOCKET_CLIENT.sendPlayerInfoAsync(json);

        } else {
            PlayerInfo playerInfo = new PlayerInfo(
                playerName,
                source.getPlayer().getUuidAsString(),
                new double[] {pos.x, pos.y, pos.z},
                "spawn",
                source.getWorld().getRegistryKey().getValue().toString(),
                "console",
                "00000000-0000-0000-0000-000000000000"
            );

            String json = playerInfo.toJson();
            SOCKET_CLIENT.sendPlayerInfoAsync(json);
        }
    }
}