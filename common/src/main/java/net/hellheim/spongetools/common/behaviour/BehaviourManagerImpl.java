package net.hellheim.spongetools.common.behaviour;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallback;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.TypedBehaviourCallback;

@SuppressWarnings("unchecked")
public final class BehaviourManagerImpl implements BehaviourManager {
	
	private final Map<Class<?>, BehaviourRegistrationImpl<?>> registrations = new HashMap<>();
	
	@Override
	public <H> boolean supportsBehaviour(
		final H holder, final BehaviourType<?> type
	) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(type, "type");
		for (final var entry : this.registrations.entrySet()) {
			if (entry.getKey().isInstance(holder)
					&& entry.getValue().behaviourProviders.containsKey(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public <H, B extends Behaviour<?, ?>> Optional<B> behaviour(
		final H holder, final BehaviourType<B> type
	) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(type, "type");
		for (final var entry : this.registrations.entrySet()) {
			if (!entry.getKey().isInstance(holder)) {
				continue;
			}
			
			final BehaviourRegistrationImpl<H> registration = (BehaviourRegistrationImpl<H>) entry.getValue();
			final var provider = registration.behaviourProviders.get(type);
			if (provider != null) {
				return Optional.ofNullable((B) provider.apply(holder));
			}
		}
		
		return Optional.empty();
	}
	
	@Override
	public <H, E> Collection<TypedBehaviourCallback<E, ?, ?>> callbacks(
		final H behaviourHolder, final Class<E> callbackHolder
	) {
		Objects.requireNonNull(behaviourHolder, "behaviourHolder");
		Objects.requireNonNull(callbackHolder, "callbackHolder");
		final BehaviourCallbackHolderLogic.Mutable<E> callbacks = BehaviourCallbackHolderLogic.mutable();
		for (final var behaviourEntry : this.registrations.entrySet()) {
			if (!behaviourEntry.getKey().isInstance(behaviourHolder)) {
				continue;
			}
			
			final BehaviourRegistrationImpl<H> behaviourRegistration = (BehaviourRegistrationImpl<H>) behaviourEntry.getValue();
			for (final var callbackEntry : behaviourRegistration.registrations.entrySet()) {
				if (!callbackHolder.isAssignableFrom(callbackEntry.getKey())) {
					continue;
				}
				
				final CallbackRegistrationImpl<H, E> callbackRegistration = (CallbackRegistrationImpl<H, E>) callbackEntry.getValue();
				for (final var providerEntry : callbackRegistration.callbackProviders.entrySet()) {
					final var callback = providerEntry.getValue().apply(behaviourHolder);
					if (callback != null) {
						this.appendCallback(callbacks, providerEntry.getKey(), callback);
					}
				}
			}
		}
		
		return callbacks.callbacks();
	}
	
	private <E, R, A extends BehaviourArgs> void appendCallback(
		final BehaviourCallbackHolderLogic.Mutable<E> callbacks,
		final BehaviourType<?> type,
		final BehaviourCallback<E, ?, ?> callback
	) {
		callbacks.offer((BehaviourType<? extends Behaviour<R, A>>) type, (BehaviourCallback<E, R, A>) callback);
	}
	
	@Override
	public <H, E, R, A extends BehaviourArgs> Optional<BehaviourCallback<E, R, A>> callback(
		final H behaviourHolder, final Class<E> callbackHolder,
		final BehaviourType<? extends Behaviour<R, A>> type
	) {
		Objects.requireNonNull(behaviourHolder, "behaviourHolder");
		Objects.requireNonNull(callbackHolder, "callbackHolder");
		Objects.requireNonNull(type, "type");
		for (final var behaviourEntry : this.registrations.entrySet()) {
			if (!behaviourEntry.getKey().isInstance(behaviourHolder)) {
				continue;
			}
			
			final BehaviourRegistrationImpl<H> behaviourRegistration = (BehaviourRegistrationImpl<H>) behaviourEntry.getValue();
			for (final var callbackEntry : behaviourRegistration.registrations.entrySet()) {
				if (!callbackHolder.isAssignableFrom(callbackEntry.getKey())) {
					continue;
				}
				
				final CallbackRegistrationImpl<H, ?> callbackRegistration = callbackEntry.getValue();
				final var provider = callbackRegistration.callbackProviders.get(type);
				if (provider != null) {
					return Optional.ofNullable((BehaviourCallback<E, R, A>) provider.apply(behaviourHolder));
				}
			}
		}
		
		return Optional.empty();
	}
	
	@Override
	public <H> BehaviourRegistration<H> behaviour(final Class<H> behaviourHolder) {
		Objects.requireNonNull(behaviourHolder, "behaviourHolder");
		return (BehaviourRegistration<H>) this.registrations.computeIfAbsent(behaviourHolder, BehaviourRegistrationImpl::new);
	}
	
	public static final class BehaviourRegistrationImpl<H> implements BehaviourManager.BehaviourRegistration<H> {
		
		private final Class<H> behaviourHolder;
		private final Map<BehaviourType<?>, Function<H, @Nullable Behaviour<?, ?>>> behaviourProviders;
		private final Map<Class<?>, CallbackRegistrationImpl<H, ?>> registrations;
		
		private BehaviourRegistrationImpl(final Class<H> behaviourHolder) {
			this.behaviourHolder = behaviourHolder;
			this.behaviourProviders = new IdentityHashMap<>();
			this.registrations = new HashMap<>();
		}
		
		@Override
		public <B extends Behaviour<?, ?>> BehaviourRegistration<H> register(
			final BehaviourType<B> type,
			final Function<H, @Nullable B> behaviourProvider
		) {
			Objects.requireNonNull(type, "type");
			Objects.requireNonNull(behaviourProvider, "behaviourProvider");
			if (this.behaviourProviders.containsKey(type)) {
				throw new IllegalArgumentException(String.format(
						"Behaviour provider is already registered for type %s for behaviour holder %s",
						type, this.behaviourHolder
						));
			}
			
			this.behaviourProviders.put(type, (Function<H, @Nullable Behaviour<?, ?>>) behaviourProvider);
			return this;
		}
		
		@Override
		public <E> CallbackRegistration<H, E> callbacks(final Class<E> callbackHolder) {
			Objects.requireNonNull(callbackHolder, "callbackHolder");
			return (CallbackRegistration<H, E>) this.registrations
					.computeIfAbsent(callbackHolder, $ -> new CallbackRegistrationImpl<>(this.behaviourHolder, callbackHolder));
		}
	}
	
	public static final class CallbackRegistrationImpl<H, E> implements BehaviourManager.CallbackRegistration<H, E> {
		
		private final Class<H> behaviourHolder;
		private final Class<E> callbackHolder;
		private final Map<BehaviourType<?>, Function<H, @Nullable BehaviourCallback<E, ?, ?>>> callbackProviders;
		
		private CallbackRegistrationImpl(final Class<H> behaviourHolder, final Class<E> callbackHolder) {
			this.behaviourHolder = behaviourHolder;
			this.callbackHolder = callbackHolder;
			this.callbackProviders = new IdentityHashMap<>();
		}
		
		@Override
		public <R, A extends BehaviourArgs> CallbackRegistration<H, E> register(
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<H, @Nullable BehaviourCallback<E, R, A>> callbackProvider
		) {
			Objects.requireNonNull(type, "type");
			Objects.requireNonNull(callbackProvider, "callbackProvider");
			if (this.callbackProviders.containsKey(type)) {
				throw new IllegalArgumentException(String.format(
						"Callback provider is already registered for type %s for behaviour holder %s & callback holder %s",
						type, this.behaviourHolder, this.callbackHolder
						));
			}
			
			this.callbackProviders.put(type, (Function<H, @Nullable BehaviourCallback<E, ?, ?>>) (Object) callbackProvider);
			return this;
		}
	}
}
