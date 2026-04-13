package net.hellheim.spongetools.common.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleUnaryOperator;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.hellheim.spongetools.bridge.RangedAttributeBridge;
import net.hellheim.spongetools.event.AttributeEvent;

public abstract class AttributeEventImpl
		extends AbstractLifecycleEvent
		implements AttributeEvent {
	
	public AttributeEventImpl(final Cause cause, final Game game) {
		super(cause, game);
	}
	
	public static final class ModifyImpl
			extends AttributeEventImpl
			implements AttributeEvent.Modify {
		
		public ModifyImpl(final Cause cause, final Game game) {
			super(cause, game);
		}

		@Override
		public AttributeStep attribute(final AttributeType type) {
			return new AttributeStepImpl(Objects.requireNonNull(type, "type"));
		}
		
		private static final class AttributeStepImpl
				implements AttributeEvent.Modify.AttributeStep {
			
			private final AttributeType type;
			
			private AttributeStepImpl(final AttributeType type) {
				this.type = type;
			}
			
			@Override
			public AttributeStep base(final DoubleUnaryOperator modifier) {
				Objects.requireNonNull(modifier, "modifier");
				((AttributeBridge) this.type).spongetools$bridge$modifyBase(modifier);
				return this;
			}

			@Override
			public AttributeStep min(final DoubleUnaryOperator modifier) {
				Objects.requireNonNull(modifier, "modifier");
				if (this.type instanceof final RangedAttributeBridge bridge) {
					bridge.spongetools$bridge$modifyMin(modifier);
				}
				return this;
			}

			@Override
			public AttributeStep max(final DoubleUnaryOperator modifier) {
				Objects.requireNonNull(modifier, "modifier");
				if (this.type instanceof final RangedAttributeBridge bridge) {
					bridge.spongetools$bridge$modifyMax(modifier);
				}
				return this;
			}
		}
	}
	
	public static final class RegisterToEntityImpl
			extends AttributeEventImpl
			implements AttributeEvent.RegisterToEntity {
		
		public final Map<EntityType<?>, EntityStepImpl> entities = new HashMap<>();
		
		public RegisterToEntityImpl(final Cause cause, final Game game) {
			super(cause, game);
		}
		
		@Override
		public EntityStep entity(final EntityType<?> type) {
			return this.entities.computeIfAbsent(Objects.requireNonNull(type, "type"), $ -> new EntityStepImpl());
		}
		
		public static final class EntityStepImpl
				implements EntityStep {
			
			public final Map<AttributeType, OptionalDouble> values = new HashMap<>();
			
			private EntityStepImpl() {
			}
			
			@Override
			public EntityStep register(final AttributeType type, final double defaultValue) {
				this.values.put(type, OptionalDouble.of(defaultValue));
				return this;
			}
			
			@Override
			public EntityStep register(final AttributeType type) {
				this.values.put(type, OptionalDouble.empty());
				return this;
			}
		}
	}
}
