package net.hellheim.spongetools.custom.item.model;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RangeSelectEntry(float threshold, ItemModelDefinition model) {
	public static final Codec<RangeSelectEntry> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.FLOAT.fieldOf("threshold").forGetter(RangeSelectEntry::threshold),
					ItemModelDefinition.CODEC.fieldOf("model").forGetter(RangeSelectEntry::model)
					).apply(instance, RangeSelectEntry::new));
	
	public RangeSelectEntry(final float threshold, final ItemModelDefinition model) {
		this.threshold = threshold;
		this.model = Objects.requireNonNull(model, "model");
	}
}
