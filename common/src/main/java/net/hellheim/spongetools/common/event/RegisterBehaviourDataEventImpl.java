package net.hellheim.spongetools.common.event;

import java.util.Objects;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.event.RegisterBehaviourDataEvent;

public final class RegisterBehaviourDataEventImpl
		extends AbstractLifecycleEvent
		implements RegisterBehaviourDataEvent {
	
	private final BehaviourManager manager;
	
	public RegisterBehaviourDataEventImpl(
		final Cause cause, final Game game, final BehaviourManager manager
	) {
		super(cause, game);
		this.manager = Objects.requireNonNull(manager, "manager");
	}
	
	@Override
	public BehaviourManager manager() {
		return this.manager;
	}
}
