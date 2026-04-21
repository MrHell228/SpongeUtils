package net.hellheim.spongetools.event;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.attribute.Attribute;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

public interface AttributeEvent extends LifecycleEvent {
	
	/**
	 * Event for modifying base, min and max values of the {@link AttributeType}.
	 */
	interface Modify extends AttributeEvent {
		
		default AttributeStep attribute(final Supplier<? extends AttributeType> type) {
			return this.attribute(Objects.requireNonNull(type, "type").get());
		}
		
		AttributeStep attribute(AttributeType type);
		
		interface AttributeStep {
			
			AttributeStep base(DoubleUnaryOperator modifier);
			
			AttributeStep min(DoubleUnaryOperator modifier);
			
			AttributeStep max(DoubleUnaryOperator modifier);
		}
	}
	
	/**
	 * Event for registering default {@link Attribute}s for {@link EntityType}s.
	 */
	interface RegisterToEntity {
		
		default EntityStep entity(final Supplier<? extends EntityType<?>> type) {
			return this.entity(Objects.requireNonNull(type, "type").get());
		}
		
		EntityStep entity(EntityType<?> type);
		
		interface EntityStep {
			
			default EntityStep register(final Supplier<? extends AttributeType> type, final double defaultValue) {
				return this.register(Objects.requireNonNull(type, "type").get(), defaultValue);
			}
			
			EntityStep register(AttributeType type, double defaultValue);
			
			default EntityStep register(final Supplier<? extends AttributeType> type) {
				return this.register(Objects.requireNonNull(type, "type").get());
			}
			
			EntityStep register(AttributeType type);
		}
	}
}
