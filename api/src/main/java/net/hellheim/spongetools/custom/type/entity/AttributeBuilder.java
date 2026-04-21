package net.hellheim.spongetools.custom.type.entity;

import java.util.Objects;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Builder;
import org.spongepowered.api.util.Tristate;

/**
 * Builder for creating custom {@link AttributeType}s. <br>
 * All attribute types created via this builder must be registered to {@link RegistryTypes#ATTRIBUTE_TYPE}.
 */
public interface AttributeBuilder extends Builder<AttributeType, AttributeBuilder> {
	
	/**
	 * Returns the new {@link AttributeBuilder}.
	 * 
	 * @return The new builder
	 */
	public static AttributeBuilder create() {
		return Sponge.game().builderProvider().provide(AttributeBuilder.class);
	}
	
	/**
	 * Sets the translation key from the given {@link ResourceKey}.
	 * 
	 * @param key The resource key
	 * @return This builder, for chaining
	 */
	default AttributeBuilder translationKey(final ResourceKey key) {
		Objects.requireNonNull(key, "key");
		return this.translationKey("attribute.name." + key.namespace() + "." + key.value());
	}
	
	/**
	 * Sets the translation key of the attribute.
	 * 
	 * @param key The translation key
	 * @return This builder, for chaining
	 */
	AttributeBuilder translationKey(String key);
	
	/**
	 * Sets the default value of the attribute.
	 * 
	 * @param defaultValue The default value
	 * @return This builder, for chaining
	 */
	AttributeBuilder defaultValue(double defaultValue);
	
	/**
	 * Sets the sentiment of the attribute. The given value is interpreted in the following way: <br>
	 * {@link Tristate#TRUE} -> Positive sentiment <br>
	 * {@link Tristate#UNDEFINED} -> Neutral sentiment <br>
	 * {@link Tristate#FALSE} -> Negative sentiment
	 * 
	 * @param sentiment The sentiment
	 * @return This builder, for chaining
	 */
	AttributeBuilder sentiment(Tristate sentiment);
	
	/**
	 * Sets the range of the possible attribute values.
	 * 
	 * @param min The min value
	 * @param max The max value
	 * @return This builder, for chaining
	 */
	AttributeBuilder ranged(double min, double max);
}
