package net.hellheim.spongetools.custom.block;

import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public interface CustomBlockType extends
		BehaviourCallbackHolder<BlockStateExtension> {
	
	BlockStateProvider stateProvider();
	
	/**
	 * @return The state bound to this block
	 * @throws IllegalStateException if state is not yet bound
	 * @see #bind(BlockState)
	 */
	BlockState state();
	
	// TODO move the algorithm description to somewhere else.
	/** 
	 * Binds the state to this {@link CustomBlockType}. <br>
	 * The state is chosen in a way where all registered
	 * {@link CustomBlockType}s get the appropriate result. <br>
	 * If block is already used in the world, the used state would be provided. <br>
	 * If block is not yet used in the world, the state would be chosen from {@link #stateProvider()}. <br>
	 * If it's not possible to chose the state, TODO something is thrown.
	 * 
	 * @param state The state to bind to this {@link CustomBlockType}
	 * @throws IllegalStateException if state is already bound
	 */
	void bind(BlockState state);
}
