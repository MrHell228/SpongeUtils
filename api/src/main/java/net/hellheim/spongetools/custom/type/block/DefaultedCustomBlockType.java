package net.hellheim.spongetools.custom.type.block;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.Model;
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
	default Optional<Variant> model() {
		return this.properties().model();
	}
	
	@Override
	default Map<UnaryOperator<ResourceKey>, Model> companions() {
		return this.properties().companions();
	}
}
