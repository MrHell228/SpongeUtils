package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 
 * @param <H> Behaviour holder
 */
public interface ModifiableBehaviour<H> {
	
	BehaviourManager manager();
	
	H owner();
	
	/**
	 * Returns the current behaviour callback for the given type, if present.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @return The current behaviour callback for the given type, if present
	 */
	<R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> get(BehaviourType<? extends Behaviour<R, A>> type);
	
	/**
	 * Sets the given behaviour callback for the given type. <br>
	 * Overrides any previously registered callbacks.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @param callback The behaviour callback
	 * @return This modifiable behaviour, for chaining
	 */
	<R, A extends BehaviourArgs> ModifiableBehaviour<H> set(BehaviourType<? extends Behaviour<R, A>> type, BehaviourCallback<H, R, A> callback);
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> set(
		final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		return this.set(type, (holder, origin, args) -> behaviour.apply(args));
	}
	
	default <A extends BehaviourArgs> ModifiableBehaviour<H> set(
		final BehaviourType<? extends Behaviour<Void, A>> type, final Consumer<A> behaviour
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		return this.set(type, (holder, origin, args) -> {
			behaviour.accept(args);
			return null;
		});
	}
	
	/**
	 * Sets the given value as a result of the callback for the given type.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @param value The value to return
	 * @return This modifiable behaviour, for chaining
	 */
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> setValue(
		final BehaviourType<? extends Behaviour<R, A>> type, final R value
	) {
		Objects.requireNonNull(value, "value");
		return this.set(type, (holder, origin, args) -> value);
	}
	
	/**
	 * Sets the given value as a result of the callback for the given type.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @param value The value to return
	 * @return This modifiable behaviour, for chaining
	 */
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> setValue(
		final BehaviourType<? extends Behaviour<R, A>> type, final Supplier<? extends R> valueSupplier
	) {
		Objects.requireNonNull(valueSupplier, "valueSupplier");
		return this.setValue(type, valueSupplier.get());
	}
	
	default <R, A extends BehaviourArgs, B extends Behaviour<R, A>> ModifiableBehaviour<H> setBehaviour(
		final BehaviourType<B> type, final B behaviour
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		return this.set(type, (holder, origin, args) -> behaviour.call(args));
	}
	
	/**
	 * Wraps the given callback around the current callback. <br>
	 * Means that the current callback becomes the "original" for the given one. <br>
	 * If current callback is not present, simply sets the given callback.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @param callback The behaviour callback
	 * @return This modifiable behaviour, for chaining
	 */
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> append(
		final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.get(type)
				.<BehaviourCallback<H, R, A>>map(old -> (holder, origin, args) ->
						callback.call(holder, (newArgs) -> old.call(holder, origin, newArgs), args))
				.orElse(callback));
	}
	
	/**
	 * Wraps the current callback around the given callback. <br>
	 * Means that the given callback becomes the "original" for the current one. <br>
	 * If current callback is not present, simply sets the given callback.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @param callback The behaviour callback
	 * @return This modifiable behaviour, for chaining
	 */
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prepend(
		final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.get(type)
				.<BehaviourCallback<H, R, A>>map(old -> (holder, origin, args) ->
						old.call(holder, (newArgs) -> callback.call(holder, origin, newArgs), args))
				.orElse(callback));
	}
	
	
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> appendAfter(
		final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
	) {
		Objects.requireNonNull(action, "action");
		return this.append(type, (holder, origin, args) -> {
			final R result = origin.call(args);
			action.accept(args);
			return result;
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> appendAfter(
		final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour, final BinaryOperator<R> resultMerger
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		Objects.requireNonNull(resultMerger, "resultMerger");
		return this.append(type, (holder, origin, args) -> {
			final R oldResult = origin.call(args);
			final R newResult = behaviour.apply(args);
			return resultMerger.apply(oldResult, newResult);
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> appendBefore(
		final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
	) {
		Objects.requireNonNull(action, "action");
		return this.append(type, (holder, origin, args) -> {
			action.accept(args);
			final R result = origin.call(args);
			return result;
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> appendBefore(
		final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour, final BinaryOperator<R> resultMerger
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		Objects.requireNonNull(resultMerger, "resultMerger");
		return this.append(type, (holder, origin, args) -> {
			final R newResult = behaviour.apply(args);
			final R oldResult = origin.call(args);
			return resultMerger.apply(oldResult, newResult);
		});
	}
	
	
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prependAfter(
		final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
	) {
		Objects.requireNonNull(action, "action");
		return this.prepend(type, (holder, origin, args) -> {
			final R result = origin.call(args);
			action.accept(args);
			return result;
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prependAfter(
		final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour, final BinaryOperator<R> resultMerger
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		Objects.requireNonNull(resultMerger, "resultMerger");
		return this.prepend(type, (holder, origin, args) -> {
			final R oldResult = origin.call(args);
			final R newResult = behaviour.apply(args);
			return resultMerger.apply(oldResult, newResult);
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prependBefore(
		final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
	) {
		Objects.requireNonNull(action, "action");
		return this.prepend(type, (holder, origin, args) -> {
			action.accept(args);
			final R result = origin.call(args);
			return result;
		});
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prependBefore(
		final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour, final BinaryOperator<R> resultMerger
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		Objects.requireNonNull(resultMerger, "resultMerger");
		return this.prepend(type, (holder, origin, args) -> {
			final R newResult = behaviour.apply(args);
			final R oldResult = origin.call(args);
			return resultMerger.apply(oldResult, newResult);
		});
	}
}
