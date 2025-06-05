package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherRendering.class)
public class FrameGraphBuilderMixin {
    @Unique
    private int waitTime;

    @Inject(method = "renderPrecipitation(Lnet/minecraft/world/World;Lnet/minecraft/client/render/VertexConsumerProvider;IFLnet/minecraft/util/math/Vec3d;)V", at = @At(value = "HEAD"), cancellable = true)
    private void renderPrecipitation(CallbackInfo ci) {
        this.waitTime += 1;
        if (Mcrider_bug_fixClient.Riding) {
            if (getWaitTime() > Mcrider_bug_fixClient.gameAcceleration) {
                setWaitTime(0);
                //System.out.print("Run!");
            } else {
                //System.out.print("Cancel!");
                ci.cancel();
            }
        }
    }

    @Unique
    public int getWaitTime() {
        return this.waitTime;
    }

    @Unique
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
