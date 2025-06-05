package com.gihub.cidite.test.mcrider_bug_fix.client.mixin.particle;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.particle.CampfireSmokeParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireSmokeParticle.class)
public class CampfireSmokeParticleMixin {
    @Unique
    private int waitTime;

    @Unique
    public int getWaitTime() {
        return this.waitTime;
    }

    @Unique
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
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

}
