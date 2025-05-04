package net.hellheim.spongetools.custom.block;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public interface CustomBlockType extends
		BehaviourCallbackHolder<BlockStateExtension> {
	
	BlockStateProvider stateProvider();
	
	BlockStateExtension stateExtension();
}
