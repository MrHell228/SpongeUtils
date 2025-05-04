package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Optional;

import org.spongepowered.api.Sponge;
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
		return Sponge.game().factoryProvider().provide(Factory.class).getFor(state);
	}
	
	BlockState state();
	
	@Override
	default BlockState getAsBlockState() {
		return this.state();
	}
	
	@Override
	default boolean supports(final BehaviourType<?> type) {
		return BehaviourManager.get().supports(this.state(), type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> Optional<B> get(final BehaviourType<B> type) {
		return BehaviourManager.get().get(this.state(), type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> B require(final BehaviourType<B> type) {
		return BehaviourManager.get().require(this.state(), type);
	}
	
	interface Factory {
		
		BlockStateExtension getFor(final BlockState state);
	}
}
