package net.hellheim.spongetools.custom.model.item.enums;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum CompassTarget implements StringRepresentable {
	NONE("none"),
	LODESTONE("lodestone"),
	SPAWN("spawn"),
	RECOVERY("recovery");
	
	public static final Codec<CompassTarget> CODEC = StringRepresentableCodec.fromValues(CompassTarget::values);
	
	private final String name;
	
	private CompassTarget(final String name) {
		this.name = name;
	}
	
	@Override
	public String serializationString() {
		return this.name;
	}
}
