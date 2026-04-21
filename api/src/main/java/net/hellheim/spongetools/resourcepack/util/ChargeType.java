package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

// TODO Maybe expose as registry?
public enum ChargeType implements StringRepresentable {
	
    NONE,
    ARROW,
    ROCKET;
	
    public static final Codec<ChargeType> CODEC = StringRepresentableCodec.fromValues(ChargeType::values);
    
    @Override
    public String serializationString() {
        return this.name().toLowerCase();
    }
}
