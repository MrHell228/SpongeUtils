package net.hellheim.spongetools.object;

import java.util.Objects;
import java.util.function.IntToDoubleFunction;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.entity.attribute.AttributeOperation;

public interface AttributeModifierTemplate extends ResourceKeyed {
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final Supplier<? extends AttributeOperation> operation, final double amountPerLevel
	) {
		return AttributeModifierTemplate.of(key, Objects.requireNonNull(operation, "operation").get(), amountPerLevel);
	}
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final AttributeOperation operation, final double amountPerLevel
	) {
		return AttributeModifierTemplate.factory().of(key, operation, amountPerLevel);
	}
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final Supplier<? extends AttributeOperation> operation, final IntToDoubleFunction curve
	) {
		return AttributeModifierTemplate.of(key, Objects.requireNonNull(operation, "operation").get(), curve);
	}
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final AttributeOperation operation, final IntToDoubleFunction curve
	) {
		return AttributeModifierTemplate.factory().of(key, operation, curve);
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	AttributeOperation operation();
	
	IntToDoubleFunction curve();
	
	AttributeModifier build(int amplifier);
	
	interface Factory {
		
		AttributeModifierTemplate of(ResourceKey key, AttributeOperation operation, double amountPerLevel);
		
		AttributeModifierTemplate of(ResourceKey key, AttributeOperation operation, IntToDoubleFunction curve);
	}
}
