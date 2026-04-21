package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum TimeSource implements StringRepresentable {
    RANDOM,
    DAYTIME,
    MOON_PHASE;
	
    public static final Codec<TimeSource> CODEC = StringRepresentableCodec.fromValues(TimeSource::values);
    
    @Override
    public String serializationString() {
        return this.name().toLowerCase();
    }
}
