package net.hellheim.spongetools.custom.behaviour;

/**
 * Represents some action a {@link BehaviourHolder} can do.
 * 
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
@FunctionalInterface
public interface Behaviour<R, A extends BehaviourArgs> {
	
	R call(A args);
}
