package net.hellheim.spongetools.resourcepack.block;

import java.util.Optional;

import org.spongepowered.api.ResourceKey;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.SpongeCodecs;

public final class VariantProperties {
	
	public static final VariantProperty<ResourceKey> MODEL = property("model", SpongeCodecs.RESOURCE_KEY, Optional.empty());
	
	public static final VariantProperty<VariantRotation> X_ROT = property("x", VariantRotation.CODEC, Optional.of(VariantRotation.R0));
	
	public static final VariantProperty<VariantRotation> Y_ROT = property("y", VariantRotation.CODEC, Optional.of(VariantRotation.R0));
	
	public static final VariantProperty<Boolean> UV_LOCK = property("uvlock", Codec.BOOL, Optional.of(false));
	
	public static final VariantProperty<Integer> WEIGHT = property("weight", Codec.INT, Optional.of(0));
	
	private static <T> VariantProperty<T> property(
		final String name, final Codec<T> valueCodec, final Optional<T> defaultValue
	) {
		return new VariantProperty<>(name, valueCodec, defaultValue);
	}
	
	private VariantProperties() {
	}
}
