package net.hellheim.spongetools.proxy.optional.entity.instance;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.living.Living;

import net.hellheim.spongetools.EntityUtil;
import net.hellheim.spongetools.proxy.optional.entity.OptionalEntityProxy;
import net.hellheim.spongetools.proxy.solid.PotionEffectProxy;
import net.hellheim.spongetools.proxy.solid.PotionEffectTypeProxy;

public interface OptionalLivingEntityProxy<T extends Living> extends OptionalEntityProxy<T> {
	
	default void heal(final double health) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.heal(entity, health));
	}
	
	default void damage(final double health) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.damage(entity, health));
	}
	
	
	
	default void setEffect(final PotionEffectProxy effect) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setEffect(entity, effect));
	}
	
	default void setEffect(final PotionEffect effect) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setEffect(entity, effect));
	}
	
	default void addEffect(final PotionEffectProxy effect) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect));
	}
	
	default void addEffect(final PotionEffect effect) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect));
	}
	
	default void addEffect(final PotionEffectProxy effect, final Predicate<PotionEffect> shouldReplace) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect, shouldReplace));
	}
	
	default void addEffect(final PotionEffect effect, final Predicate<PotionEffect> shouldReplace) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect, shouldReplace));
	}
	
	default void addEffect(final PotionEffectProxy effect, final Predicate<PotionEffect> shouldReplace, final BinaryOperator<PotionEffect> merger) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect, shouldReplace, merger));
	}
	
	default void addEffect(final PotionEffect effect, final Predicate<PotionEffect> shouldReplace, final BinaryOperator<PotionEffect> merger) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.addEffect(entity, effect, shouldReplace, merger));
	}
	
	
	
	default Optional<PotionEffect> effect(final PotionEffectTypeProxy type) {
		return this.effect(type.getAsPotionEffectType());
	}
	
	default Optional<PotionEffect> effect(final Supplier<PotionEffectType> type) {
		return this.effect(type.get());
	}
	
	default Optional<PotionEffect> effect(final PotionEffectType type) {
		return this.getAsEntity().map(entity -> EntityUtil.effect(entity, type)).orElse(Optional.empty());
	}
}
