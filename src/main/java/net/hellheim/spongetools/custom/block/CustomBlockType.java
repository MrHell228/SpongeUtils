package net.hellheim.spongetools.custom.block;

import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public interface CustomBlockType {
	
	
	BlockStateProvider stateProvider();
	
	BlockStateExtension stateExtension();
	
}
