package net.hellheim.spongetools.custom.model.item;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RangeSelectEntry(float threshold, ItemDefinition model) {
	public static final Codec<RangeSelectEntry> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.FLOAT.fieldOf("threshold").forGetter(RangeSelectEntry::threshold),
					ItemDefinition.CODEC.fieldOf("model").forGetter(RangeSelectEntry::model)
					).apply(instance, RangeSelectEntry::new));
	
	public RangeSelectEntry(final float threshold, final ItemDefinition model) {
		this.threshold = threshold;
		this.model = Objects.requireNonNull(model, "model");
	}
}
