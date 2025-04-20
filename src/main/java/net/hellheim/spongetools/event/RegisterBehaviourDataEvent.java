package net.hellheim.spongetools.event;

import java.util.function.Function;

import org.spongepowered.api.event.lifecycle.LifecycleEvent;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.BehaviourMerger;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager.BehaviourRegistration;

public interface RegisterBehaviourDataEvent extends LifecycleEvent {
	
	BehaviourManager manager();
	
	default <R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void register(
		final BehaviourType<B> type, final Function<R, C> callbackProvider
	) {
		this.manager().register(type, callbackProvider);
	}
	
	default <C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void register(
		final BehaviourType<B> type, final BehaviourMerger<C> callbackMerger
	) {
		this.manager().register(type, callbackMerger);
	}
	
	default <H> BehaviourRegistration<H> create(final Class<H> holder) {
		return this.manager().create(holder);
	}
	
	default <R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void register(
		final BehaviourType<B> type,
		final Function<R, C> callbackProvider, final BehaviourMerger<C> callbackMerger
	) {
		this.register(type, callbackProvider);
		this.register(type, callbackMerger);
	}
	
	default <H, R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void register(
		final Class<H> holder, final BehaviourType<B> type,
		final Function<R, C> callbackProvider, final BehaviourMerger<C> callbackMerger,
		final Function<H, B> behaviourProvider
	) {
		this.register(type, callbackProvider, callbackMerger);
		this.create(holder).register(type, behaviourProvider);
	}
	
	default <H, C extends Behaviour.Callback<Void>, B extends Behaviour.Extendable<C>> void register(
		final Class<H> holder, final BehaviourType<B> type,
		final BehaviourMerger<C> callbackMerger,
		final Function<H, B> behaviourProvider
	) {
		this.register(type, callbackMerger);
		this.create(holder).register(type, behaviourProvider);
	}
}
