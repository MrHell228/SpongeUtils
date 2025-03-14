package net.hellheim.spongetools.custom.item.model;

import java.util.List;
import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.ExtraCodecs;

public record SelectSwitchCase<T>(List<T> values, ItemModelDefinition model) {
	public static <T> Codec<SelectSwitchCase<T>> codec(Codec<T> codec) {
		return RecordCodecBuilder.create(instance -> instance.group(
				ExtraCodecs.nonEmptyCollection(ExtraCodecs.compactList(codec)).fieldOf("when").forGetter(SelectSwitchCase::values),
				ItemModelDefinition.CODEC.fieldOf("model").forGetter(SelectSwitchCase::model)
				).apply(instance, SelectSwitchCase::new));
	}
	
	public SelectSwitchCase(final List<T> values, final ItemModelDefinition model) {
		this.values = Objects.requireNonNull(values, "values");
		this.model = Objects.requireNonNull(model, "model");
	}
}
