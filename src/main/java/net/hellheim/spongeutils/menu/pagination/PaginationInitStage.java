package net.hellheim.spongeutils.menu.pagination;

import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.type.ViewableInventory;

import net.hellheim.spongeutils.menu.Menu;

/**
 * Indicates when pagination in {@link Menu} should be created.
 * 
 * @see {@link #NONE}, {@link #BUILD}, {@link #OPEN}
 */
public enum PaginationInitStage {
	
	/**
	 * Indicates that pagination should not be created at all.
	 */
	NONE,
	
	/**
	 * Indicates that pagination should be created after {@link ViewableInventory} is built.
	 */
	BUILD,
	
	/**
	 * Indicates that pagination should be created after opening {@link Container} to the player.
	 */
	OPEN;
}
