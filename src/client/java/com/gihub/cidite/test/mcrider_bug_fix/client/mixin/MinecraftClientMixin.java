package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;


import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientWorld world;
    @Shadow @Nullable public ClientPlayerEntity player;

    @Unique
    private boolean isKartEntity() {
        if (this.player != null) {
            if (this.player.hasVehicle()) {
                Entity entity = this.player.getVehicle();
                if (entity.getType() == EntityType.COD || entity.getType() == EntityType.ARMADILLO || entity.getType() == EntityType.INTERACTION && entity.getVehicle().getType() == EntityType.ITEM_DISPLAY) {
                    return true;
                }
            }
        }
        return false;
    }

    @Inject(method = "tick",at = @At(value = "TAIL"))
    private void init(CallbackInfo ci) {
        Profiler profiler = Profilers.get();
        profiler.push("isKartEntity");
        if (this.world != null) {
            Mcrider_bug_fixClient.Riding = isKartEntity();
        }
        profiler.pop();
    }
}
