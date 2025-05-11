package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.block.BlockType;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallback;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.TypedBehaviourCallback;
import net.hellheim.spongetools.proxy.solid.block.BlockTypeProxy;

public interface BlockTypeExtension extends
		BehaviourCallbackHolder<BlockStateExtension>,
		BlockTypeProxy {
	
	static BlockTypeExtension getFor(final BlockType block) {
		return (BlockTypeExtension) Objects.requireNonNull(block, "block");
	}
	
	static BlockTypeExtension getFor(final Supplier<? extends BlockType> blockSupplier) {
		return BlockTypeExtension.getFor(Objects.requireNonNull(blockSupplier, "blockSupplier").get());
	}
	
	BlockType type();
	
	@Override
	default BlockType getAsBlockType() {
		return this.type();
	}
	
	@Override
	default Collection<TypedBehaviourCallback<BlockStateExtension, ?, ?>> callbacks() {
		return BehaviourManager.get().callbacks(this.type(), BlockStateExtension.class);
	}
	
	@Override
	default <R, A extends BehaviourArgs> Optional<BehaviourCallback<BlockStateExtension, R, A>> callback(
		final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return BehaviourManager.get().callback(this.type(), BlockStateExtension.class, type);
	}
}
