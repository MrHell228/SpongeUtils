package net.hellheim.spongetools.custom.behaviour.util;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;

public interface InteractionResult {
	
	InteractionResult.Success SUCCESS_CLIENT = InteractionResult.factory().successClient();
	
	InteractionResult.Success SUCCESS_SERVER = InteractionResult.factory().successServer();
	
	InteractionResult.Success CONSUME = InteractionResult.factory().consume();
	
	InteractionResult FAIL = InteractionResult.factory().fail();
	
	InteractionResult PASS = InteractionResult.factory().pass();
	
	InteractionResult TRY_WITHOUT_ITEM = InteractionResult.factory().tryWithoutItem();
	
	static InteractionResult.Success success(final SwingType swing, final boolean isInteraction) {
		return InteractionResult.success(swing, isInteraction, Optional.empty());
	}
	
	static InteractionResult.Success success(final SwingType swing, final boolean isInteraction, final ItemStackLike result) {
		return InteractionResult.success(swing, isInteraction, Optional.of(result.asMutableCopy()));
	}
	
	static InteractionResult.Success success(
		final SwingType swing, final boolean isInteraction, final Optional<ItemStack> result
	) {
		return InteractionResult.factory().success(swing, isInteraction, result);
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	interface Success extends InteractionResult {
		
		SwingType swing();
		
		boolean isInteraction();
		
		Optional<ItemStack> result();
		
		default Success withSwing(final SwingType swing) {
			return this.swing() == swing
					? this
					: InteractionResult.success(swing, this.isInteraction(), this.result());
		}
		
		default Success withInteraction(final boolean isInteraction) {
			return this.isInteraction() == isInteraction
					? this
					: InteractionResult.success(this.swing(), isInteraction, this.result());
		}
		
		default Success withoutResult() {
			return this.result().isEmpty()
					? this
					: InteractionResult.success(this.swing(), this.isInteraction(), Optional.empty());
		}
		
		default Success withResult(final ItemStackLike result) {
			return InteractionResult.success(null, isInteraction(), Optional.of(result.asMutableCopy()));
		}
		
		default Success withResult(final Optional<ItemStack> result) {
			return result.isEmpty()
					? this.withoutResult()
					: InteractionResult.success(this.swing(), this.isInteraction(), result);
		}
	}
	
	interface Factory {
		
		InteractionResult.Success successClient();
		
		InteractionResult.Success successServer();
		
		InteractionResult.Success consume();
		
		InteractionResult fail();
		
		InteractionResult pass();
		
		InteractionResult tryWithoutItem();
		
		InteractionResult.Success success(SwingType swing, boolean isInteraction, Optional<ItemStack> result);
	}
}
