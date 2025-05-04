package net.hellheim.spongetools.custom.block;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class CustomBlockTypeBuilder<B extends CustomBlockTypeBuilder<B>> implements
		BehaviourCallbackHolderProxy.Mutable<BlockStateExtension, B> {
	
	protected BehaviourCallbackHolderLogic.Mutable<BlockStateExtension> callbacks;
	protected @Nullable BlockStateProvider stateProvider;
	
	public CustomBlockTypeBuilder() {
		this.reset();
	}
	
	@SuppressWarnings("unchecked")
	private B cast() {
		return (B) this;
	}
	
	@Override
	public BehaviourCallbackHolder.Mutable<BlockStateExtension, ?> getAsBehaviourCallbackHolder() {
		return this.callbacks;
	}
	
	public B reset() {
		this.callbacks = BehaviourCallbackHolderLogic.mutable();
		this.stateProvider = null;
		return this.cast();
	}
	
	public CustomBlockType build() {
		return new BasicCustomBlockType(this);
	}
}
