package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private int waitTime;

    @Inject(method = "tick(Z)V", at = @At(value = "HEAD"), cancellable = true)
    private void tick(boolean paused, CallbackInfo ci) {
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
