package net.hellheim.spongetools.resourcepack.block;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;

@FunctionalInterface
public interface StatePredicate {
	
	default boolean test(final State<?> state) {
		return this.test(Objects.requireNonNull(state, "state")::stateProperty);
	}
	
	default boolean test(final StateSelector selector) {
		return this.test(Objects.requireNonNull(selector, "selector")::get);
	}
	
	boolean test(Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup);
}
