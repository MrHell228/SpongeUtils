package net.hellheim.spongetools.event;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourGroup;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;

public interface RegisterBehaviourDataEvent extends LifecycleEvent {
	
	<H> BehaviourRegistration<H> group(BehaviourGroup<H> group);
	
	interface BehaviourRegistration<H> {
		
		/**
		 * Registers behaviour provider
		 */
		<B extends Behaviour<?, ?>> BehaviourRegistration<H> register(
			BehaviourType<B> type,
			Function<H, @Nullable B> behaviourProvider
		);
	}
}
