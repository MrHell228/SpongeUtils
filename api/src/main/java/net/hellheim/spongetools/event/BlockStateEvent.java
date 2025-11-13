package net.hellheim.spongetools.event;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.BlockStateHolder;
import net.hellheim.spongetools.custom.type.block.BlockStateProvider;
import net.hellheim.spongetools.resourcepack.block.Variant;

public interface BlockStateEvent extends LifecycleEvent {
	
	interface RegisterHolder extends BlockStateEvent {
		
		BlockStateDispatcher dispatcher();
		
		default void register(final BlockStateHolder holder, final BlockStateProvider provider) {
			this.dispatcher().submit(holder, provider);
		}
	}
	
	interface RegisterDisplay extends BlockStateEvent {
		
		/**
		 * Registers how the {@link BlockState} is displayed to the clients.
		 * 
		 * @param state The server-side {@link BlockState}
		 * @param display The client-side {@link BlockState}
		 * @throws IllegalArgumentException if display does not belong to vanilla block
		 */
		void register(BlockState state, BlockState display);
		
		default void allToOne(final BlockType block, final BlockState display) {
			block.validStates().forEach(state -> this.register(state, display));
		}
		
		default void allToOne(final BlockType block, final Function<BlockType, BlockState> displayProvider) {
			this.allToOne(block, displayProvider.apply(block));
		}
		
		default void allToOne(final Supplier<BlockType> block, final Function<BlockType, BlockState> displayProvider) {
			this.allToOne(block.get(), displayProvider);
		}
	}
	
	interface RegisterVariant extends BlockStateEvent {
		
		/**
		 * Registers the {@link Variant}s to display
		 * 
		 * @param state
		 * @param variants
		 */
		void register(BlockState state, List<Variant> variants);
	}
}
