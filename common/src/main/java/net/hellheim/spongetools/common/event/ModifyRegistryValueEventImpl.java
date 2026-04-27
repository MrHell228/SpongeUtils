package net.hellheim.spongetools.common.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

import org.spongepowered.api.Game;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.potion.PotionType;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.bridge.RangedAttributeBridge;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.event.ModifyRegistryValueEvent;
import net.hellheim.spongetools.mixin.world.item.alchemy.PotionAccessor;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.hellheim.spongetools.util.EffectUtil;

public abstract class ModifyRegistryValueEventImpl
		extends AbstractLifecycleEvent
		implements ModifyRegistryValueEvent {
	
	public ModifyRegistryValueEventImpl(final Cause cause, final Game game) {
		super(cause, game);
	}
	
	public static final class ModifyAttributeImpl
			extends ModifyRegistryValueEventImpl
			implements ModifyRegistryValueEvent.ModifyAttribute {
	
		public ModifyAttributeImpl(final Cause cause, final Game game) {
			super(cause, game);
		}
		
		@Override
		public AttributeStep attribute(final AttributeType type) {
			return new AttributeStepImpl(Objects.requireNonNull(type, "type"));
		}
		
		private static final class AttributeStepImpl
				implements ModifyRegistryValueEvent.ModifyAttribute.AttributeStep {
			
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
	
	public static final class RegisterAttributeToEntityImpl
			extends ModifyRegistryValueEventImpl
			implements ModifyRegistryValueEvent.RegisterAttributeToEntity {
	
		public final Map<EntityType<?>, EntityStepImpl> entities = new HashMap<>();
		
		public RegisterAttributeToEntityImpl(final Cause cause, final Game game) {
			super(cause, game);
		}
		
		@Override
		public EntityStep entity(final EntityType<?> type) {
			return this.entities.computeIfAbsent(
					Objects.requireNonNull(type, "type"),
					$ -> new EntityStepImpl());
		}
		
		public static final class EntityStepImpl
				implements ModifyRegistryValueEvent.RegisterAttributeToEntity.EntityStep {
			
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
	
	public static final class ModifyPotionEffectImpl
			extends ModifyRegistryValueEventImpl
			implements ModifyRegistryValueEvent.ModifyPotionEffect {
		
		public final Map<PotionEffectType, EffectStepImpl> steps = new HashMap<>();
		
		public ModifyPotionEffectImpl(final Cause cause, final Game game) {
			super(cause, game);
		}
		
		@Override
		public EffectStep effect(final PotionEffectType effect) {
			return this.steps.computeIfAbsent(Objects.requireNonNull(effect, "effect"), EffectStepImpl::new);
		}
		
		public static final class EffectStepImpl
				implements ModifyRegistryValueEvent.ModifyPotionEffect.EffectStep {
			
			public final Map<AttributeType, AttributeModifierTemplate> attributes = new HashMap<>();
			public Optional<SoundType> sound;
			
			public EffectStepImpl(final PotionEffectType effect) {
				this.attributes.putAll(EffectUtil.attributeTemplates(effect));
				this.sound = ((MobEffectBridge) effect).spongetools$bridge$sound();
			}
			
			@Override
			public EffectStep attributes(final Consumer<Map<AttributeType, AttributeModifierTemplate>> modifier) {
				Objects.requireNonNull(modifier, "modifier").accept(this.attributes);
				return this;
			}
			
			@Override
			public EffectStep soundOnAdd(final UnaryOperator<Optional<SoundType>> modifier) {
				Objects.requireNonNull(modifier, "modifier");
				this.sound = Objects.requireNonNull(modifier.apply(this.sound), "soundOnAdd");
				return this;
			}
		}
	}
	
	public static final class ModifyPotionImpl
			extends ModifyRegistryValueEventImpl
			implements ModifyRegistryValueEvent.ModifyPotion {
		
		public final Map<PotionType, PotionStepImpl> steps = new HashMap<>();
		
		public ModifyPotionImpl(final Cause cause, final Game game) {
			super(cause, game);
		}
		
		@Override
		public PotionStep potion(final PotionType potion) {
			return this.steps.computeIfAbsent(Objects.requireNonNull(potion, "potion"), PotionStepImpl::new);
		}
		
		public static final class PotionStepImpl
				implements ModifyRegistryValueEvent.ModifyPotion.PotionStep {
			
			public final List<PotionEffect> effects;
			
			public PotionStepImpl(final PotionType potion) {
				this.effects = new ArrayList<>(((PotionAccessor) potion).accessor$effects().stream()
						.map(Converter::asSponge)
						.toList());
			}
			
			@Override
			public PotionStep effects(final Consumer<List<PotionEffect>> modifier) {
				Objects.requireNonNull(modifier, "modifier").accept(this.effects);
				return this;
			}
		}
	}
}
