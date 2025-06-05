package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;


import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.tick.TickManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow private final RenderTickCounter.Dynamic renderTickCounter = new RenderTickCounter.Dynamic(((float)Mcrider_bug_fixClient.gameAcceleration * 20), 0L, this::getTargetMillisPerTick);
    @Shadow @Nullable public ClientWorld world;
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Final private static Logger LOGGER;

    @Unique
    private float getTargetMillisPerTick(float millis) {
        if (this.world != null) {
            TickManager tickManager = this.world.getTickManager();
            if (tickManager.shouldTick()) {
                return Math.max(millis, tickManager.getMillisPerTick());
            }
        }
        return millis;
    }

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
            TickManager tickManager = this.world.getTickManager();
            if (isKartEntity()) {
                tickManager.setTickRate((float) Mcrider_bug_fixClient.gameAcceleration * 20);
                Mcrider_bug_fixClient.Riding = true;
                if (Mcrider_bug_fixClient.displayEntityModification == 1) {
                    Mcrider_bug_fixClient.displayEntityModification = 2;
                }
                if (!Mcrider_bug_fixClient.isRiding) {
                    Mcrider_bug_fixClient.isRiding = true;
                    Mcrider_bug_fixClient.displayEntityModification = 1;
                }
            } else if (Mcrider_bug_fixClient.Riding) {
                tickManager.setTickRate(20.0F);
                Mcrider_bug_fixClient.Riding = false;
                Mcrider_bug_fixClient.isRiding = false;
                if (Mcrider_bug_fixClient.displayEntityModification == -1) {
                    Mcrider_bug_fixClient.displayEntityModification = 0;
                }
                if (Mcrider_bug_fixClient.displayEntityModification == 2) {
                    Mcrider_bug_fixClient.displayEntityModification = -1;
                }
            }
        }
        profiler.pop();
    }
}
