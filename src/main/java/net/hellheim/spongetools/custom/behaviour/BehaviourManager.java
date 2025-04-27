package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;

public interface BehaviourManager {
	
	static BehaviourManager get() {
		return Sponge.game().factoryProvider().provide(BehaviourManager.class);
	}
	
	<H> boolean supports(H holder, BehaviourType<?> type);
	
	<H, B extends Behaviour<?, ?>> Optional<B> get(H holder, BehaviourType<B> type);
	
	<H> BehaviourRegistration<H> create(Class<H> holder);
	
	interface BehaviourRegistration<H> {
		
		<B extends Behaviour<?, ?>> BehaviourRegistration<H> register(
			BehaviourType<B> type, Function<H, @Nullable B> behaviourProvider
		);
	}
}
