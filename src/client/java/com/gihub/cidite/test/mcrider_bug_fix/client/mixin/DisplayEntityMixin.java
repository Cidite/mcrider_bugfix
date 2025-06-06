package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.entity.decoration.DisplayEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayEntity.class)
public abstract class DisplayEntityMixin {
    @Unique private boolean isGameAcceleration = false;
    @Unique private boolean onSummon = false;

    @Unique public int defaultInterpolationDuration;

    @Unique public int defaultTeleportDuration;

    @Unique public int defaultStartInterpolation;

    @Shadow public abstract void setTeleportDuration(int teleportDuration);

    @Shadow public abstract int getTeleportDuration();

    @Shadow public abstract void setInterpolationDuration(int interpolationDuration);

    @Shadow public abstract int getInterpolationDuration();

    @Shadow public abstract void setStartInterpolation(int startInterpolation);

    @Shadow public abstract int getStartInterpolation();


//    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/DisplayEntity;getInterpolationDuration()I"))
//    private int getInterpolationDuration(DisplayEntity instance) {
//        if (Mcrider_bug_fixClient.Riding) {
//            int i = Mcrider_bug_fixClient.gameAcceleration;
//            return this.getInterpolationDuration() * i;
//        } else {
//            return this.getInterpolationDuration();
//        }
//    }
//    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/DisplayEntity;getStartInterpolation()I"))
//    private int getStartInterpolation(DisplayEntity instance) {
//        if (Mcrider_bug_fixClient.Riding) {
//            int i = Mcrider_bug_fixClient.gameAcceleration;
//            return this.getStartInterpolation() * i;
//        } else {
//            return this.getStartInterpolation();
//        }
//    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(CallbackInfo ci) {
        if (this.onSummon) {
            this.defaultTeleportDuration = this.getTeleportDuration();
            this.defaultInterpolationDuration = this.getInterpolationDuration();
            this.defaultStartInterpolation = this.getStartInterpolation();
            this.onSummon = true;
        }
        if (Mcrider_bug_fixClient.Riding) {
            if (!isGameAcceleration) {
                int i = Mcrider_bug_fixClient.gameAcceleration;
                this.setTeleportDuration(this.getTeleportDuration() * i);
                this.setInterpolationDuration(this.getInterpolationDuration() * i);
                this.setStartInterpolation(this.getStartInterpolation() * i);
                isGameAcceleration = true;
            }
        } else if (isGameAcceleration) {
            this.setTeleportDuration(this.defaultTeleportDuration);
            this.setInterpolationDuration(this.defaultInterpolationDuration);
            this.setStartInterpolation(this.defaultStartInterpolation);
            isGameAcceleration = false;
        }
    }

}
