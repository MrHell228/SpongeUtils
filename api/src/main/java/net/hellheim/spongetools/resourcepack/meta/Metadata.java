package net.hellheim.spongetools.resourcepack.meta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.api.resource.pack.PackType;

import com.mojang.serialization.Codec;

public record Metadata(List<MetadataSection> sections) implements MetadataLike {
	
	public static final Codec<Metadata> CODEC_CLIENT = Metadata.codec(PackType::client);
	public static final Codec<Metadata> CODEC_SERVER = Metadata.codec(PackType::server);
	
	public Metadata(final List<MetadataSection> sections) {
		this.sections = List.copyOf(sections);
	}
	
	public static Metadata of(final MetadataSectionLike... sections) {
		return new Metadata(Arrays.stream(sections).map(MetadataSectionLike::asSection).toList());
	}
	
	public static Metadata of(final Collection<? extends MetadataSectionLike> sections) {
		return new Metadata(sections.stream().map(MetadataSectionLike::asSection).toList());
	}
	
	private static Codec<Metadata> codec(final Supplier<PackType> type) {
		return Codec.lazyInitialized(() -> (type.get() == PackType.client()
				? MetadataSection.LIST_CODEC_CLIENT
				: MetadataSection.LIST_CODEC_SERVER).xmap(
						sections -> new Metadata(sections),
						Metadata::sections));
	}
	
	@Override
	public Metadata asMetadata() {
		return this;
	}
}
