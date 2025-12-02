package net.hellheim.spongetools.custom.behaviour;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.spongepowered.api.Sponge;

public interface BehaviourManager {
	
	static BehaviourManager get() {
		return Sponge.game().factoryProvider().provide(BehaviourManager.class);
	}
	
	<H> boolean supports(H holder, BehaviourGroup<H> group);
	
	<H> boolean supports(H holder, BehaviourType<?> type);
	
	<H, B extends Behaviour<?, ?>> Optional<B> get(H holder, BehaviourType<B> type);
	
	default <H, B extends Behaviour<?, ?>> B require(final H holder, final BehaviourType<B> type) {
		return this.get(holder, type)
				.orElseThrow(() -> new NoSuchElementException(String.format(
						"No behaviour of type %s is present for holder %s",
						type, holder
						)));
	}
}
