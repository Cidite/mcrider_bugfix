package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.tick.TickManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Shadow @Final private TickManager tickManager;
    @Unique
    private int waitTime;

    @Nullable
    public ClientWorld world;

//    @Inject(method = "tickEntities", at = @At(value = "HEAD"), cancellable = true)
//    private void tickEntities(CallbackInfo ci) {
//            this.waitTime += 1;
//            TickManager tickManager = this.tickManager;
//            if (getWaitTime() > 3) {
//                if (tickManager.getTickRate() == 60.0F) {
//                    setWaitTime(0);
//                    //System.out.print("Run!");
//                }
//            } else {
//                //System.out.print("Cancel!");
//                ci.cancel();
//            }
//    }

    @Unique
    public int getWaitTime() {
        return this.waitTime;
    }

    @Unique
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

}