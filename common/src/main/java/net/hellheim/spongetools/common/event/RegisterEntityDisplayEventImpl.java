package net.hellheim.spongetools.common.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.custom.type.entity.EntityDisplayType;
import net.hellheim.spongetools.event.RegisterEntityDisplayEvent;

public final class RegisterEntityDisplayEventImpl extends AbstractLifecycleEvent implements RegisterEntityDisplayEvent {
	
	private final Map<EntityType<?>, EntityDisplayRegistrationImpl> registrations = new HashMap<>();
	
	public RegisterEntityDisplayEventImpl(final Cause cause, final Game game) {
		super(cause, game);
	}
	
	@Override
	public EntityDisplayRegistration type(EntityType<?> type) {
		Objects.requireNonNull(type, "type");
		return this.registrations.computeIfAbsent(type, EntityDisplayRegistrationImpl::new);
	}
	
	public Map<EntityType<?>, List<EntityDisplayType>> display() {
		return this.registrations.values().stream()
				.collect(Collectors.toMap(EntityDisplayRegistrationImpl::type, EntityDisplayRegistrationImpl::get));
	}
	
	private static final class EntityDisplayRegistrationImpl implements EntityDisplayRegistration {
		
		private final EntityType<?> type;
		private final List<EntityDisplayType> display = new ArrayList<>();
		private final List<EntityDisplayType> displayView = Collections.unmodifiableList(this.display);
		
		public EntityDisplayRegistrationImpl(final EntityType<?> type) {
			this.type = type;
		}
		
		@Override
		public EntityType<?> type() {
			return this.type;
		}
		
		@Override
		public List<EntityDisplayType> get() {
			return this.displayView;
		}
		
		@Override
		public EntityDisplayRegistration set(final EntityDisplayType... types) {
			this.display.clear();
			return this.add(types);
		}
		
		@Override
		public EntityDisplayRegistration set(final Iterable<? extends EntityDisplayType> types) {
			this.display.clear();
			return this.add(types);
		}
		
		@Override
		public EntityDisplayRegistration add(final EntityDisplayType... types) {
			for (final EntityDisplayType type : Objects.requireNonNull(types, "types")) {
				this.display.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
		
		@Override
		public EntityDisplayRegistration add(final Iterable<? extends EntityDisplayType> types) {
			for (final EntityDisplayType type : Objects.requireNonNull(types, "types")) {
				this.display.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
	}
}
