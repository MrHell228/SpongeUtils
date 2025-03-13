package net.hellheim.spongetools.codec;

import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.util.Ticks;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

public final class BuildableCodecs {
	
	public static final Codec<Enchantment> ENCHANTMENT = BuildableCodecs.register(Enchantment.class,
			instance -> instance.group(
					RegistryCodecs.ENCHANTMENT_TYPE.fieldOf("type").forGetter(Enchantment::type),
					Codec.INT.optionalFieldOf("level", 1).forGetter(Enchantment::level)
					).apply(instance, Enchantment::of)
			);
	
	public static final Codec<PotionEffect> POTION_EFFECT = BuildableCodecs.register(PotionEffect.class,
			instance -> instance.group(
					RegistryCodecs.POTION_EFFECT_TYPE.fieldOf("type").forGetter(PotionEffect::type),
					Codec.INT.optionalFieldOf("amplifier", 0).forGetter(PotionEffect::amplifier),
					SpongeCodecs.TICKS.optionalFieldOf("duration", Ticks.infinite()).forGetter(PotionEffect::duration),
					Codec.BOOL.optionalFieldOf("ambient").forGetter(p -> Optional.of(p.isAmbient())),
					Codec.BOOL.optionalFieldOf("particles").forGetter(p -> Optional.of(p.showsParticles())),
					Codec.BOOL.optionalFieldOf("icon").forGetter(p -> Optional.of(p.showsIcon()))
					).apply(instance, (type, amplifier, duration, ambient, particles, icon) -> {
						final PotionEffect.Builder builder = PotionEffect.builder();
						if (ambient.isPresent()) {
							builder.ambient(ambient.get());
						}
						if (particles.isPresent()) {
							builder.showParticles(particles.get());
						}
						if (icon.isPresent()) {
							builder.showIcon(icon.get());
						}
						return builder
								.potionType(type)
								.amplifier(amplifier)
								.duration(duration)
								.build();
					})
			);
	
	private static <T> Codec<T> register(final Class<T> type, final Function<Instance<T>, ? extends App<Mu<T>, T>> builder) {
		final Codec<T> codec = RecordCodecBuilder.create(builder);
		TypeCodecs.register(type, codec);
		return codec;
	}
	
	private BuildableCodecs() {
	}
}
