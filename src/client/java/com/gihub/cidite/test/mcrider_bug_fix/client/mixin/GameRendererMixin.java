package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow public abstract MinecraftClient getClient();

    @Unique private PlayerInput lastPlayerInput = PlayerInput.DEFAULT;

    @Unique float lastYaw;

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        Profiler profiler = Profilers.get();
        profiler.push("kartPacketAcceleration");
        if (Mcrider_bug_fixClient.Riding) {
            ClientPlayerEntity player = getClient().player;
            if (player != null) {
                PlayerInput input = getPlayerInput(player);

                //키 입력이 감지되면 패킷 발사
                if (!this.lastPlayerInput.equals(input)) {
                    player.networkHandler.sendPacket(new PlayerInputC2SPacket(input));
                    this.lastPlayerInput = input;
                }

                //카메라의 가로 방향이 회전하면 패킷 발사
                if (this.lastYaw != player.getYaw()) {
                    player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(player.getYaw(), player.getPitch(), player.isOnGround(), player.horizontalCollision));
                    this.lastYaw = player.getYaw();
                }

                Entity entity = player.getRootVehicle();
                if (entity != player && entity.isLogicalSideForUpdatingMovement()) {
                    player.networkHandler.sendPacket(VehicleMoveC2SPacket.fromVehicle(entity));
                }

            }
        }
        profiler.pop();
    }

    @Unique
    private @NotNull PlayerInput getPlayerInput(ClientPlayerEntity player) {
        GameOptions options = getClient().options;
        PlayerInput mcInput = player.input.playerInput;

        return new PlayerInput(
                options.forwardKey.isPressed(),
                options.backKey.isPressed(),
                options.leftKey.isPressed(),
                options.rightKey.isPressed(),
                mcInput.jump(),
                mcInput.sneak(),
                mcInput.sprint()
        );
    }
}
