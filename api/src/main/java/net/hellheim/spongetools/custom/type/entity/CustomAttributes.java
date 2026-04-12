package net.hellheim.spongetools.custom.type.entity;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Tristate;

/**
 * Utility class for creating new and modifying existing {@link AttributeType}s.
 */
public final class CustomAttributes {
	
	/**
	 * Returns the new {@link Builder}.
	 * 
	 * @return The new builder
	 */
	public static Builder builder() {
		return Sponge.game().builderProvider().provide(Builder.class);
	}
	
	/**
	 * Modifies the values of the given {@link AttributeType}.
	 * 
	 * @param type The attribute type
	 * @param baseModifier The base value modifier
	 * @param minModifier  The min value modifier
	 * @param maxModifier  The max value modifier
	 */
	public static void modify(
		final Supplier<? extends AttributeType> type,
		final DoubleUnaryOperator baseModifier,
		final DoubleUnaryOperator minModifier,
		final DoubleUnaryOperator maxModifier
	) {
		CustomAttributes.modify(Objects.requireNonNull(type).get(), baseModifier, minModifier, maxModifier);
	}
	
	/**
	 * Modifies the values of the given {@link AttributeType}.
	 * 
	 * @param type The attribute type
	 * @param baseModifier The base-value modifier
	 * @param minModifier  The min-value modifier
	 * @param maxModifier  The max-value modifier
	 */
	public static void modify(
		final AttributeType type,
		final DoubleUnaryOperator baseModifier,
		final DoubleUnaryOperator minModifier,
		final DoubleUnaryOperator maxModifier
	) {
		Sponge.game().factoryProvider().provide(Factory.class).modify(type, baseModifier, minModifier, maxModifier);
	}
	
	/**
	 * Builder for creating custom {@link AttributeType}s. <br>
	 * All attribute types created via this builder must be registered to {@link RegistryTypes#ATTRIBUTE_TYPE}.
	 */
	public interface Builder extends org.spongepowered.api.util.Builder<AttributeType, Builder> {
		
		/**
		 * Sets the translation key from the given {@link ResourceKey}.
		 * 
		 * @param key The resource key
		 * @return This builder, for chaining
		 */
		default Builder translationKey(final ResourceKey key) {
			Objects.requireNonNull(key, "key");
			return this.translationKey("attribute.name." + key.namespace() + "." + key.value());
		}
		
		/**
		 * Sets the translation key of the attribute.
		 * 
		 * @param key The translation key
		 * @return This builder, for chaining
		 */
		Builder translationKey(String key);
		
		/**
		 * Sets the default value of the attribute.
		 * 
		 * @param defaultValue The default value
		 * @return This builder, for chaining
		 */
		Builder defaultValue(double defaultValue);
		
		/**
		 * Sets the sentiment of the attribute. The given value is interpreted in the following way: <br>
		 * {@link Tristate#TRUE} -> Positive sentiment <br>
		 * {@link Tristate#UNDEFINED} -> Neutral sentiment <br>
		 * {@link Tristate#FALSE} -> Negative sentiment
		 * 
		 * @param sentiment The sentiment
		 * @return This builder, for chaining
		 */
		Builder sentiment(Tristate sentiment);
		
		/**
		 * Sets the range of the possible attribute values.
		 * 
		 * @param min The min value
		 * @param max The max value
		 * @return This builder, for chaining
		 */
		Builder ranged(double min, double max);
	}
	
	public interface Factory {
		
		void modify(
			AttributeType type,
			DoubleUnaryOperator baseModifier,
			DoubleUnaryOperator minModifier,
			DoubleUnaryOperator maxModifier
		);
	}
	
	private CustomAttributes() {
	}
}
