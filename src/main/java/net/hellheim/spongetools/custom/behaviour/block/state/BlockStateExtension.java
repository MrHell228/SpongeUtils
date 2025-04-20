package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolderProxy;
import net.hellheim.spongetools.proxy.solid.block.BlockStateProxy;

/**
 * Can be used to override default {@link BlockState} behaviour.
 */
public interface BlockStateExtension extends BehaviourHolderProxy<BlockState>, BlockStateProxy {
	
	static BlockStateExtension getFor(final BlockState state) {
		return Sponge.game().factoryProvider().provide(Factory.class).getFor(state);
	}
	
	@Override
	default BlockState getAsBlockState() {
		return this.owner();
	}
	
	interface Factory {
		
		BlockStateExtension getFor(final BlockState state);
	}
}
