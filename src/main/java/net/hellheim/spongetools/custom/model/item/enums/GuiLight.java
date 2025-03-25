package net.hellheim.spongetools.custom.model.item.enums;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum GuiLight implements StringRepresentable {
	FRONT("front"),
	SIDE("side");
	
	public static final Codec<GuiLight> CODEC = StringRepresentableCodec.fromValues(GuiLight.values());
	public static final GuiLight DEFAULT = GuiLight.SIDE;
	
	private final String name;
	
	private GuiLight(String name) {
		this.name = name;
	}
	
	@Override
	public String serializationString() {
		return this.name;
	}
}
