package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Objects;

import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.proxy.solid.block.BlockStateProxy;

/**
 * Can be used to override default {@link BlockState} behaviour.
 */
public interface BlockStateExtension extends
		BehaviourHolder.Defaulted,
		BehaviourCallbackHolder<BlockStateExtension>,
		BlockStateProxy {
	
	static BlockStateExtension getFor(final BlockState state) {
		return (BlockStateExtension) Objects.requireNonNull(state, "state");
	}
	
	BlockState state();
	
	BlockState display();
	
	@Override
	default BlockState getAsBlockState() {
		return this.state();
	}
	
	@Override
	default Object getAsActualBehaviourHolder() {
		return this.state();
	}
}
