package net.hellheim.spongetools.custom.item.model;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

// TODO Expose as registry?
public enum SkullType implements StringRepresentable {
	SKELETON("skeleton"),
	WITHER_SKELETON("wither_skeleton"),
	PLAYER("player"),
	ZOMBIE("zombie"),
	CREEPER("creeper"),
	PIGLIN("piglin"),
	DRAGON("dragon");
	
	public static final Codec<SkullType> CODEC = StringRepresentableCodec.fromValues(SkullType::values);
	
	private final String name;
	
	private SkullType(String name) {
        this.name = name;
    }
	
	@Override
	public String serializationString() {
		return this.name;
	}
}
