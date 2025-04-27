package net.hellheim.spongetools.custom.behaviour;

/**
 * Represents some action a {@link BehaviourHolder} can do.
 * 
 * @param <H> Behaviour holder
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
@FunctionalInterface
public interface Behaviour<H, R, A extends BehaviourArgs> {
	
	R call(A args);
}
