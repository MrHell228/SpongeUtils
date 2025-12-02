package net.hellheim.spongetools.common.behaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourGroup;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.event.RegisterBehaviourDataEvent.BehaviourRegistration;

@SuppressWarnings("unchecked")
public final class BehaviourManagerImpl implements BehaviourManager {
	
	private final Map<BehaviourGroup<?>, BehaviourRegistrationImpl<?>> registrations = new HashMap<>();
	private final Map<BehaviourType<?>, BehaviourGroup<?>> typeToGroup = new HashMap<>();
	
	@Override
	public <H> boolean supports(final H holder, final BehaviourGroup<H> group) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(group, "group");
		return group.baseClass().isInstance(group.extractBehaviourBase(holder));
	}
	
	@Override
	public <H> boolean supports(final H holder, final BehaviourType<?> type) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(type, "type");
		final @Nullable BehaviourGroup<?> group = this.typeToGroup.get(type);
		return group != null && group.baseClass().isInstance(holder);
	}
	
	@Override
	public <H, B extends Behaviour<?, ?>> Optional<B> get(final H holder, final BehaviourType<B> type) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(type, "type");
		final @Nullable BehaviourGroup<?> group = this.typeToGroup.get(type);
		if (group == null || !group.baseClass().isInstance(holder)) {
			return Optional.empty();
		}
		
		final BehaviourRegistrationImpl<H> registration = (BehaviourRegistrationImpl<H>) this.registrations.get(group);
		final var provider = registration.behaviourProviders.get(type);
		return Optional.ofNullable((B) provider.apply(holder));
	}
	
	public <H> BehaviourRegistration<H> registration(final BehaviourGroup<H> group) {
		Objects.requireNonNull(group, "group");
		return (BehaviourRegistration<H>) this.registrations.computeIfAbsent(group, BehaviourRegistrationImpl::new);
	}
	
	private final class BehaviourRegistrationImpl<H> implements BehaviourRegistration<H> {
		
		private final BehaviourGroup<H> group;
		private final Map<BehaviourType<?>, Function<H, @Nullable Behaviour<?, ?>>> behaviourProviders;
		
		private BehaviourRegistrationImpl(final BehaviourGroup<H> group) {
			this.group = group;
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
						"Behaviour provider is already registered for type %s for behaviour group %s",
						type.key(), this.group
						));
			} else if (BehaviourManagerImpl.this.typeToGroup.containsKey(type)) {
				throw new IllegalArgumentException(String.format(
						"Behaviour type %s is already registered for group %s, tried to register for group %s",
						type.key(), BehaviourManagerImpl.this.typeToGroup.get(type), this.group));
			}
			
			this.behaviourProviders.put(type, (Function<H, @Nullable Behaviour<?, ?>>) behaviourProvider);
			BehaviourManagerImpl.this.typeToGroup.put(type, this.group);
			return this;
		}
	}
}
