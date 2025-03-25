package net.hellheim.spongetools.custom.model.item;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

// TODO Maybe expose as registry?
public enum ItemDisplayContext implements StringRepresentable {
	NONE("none"),
	THIRD_PERSON_LEFT_HAND("thirdperson_lefthand"),
	THIRD_PERSON_RIGHT_HAND("thirdperson_righthand"),
	FIRST_PERSON_LEFT_HAND("firstperson_lefthand"),
	FIRST_PERSON_RIGHT_HAND("firstperson_righthand"),
	HEAD("head"),
	GUI("gui"),
	GROUND("ground"),
	FIXED("fixed");
	
	public static final Codec<ItemDisplayContext> CODEC = StringRepresentableCodec.fromValues(ItemDisplayContext::values);
	
	private final String name;
	
	private ItemDisplayContext(String name) {
		this.name = name;
	}

	@Override
	public String serializationString() {
		return this.name;
	}
}
