package net.hellheim.spongetools.common.event;

import java.util.Objects;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.event.RegisterBlockStateHolderEvent;

public final class RegisterBlockStateHolderEventImpl
		extends AbstractLifecycleEvent
		implements RegisterBlockStateHolderEvent {
	
	private final BlockStateDispatcher dispatcher;
	
	public RegisterBlockStateHolderEventImpl(
		final Cause cause, final Game game, final BlockStateDispatcher dispatcher
	) {
		super(cause, game);
		this.dispatcher = Objects.requireNonNull(dispatcher, "dispatcher");
	}
	
	@Override
	public BlockStateDispatcher dispatcher() {
		return this.dispatcher;
	}
}
