package net.hellheim.spongetools.common.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.event.RegisterBlockStateBehaviourEvent;

public final class RegisterBlockStateBehaviourEventImpl
		extends AbstractLifecycleEvent
		implements RegisterBlockStateBehaviourEvent {
	
	public final Map<BlockState, BehaviourStepImpl> steps = new HashMap<>();
	
	public RegisterBlockStateBehaviourEventImpl(
		final Cause cause, final Game game, final BehaviourManager manager
	) {
		super(cause, game);
	}
	
	@Override
	public BehaviourStep state(final BlockState state) {
		Objects.requireNonNull(state, "state");
		return this.steps.computeIfAbsent(state, BehaviourStepImpl::new);
	}
	
	public final class BehaviourStepImpl implements
			RegisterBlockStateBehaviourEvent.BehaviourStep,
			BehaviourCallbackHolderProxy.Mutable<BlockStateExtension, RegisterBlockStateBehaviourEvent.BehaviourStep> {
		
		private final BlockState state;
		public final BehaviourCallbackHolderLogic.Mutable<BlockStateExtension> callbacks;
		
		private BehaviourStepImpl(final BlockState state) {
			this.state = state;
			this.callbacks = BehaviourCallbackHolderLogic.mutable();
		}
		
		@Override
		public BlockState state() {
			return this.state;
		}
		
		@Override
		public BehaviourCallbackHolder.Mutable<BlockStateExtension, ?> getAsBehaviourCallbackHolder() {
			return this.callbacks;
		}
	}
}
