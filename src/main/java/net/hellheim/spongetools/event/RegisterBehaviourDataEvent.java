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
	
	default <R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void registerProvider(
		final BehaviourType<B> type, final Function<R, C> callbackProvider
	) {
		this.manager().registerProvider(type, callbackProvider);
	}
	
	default <C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void registerConverter(
		final BehaviourType<B> type, final Function<B, C> callbackConverter
	) {
		this.manager().registerConverter(type, callbackConverter);
	}
	
	default <C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void registerConverterBefore(
		final BehaviourType<B> type, final Function<B, C> callbackConverterBefore
	) {
		this.manager().registerConverterBefore(type, callbackConverterBefore);
	}
	
	default <C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void registerConverterAfter(
		final BehaviourType<B> type, final Function<B, C> callbackConverterAfter
	) {
		this.manager().registerConverterAfter(type, callbackConverterAfter);
	}
	
	default <C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void registerMerger(
		final BehaviourType<B> type, final BehaviourMerger<C> callbackMerger
	) {
		this.manager().registerMerger(type, callbackMerger);
	}
	
	default <H> BehaviourRegistration<H> create(final Class<H> holder) {
		return this.manager().create(holder);
	}
	
	default <R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void register(
		final BehaviourType<B> type,
		final Function<R, C> callbackProvider,
		final Function<B, C> callbackConverter,
		final Function<B, C> callbackConverterBefore,
		final Function<B, C> callbackConverterAfter,
		final BehaviourMerger<C> callbackMerger
	) {
		this.registerProvider(type, callbackProvider);
		this.registerConverter(type, callbackConverter);
		this.registerConverterBefore(type, callbackConverterBefore);
		this.registerConverterAfter(type, callbackConverterAfter);
		this.registerMerger(type, callbackMerger);
	}
	
	default <C extends Behaviour.Callback<Void>, B extends Behaviour.Extendable<C>> void register(
		final BehaviourType<B> type,
		final Function<B, C> callbackConverter,
		final Function<B, C> callbackConverterBefore,
		final Function<B, C> callbackConverterAfter,
		final BehaviourMerger<C> callbackMerger
	) {
		this.registerConverter(type, callbackConverter);
		this.registerConverterBefore(type, callbackConverterBefore);
		this.registerConverterAfter(type, callbackConverterAfter);
		this.registerMerger(type, callbackMerger);
	}
}
