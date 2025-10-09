package net.hellheim.spongetools.custom.type.block;

import java.util.List;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.block.Variant;

public interface DefaultedCustomBlockType extends
		CustomBlockType,
		BehaviourCallbackHolderProxy<BlockStateExtension> {
	
	CustomBlockTypeProperties properties();
	
	@Override
	default BehaviourCallbackHolder<BlockStateExtension> getAsBehaviourCallbackHolder() {
		return this.properties();
	}
	
	@Override
	default BlockStateProvider stateProvider() {
		return this.properties().stateProvider();
	}
	
	@Override
	default List<Variant> model() {
		return this.properties().model();
	}
}
