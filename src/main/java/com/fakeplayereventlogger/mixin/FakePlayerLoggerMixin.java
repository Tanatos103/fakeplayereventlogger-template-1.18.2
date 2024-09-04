package com.fakeplayereventlogger.mixin;

import carpet.commands.PlayerCommand;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCommand.class)
public abstract class FakePlayerLoggerMixin {
    private static final Logger LOGGER = LogManager.getLogger("FakePlayerLogger");

    @Inject(method = "spawn", at = @At("HEAD"), remap = false)
    private static void logFakePlayerSpawn(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        String playerName = context.getSource().getName();
        LOGGER.info("El jugador {} ha ejecutado el comando 'spawn' para un fakeplayer.", playerName);
    }
}
