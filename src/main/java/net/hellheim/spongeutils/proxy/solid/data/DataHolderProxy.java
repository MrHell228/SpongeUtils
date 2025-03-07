package net.hellheim.spongeutils.proxy.solid.data;

import org.spongepowered.api.data.DataHolder;

public interface DataHolderProxy extends ValueContainerProxy, DataHolder {
	
	@Override
	DataHolder getAsDataHolder();
}
