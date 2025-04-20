package net.hellheim.spongetools.custom.behaviour;

@FunctionalInterface
public interface BehaviourMerger<C extends Behaviour.Callback<?>> {
	
	/**
	 * Wraps the second behaviour around the first behaviour. <br>
	 * Means that first behaviour becomes the "original" for the second one.
	 * 
	 * @param b1 The "original" behaviour
	 * @param b2 The "wrapper" behaviour
	 * @return The merged behaviour
	 */
	C merge(C b1, C b2);
}
