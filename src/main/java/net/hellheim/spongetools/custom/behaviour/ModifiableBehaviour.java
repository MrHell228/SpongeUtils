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
	<A extends BehaviourArgs> Optional<BehaviourCallback.Action<H, A>> getAction(BehaviourType<? extends Behaviour.Action<A>> type);
	
	<R, A extends BehaviourArgs> Optional<BehaviourCallback.Product<H, R, A>> getProduct(BehaviourType<? extends Behaviour.Product<R, A>> type);
	
	<A extends BehaviourArgs> ModifiableBehaviour<H> set(BehaviourType<? extends Behaviour.Action<A>> type, BehaviourCallback.Action<H, A> callback);
	
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
	<R, A extends BehaviourArgs> ModifiableBehaviour<H> set(BehaviourType<? extends Behaviour.Product<R, A>> type, BehaviourCallback.Product<H, R, A> callback);
	
	default <A extends BehaviourArgs, B extends Behaviour.Action<A>> ModifiableBehaviour<H> set(
		final BehaviourType<? extends Behaviour.Action<A>> type, final Behaviour.Action<A> behaviour
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		return this.set(type, (holder, origin, args) -> behaviour.call(args));
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> set(
		final BehaviourType<? extends Behaviour.Product<R, A>> type, final Behaviour.Product<R, A> behaviour
	) {
		Objects.requireNonNull(behaviour, "behaviour");
		return this.set(type, (holder, origin, args) -> behaviour.call(args));
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
		final BehaviourType<? extends Behaviour.Product<R, A>> type, final R value
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
		final BehaviourType<? extends Behaviour.Product<R, A>> type, final Supplier<? extends R> valueSupplier
	) {
		Objects.requireNonNull(valueSupplier, "valueSupplier");
		return this.setValue(type, valueSupplier.get());
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
	default <A extends BehaviourArgs> ModifiableBehaviour<H> append(
		final BehaviourType<? extends Behaviour.Action<A>> type, final BehaviourCallback.Action<H, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.getAction(type)
				.<BehaviourCallback.Action<H, A>>map(old -> (holder, origin, args) ->
						callback.call(holder, (newArgs) -> old.call(holder, origin, newArgs), args))
				.orElse(callback));
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> append(
		final BehaviourType<? extends Behaviour.Product<R, A>> type, final BehaviourCallback.Product<H, R, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.getProduct(type)
				.<BehaviourCallback.Product<H, R, A>>map(old -> (holder, origin, args) ->
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
	default <A extends BehaviourArgs> ModifiableBehaviour<H> prepend(
		final BehaviourType<? extends Behaviour.Action<A>> type, final BehaviourCallback.Action<H, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.getAction(type)
				.<BehaviourCallback.Action<H, A>>map(old -> (holder, origin, args) ->
						old.call(holder, (newArgs) -> callback.call(holder, origin, newArgs), args))
				.orElse(callback));
	}
	
	default <R, A extends BehaviourArgs> ModifiableBehaviour<H> prepend(
		final BehaviourType<? extends Behaviour.Product<R, A>> type, final BehaviourCallback.Product<H, R, A> callback
	) {
		Objects.requireNonNull(callback, "callback");
		return this.set(type, this.getProduct(type)
				.<BehaviourCallback.Product<H, R, A>>map(old -> (holder, origin, args) ->
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
