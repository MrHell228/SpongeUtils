package net.hellheim.spongetools.custom.item.model.enums;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

// TODO Maybe expose as registry?
public enum ChargeType implements StringRepresentable {
	
    NONE("none"),
    ARROW("arrow"),
    ROCKET("rocket");

    public static final Codec<ChargeType> CODEC = StringRepresentableCodec.fromValues(ChargeType::values);
    
    private final String name;

    private ChargeType(final String name) {
        this.name = name;
    }

    @Override
    public String serializationString() {
        return this.name;
    }
}
