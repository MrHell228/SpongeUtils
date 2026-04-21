package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.entity.Entity;

public interface EntityDisplay {
	
	/**
	 * Returns the type used to create {@link #display()}.
	 * 
	 * @return The entity display type
	 */
	EntityDisplayType type();
	
	/**
	 * Returns the owner entity of its display entities.
	 * 
	 * @return The owner entity
	 */
	Entity owner();
	
	/**
	 * Return the entity client sees.
	 * 
	 * @return The display entity
	 */
	Entity display();
}
