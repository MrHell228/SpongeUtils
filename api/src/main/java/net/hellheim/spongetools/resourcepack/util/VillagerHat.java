package net.hellheim.spongetools.resourcepack.util;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;
import net.hellheim.spongetools.resourcepack.meta.MetadataSection;
import net.hellheim.spongetools.resourcepack.meta.MetadataSectionLike;

public enum VillagerHat implements StringRepresentable, MetadataSectionLike {
	
	NONE,
	
	PARTIAL,
	
	FULL;
	
	public static final Codec<VillagerHat> CODEC = StringRepresentableCodec.fromValues(VillagerHat::values);
	
	@Override
	public String serializationString() {
		return this.name().toLowerCase();
	}
	
	@Override
	public MetadataSection.Villager asSection() {
		return MetadataSection.villager(this);
	}
}
