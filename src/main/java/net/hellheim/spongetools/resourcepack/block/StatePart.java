package net.hellheim.spongetools.resourcepack.block;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.state.StateProperty;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record StatePart(Optional<StateCondition> condition, List<Variant> variants) {
	
	public static final Codec<StatePart> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					StateCondition.CODEC.optionalFieldOf("when").forGetter(StatePart::condition),
					Variant.LIST_CODEC.fieldOf("apply").forGetter(StatePart::variants)
					).apply(instance, StatePart::new));
	
	public static final Codec<List<StatePart>> LIST_CODEC = StatePart.CODEC.listOf();
	
	public StatePart(final Optional<StateCondition> condition, final List<Variant> variants) {
		this.condition = Objects.requireNonNull(condition, "condition")
				.filter(c -> c != StateCondition.alwaysTrue());
		this.variants = List.copyOf(Variant.validate(variants));
	}
	
	public static StatePart of(final Optional<StateCondition> condition, final List<Variant> variants) {
		return new StatePart(condition, variants);
	}
	
	public static StatePart of(final Optional<StateCondition> condition, final Variant... variants) {
		return StatePart.of(condition, List.of(variants));
	}
	
	public static StatePart of(final StateCondition condition, final List<Variant> variants) {
		return StatePart.of(Optional.of(condition), variants);
	}
	
	public static StatePart of(final StateCondition condition, final Variant... variants) {
		return StatePart.of(Optional.of(condition), variants);
	}
	
	public static StatePart of(final List<Variant> variants) {
		return StatePart.of(Optional.empty(), variants);
	}
	
	public static StatePart of(final Variant... variants) {
		return StatePart.of(Optional.empty(), variants);
	}
	
	public Set<StateProperty<?>> properties() {
		return this.condition.map(StateCondition::properties).orElse(Set.of());
	}
}
