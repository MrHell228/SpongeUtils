package net.hellheim.spongetools.custom.behaviour;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents something that can have {@link Behaviour}s.
 */
public interface BehaviourGroup {
	
	/**
	 * Returns whether the given type is registered for this {@link BehaviourGroup}.
	 * 
	 * @param type The behaviour type
	 * @return True if this holder supports the given type
	 */
	boolean supports(BehaviourType<?> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourGroup} will use, if present.
	 * If no behaviour callbacks are registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The behaviour type
	 * @return The behaviour, if present
	 */
	<B extends Behaviour<?, ?>> Optional<B> get(BehaviourType<B> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourGroup} will use.
	 * If no custom behaviour is registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The behaviour type
	 * @return The behaviour
	 * @throws NoSuchElementException if the behaviour is not present on this {@link BehaviourGroup}
	 */
	<B extends Behaviour<?, ?>> B require(BehaviourType<B> type);
}
