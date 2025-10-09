package net.hellheim.spongetools.object;

import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.entity.attribute.AttributeOperation;

public record AttributeModifierTemplate(ResourceKey key, AttributeOperation operation, double amountPerLevel) implements ResourceKeyed {
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final Supplier<? extends AttributeOperation> operation, final double amountPerLevel
	) {
		return AttributeModifierTemplate.of(key, Objects.requireNonNull(operation, "operation").get(), amountPerLevel);
	}
	
	public static AttributeModifierTemplate of(
		final ResourceKey key, final AttributeOperation operation, final double amountPerLevel
	) {
		return new AttributeModifierTemplate(key, operation, amountPerLevel);
	}
	
	public AttributeModifierTemplate(
		final ResourceKey key, final AttributeOperation operation, final double amountPerLevel
	) {
		this.key = Objects.requireNonNull(key, "key");
		this.operation = Objects.requireNonNull(operation, "operation");
		this.amountPerLevel = amountPerLevel;
	}
	
	public AttributeModifier build(int amplifier) {
		return AttributeModifier.builder()
				.key(this.key)
				.operation(this.operation)
				.amount(this.amountPerLevel * (amplifier + 1))
				.build();
	}
}
