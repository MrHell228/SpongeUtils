package net.hellheim.spongetools.custom.behaviour;

/**
 * Callbacks are used to add additional behaviour on top of the default one.
 * 
 * @param <H> Behaviour holder
 * @param <R> Return type
 * @param <A> Behaviour arguments
 * @param <B> Behaviour
 */
@FunctionalInterface
public interface BehaviourCallback<H, R, A extends BehaviourArgs, B extends Behaviour<H, R, A>> {
	
	R call(H holder, B origin, A args);
}
