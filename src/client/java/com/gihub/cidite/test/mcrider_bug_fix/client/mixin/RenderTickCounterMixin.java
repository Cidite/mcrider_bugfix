package com.gihub.cidite.test.mcrider_bug_fix.client.mixin;

import com.gihub.cidite.test.mcrider_bug_fix.client.Mcrider_bug_fixClient;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.*;

@Mixin(TickManager.class)
public class RenderTickCounterMixin {
    @Shadow protected float tickRate = ((float)Mcrider_bug_fixClient.gameAcceleration * 20);
}

