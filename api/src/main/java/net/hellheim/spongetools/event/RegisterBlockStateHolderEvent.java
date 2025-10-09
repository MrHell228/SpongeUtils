package net.hellheim.spongetools.event;

import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.BlockStateHolder;
import net.hellheim.spongetools.custom.type.block.BlockStateProvider;

public interface RegisterBlockStateHolderEvent extends LifecycleEvent {
	
	BlockStateDispatcher dispatcher();
	
	default void register(final BlockStateHolder holder, final BlockStateProvider provider) {
		this.dispatcher().submit(holder, provider);
	}
}
