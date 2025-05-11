package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum GuiLight implements StringRepresentable {
	FRONT,
	SIDE;
	
	public static final Codec<GuiLight> CODEC = StringRepresentableCodec.fromValues(GuiLight.values());
	public static final GuiLight DEFAULT = GuiLight.SIDE;
	
	@Override
	public String serializationString() {
		return this.name().toLowerCase();
	}
}
