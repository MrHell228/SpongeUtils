package net.hellheim.spongetools.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.util.Ticks;

import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.hellheim.spongetools.proxy.solid.PotionEffectProxy;
import net.hellheim.spongetools.proxy.solid.PotionEffectTypeProxy;

public final class EffectUtil {
	
	public static PotionEffect of(final PotionEffectTypeProxy type, final int durationInSeconds) {
		return of(type.getAsPotionEffectType(), 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final Supplier<PotionEffectType> type, final int durationInSeconds) {
		return of(type.get(), 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final PotionEffectType type, final int durationInSeconds) {
		return of(type, 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final PotionEffectTypeProxy type, final Ticks duration) {
		return of(type.getAsPotionEffectType(), 0, duration);
	}
	
	public static PotionEffect of(final Supplier<PotionEffectType> type, final Ticks duration) {
		return of(type.get(), 0, duration);
	}
	
	public static PotionEffect of(final PotionEffectType type, final Ticks duration) {
		return of(type, 0, duration);
	}
	
	
	public static PotionEffect of(
		final PotionEffectTypeProxy type, final int amplifier, final int durationInSeconds
	) {
		return of(type.getAsPotionEffectType(), amplifier, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(
		final Supplier<PotionEffectType> type, final int amplifier, final int durationInSeconds
	) {
		return of(type.get(), amplifier, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(
		final PotionEffectType type, final int amplifier, final int durationInSeconds
	) {
		return of(type, amplifier, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(
		final PotionEffectTypeProxy type, final int amplifier, final Ticks duration
	) {
		return of(type.getAsPotionEffectType(), amplifier, duration);
	}
	
	public static PotionEffect of(
		final Supplier<PotionEffectType> type, final int amplifier, final Ticks duration
	) {
		return of(type.get(), amplifier, duration);
	}
	
	public static PotionEffect of(
		final PotionEffectType type, final int amplifier, final Ticks duration
	) {
		return PotionEffect.of(type, amplifier, duration);
	}
	
	public static PotionEffect of(final PotionEffectProxy source) {
		return source.getAsPotionEffect();
	}
	
	public static PotionEffect of(PotionEffect effect) {
		return effect;
	}
	
	
	public static Map<AttributeType, AttributeModifier> attributes(final PotionEffectProxy effect) {
		return EffectUtil.attributes(effect.getAsPotionEffect());
	}
	
	public static Map<AttributeType, AttributeModifier> attributes(final Supplier<PotionEffect> effect) {
		return EffectUtil.attributes(effect.get());
	}
	
	public static Map<AttributeType, AttributeModifier> attributes(final PotionEffect effect) {
		return EffectUtil.attributes(effect.type(), effect.amplifier());
	}
	
	public static Map<AttributeType, AttributeModifier> attributes(final PotionEffectTypeProxy type, final int amplifier) {
		return EffectUtil.attributes(type.getAsPotionEffectType(), amplifier);
	}
	
	public static Map<AttributeType, AttributeModifier> attributes(final Supplier<PotionEffectType> type, final int amplifier) {
		return EffectUtil.attributes(type.get(), amplifier);
	}
	
	public static Map<AttributeType, AttributeModifier> attributes(final PotionEffectType type, final int amplifier) {
		final Map<AttributeType, AttributeModifier> modifiers = new HashMap<>();
		EffectUtil.attributeTemplates(type).forEach(
				(attribute, template) -> modifiers.put(attribute, template.build(amplifier)));
		return modifiers;
	}
	
	public static Map<AttributeType, AttributeModifierTemplate> attributeTemplates(final PotionEffectTypeProxy type) {
		return EffectUtil.attributeTemplates(type.getAsPotionEffectType());
	}
	
	public static Map<AttributeType, AttributeModifierTemplate> attributeTemplates(final Supplier<PotionEffectType> type) {
		return EffectUtil.attributeTemplates(type.get());
	}
	
	public static Map<AttributeType, AttributeModifierTemplate> attributeTemplates(final PotionEffectType type) {
		return EffectUtil.factory().modifierTemplates(type);
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	public interface Factory {
		
		Map<AttributeType, AttributeModifierTemplate> modifierTemplates(PotionEffectType type);
	}
	
	private EffectUtil() {
	}
}
