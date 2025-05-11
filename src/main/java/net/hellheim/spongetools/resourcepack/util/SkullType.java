package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

// TODO Expose as registry?
public enum SkullType implements StringRepresentable {
	SKELETON,
	WITHER_SKELETON,
	PLAYER,
	ZOMBIE,
	CREEPER,
	PIGLIN,
	DRAGON;
	
	public static final Codec<SkullType> CODEC = StringRepresentableCodec.fromValues(SkullType::values);
	
	@Override
	public String serializationString() {
		return this.name().toLowerCase();
	}
}
