package net.hellheim.spongetools.custom.metadata;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

public enum VillagerHat implements StringRepresentable, MetadataSectionLike {
	NONE("none"),
	PARTIAL("partial"),
	FULL("full");
	
	public static final Codec<VillagerHat> CODEC = StringRepresentableCodec.fromValues(VillagerHat::values);
	
	private final String name;
	
	private VillagerHat(String name) {
		this.name = name;
	}
	
	@Override
	public String serializationString() {
		return this.name;
	}
	
	@Override
	public MetadataSection.Villager asSection() {
		return MetadataSection.villager(this);
	}
}
