package net.hellheim.spongetools.custom.behaviour;

/**
 * Represents the wrapper around the actual behaviour holder
 * that does not directly implement {@link BehaviourHolder}.
 * 
 * @param <H> The type of the actual behaviour holder
 */
public interface BehaviourHolderProxy<H> extends BehaviourHolder {
	
	/**
	 * Returns the owner of this extension.
	 * 
	 * @return The owner of this extension
	 */
	H owner();
	
	/**
	 * 
	 * @param <H> The type of the actual behaviour holder
	 */
	interface Mutable<H, M extends Mutable<H, M>> extends BehaviourHolder.Mutable<H, M>, BehaviourHolderProxy<H> {
	}
}
