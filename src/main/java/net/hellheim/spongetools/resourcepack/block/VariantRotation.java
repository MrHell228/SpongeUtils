package net.hellheim.spongetools.resourcepack.block;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum VariantRotation implements StringRepresentable {
	
	R0(0),
	R90(90),
	R180(180),
	R270(270);
	
	public static final Codec<VariantRotation> CODEC = StringRepresentableCodec.fromValues(VariantRotation::values);
	
	private final int value;
	
	private VariantRotation(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
	@Override
	public String serializationString() {
		return Integer.toString(this.value);
	}
}
