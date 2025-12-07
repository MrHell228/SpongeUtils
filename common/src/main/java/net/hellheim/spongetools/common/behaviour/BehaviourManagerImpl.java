package net.hellheim.spongetools.common.behaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;

@SuppressWarnings("unchecked")
public final class BehaviourManagerImpl implements BehaviourManager {
	
	private final Map<Class<?>, BehaviourRegistrationImpl<?>> registrations = new HashMap<>();
	
	@Override
	public <H> boolean supports(final H holder, final BehaviourType<?> type) {
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
	public <H, B extends Behaviour<?, ?>> Optional<B> get(final H holder, final BehaviourType<B> type) {
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
	public <H> BehaviourRegistration<H> registration(final Class<H> behaviourHolder) {
		Objects.requireNonNull(behaviourHolder, "behaviourHolder");
		return (BehaviourRegistration<H>) this.registrations.computeIfAbsent(behaviourHolder, BehaviourRegistrationImpl::new);
	}
	
	private final class BehaviourRegistrationImpl<H> implements BehaviourManager.BehaviourRegistration<H> {
		
		private final Class<H> behaviourHolder;
		private final Map<BehaviourType<?>, Function<H, @Nullable Behaviour<?, ?>>> behaviourProviders;
		
		private BehaviourRegistrationImpl(final Class<H> behaviourHolder) {
			this.behaviourHolder = behaviourHolder;
			this.behaviourProviders = new HashMap<>();
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
						type.key(), this.behaviourHolder
						));
			}
			
			this.behaviourProviders.put(type, (Function<H, @Nullable Behaviour<?, ?>>) behaviourProvider);
			return this;
		}
	}
}
