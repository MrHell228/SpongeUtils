package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;

import com.mojang.serialization.MapCodec;

/**
 * Represents some action a {@link BehaviourHolder} can do.
 */
public interface Behaviour {
	
	interface Extendable<C extends Callback<?>> extends Behaviour {
	}
	
	/**
	 * Callbacks are used to add additional behaviour on top of the default one.
	 * 
	 * @param <R> The return type of this callback
	 */
	interface Callback<R> {
	}
	
	/**
	 * TODO
	 * @param <R> The return type of this callback
	 */
	interface SerializableCallback<R> extends Callback<R> {
		
		default Optional<? extends MapCodec<? extends SerializableCallback<R>>> codec() {
			return Optional.empty();
		}
	}
}
