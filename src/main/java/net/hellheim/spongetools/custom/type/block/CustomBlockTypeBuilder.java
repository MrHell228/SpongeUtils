package net.hellheim.spongetools.custom.type.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.custom.behaviour.type.BlockTypeExtension;
import net.hellheim.spongetools.resourcepack.block.Variant;

@SuppressWarnings("unchecked")
public class CustomBlockTypeBuilder<B extends CustomBlockTypeBuilder<B>> implements
		BehaviourCallbackHolderProxy.Mutable<BlockStateExtension, B> {
	
	protected BehaviourCallbackHolderLogic.Mutable<BlockStateExtension> callbacks;
	protected @Nullable BlockStateProvider stateProvider;
	protected final List<Variant> model = new ArrayList<>();
	
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
	
	
	public B model(final Variant... variants) {
		for (final Variant variant : Objects.requireNonNull(variants, "variants")) {
			this.model.add(Objects.requireNonNull(variant, "variant"));
		}
		
		return this.cast();
	}
	
	public B model(final Iterable<? extends Variant> variants) {
		for (final Variant variant : Objects.requireNonNull(variants, "variants")) {
			this.model.add(Objects.requireNonNull(variant, "variant"));
		}
		
		return this.cast();
	}
	
	
	public B reset() {
		this.callbacks = BehaviourCallbackHolderLogic.mutable();
		this.stateProvider = null;
		this.model.clear();
		return this.cast();
	}
	
	public B validate() {
		if (this.stateProvider == null) {
			throw new IllegalArgumentException("stateProvider must be set");
		}
		
		return this.cast();
	}
	
	public CustomBlockType build() {
		return new BasicCustomBlockType(this);
	}
}
