package net.hellheim.spongetools.custom.behaviour;

/**
 * Callbacks are used to add additional behaviour on top of the default one.
 * 
 * @param <H> Behaviour holder
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
public interface BehaviourCallback {
	
	@FunctionalInterface
	interface Action<H, A extends BehaviourArgs> extends BehaviourCallback {
		
		void call(H holder, Behaviour.Action<A> origin, A args);
	}
	
	@FunctionalInterface
	interface Product<H, R, A extends BehaviourArgs> extends BehaviourCallback {
		
		R call(H holder, Behaviour.Product<R, A> origin, A args);
	}
}
