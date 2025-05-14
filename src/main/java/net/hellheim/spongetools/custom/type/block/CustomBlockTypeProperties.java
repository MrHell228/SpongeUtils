package net.hellheim.spongetools.custom.type.block;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class CustomBlockTypeProperties implements
		BehaviourCallbackHolderProxy<BlockStateExtension> {
	
	private final BehaviourCallbackHolderLogic.Immutable<BlockStateExtension> callbacks = null;
	
	@Override
	public BehaviourCallbackHolder<BlockStateExtension> getAsBehaviourCallbackHolder() {
		return this.callbacks;
	}
}
