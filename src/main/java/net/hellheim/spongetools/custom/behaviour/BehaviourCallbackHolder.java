package net.hellheim.spongetools.custom.behaviour;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface BehaviourCallbackHolder<H> {
	
	Collection<TypedBehaviourCallback<H, ?, ?>> callbacks();
	
	/**
	 * Returns the current behaviour callback for the given type, if present.
	 * 
	 * @param <R> Behaviour return type
	 * @param <A> Behaviour arguments
	 * @param type The behaviour type
	 * @return The current behaviour callback for the given type, if present
	 */
	<R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(BehaviourType<? extends Behaviour<R, A>> type);
	
	<R, A extends BehaviourArgs> @Nullable BehaviourCallback<H, R, A> callbackOrNull(BehaviourType<? extends Behaviour<R, A>> type);
	
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
		<R, A extends BehaviourArgs> M offer(BehaviourType<? extends Behaviour<R, A>> type, BehaviourCallback<H, R, A> callback);
		
		default <R, A extends BehaviourArgs> M offer(
			final BehaviourType<? extends Behaviour<R, A>> type, final Function<A, R> behaviour
		) {
			Objects.requireNonNull(behaviour, "behaviour");
			return this.offer(type, (holder, origin, args) -> behaviour.apply(args));
		}
		
		default <A extends BehaviourArgs> M offer(
			final BehaviourType<? extends Behaviour<Void, A>> type, final Consumer<A> behaviour
		) {
			Objects.requireNonNull(behaviour, "behaviour");
			return this.offer(type, (holder, origin, args) -> {
				behaviour.accept(args);
				return null;
			});
		}
		
		default <R, A extends BehaviourArgs> M offer(final TypedBehaviourCallback<H, R, A> callback) {
			Objects.requireNonNull(callback, "callback");
			return this.offer(callback.type(), callback.callback());
		}
		
		default M offerFrom(final BehaviourCallbackHolder<H> holder) {
			Objects.requireNonNull(holder, "holder").callbacks().forEach(this::offer);
			return this.cast();
		}
		
		default <R, A extends BehaviourArgs> M offerFrom(
			final BehaviourType<? extends Behaviour<R, A>> type,
			final BehaviourCallbackHolder<H> holder
		) {
			Objects.requireNonNull(holder, "holder").callback(type).ifPresent(callback -> this.offer(type,  callback));
			return this.cast();
		}
		
		default M offerFrom(
			final BehaviourCallbackHolder<H> holder,
			final BehaviourType<?> firstType,
			final BehaviourType<?>... otherTypes
		) {
			this.offerFrom((BehaviourType<? extends Behaviour<Object, BehaviourArgs>>) firstType, holder);
			for (final BehaviourType<? extends Behaviour<?, ?>> type : Objects.requireNonNull(otherTypes, "otherTypes")) {
				this.offerFrom((BehaviourType<? extends Behaviour<Object, BehaviourArgs>>) type, holder);
			}
			return this.cast();
		}
		
		default M offerFrom(
			final BehaviourCallbackHolder<H> holder,
			final Iterable<? extends BehaviourType<?>> types
		) {
			for (final BehaviourType<? extends Behaviour<?, ?>> type : Objects.requireNonNull(types, "types")) {
				this.offerFrom((BehaviourType<? extends Behaviour<Object, BehaviourArgs>>) type, holder);
			}
			return this.cast();
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
		default <R, A extends BehaviourArgs> M set(
			final BehaviourType<? extends Behaviour<R, A>> type, final R value
		) {
			Objects.requireNonNull(value, "value");
			return this.offer(type, (holder, origin, args) -> value);
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
		default <R, A extends BehaviourArgs> M set(
			final BehaviourType<? extends Behaviour<R, A>> type, final Supplier<? extends R> valueSupplier
		) {
			Objects.requireNonNull(valueSupplier, "valueSupplier");
			return this.set(type, valueSupplier.get());
		}
		
		default <R, A extends BehaviourArgs> M transform(
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<@Nullable BehaviourCallback<H, R, A>, BehaviourCallback<H, R, A>> function
		) {
			Objects.requireNonNull(function, "function");
			return this.offer(type, function.apply(this.callback(type).orElse(null)));
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
			final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
		) {
			Objects.requireNonNull(callback, "callback");
			return this.transform(type, old -> old == null
					? callback
					: (holder, origin, args) ->
							callback.call(holder, (newArgs) -> old.call(holder, origin, newArgs), args));
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
		default <R, A extends BehaviourArgs> M prepend(
			final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
		) {
			Objects.requireNonNull(callback, "callback");
			return this.transform(type, old -> old == null
					? callback
					: (holder, origin, args) ->
							old.call(holder, (newArgs) -> callback.call(holder, origin, newArgs), args));
		}
		
		
		
		default <R, A extends BehaviourArgs> M appendAfter(
			final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.append(type, (holder, origin, args) -> {
				final R result = origin.call(args);
				action.accept(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M appendAfter(
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
		
		default <R, A extends BehaviourArgs> M appendBefore(
			final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.append(type, (holder, origin, args) -> {
				action.accept(args);
				final R result = origin.call(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M appendBefore(
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
		
		
		
		default <R, A extends BehaviourArgs> M prependAfter(
			final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.prepend(type, (holder, origin, args) -> {
				final R result = origin.call(args);
				action.accept(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M prependAfter(
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
		
		default <R, A extends BehaviourArgs> M prependBefore(
			final BehaviourType<? extends Behaviour<R, A>> type, final Consumer<A> action
		) {
			Objects.requireNonNull(action, "action");
			return this.prepend(type, (holder, origin, args) -> {
				action.accept(args);
				final R result = origin.call(args);
				return result;
			});
		}
		
		default <R, A extends BehaviourArgs> M prependBefore(
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
}
