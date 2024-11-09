package net.hellheim.spongeutils.source.optional.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;

public interface OptionalDataHolderSource extends OptionalValueContainerSource, DataHolder {
	
	@Override
	Optional<? extends DataHolder> getAsDataHolder();
}
