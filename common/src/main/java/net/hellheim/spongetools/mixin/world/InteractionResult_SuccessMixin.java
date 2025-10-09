package net.hellheim.spongetools.mixin.world;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(net.minecraft.world.InteractionResult.Success.class)
public abstract class InteractionResult_SuccessMixin implements InteractionResult.Success {

    @Shadow @Final private net.minecraft.world.InteractionResult.SwingSource swingSource;
    @Shadow @Final private net.minecraft.world.InteractionResult.ItemContext itemContext;

    @Override
    public SwingType swing() {
        return Converter.asSponge(this.swingSource);
    }

    @Override
    public boolean isInteraction() {
        return this.itemContext.wasItemInteraction();
    }

    @Override
    public Optional<ItemStack> result() {
        return Optional.ofNullable(Converter.asSponge(this.itemContext.heldItemTransformedTo()));
    }
}
