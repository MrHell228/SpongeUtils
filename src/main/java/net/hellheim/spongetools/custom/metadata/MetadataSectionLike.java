package net.hellheim.spongetools.custom.metadata;

public interface MetadataSectionLike extends MetadataLike {
	
	MetadataSection asSection();
	
	@Override
	default Metadata asMetadata() {
		return Metadata.of(this);
	}
}
