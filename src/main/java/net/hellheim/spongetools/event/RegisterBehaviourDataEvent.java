package net.hellheim.spongetools.event;

import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager.BehaviourRegistration;

public interface RegisterBehaviourDataEvent extends LifecycleEvent {
	
	BehaviourManager manager();
	
	default <H> BehaviourRegistration<H> behaviour(final Class<H> holder) {
		return this.manager().behaviour(holder);
	}
}
