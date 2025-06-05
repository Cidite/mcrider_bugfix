package com.gihub.cidite.test.mcrider_bug_fix.client.mixin.particle;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class ParticleManagerMixin {
    @Shadow protected double velocityX;
    @Shadow protected double velocityY;
    @Shadow protected double velocityZ;

    @Shadow public abstract int getMaxAge();

    @Shadow protected int maxAge;
    @Unique
    private boolean ParticleAcceleration = true;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(CallbackInfo ci) {
        if (ParticleAcceleration && Mcrider_bug_fixClient.Riding) {
            this.velocityX = getVelocityX() / Mcrider_bug_fixClient.gameAcceleration;
            this.velocityY = getVelocityY() / Mcrider_bug_fixClient.gameAcceleration;
            this.velocityZ = getVelocityZ() / Mcrider_bug_fixClient.gameAcceleration;
            this.maxAge = this.getMaxAge() * Mcrider_bug_fixClient.gameAcceleration;
            ParticleAcceleration = false;
        }
    }

    @Unique
    public double getVelocityX() {
        return this.velocityX;
    }
    @Unique
    public double getVelocityY() {
        return this.velocityY;
    }
    @Unique
    public double getVelocityZ() {
        return this.velocityZ;
    }

}
