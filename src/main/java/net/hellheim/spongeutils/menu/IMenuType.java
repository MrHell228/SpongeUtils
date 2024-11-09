package net.hellheim.spongeutils.menu;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.ContainerTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;
import org.spongepowered.api.item.inventory.menu.handler.InventoryCallbackHandler;
import org.spongepowered.api.item.inventory.type.ViewableInventory;

import net.hellheim.spongeutils.menu.handler.MenuInventoryCallbackHandler;
import net.hellheim.spongeutils.menu.pagination.Pagination;
import net.hellheim.spongeutils.menu.pagination.PaginationInitStage;
import net.kyori.adventure.text.Component;

/**
 * {@link IMenuType} is an object that represents current state of {@link Menu}. <br>
 * It corresponds to single {@link ViewableInventory} with all the required stuff to allow maximum customization. <br>
 * <br>
 * For easy configuration of {@link IMenuType}s, see {@link IDefaultedMenuType}.
 * 
 * @param <M> The {@link Menu}
 */
public interface IMenuType<M extends Menu<M>> {
	
	/**
	 * Returns {@link IMenuType} that should be treated as default previous for this {@link IMenuType}
	 * or <code>null</code> if there should be no default previous {@link IMenuType}.
	 * 
	 * @return Default previous {@link IMenuType}
	 */
	@Nullable IMenuType<M> defaultPrevious();
	
	/**
	 * Returns whether this {@link IMenuType} should be opened. <br>
	 * Called before {@link ViewableInventory inventory} is acquired via {@link #inventory(Menu)}. <br>
	 * Usually, if this method return <code>false</code>,
	 * different actions happen (e.g. {@link Menu#back()} or {@link Menu#close()}).
	 * 
	 * @param menu The {@link Menu}
	 * @return <code>True</code> if this {@link IMenuType} should be opened
	 */
	boolean test(M menu);
	
	/**
	 * Returns the {@link ContainerType} of inventory provided by {@link #inventory(Menu)}.
	 * 
	 * @return The type of inventory assosiated with this {@link IMenuType}
	 */
	@Nullable ContainerType inventoryType();
	
	/**
	 * Returns {@link ViewableInventory} that will be opened to the player. <br>
	 * 
	 * @param menu The {@link Menu}
	 * @return The {@link ViewableInventory inventory}
	 */
	ViewableInventory inventory(M menu);
	
	/**
	 * Returns whether opened {@link ViewableInventory inventory} should be readonly. <br>
	 * See {@link InventoryMenu#setReadOnly(boolean)}.
	 * 
	 * @param menu The {@link Menu}
	 * @return True if opened {@link ViewableInventory inventory} should be readonly 
	 */
	boolean isReadOnly(M menu);
	
	/**
	 * Returns {@link MenuInventoryCallbackHandler handlers} that will be registered for opened {@link ViewableInventory inventory}. <br>
	 * See {@link InventoryMenu#registerHandler(InventoryCallbackHandler)}
	 * 
	 * @return {@link MenuInventoryCallbackHandler Menu handlers}
	 */
	Iterable<MenuInventoryCallbackHandler<M>> handlers();
	
	/**
	 * Returns title that newly opened {@link Container} should have.
	 * 
	 * @param menu The {@link Menu}
	 * @return Title of the opened container
	 */
	Component title(M menu);
	
	/**
	 * Called after inventory is opened to the player to modify it if needed. <br>
	 * 
	 * Should be used when container has slots that are not present in {@link ViewableInventory inventory}
	 * returned by {@link #inventory(Menu)} (e.g. in {@link ContainerTypes#ANVIL}).
	 * 
	 * @param menu The {@link Menu}
	 * @param container The opened container
	 */
	void finalize(M menu, Container container);
	
	/**
	 * Returns {@link PaginationInitStage init stage} of pagination.
	 * 
	 * @return pagination init stage
	 */
	PaginationInitStage paginationStage();
	
	/**
	 * Constructs and returns the {@link Pagination}.
	 * 
	 * @param menu The {@link Menu}
	 * @param inventory The inventory to build pagination in
	 * @return The constructed pagination
	 * @throws UnsupportedOperationException If {@link #paginationStage(Menu)} returns {@link PaginationInitStage#NONE}
	 */
	Pagination pagination(M menu, Inventory inventory);
}
