package net.hellheim.spongetools.common.event.listener;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.common.event.RegisterBlockStateHolderEventImpl;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.event.RegisterBlockStateHolderEvent;

public final class BlockStateDispatcherEventListener {
	
	public static void fireEvent(final LifecycleEvent e) {
		final BlockStateDispatcher dispatcher = BlockStateDispatcher.get();
		Sponge.eventManager().post(
				new RegisterBlockStateHolderEventImpl(e.cause(), e.game(), dispatcher));
		dispatcher.dispatch();
	}
	
	@Listener
	public void registerCustomBlockTypeStates(final RegisterBlockStateHolderEvent event) {
		// TODO is this needed now?
		/*CustomBlockType.registry().get().streamEntries()
				.forEach(entry -> {
					final CustomBlockType block = entry.value();
					event.register(block, block.stateProvider());
				});*/
	}
}
