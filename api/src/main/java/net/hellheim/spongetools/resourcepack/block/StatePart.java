package net.hellheim.spongetools.resourcepack.block;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.state.StateProperty;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record StatePart(Optional<StateCondition> condition, Variant variant) {
	
	public static final Codec<StatePart> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					StateCondition.CODEC.optionalFieldOf("when").forGetter(StatePart::condition),
					Variant.CODEC.fieldOf("apply").forGetter(StatePart::variant)
					).apply(instance, StatePart::new));
	
	public static final Codec<List<StatePart>> LIST_CODEC = StatePart.CODEC.listOf();
	
	public StatePart(final Optional<StateCondition> condition, final Variant variant) {
		this.condition = Objects.requireNonNull(condition, "condition")
				.filter(c -> c != StateCondition.alwaysTrue());
		this.variant = variant;
	}
	
	public static StatePart of(final Optional<StateCondition> condition, final Variant variant) {
		return StatePart.of(condition, variant);
	}
	
	public static StatePart of(final StateCondition condition, final Variant variant) {
		return StatePart.of(Optional.of(condition), variant);
	}
	
	public static StatePart of(final Variant variant) {
		return StatePart.of(Optional.empty(), variant);
	}
	
	public Set<StateProperty<?>> properties() {
		return this.condition.map(StateCondition::properties).orElse(Set.of());
	}
}
