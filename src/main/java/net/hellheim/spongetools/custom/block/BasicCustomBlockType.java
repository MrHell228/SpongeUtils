package net.hellheim.spongetools.custom.block;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class BasicCustomBlockType implements
		CustomBlockType,
		BehaviourHolderProxy,
		BehaviourCallbackHolderProxy<BlockStateExtension> {
	
	private final BehaviourCallbackHolderLogic<BlockStateExtension> callbacks;
	private final BlockStateProvider stateProvider;
	private @MonotonicNonNull BlockState state;
	private @MonotonicNonNull BlockStateExtension stateExtension;
	
	public BasicCustomBlockType(final CustomBlockTypeBuilder<?> builder) {
		Objects.requireNonNull(builder, "builder").validate();
		this.callbacks = builder.callbacks.asImmutable();
		this.stateProvider = builder.stateProvider;
		this.bind(this.stateProvider.provide());
	}
	
	@Override
	public BlockStateProvider stateProvider() {
		return this.stateProvider;
	}
	
	@Override
	public BlockState state() {
		if (this.state == null) {
			throw new IllegalStateException("State is not yet bound");
		}
		
		return this.state;
	}
	
	@Override
	public void bind(final BlockState state) {
		if (this.state != null) {
			throw new IllegalStateException("State is already bound");
		}
		
		this.state = Objects.requireNonNull(state, "state");
		this.stateExtension = BlockStateExtension.getFor(state);
	}
	
	@Override
	public BehaviourHolder getAsBehaviourHolder() {
		return this.stateExtension;
	}
	
	@Override
	public BehaviourCallbackHolder<BlockStateExtension> getAsBehaviourCallbackHolder() {
		return this.callbacks;
	}
}
