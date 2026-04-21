package net.hellheim.spongetools.mixin.world;

import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.world.InteractionResult.class)
public interface InteractionResultMixin extends InteractionResult {
}
