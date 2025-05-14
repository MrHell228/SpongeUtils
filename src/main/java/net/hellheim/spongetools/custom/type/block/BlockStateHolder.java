package net.hellheim.spongetools.custom.type.block;

import org.spongepowered.api.block.BlockState;

public interface BlockStateHolder {
	
	/**
	 * @return The state bound to this holder
	 * @throws IllegalStateException if state is not yet bound
	 * @see #bind(BlockState)
	 */
	BlockState state();
	
	// TODO move the algorithm description to somewhere else.
	/** 
	 * Binds the state to this {@link BlockStateHolder}. <br>
	 * The state is chosen in a way where all submitted
	 * {@link BlockStateHolder}s get the appropriate result. <br>
	 * If block is already used in the world, the used state would be provided. <br>
	 * If block is not yet used in the world, the state would be chosen from {@link #stateProvider()}. <br>
	 * If it's not possible to chose the state, TODO something is thrown.
	 * 
	 * @param state The state to bind to this {@link BlockStateHolder}
	 * @throws IllegalStateException if state is already bound
	 */
	void bind(BlockState state);
}
