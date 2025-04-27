package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.data.type.PushReaction;

@FunctionalInterface
public interface PushReactionBlockStateBehaviour
		extends BlockStateBehaviour<PushReactionBlockStateBehaviour.Callback> {
	
	PushReaction apply();
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<PushReaction> {
		
		PushReaction apply(
			BlockStateExtension state, PushReactionBlockStateBehaviour origin
		);
	}
}
