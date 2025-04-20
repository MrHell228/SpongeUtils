package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;

public interface BehaviourManager {
	
	static BehaviourManager get() {
		return Sponge.game().factoryProvider().provide(BehaviourManager.class);
	}
	
	<R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> Function<R, C> callbackProvider(
		BehaviourType<B> type
	);
	
	<C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> BehaviourMerger<C> callbackMerger(
		BehaviourType<B> type
	);
	
	<H, B extends Behaviour> boolean supports(H holder, BehaviourType<B> type);
	
	<H, B extends Behaviour> Optional<B> get(H holder, BehaviourType<B> type);
	
	<R, C extends Behaviour.Callback<R>, B extends Behaviour.Extendable<C>> void register(
		BehaviourType<B> type, Function<R, C> callbackProvider
	);
	
	<C extends Behaviour.Callback<?>, B extends Behaviour.Extendable<C>> void register(
		BehaviourType<B> type, BehaviourMerger<C> callbackMerger
	);
	
	<H> BehaviourRegistration<H> create(Class<H> holder);
	
	interface BehaviourRegistration<H> {
		
		<B extends Behaviour> BehaviourRegistration<H> register(
			BehaviourType<B> type, Function<H, @Nullable B> behaviourProvider
		);
	}
}
