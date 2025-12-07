package net.hellheim.spongetools.common.event;

import java.util.Objects;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Cause;
import org.spongepowered.common.event.lifecycle.AbstractLifecycleEvent;

import net.hellheim.spongetools.common.behaviour.BehaviourManagerImpl;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.event.RegisterBehaviourDataEvent;

public final class RegisterBehaviourDataEventImpl
		extends AbstractLifecycleEvent
		implements RegisterBehaviourDataEvent {
	
	private final BehaviourManagerImpl manager;
	
	public RegisterBehaviourDataEventImpl(
		final Cause cause, final Game game, final BehaviourManagerImpl manager
	) {
		super(cause, game);
		this.manager = Objects.requireNonNull(manager, "manager");
	}
	
	@Override
	public <H> BehaviourManager.BehaviourRegistration<H> registration(final Class<H> behaviourHolder) {
		return this.manager.registration(behaviourHolder);
	}
}
