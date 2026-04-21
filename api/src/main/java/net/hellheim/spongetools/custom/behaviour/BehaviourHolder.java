package net.hellheim.spongetools.custom.behaviour;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents something that can have {@link Behaviour}s.
 */
public interface BehaviourHolder {
	
	/**
	 * Returns whether the given behaviour type is registered for this {@link BehaviourHolder}.
	 * 
	 * @param type The behaviour type
	 * @return True if this holder supports the given type
	 */
	boolean supports(BehaviourType<?> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} has, if present.
	 * If no behaviour callbacks are registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The behaviour type
	 * @return The behaviour, if present
	 */
	<B extends Behaviour<?, ?>> Optional<B> get(BehaviourType<B> type);
	
	/**
	 * Returns the actual behaviour this {@link BehaviourHolder} has.
	 * If no custom behaviour is registered, this would be the "vanilla" behaviour.
	 * 
	 * @param type The behaviour type
	 * @return The behaviour
	 * @throws NoSuchElementException if the behaviour is not present for this {@link BehaviourHolder}
	 */
	<B extends Behaviour<?, ?>> B require(BehaviourType<B> type);
	
	interface Defaulted extends BehaviourHolder {
		
		Object getAsActualBehaviourHolder();
		
		@Override
		default boolean supports(final BehaviourType<?> type) {
			return BehaviourManager.get().supports(this.getAsActualBehaviourHolder(), type);
		}
		
		@Override
		default <B extends Behaviour<?, ?>> Optional<B> get(final BehaviourType<B> type) {
			return BehaviourManager.get().get(this.getAsActualBehaviourHolder(), type);
		}
		
		@Override
		default <B extends Behaviour<?, ?>> B require(final BehaviourType<B> type) {
			return BehaviourManager.get().require(this.getAsActualBehaviourHolder(), type);
		}
	}
	
	interface Proxy extends BehaviourHolder {
		
		BehaviourHolder getAsBehaviourHolder();
		
		@Override
		default boolean supports(final BehaviourType<?> type) {
			return this.getAsBehaviourHolder().supports(type);
		}
		
		@Override
		default <B extends Behaviour<?, ?>> Optional<B> get(final BehaviourType<B> type) {
			return this.getAsBehaviourHolder().get(type);
		}
		
		@Override
		default <B extends Behaviour<?, ?>> B require(final BehaviourType<B> type) {
			return this.getAsBehaviourHolder().require(type);
		}
	}
}
