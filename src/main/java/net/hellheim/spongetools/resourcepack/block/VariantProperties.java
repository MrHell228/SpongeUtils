package net.hellheim.spongetools.resourcepack.block;

import java.util.Optional;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.rotation.Rotation;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.util.GeomUtil;

public final class VariantProperties {
	
	public static final VariantProperty<ResourceKey> MODEL = property("model", SpongeCodecs.RESOURCE_KEY, Optional.empty());
	
	public static final VariantProperty<Rotation> X_ROT = property("x", SpongeCodecs.ROTATION_BY_ANGLE, Optional.of(GeomUtil.ROT_0));
	
	public static final VariantProperty<Rotation> Y_ROT = property("y", SpongeCodecs.ROTATION_BY_ANGLE, Optional.of(GeomUtil.ROT_0));
	
	public static final VariantProperty<Boolean> UV_LOCK = property("uvlock", Codec.BOOL, Optional.of(false));
	
	public static final VariantProperty<Integer> WEIGHT = property("weight", Codec.INT, Optional.of(1));
	
	private static <T> VariantProperty<T> property(
		final String name, final Codec<T> valueCodec, final Optional<T> defaultValue
	) {
		return new VariantProperty<>(name, valueCodec, defaultValue);
	}
	
	private VariantProperties() {
	}
}
