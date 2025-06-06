package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.LookAtS2CPacket;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.PlayerInput;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract MinecraftClient getClient();

//    @ModifyConstant(method = "updateFovMultiplier", constant = @Constant(floatValue = 0.5F))
//    private float updateFovMultiplier(float constant) {
//        if (Mcrider_bug_fixClient.Riding) {
//            return 0.5F / Mcrider_bug_fixClient.gameAcceleration;
//        } else {
//            return 0.5F;
//        }
//    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        if (Mcrider_bug_fixClient.Riding) {
            ClientPlayerEntity player = getClient().player;
            if (player != null) {
                GameOptions options = getClient().options;
		PlayerInput mcInput = player.input.playerInput;

		PlayerInput input = new PlayerInput(
			options.forwardKey.isPressed(),
			options.backKey.isPressed(),
			options.leftKey.isPressed(),
			options.rightKey.isPressed(),
			mcInput.jump(),
			mcInput.sneak(),
			mcInput.sprint()
		);
                player.networkHandler.sendPacket(new PlayerInputC2SPacket(input));

                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(player.getYaw(), player.getPitch(), player.isOnGround(), player.horizontalCollision));
                Entity entity = player.getRootVehicle();
                if (entity != player && entity.isLogicalSideForUpdatingMovement()) {
                    player.networkHandler.sendPacket(VehicleMoveC2SPacket.fromVehicle(entity));
                }

            }
        }
    }
}
