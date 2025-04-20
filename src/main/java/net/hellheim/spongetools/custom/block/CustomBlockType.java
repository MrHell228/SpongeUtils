package net.hellheim.spongetools.custom.block;

import net.hellheim.spongetools.custom.behaviour.block.state.BlockStateExtension;

public interface CustomBlockType {
	
	
	BlockStateProvider stateProvider();
	
	BlockStateExtension stateExtension();
	
}
