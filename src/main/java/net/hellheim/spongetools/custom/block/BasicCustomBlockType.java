package net.hellheim.spongetools.custom.block;

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
	private final BlockState state;
	private final BlockStateExtension stateExtension;
	
	public BasicCustomBlockType(final CustomBlockTypeBuilder<?> builder) {
		this.callbacks = builder.callbacks.asImmutable();
		this.stateProvider = builder.stateProvider;
		this.state = this.stateProvider.provide();
		this.stateExtension = BlockStateExtension.getFor(this.state);
	}
	
	@Override
	public BlockStateProvider stateProvider() {
		return this.stateProvider;
	}
	
	@Override
	public BlockStateExtension stateExtension() {
		return this.stateExtension;
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
