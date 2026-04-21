package net.hellheim.spongetools.proxy.optional.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;

public interface OptionalDataHolderProxy extends OptionalValueContainerProxy, DataHolder {
	
	@Override
	Optional<? extends DataHolder> getAsDataHolder();
}
