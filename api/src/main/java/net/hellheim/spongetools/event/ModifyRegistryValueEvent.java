package net.hellheim.spongetools.event;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.attribute.Attribute;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;
import org.spongepowered.api.item.potion.PotionType;

import net.hellheim.spongetools.object.AttributeModifierTemplate;

/**
 * Contains events that allow modifying values of some registries.
 */
public interface ModifyRegistryValueEvent extends LifecycleEvent {
	
	/**
	 * Event for modifying base, min and max values of the {@link AttributeType}.
	 */
	interface ModifyAttribute extends ModifyRegistryValueEvent {
		
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
	interface RegisterAttributeToEntity extends ModifyRegistryValueEvent {
		
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
	
	interface ModifyPotionEffect extends ModifyRegistryValueEvent {
		
		default EffectStep effect(final Supplier<? extends PotionEffectType> effect) {
			return this.effect(Objects.requireNonNull(effect, "effect").get());
		}
		
		EffectStep effect(PotionEffectType effect);
		
		interface EffectStep {
			
			EffectStep attributes(Consumer<Map<AttributeType, AttributeModifierTemplate>> modifier);
			
			EffectStep soundOnAdd(UnaryOperator<Optional<SoundType>> modifier);
		}
	}
	
	interface ModifyPotion extends ModifyRegistryValueEvent {
		
		default PotionStep potion(final Supplier<? extends PotionType> potion) {
			return this.potion(Objects.requireNonNull(potion, "potion").get());
		}
		
		PotionStep potion(PotionType potion);
		
		interface PotionStep {
			
			PotionStep effects(Consumer<List<PotionEffect>> modifier);
		}
	}
}
