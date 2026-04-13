package net.hellheim.spongetools.common.builder;

import java.util.Objects;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.util.Tristate;

import net.hellheim.spongetools.custom.type.entity.CustomAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public final class CustomAttributesBuilder implements CustomAttributes.Builder {
	
	private @Nullable String tranlationKey;
	private @Nullable Double defaultValue;
	private Sentiment sentiment;
	private double min;
	private double max;
	
	public CustomAttributesBuilder() {
		this.reset();
	}
	
	@Override
	public CustomAttributes.Builder translationKey(final String key) {
		this.tranlationKey = Objects.requireNonNull(key, "key");
		return this;
	}
	
	@Override
	public CustomAttributes.Builder defaultValue(final double defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	@Override
	public CustomAttributes.Builder sentiment(final Tristate sentiment) {
		this.sentiment = switch (Objects.requireNonNull(sentiment, "sentiment")) {
			case TRUE -> Sentiment.POSITIVE;
			case UNDEFINED -> Sentiment.NEUTRAL;
			case FALSE -> Sentiment.NEGATIVE;
		};
		return this;
	}
	
	@Override
	public CustomAttributes.Builder ranged(final double min, final double max) {
		this.min = min;
		this.max = max;
		return this;
	}
	
	@Override
	public CustomAttributes.Builder reset() {
		this.tranlationKey = null;
		this.defaultValue = null;
		this.sentiment = Sentiment.NEUTRAL;
		this.min = -Double.MAX_VALUE;
		this.max = +Double.MAX_VALUE;
		return this;
	}
	
	@Override
	public AttributeType build() {
		if (this.tranlationKey == null) {
			throw new IllegalStateException("Translation key must be set");
		} else if (this.defaultValue == null) {
			throw new IllegalStateException("Default value must be set");
		}
		
		final Attribute attribute = new RangedAttribute(this.tranlationKey, this.defaultValue, this.min, this.max)
				.setSyncable(false)
				.setSentiment(this.sentiment);
        ((AttributeBridge) attribute).spongetools$bridge$setCustom();
        return (AttributeType) attribute;
	}
}
