package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Callbacks are used to add additional behaviour on top of the default one.
 * 
 * @param <H> Behaviour holder
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
@FunctionalInterface
public interface BehaviourCallback<H, R, A extends BehaviourArgs> {
	
	R call(H holder, Behaviour<R, A> origin, A args);
	
	static <H, R, A extends BehaviourArgs> BehaviourCallback<H, R, A> of(final Function<A, R> behaviour) {
		Objects.requireNonNull(behaviour, "behaviour");
		return (holder, origin, args) -> behaviour.apply(args);
	}
	
	static <H, R, A extends BehaviourArgs> BehaviourCallback<H, R, A> result(final Supplier<R> result) {
		Objects.requireNonNull(result, "result");
		return (holder, origin, args) -> result.get();
	}
	
	static <H, R, A extends BehaviourArgs> BehaviourCallback<H, R, A> result(final R result) {
		Objects.requireNonNull(result, "result");
		return (holder, origin, args) -> result;
	}
	
	static <H, A extends BehaviourArgs> BehaviourCallback<H, Void, A> action(final BiConsumer<H, A> action) {
		Objects.requireNonNull(action, "action");
		return (holder, origin, args) -> {
			action.accept(holder, args);
			return null;
		};
	}
	
	static <H, A extends BehaviourArgs> BehaviourCallback<H, Void, A> action(final Consumer<A> action) {
		Objects.requireNonNull(action, "action");
		return (holder, origin, args) -> {
			action.accept(args);
			return null;
		};
	}
	
	static <H, A extends BehaviourArgs> BehaviourCallback<H, Void, A> action(final Runnable action) {
		Objects.requireNonNull(action, "action");
		return (holder, origin, args) -> {
			action.run();
			return null;
		};
	}
}
