package net.hellheim.spongetools.custom.type.block;

import java.util.List;
import java.util.Objects;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.block.Variant;

public class CustomBlockTypeProperties implements
		CustomBlockTypeLike,
		BehaviourCallbackHolderProxy<BlockStateExtension> {
	
	private final BehaviourCallbackHolderLogic<BlockStateExtension> callbacks;
	private final BlockStateProvider stateProvider;
	private final List<Variant> model;
	
	public CustomBlockTypeProperties(final CustomBlockTypeBuilder<?> builder) {
		Objects.requireNonNull(builder, "builder").validate();
		this.callbacks = builder.callbacks.asImmutable();
		this.stateProvider = builder.stateProvider;
		this.model = List.copyOf(builder.model);
	}
	
	@Override
	public BehaviourCallbackHolder<BlockStateExtension> getAsBehaviourCallbackHolder() {
		return this.callbacks;
	}
	
	@Override
	public BlockStateProvider stateProvider() {
		return this.stateProvider;
	}
	
	@Override
	public List<Variant> model() {
		return this.model;
	}
	
	public CustomBlockTypeBuilder<?> toBuilder() {
		return new CustomBlockTypeBuilder<>().from(this);
	}
}
