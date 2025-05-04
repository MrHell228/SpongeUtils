package net.hellheim.spongetools.custom.behaviour;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents something that can have {@link Behaviour}s.
 */
public interface BehaviourHolder {
	
	/**
	 * Returns whether the given type is registered for this {@link BehaviourHolder}.
	 * 
	 * @param type The type of the behaviour
	 * @return True if this holder supports the given type
	 */
	boolean supports(BehaviourType<?> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} will use, if present.
	 * If no behaviour callbacks are registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The type of the behaviour
	 * @return The behaviour, if present
	 */
	<B extends Behaviour<?, ?>> Optional<B> get(BehaviourType<B> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} will use.
	 * If no custom behaviour is registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The type of the behaviour
	 * @return The behaviour
	 * @throws NoSuchElementException if the behaviour is not present on this {@link BehaviourHolder}
	 */
	<B extends Behaviour<?, ?>> B require(final BehaviourType<B> type);
	
	interface Mutable<H, M extends Mutable<H, M>> extends BehaviourHolder, BehaviourCallbackHolder.Mutable<H, M> {
	}
}
