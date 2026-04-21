package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface BehaviourCallbackHolder<H> {
	
	Set<TypedBehaviourCallback<H, ?, ?>> callbacks();
	
	/**
	 * Returns the current behaviour callback for the given type, if present.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @return The current behaviour callback for the given type, if present
	 */
	<R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(
			BehaviourLayer layer, BehaviourType<? extends Behaviour<R, A>> type);
	
	@SuppressWarnings("unchecked")
	interface Mutable<H, M extends Mutable<H, M>> extends BehaviourCallbackHolder<H> {
		
		private M cast() {
			return (M) this;
		}
		
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
		<R, A extends BehaviourArgs> M offer(
			BehaviourLayer layer, BehaviourType<? extends Behaviour<R, A>> type, BehaviourCallback<H, R, A> callback
		);
		
		
		default <R, A extends BehaviourArgs> M offer(
			final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
		) {
			return this.offer(BehaviourLayer.TOP, type, callback);
		}
		
		default <R, A extends BehaviourArgs> M offer(final TypedBehaviourCallback<H, R, A> callback) {
			Objects.requireNonNull(callback, "callback");
			return this.offer(callback.layer(), callback.type(), callback.callback());
		}
		
		default M offerAll(final Iterable<TypedBehaviourCallback<H, ?, ?>> callbacks) {
			Objects.requireNonNull(callbacks, "callbacks").forEach(this::offer);
			return this.cast();
		}
		
		default M offerFrom(final BehaviourCallbackHolder<H> holder) {
			return this.offerAll(Objects.requireNonNull(holder, "holder").callbacks());
		}
		
		default <R, A extends BehaviourArgs> M transform(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<@Nullable BehaviourCallback<H, R, A>, BehaviourCallback<H, R, A>> function
		) {
			Objects.requireNonNull(function, "function");
			return this.offer(type, function.apply(this.callback(layer, type).orElse(null)));
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
		default <R, A extends BehaviourArgs> M append(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final BehaviourCallback<H, R, A> callback
		) {
			Objects.requireNonNull(callback, "callback");
			return this.transform(layer, type, old -> old == null
					? callback
					: (holder, origin, args) ->
							callback.call(holder, (newArgs) -> old.call(holder, origin, newArgs), args));
		}
		
		
		
		default <R, A extends BehaviourArgs> M appendAfter(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.append(layer, type, (holder, origin, args) -> {
				final R result = origin.call(args);
				action.accept(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M appendAfter(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<A, R> behaviour,
			final BinaryOperator<R> resultMerger
		) {
			Objects.requireNonNull(behaviour, "behaviour");
			Objects.requireNonNull(resultMerger, "resultMerger");
			return this.append(layer, type, (holder, origin, args) -> {
				final R oldResult = origin.call(args);
				final R newResult = behaviour.apply(args);
				return resultMerger.apply(oldResult, newResult);
			});
		}
		
		default <R, A extends BehaviourArgs> M appendBefore(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.append(layer, type, (holder, origin, args) -> {
				action.accept(args);
				final R result = origin.call(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M appendBefore(
		final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<A, R> behaviour,
			final BinaryOperator<R> resultMerger
		) {
			Objects.requireNonNull(behaviour, "behaviour");
			Objects.requireNonNull(resultMerger, "resultMerger");
			return this.append(layer, type, (holder, origin, args) -> {
				final R newResult = behaviour.apply(args);
				final R oldResult = origin.call(args);
				return resultMerger.apply(oldResult, newResult);
			});
		}
	}
}
