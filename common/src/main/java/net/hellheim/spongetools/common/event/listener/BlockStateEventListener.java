package net.hellheim.spongetools.common.event.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Listener;

import net.hellheim.spongetools.common.event.BlockStateEventImpl;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.BlockStateHolder;
import net.hellheim.spongetools.custom.type.block.ModeledBlock;
import net.hellheim.spongetools.resourcepack.block.Variant;
import net.hellheim.spongetools.event.BlockStateEvent;

public final class BlockStateEventListener {
	
	public static Map<BlockState, List<Variant>> fireEvents(final Game game, final Cause cause, final Logger logger) {
		final BlockStateDispatcher dispatcher = BlockStateDispatcher.get();
		Sponge.eventManager().post(
				new BlockStateEventImpl.RegisterHolderImpl(cause, game, logger, dispatcher));
		dispatcher.dispatch();
		
		Sponge.eventManager().post(new BlockStateEventImpl.RegisterDisplayImpl(cause, game, logger));
		
		final Map<BlockState, List<Variant>> variants = new HashMap<>();
		Sponge.eventManager().post(new BlockStateEventImpl.RegisterVariantImpl(cause, game, logger, variants));
		return variants;
	}
	
	@Listener
	public void registerHolder(final BlockStateEvent.RegisterHolder event) {
		ModeledBlock.registry().get().stream().forEach(block ->
			block.variantToProviderMap().forEach((variant, provider) ->
				event.register(BlockStateHolder.of(variant), provider)));
	}
	
	@Listener
	public void registerDisplay(final BlockStateEvent.RegisterDisplay event) {
		ModeledBlock.registry().get().stream().forEach(block ->
			block.type().validStates().forEach(state ->
				event.register(state, BlockStateHolder.of(block.variants().getFor(state)).state())));
		
		// Allows to use 1149 custom block models... actually wow
		event.allToOne(BlockTypes.NOTE_BLOCK, BlockType::defaultState);
	}
	
	@Listener
	public void registerVariant(final BlockStateEvent.RegisterVariant event) {
		ModeledBlock.registry().get().stream().forEach(block ->
			block.type().validStates().forEach(state -> {
				final Variant variant = block.variants().getFor(state);
				event.register(BlockStateHolder.of(variant).state(), List.of(variant));
			}));
	}
}
