package net.hellheim.spongetools.custom.behaviour;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class BehaviourCallbackHolderLogic<H> implements BehaviourCallbackHolder<H> {
	
	protected final Set<TypedBehaviourCallback<H, ?, ?>> callbackSet;
	protected final Map<BehaviourLayer, Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>>> callbackMap;
	
	protected BehaviourCallbackHolderLogic(
		final Set<TypedBehaviourCallback<H, ?, ?>> callbackSet,
		final Map<BehaviourLayer, Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>>> callbackMap
	) {
		this.callbackSet = callbackSet;
		this.callbackMap = callbackMap;
	}
	
	public static <H> Mutable<H> mutable() {
		return new Mutable<>();
	}
	
	public static <H> Immutable<H> immutable() {
		return new Immutable<>();
	}
	
	public static <H> Mutable<H> mutableOf(final Collection<? extends TypedBehaviourCallback<H, ?, ?>> callbacks) {
		return new Mutable<>(callbacks);
	}
	
	public static <H> Immutable<H> immutableOf(final Collection<? extends TypedBehaviourCallback<H, ?, ?>> callbacks) {
		return new Immutable<>(callbacks);
	}
	
	private static <H> Map<BehaviourLayer, Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>>> createMap(
		final Collection<? extends TypedBehaviourCallback<H, ?, ?>> callbacks
	) {
		final Map<BehaviourLayer, Map<BehaviourType<?>, TypedBehaviourCallback<H, ?, ?>>> map = new HashMap<>();
		callbacks.forEach(typed -> {
			map.computeIfAbsent(typed.layer(), $ -> new HashMap<>()).put(typed.type(), typed);
		});
		return map;
	}
	
	@Override
	public Set<TypedBehaviourCallback<H, ?, ?>> callbacks() {
		return this.callbackSet;
	}
	
	@Override
	public <R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(
		final BehaviourLayer layer, final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return Optional.ofNullable(this.callbackOrNull(layer, type));
	}
	
	public <R, A extends BehaviourArgs> @Nullable BehaviourCallback<H, R, A> callbackOrNull(
		final BehaviourLayer layer, final BehaviourType<? extends Behaviour<R, A>> type
	) {
		Objects.requireNonNull(layer, "layer");
		Objects.requireNonNull(type, "type");
		final var layerCallbacks = this.callbackMap.get(layer);
		if (layerCallbacks == null) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		final var callback = (TypedBehaviourCallback<H, R, A>) layerCallbacks.get(type);
		return callback == null ? null : callback.callback();
	}
	
	public abstract Mutable<H> asMutable();
	
	public abstract Mutable<H> asMutableCopy();
	
	public abstract Immutable<H> asImmutable();
	
	public static class Mutable<H>
			extends BehaviourCallbackHolderLogic<H>
			implements BehaviourCallbackHolder.Mutable<H, Mutable<H>> {
		
		private Mutable() {
			super(new HashSet<>(), new HashMap<>());
		}
		
		private Mutable(final Collection<? extends TypedBehaviourCallback<H, ?, ?>> callbacks) {
			super(new HashSet<>(callbacks), BehaviourCallbackHolderLogic.createMap(callbacks));
		}
		
		@Override
		public <R, A extends BehaviourArgs> BehaviourCallbackHolderLogic.Mutable<H> offer(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final BehaviourCallback<H, R, A> callback
		) {
			Objects.requireNonNull(layer, "layer");
			Objects.requireNonNull(type, "type");
			Objects.requireNonNull(callback, "callback");
			final TypedBehaviourCallback<H, R, A> typed = TypedBehaviourCallback.of(type, callback);
			this.callbackMap
					.computeIfAbsent(layer, $ -> new HashMap<>())
					.put(type, typed);
			this.callbackSet.add(typed);
			return this;
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutable() {
			return this;
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutableCopy() {
			return BehaviourCallbackHolderLogic.mutableOf(this.callbackSet);
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Immutable<H> asImmutable() {
			return BehaviourCallbackHolderLogic.immutableOf(this.callbackSet);
		}
		
		public void clear() {
			this.callbackSet.clear();
			this.callbackMap.clear();
		}
	}
	
	public static class Immutable<H>
			extends BehaviourCallbackHolderLogic<H> {
		
		private Immutable() {
			super(Set.of(), Map.of());
		}
		
		private Immutable(final Collection<? extends TypedBehaviourCallback<H, ?, ?>> callbacks) {
			super(Set.copyOf(callbacks), Map.copyOf(BehaviourCallbackHolderLogic.createMap(callbacks)));
		}
		
		@Override
		public BehaviourCallbackHolderLogic.Mutable<H> asMutable() {
			return BehaviourCallbackHolderLogic.mutableOf(this.callbackSet);
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
