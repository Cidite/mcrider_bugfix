package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyConstant(method = "updateFovMultiplier", constant = @Constant(floatValue = 0.5F))
    private float updateFovMultiplier(float constant) {
        if (Mcrider_bug_fixClient.Riding) {
            return 0.5F / Mcrider_bug_fixClient.gameAcceleration;
        } else {
            return 0.5F;
        }
    }
}
