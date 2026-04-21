package net.hellheim.spongetools.event;

import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.BehaviourManager;

public interface RegisterBehaviourDataEvent extends LifecycleEvent {
	
	<H> BehaviourManager.BehaviourRegistration<H> registration(Class<H> behaviourHolder);
}
