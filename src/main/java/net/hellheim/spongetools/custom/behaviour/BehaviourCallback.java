package net.hellheim.spongetools.custom.behaviour;

/**
 * Callbacks are used to add additional behaviour on top of the default one.
 * 
 * @param <H> Behaviour holder
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
@FunctionalInterface
public interface BehaviourCallback<H, R, A extends BehaviourArgs> {
	
	R call(H holder, Behaviour<R, A> origin, A args);
}
