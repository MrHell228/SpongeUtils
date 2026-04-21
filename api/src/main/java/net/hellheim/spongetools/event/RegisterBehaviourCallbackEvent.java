package net.hellheim.spongetools.event;

import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;

public interface RegisterBehaviourCallbackEvent<T, I> extends LifecycleEvent {
	
	BehaviourStep<T, I> type(T type);
	
	interface BehaviourStep<T, I> extends BehaviourCallbackHolder.Mutable<I, BehaviourStep<T, I>> {
		
		T type();
	}
}
