package net.hellheim.spongetools.common.factory;

import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult.Success;
import net.minecraft.world.InteractionResult;

public final class InteractionResultFactory implements net.hellheim.spongetools.custom.behaviour.util.InteractionResult.Factory {
	
	@Override
	public Success successServer() {
		return Converter.asSponge(InteractionResult.SUCCESS_SERVER);
	}
	
	@Override
	public Success successClient() {
		return Converter.asSponge(InteractionResult.SUCCESS);
	}
	
	@Override
	public Success consume() {
		return Converter.asSponge(InteractionResult.CONSUME);
	}
	
	@Override
	public net.hellheim.spongetools.custom.behaviour.util.InteractionResult fail() {
		return Converter.asSponge(InteractionResult.FAIL);
	}
	
	@Override
	public net.hellheim.spongetools.custom.behaviour.util.InteractionResult pass() {
		return Converter.asSponge(InteractionResult.PASS);
	}
	
	@Override
	public net.hellheim.spongetools.custom.behaviour.util.InteractionResult tryWithoutItem() {
		return Converter.asSponge(InteractionResult.TRY_WITH_EMPTY_HAND);
	}
	
	@Override
	public Success success(final SwingType swing, final boolean isInteraction, final Optional<ItemStack> result) {
		return Converter.asSponge(new InteractionResult.Success(
				Converter.asVanilla(Objects.requireNonNull(swing, "swing")),
				new InteractionResult.ItemContext(
						isInteraction,
						Converter.asVanilla(Objects.requireNonNull(result, "result").orElse(null))
						)
				));
	}
}
