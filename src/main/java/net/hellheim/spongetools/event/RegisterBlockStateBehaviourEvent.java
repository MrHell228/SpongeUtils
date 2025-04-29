package net.hellheim.spongetools.event;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import com.google.common.collect.Streams;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public interface RegisterBlockStateBehaviourEvent extends LifecycleEvent {
	
	BehaviourStep state(BlockState state);
	
	
	default Stream<BehaviourStep> states(final Stream<? extends BlockState> states) {
		return states.map(this::state);
	}
	
	default Stream<BehaviourStep> states(final BlockState... states) {
		return this.states(Arrays.stream(states));
	}
	
	@SuppressWarnings("unchecked")
	default Stream<BehaviourStep> states(final Supplier<? extends BlockState>... states) {
		return this.states(Arrays.stream(states).map(Supplier::get));
	}
	
	default Stream<BehaviourStep> states(final Iterable<? extends BlockState> states) {
		return this.states(Streams.stream(states));
	}
	
	
	default BehaviourStep defaultState(final BlockType type) {
		return this.state(type.defaultState());
	}
	
	default BehaviourStep defaultState(final Supplier<? extends BlockType> type) {
		return this.defaultState(type.get());
	}
	
	
	default Stream<BehaviourStep> defaultStates(final Stream<? extends BlockType> types) {
		return types.map(this::defaultState);
	}
	
	default Stream<BehaviourStep> defaultStates(final BlockType... types) {
		return this.defaultStates(Arrays.stream(types));
	}
	
	@SuppressWarnings("unchecked")
	default Stream<BehaviourStep> defaultStates(final Supplier<? extends BlockType>... types) {
		return this.defaultStates(Arrays.stream(types).map(Supplier::get));
	}
	
	default Stream<BehaviourStep> defaultStates(final Iterable<? extends BlockType> types) {
		return this.defaultStates(Streams.stream(types));
	}
	
	
	default Stream<BehaviourStep> allStates(final Stream<? extends BlockType> types) {
		return types.flatMap(type -> type.validStates().stream()).map(this::state);
	}
	
	default Stream<BehaviourStep> allStates(final BlockType... types) {
		return this.allStates(Arrays.stream(types));
	}
	
	@SuppressWarnings("unchecked")
	default Stream<BehaviourStep> allStates(final Supplier<? extends BlockType>... types) {
		return this.allStates(Arrays.stream(types).map(Supplier::get));
	}
	
	default Stream<BehaviourStep> allStates(final Iterable<? extends BlockType> types) {
		return this.allStates(Streams.stream(types));
	}
	
	
	interface BehaviourStep extends BehaviourHolderProxy.Mutable<BlockStateExtension, BehaviourStep> {
	}
}
