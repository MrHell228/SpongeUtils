package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.proxy.solid.block.BlockStateProxy;

/**
 * Can be used to override default {@link BlockState} behaviour.
 */
public interface BlockStateExtension extends
		BehaviourHolder,
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
	default boolean supports(final BehaviourType<?> type) {
		return BehaviourManager.get().supportsBehaviour(this.state(), type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> Optional<B> get(final BehaviourType<B> type) {
		return BehaviourManager.get().behaviour(this.state(), type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> B require(final BehaviourType<B> type) {
		return BehaviourManager.get().requireBehaviour(this.state(), type);
	}
}
