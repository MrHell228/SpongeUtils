package net.hellheim.spongetools.resourcepack.meta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.proxy.solid.codec.CodecProxy;

public record Metadata(List<MetadataSection> sections) implements CodecProxy<Metadata>, MetadataLike {
	
	public static final Codec<Metadata> CODEC = MetadataSection.LIST_CODEC.xmap(Metadata::new, Metadata::sections);
	
	public Metadata(final List<MetadataSection> sections) {
		this.sections = List.copyOf(sections);
	}
	
	public static Metadata of(final MetadataSectionLike... sections) {
		return new Metadata(Arrays.stream(sections).map(MetadataSectionLike::asSection).toList());
	}
	
	public static Metadata of(final Collection<? extends MetadataSectionLike> sections) {
		return new Metadata(sections.stream().map(MetadataSectionLike::asSection).toList());
	}
	
	@Override
	public Codec<Metadata> codec() {
		return CODEC;
	}
	
	@Override
	public Metadata asMetadata() {
		return this;
	}
}
