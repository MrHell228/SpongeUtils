package net.hellheim.spongetools.custom.type.entity;

import java.util.List;

import org.spongepowered.api.entity.Entity;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;

public interface EntityExtension extends BehaviourHolder {
	
	static EntityExtension getFor(final Entity entity) {
		return (EntityExtension) entity;
	}
	
	/**
	 * Returns the owner entity of this extension.
	 * 
	 * @return The owner entity
	 */
	Entity owner();
	
	/**
	 * Returns all the entities to display to the client.
	 * 
	 * @return The entity displays
	 */
	List<EntityDisplay> display();
	
	/**
	 * Performs the entity action by its id on the client. <br>
	 * Vanilla actions are catalogued in {@link EntityClientActions}. <br>
	 * Only actions supported by entity will have effect on client.
	 * 
	 * @param action The entity action id
	 */
	void act(byte action);
}
