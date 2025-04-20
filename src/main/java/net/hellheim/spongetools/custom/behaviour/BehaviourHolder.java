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
	default boolean supports(final BehaviourType<?> type) {
		return BehaviourManager.get().supports(this.unwrap(), type);
	}
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} will use, if present.
	 * If no behaviour callbacks are registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The type of the behaviour
	 * @return The behaviour, if present
	 */
	default <B extends Behaviour> Optional<B> get(final BehaviourType<B> type) {
		return BehaviourManager.get().get(this.unwrap(), type);
	}
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} will use.
	 * If no custom behaviour is registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The type of the behaviour
	 * @return The behaviour
	 * @throws NoSuchElementException if the type is not present on this {@link BehaviourHolder}
	 */
	default <B extends Behaviour> B require(final BehaviourType<B> type) {
		return this.get(type).orElseThrow(() -> new NoSuchElementException(String.format(
				"No behaviour of type %s is present for holder %s",
				type, this.unwrap()
				)));
	}
	
	private Object unwrap() {
		Object actualHolder = this;
		while (actualHolder instanceof final BehaviourHolderProxy<?> proxy) {
			actualHolder = proxy.owner();
		}
		return actualHolder;
	}
}
