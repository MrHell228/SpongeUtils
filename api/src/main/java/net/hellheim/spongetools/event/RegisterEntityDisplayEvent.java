package net.hellheim.spongetools.event;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.type.entity.EntityDisplayType;

public interface RegisterEntityDisplayEvent extends LifecycleEvent {
	
	EntityDisplayRegistration type(EntityType<?> type);
	
	default EntityDisplayRegistration type(final Supplier<? extends EntityType<?>> type) {
		return this.type(Objects.requireNonNull(type, "type").get());
	}
	
	interface EntityDisplayRegistration {
		
		EntityType<?> type();
		
		List<EntityDisplayType> get();
		
		EntityDisplayRegistration set(EntityDisplayType... types);
		
		EntityDisplayRegistration set(Iterable<? extends EntityDisplayType> types);
		
		EntityDisplayRegistration add(EntityDisplayType... types);
		
		EntityDisplayRegistration add(Iterable<? extends EntityDisplayType> types);
	}
}
