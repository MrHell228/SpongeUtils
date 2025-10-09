package net.hellheim.spongetools.custom.type.block;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;

public interface BlockStateDispatcher {
	
	static BlockStateDispatcher get() {
		return Sponge.game().factoryProvider().provide(BlockStateDispatcher.class);
	}
	
	Optional<BlockStateHolder> get(BlockState state);
	
	boolean isOccupied(BlockState state);
	
	default boolean isAvailable(final BlockState state) {
		return !this.isOccupied(state);
	}
	
	default void dispatch(final BlockStateHolder holder, final BlockStateProvider provider) {
		this.submit(holder, provider);
		this.dispatch();
	}
	
	void submit(BlockStateHolder holder, BlockStateProvider provider);
	
	void dispatch();
}
