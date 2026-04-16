package net.hellheim.spongetools.resourcepack.meta;

import org.spongepowered.api.resource.pack.PackType;

public interface MetadataSectionLike extends MetadataLike {
	
	PackType type();
	
	MetadataSection asSection();
	
	@Override
	default Metadata asMetadata() {
		return Metadata.of(this);
	}
	
	interface ClientSection extends MetadataSectionLike {
		
		@Override
		default PackType type() {
			return PackType.client();
		}
	}
	
	interface ServerSection extends MetadataSectionLike {
		
		@Override
		default PackType type() {
			return PackType.server();
		}
	}
}
