package net.hellheim.spongetools.resourcepack.meta;

public interface MetadataSectionLike extends MetadataLike {
	
	MetadataSection asSection();
	
	@Override
	default Metadata asMetadata() {
		return Metadata.of(this);
	}
}
