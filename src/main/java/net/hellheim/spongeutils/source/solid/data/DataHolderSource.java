package net.hellheim.spongeutils.source.solid.data;

import org.spongepowered.api.data.DataHolder;

public interface DataHolderSource extends ValueContainerSource, DataHolder {
	
	@Override
	DataHolder getAsDataHolder();
}
