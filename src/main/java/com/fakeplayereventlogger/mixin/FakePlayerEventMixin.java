package com.fakeplayereventlogger.mixin;

import carpet.patches.EntityPlayerMPFake;
import com.fakeplayereventlogger.aurum.PlayerInfo;
import com.fakeplayereventlogger.aurum.SocketClient;
import com.fakeplayereventlogger.aurum.Config;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMPFake.class)
public abstract class FakePlayerEventMixin {
    private static final SocketClient SOCKET_CLIENT;

    static {
        SOCKET_CLIENT = new SocketClient(Config.getServerHost(), Config.getServerPort());
    }

    @Inject(method = "kill(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    private void logFakePlayerDisconnect(Text reason, CallbackInfo ci) {
        logFakePlayerEvent("disconnect", reason.getString());
    }


    private void logFakePlayerEvent(String action, String reason) {
        EntityPlayerMPFake fakePlayer = (EntityPlayerMPFake) (Object) this;
        
        PlayerInfo playerInfo = new PlayerInfo(
            fakePlayer.getName().getString(),
            fakePlayer.getUuidAsString(),
            new double[]{fakePlayer.getX(), fakePlayer.getY(), fakePlayer.getZ()},
            action,
            fakePlayer.getWorld().getRegistryKey().getValue().toString(),   
            null,
            null,
            reason
        );
        
        String json = playerInfo.toJson();
        SOCKET_CLIENT.sendPlayerInfoAsync(json);
    }
}