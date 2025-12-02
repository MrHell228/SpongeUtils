package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;

public record BehaviourType<B extends Behaviour<?, ?>>(ResourceKey key) implements ResourceKeyed {
	
	public BehaviourType(final ResourceKey key) {
		this.key = Objects.requireNonNull(key, "key");
	}
	
	public static <B extends Behaviour<?, ?>> BehaviourType<B> of(final ResourceKey key) {
		return new BehaviourType<>(key);
	}
}
