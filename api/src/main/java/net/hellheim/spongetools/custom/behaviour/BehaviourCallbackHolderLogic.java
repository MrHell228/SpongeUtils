package net.hellheim.spongetools.custom.behaviour;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class BehaviourCallbackHolderLogic<H> implements BehaviourCallbackHolder<H> {
	
	protected final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks;
	
	protected BehaviourCallbackHolderLogic(
		final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks
	) {
		this.callbacks = callbacks;
	}
	
	public static <H> Mutable<H> mutable() {
		return new Mutable<>();
	}
	
	public static <H> Immutable<H> immutable() {
		return new Immutable<>();
	}
	
	public static <H> Mutable<H> mutableOf(
		final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks
	) {
		return new Mutable<>(callbacks);
	}
	
	public static <H> Immutable<H> immutableOf(
		final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks
	) {
		return new Immutable<>(callbacks);
	}
	
	@Override
	public Collection<TypedBehaviourCallback<H, ?, ?>> callbacks() {
		return this.callbacks.values();
	}
	
	@Override
	public <R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(
		final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return Optional.ofNullable(this.callbackOrNull(type));
	}
	
	@Override
	public <R, A extends BehaviourArgs> @Nullable BehaviourCallback<H, R, A> callbackOrNull(
		final BehaviourType<? extends Behaviour<R, A>> type) {
		Objects.requireNonNull(type, "type");
		@SuppressWarnings("unchecked")
		final var callback = (TypedBehaviourCallback<H, R, A>) this.callbacks.get(type);
		return callback == null ? null : callback.callback();
	}
	
	public abstract Mutable<H> asMutable();
	
	public abstract Mutable<H> asMutableCopy();
	
	public abstract Immutable<H> asImmutable();
	
	public static class Mutable<H>
			extends BehaviourCallbackHolderLogic<H>
			implements BehaviourCallbackHolder.Mutable<H, Mutable<H>> {
		
		private Mutable() {
			super(new HashMap<>());
		}
		
		private Mutable(final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks) {
			super(new HashMap<>(callbacks));
		}
		
		@Override
		public <R, A extends BehaviourArgs> BehaviourCallbackHolderLogic.Mutable<H> offer(
			final BehaviourType<? extends Behaviour<R, A>> type,
			final BehaviourCallback<H, R, A> callback
		) {
			Objects.requireNonNull(type, "type");
			Objects.requireNonNull(callback, "callback");
			this.callbacks.put(type, TypedBehaviourCallback.of(type, callback));
			return this;
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutable() {
			return this;
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutableCopy() {
			return BehaviourCallbackHolderLogic.mutableOf(this.callbacks);
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Immutable<H> asImmutable() {
			return BehaviourCallbackHolderLogic.immutableOf(this.callbacks);
		}
	}
	
	public static class Immutable<H>
			extends BehaviourCallbackHolderLogic<H> {
		
		private Immutable() {
			super(Map.of());
		}
		
		private Immutable(final Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>> callbacks) {
			super(Map.copyOf(callbacks));
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutable() {
			return BehaviourCallbackHolderLogic.mutableOf(this.callbacks);
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutableCopy() {
			return this.asMutable();
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Immutable<H> asImmutable() {
			return this;
		}
	}
}
