package net.hellheim.spongetools.common.factory;

import org.spongepowered.api.resource.pack.PackType;

import net.hellheim.spongetools.resourcepack.meta.PackFormat;

public final class PackFactory implements PackFormat.Factory {
	
	@Override
	public int lastPreMinorVersion(final PackType type) {
		return net.minecraft.server.packs.metadata.pack.PackFormat.lastPreMinorVersion(
				(net.minecraft.server.packs.PackType) (Object) type);
	}
}
