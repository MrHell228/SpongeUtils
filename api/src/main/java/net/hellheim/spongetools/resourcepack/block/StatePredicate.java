package net.hellheim.spongetools.resourcepack.block;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;

@FunctionalInterface
public interface StatePredicate {
	
	default boolean matchAnyState(final Iterable<? extends State<?>> states) {
		for (final State<?> state : Objects.requireNonNull(states, "states")) {
			if (this.test(state)) {
				return true;
			}
		}
		return false;
	}
	
	default boolean matchAnyState(final State<?>... states) {
		for (final State<?> state : Objects.requireNonNull(states, "states")) {
			if (this.test(state)) {
				return true;
			}
		}
		return false;
	}
	
	default boolean matchAnySelector(final Iterable<? extends StateSelector> selectors) {
		for (final StateSelector selector : Objects.requireNonNull(selectors, "selectors")) {
			if (this.test(selector)) {
				return true;
			}
		}
		return false;
	}
	
	default boolean matchAnySelector(final StateSelector... selectors) {
		for (final StateSelector selector : Objects.requireNonNull(selectors, "selectors")) {
			if (this.test(selector)) {
				return true;
			}
		}
		return false;
	}
	
	default boolean test(final State<?> state) {
		return this.test(Objects.requireNonNull(state, "state")::stateProperty);
	}
	
	default boolean test(final StateSelector selector) {
		return this.test(Objects.requireNonNull(selector, "selector")::get);
	}
	
	boolean test(Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup);
}
