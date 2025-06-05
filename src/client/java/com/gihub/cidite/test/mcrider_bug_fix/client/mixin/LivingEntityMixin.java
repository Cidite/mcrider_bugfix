package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyConstant(method = "getHandSwingDuration", constant = @Constant(intValue = 6))
    private int getHandSwingDuration(int constant) {
        if (Mcrider_bug_fixClient.Riding) {
            return 6 * Mcrider_bug_fixClient.gameAcceleration;
        }
        return 6;
    }
    @ModifyConstant(method = "tick", constant = @Constant(floatValue = 57.295776F))
    private float tick(float constant) {
        if (Mcrider_bug_fixClient.Riding) {
            return 57.295776F / Mcrider_bug_fixClient.gameAcceleration;
        }
        return 57.295776F;
    }

}
