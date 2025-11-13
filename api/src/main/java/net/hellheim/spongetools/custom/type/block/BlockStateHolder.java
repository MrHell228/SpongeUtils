package net.hellheim.spongetools.custom.type.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.proxy.solid.block.BlockStateProxy;
import net.hellheim.spongetools.resourcepack.block.Variant;

public interface BlockStateHolder extends BlockStateProxy {
	
	static VariantBased of(final Variant variant) {
		return VariantBased.MAP.computeIfAbsent(Objects.requireNonNull(variant, "variant"), VariantBased::new);
	}
	
	/**
	 * @return True if state is bound to this holder
	 */
	boolean isBound();
	
	/**
	 * @return The state bound to this holder
	 * @throws IllegalStateException if state is not yet bound
	 * @see #bind(BlockState)
	 */
	BlockState state();
	
	// TODO move the algorithm description to somewhere else.
	/** 
	 * Binds the state to this {@link BlockStateHolder}. <br>
	 * The state is chosen in a way where all submitted
	 * {@link BlockStateHolder}s get the appropriate result. <br>
	 * If block is already used in the world, the used state would be provided. <br>
	 * If block is not yet used in the world, the state would be chosen from {@link #stateProvider()}. <br>
	 * If it's not possible to chose the state, TODO something is thrown.
	 * 
	 * @param state The state to bind to this {@link BlockStateHolder}
	 * @throws IllegalStateException if state is already bound
	 */
	void bind(BlockState state);
	
	@Override
	default BlockState getAsBlockState() {
		return this.state();
	}
	
	class Simple implements BlockStateHolder {
		
		private @MonotonicNonNull BlockState state;
		
		@Override
		public boolean isBound() {
			return this.state != null;
		}

		@Override
		public BlockState state() {
			if (this.state == null) {
				throw new IllegalStateException("State is not yet bound.");
			}
			return this.state;
		}

		@Override
		public void bind(final BlockState state) {
			if (this.state != null) {
				throw new IllegalStateException("State is already bound.");
			}
			
			this.state = Objects.requireNonNull(state, "state");
		}
	}
	
	final class VariantBased extends Simple {
		
		private static final Map<Variant, VariantBased> MAP = new HashMap<>();
		
		private final Variant variant;
		
		private VariantBased(final Variant variant) {
			this.variant = variant;
		}
		
		public Variant variant() {
			return this.variant;
		}
	}
}
