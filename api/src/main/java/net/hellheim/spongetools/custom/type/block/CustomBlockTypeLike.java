package net.hellheim.spongetools.custom.type.block;

import java.util.List;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.block.Variant;

public interface CustomBlockTypeLike extends
		BehaviourCallbackHolder<BlockStateExtension> {
	
	BlockStateProvider stateProvider();
	
	List<Variant> model();
}
