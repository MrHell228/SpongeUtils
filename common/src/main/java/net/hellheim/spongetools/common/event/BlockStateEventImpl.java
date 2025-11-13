package net.hellheim.spongetools.common.event;

import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Game;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.bridge.BlockStateBaseBridge;
import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.event.BlockStateEvent;
import net.hellheim.spongetools.resourcepack.block.VariantList;
import net.hellheim.spongetools.resourcepack.block.VariantListLike;

public abstract class BlockStateEventImpl
		extends AbstractLifecycleEvent
		implements BlockStateEvent {
	
	protected final Logger logger;
	
	public BlockStateEventImpl(final Cause cause, final Game game, final Logger logger) {
		super(cause, game);
		this.logger = logger;
	}
	
	public static final class RegisterHolderImpl
			extends BlockStateEventImpl
			implements BlockStateEvent.RegisterHolder {
		
		private final BlockStateDispatcher dispatcher;
		
		public RegisterHolderImpl(
			final Cause cause, final Game game, final Logger logger, final BlockStateDispatcher dispatcher
		) {
			super(cause, game, logger);
			this.dispatcher = Objects.requireNonNull(dispatcher, "dispatcher");
		}
		
		@Override
		public BlockStateDispatcher dispatcher() {
			return this.dispatcher;
		}
	}
	
	public static final class RegisterDisplayImpl
			extends BlockStateEventImpl
			implements BlockStateEvent.RegisterDisplay {
		
		public RegisterDisplayImpl(final Cause cause, final Game game, final Logger logger) {
			super(cause, game, logger);
		}
		
		@Override
		public void register(final BlockState state, final BlockState display) {
			Objects.requireNonNull(state, "state");
			Objects.requireNonNull(display, "display");
			if (!display.type().key(RegistryTypes.BLOCK_TYPE).namespace().equals(ResourceKey.MINECRAFT_NAMESPACE)) {
				throw new IllegalArgumentException(String.format(
						"Tried to register non-vanilla display for state %s: %s",
						state.asString(), display.asString()));
			}
			
			final BlockState oldDisplay = BlockStateExtension.getFor(state).display();
			if (state != oldDisplay && oldDisplay != BlockTypeUtil.DEFAULT_STATE.get()) {
				this.logger.warn("Duplicate display registered for state %s (old: %s, new: %s)",
						state.asString(), oldDisplay.asString(), display.asString());
			}
			
			((BlockStateBaseBridge) state).spongetools$bridge$setNetworkState(Converter.asVanilla(display));
		}
	}
	
	public static final class RegisterVariantImpl
			extends BlockStateEventImpl
			implements BlockStateEvent.RegisterVariant {
		
		private final Map<BlockState, VariantList> variants;
		
		public RegisterVariantImpl(
			final Cause cause, final Game game, final Logger logger, final Map<BlockState, VariantList> variants
		) {
			super(cause, game, logger);
			this.variants = variants;
		}
		
		@Override
		public void register(final BlockState state, final VariantListLike variants) {
			Objects.requireNonNull(state, "state");
			Objects.requireNonNull(variants, "variants");
			final @Nullable VariantList oldVariants = this.variants.put(state, variants.asVariantList());
			if (oldVariants != null) {
				this.logger.warn("Duplicate variants registered for state %s (old: %s, new: %s)",
						state.asString(), oldVariants.toString(), variants.toString());
			}
		}
	}
}
