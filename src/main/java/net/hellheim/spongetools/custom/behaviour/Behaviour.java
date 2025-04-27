package net.hellheim.spongetools.custom.behaviour;

/**
 * Represents something a {@link BehaviourHolder} can do.
 * 
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
public interface Behaviour {
	
	@FunctionalInterface
	interface Action<A extends BehaviourArgs> extends Behaviour {
		
		void call(A args);
	}
	
	@FunctionalInterface
	interface Product<R, A extends BehaviourArgs> extends Behaviour {
		
		R call(A args);
	}
}
