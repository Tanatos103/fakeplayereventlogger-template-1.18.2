package com.fakeplayereventlogger.mixin;

import carpet.commands.PlayerCommand;
import com.fakeplayereventlogger.aurum.PlayerInfo;
import com.fakeplayereventlogger.aurum.SocketClient;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerCommand.class)
public class PlayerKillCommandMixin {

    private static final SocketClient SOCKET_CLIENT = new SocketClient("localhost", 8080); // Adjust host and port as needed

    @Inject(method = "kill", at = @At("HEAD"), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onPlayerKillCommand(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        try {
            ServerPlayerEntity executingPlayer = context.getSource().getPlayer();
            ServerPlayerEntity targetPlayer = getTargetPlayer(context);

            if (executingPlayer != null && targetPlayer != null) {
                PlayerInfo playerInfo = new PlayerInfo(
                    targetPlayer.getName().getString(),
                    targetPlayer.getUuidAsString(),
                    new double[]{targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ()},
                    "player_kill",
                    getDimensionKey(targetPlayer.getWorld()),
                    getServerTag(executingPlayer),
                    executingPlayer.getName().getString(),
                    executingPlayer.getUuidAsString()
                );

                SOCKET_CLIENT.sendPlayerInfoAsync(playerInfo.toJson());
            }
        } catch (Exception e) {
            // Log the exception or handle it as appropriate for your mod
            e.printStackTrace();
        }
    }

    private static ServerPlayerEntity getTargetPlayer(CommandContext<ServerCommandSource> context) {
        // Implement logic to get the target player from the command context
        // This might involve parsing arguments or using other methods from PlayerCommand
        // For now, we'll return null as a placeholder
        return null;
    }

    private static String getDimensionKey(ServerWorld world) {
        RegistryKey<World> dimensionKey = world.getRegistryKey();
        return dimensionKey.getValue().toString();
    }

    private static String getServerTag(ServerPlayerEntity player) {
        return player.getServer().getServerModName() + " " + player.getServer().getVersion();
    }
}