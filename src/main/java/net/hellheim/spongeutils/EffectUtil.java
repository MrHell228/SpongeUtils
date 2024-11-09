package net.hellheim.spongeutils;

import java.util.function.Supplier;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.util.Ticks;

import net.hellheim.spongeutils.source.solid.PotionEffectSource;
import net.hellheim.spongeutils.source.solid.PotionEffectTypeSource;

public final class EffectUtil {
	
	public static PotionEffect of(final PotionEffectTypeSource type, final int durationInSeconds) {
		return of(type.getAsPotionEffectType(), 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final Supplier<PotionEffectType> type, final int durationInSeconds) {
		return of(type.get(), 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final PotionEffectType type, final int durationInSeconds) {
		return of(type, 0, TickUtil.ofSeconds(durationInSeconds));
	}
	
	public static PotionEffect of(final PotionEffectTypeSource type, final Ticks duration) {
		return of(type.getAsPotionEffectType(), 0, duration);
	}
	
	public static PotionEffect of(final Supplier<PotionEffectType> type, final Ticks duration) {
		return of(type.get(), 0, duration);
	}
	
	public static PotionEffect of(final PotionEffectType type, final Ticks duration) {
		return of(type, 0, duration);
	}
	
	
	public static PotionEffect of(
		final PotionEffectTypeSource type, final int amplifier, final int durationInSeconds
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
		final PotionEffectTypeSource type, final int amplifier, final Ticks duration
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
	
	public static PotionEffect of(final PotionEffectSource source) {
		return source.getAsPotionEffect();
	}
	
	public static PotionEffect of(PotionEffect effect) {
		return effect;
	}
	
	private EffectUtil() {
	}
}
