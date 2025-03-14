package net.hellheim.spongetools.custom.item.model;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum TimeSource implements StringRepresentable {
    RANDOM("random"),
    DAYTIME("daytime"),
    MOON_PHASE("moon_phase");
	
    public static final Codec<TimeSource> CODEC = StringRepresentableCodec.fromValues(TimeSource::values);
    
    private final String name;
    
    private TimeSource(String name) {
        this.name = name;
    }
    
    @Override
    public String serializationString() {
        return this.name;
    }
}
