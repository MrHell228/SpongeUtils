package net.hellheim.spongetools.custom.type.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.ResettableBuilder;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.custom.behaviour.type.BlockTypeExtension;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelTemplateProvider;
import net.hellheim.spongetools.resourcepack.ModelTemplates;
import net.hellheim.spongetools.resourcepack.block.Variant;
import net.hellheim.spongetools.util.ModelUtil;

@SuppressWarnings("unchecked")
public class CustomBlockTypeBuilder<B extends CustomBlockTypeBuilder<B>> implements
		BehaviourCallbackHolderProxy.Mutable<BlockStateExtension, B>,
		ResettableBuilder<CustomBlockTypeLike, B>,
		CopyableBuilder<CustomBlockTypeLike, B> {
	
	protected BehaviourCallbackHolderLogic.Mutable<BlockStateExtension> callbacks;
	protected @Nullable BlockStateProvider stateProvider;
	protected Optional<Variant> model;
	protected final Map<UnaryOperator<ResourceKey>, Model> companions = new HashMap<>();
	
	public CustomBlockTypeBuilder() {
		this.reset();
	}
	
	private B cast() {
		return (B) this;
	}
	
	@Override
	public BehaviourCallbackHolder.Mutable<BlockStateExtension, ?> getAsBehaviourCallbackHolder() {
		return this.callbacks;
	}
	
	// Overloads for #offerFrom
	
	public B offerFrom(final BlockState state) {
		return this.offerFrom(BlockStateExtension.getFor(state));
	}
	
	public B offerFrom(final BlockType block) {
		return this.offerFrom(BlockTypeExtension.getFor(block));
	}
	
	public B offerFrom(final Supplier<? extends BlockType> blockSupplier) {
		return this.offerFrom(BlockTypeExtension.getFor(blockSupplier));
	}
	
	public B offerFrom(
		final BlockState state,
		final BehaviourType<?> firstType,
		final BehaviourType<?>... otherTypes
	) {
		return this.offerFrom(BlockStateExtension.getFor(state), firstType, otherTypes);
	}
	
	public B offerFrom(
		final BlockType block,
		final BehaviourType<?> firstType,
		final BehaviourType<?>... otherTypes
	) {
		return this.offerFrom(BlockTypeExtension.getFor(block), firstType, otherTypes);
	}
	
	public B offerFrom(
		final Supplier<? extends BlockType> blockSupplier,
		final BehaviourType<?> firstType,
		final BehaviourType<?>... otherTypes
	) {
		return this.offerFrom(BlockTypeExtension.getFor(blockSupplier), firstType, otherTypes);
	}
	
	public B offerFrom(
		final BlockState state,
		final Iterable<? extends BehaviourType<?>> types
	) {
		return this.offerFrom(BlockStateExtension.getFor(state), types);
	}
	
	public B offerFrom(
		final BlockType block,
		final Iterable<? extends BehaviourType<?>> types
	) {
		return this.offerFrom(BlockTypeExtension.getFor(block), types);
	}
	
	public B offerFrom(
		final Supplier<? extends BlockType> blockSupplier,
		final Iterable<? extends BehaviourType<?>> types
	) {
		return this.offerFrom(BlockTypeExtension.getFor(blockSupplier), types);
	}
	
	// StateProvider setters
	
	public B state(final BlockStateProvider stateProvider) {
		this.stateProvider = Objects.requireNonNull(stateProvider, "stateProvider");
		return this.cast();
	}
	
	public B state(final BlockState state) {
		return this.state(BlockStateProvider.anyState(state));
	}
	
	public B defaultState(final BlockType block) {
		return this.state(Objects.requireNonNull(block, "block").defaultState());
	}
	
	public B defaultState(final Supplier<? extends BlockType> blockSupplier) {
		return this.defaultState(Objects.requireNonNull(blockSupplier, "blockSupplier").get());
	}
	
	// Model
	
	public B model(final Variant variant) {
		return this.model(Optional.of(Objects.requireNonNull(variant, "variant")));
	}
	
	public B model(final Optional<Variant> variant) {
		this.model = Objects.requireNonNull(variant, "variant");
		return this.cast();
	}
	
	// Companion models
	
	public B companion(final String keySuffix, final Model model) {
		return this.companion(key -> ModelUtil.withSuffix(key, keySuffix), model);
	}
	
	public B companion(final UnaryOperator<ResourceKey> key, final Model model) {
		this.companions.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(model, "model"));
		return this.cast();
	}
	
	public B companions(final Map<UnaryOperator<ResourceKey>, Model> companions) {
		for (final var entry : Objects.requireNonNull(companions, "companions").entrySet()) {
			this.companion(entry.getKey(), entry.getValue());
		}
		return this.cast();
	}
	
	// Both model and companions
	
	public B simpleModel(final ResourceKey key) {
		return this.simpleModel(key, ModelTemplates.CUBE_ALL);
	}
	
	public B simpleModel(final ResourceKey key, final ModelTemplateProvider.T1 template) {
		final ResourceKey prefixed = ModelUtil.withBlockPrefix(key);
		return this.model(Variant.model(prefixed))
				.companion($ -> prefixed, Model.of(template.textured(prefixed)));
	}
	
	
	@Override
	public B from(final CustomBlockTypeLike type) {
		Objects.requireNonNull(type, "type");
		return this.reset()
				.offerFrom(type)
				.state(type.stateProvider())
				.model(type.model())
				.companions(type.companions());
	}
	
	@Override
	public B reset() {
		this.callbacks = BehaviourCallbackHolderLogic.mutable();
		this.stateProvider = null;
		this.model = Optional.empty();
		this.companions.clear();
		return this.cast();
	}
	
	public B validate() {
		if (this.stateProvider == null) {
			throw new IllegalArgumentException("stateProvider must be set");
		}
		
		return this.cast();
	}
	
	public CustomBlockTypeProperties buildProperties() {
		return new CustomBlockTypeProperties(this);
	}
	
	public CustomBlockType build() {
		return new BasicCustomBlockType(this.buildProperties());
	}
}
