package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum CompassTarget implements StringRepresentable {
	NONE,
	LODESTONE,
	SPAWN,
	RECOVERY;
	
	public static final Codec<CompassTarget> CODEC = StringRepresentableCodec.fromValues(CompassTarget::values);
	
	@Override
	public String serializationString() {
		return this.name().toLowerCase();
	}
}
