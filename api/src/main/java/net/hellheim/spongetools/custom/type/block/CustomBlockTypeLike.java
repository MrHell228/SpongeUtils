package net.hellheim.spongetools.custom.type.block;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.Variant;

public interface CustomBlockTypeLike extends
		BehaviourCallbackHolder<BlockStateExtension> {
	
	BlockStateProvider stateProvider();
	
	Optional<Variant> model();
	
	Map<UnaryOperator<ResourceKey>, Model> companions();
}
