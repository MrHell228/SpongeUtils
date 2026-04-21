package net.hellheim.spongetools.mixin.world;

import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InteractionResult.SwingSource.class)
public abstract class InteractionResult_SwingSourceMixin implements SwingType {
}
