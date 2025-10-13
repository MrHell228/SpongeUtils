package net.hellheim.spongetools.custom.type.block;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.Variant;

public class CustomBlockTypeProperties implements
		CustomBlockTypeLike,
		BehaviourCallbackHolderProxy<BlockStateExtension> {
	
	private final BehaviourCallbackHolderLogic<BlockStateExtension> callbacks;
	private final BlockStateProvider stateProvider;
	private final Optional<Variant> model;
	private final Map<UnaryOperator<ResourceKey>, Model> companions;
	
	public CustomBlockTypeProperties(final CustomBlockTypeBuilder<?> builder) {
		Objects.requireNonNull(builder, "builder").validate();
		this.callbacks = builder.callbacks.asImmutable();
		this.stateProvider = builder.stateProvider;
		this.model = builder.model;
		this.companions = Map.copyOf(builder.companions);
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
	public Optional<Variant> model() {
		return this.model;
	}
	
	@Override
	public Map<UnaryOperator<ResourceKey>, Model> companions() {
		return this.companions;
	}
	
	public CustomBlockTypeBuilder<?> toBuilder() {
		return new CustomBlockTypeBuilder<>().from(this);
	}
}
