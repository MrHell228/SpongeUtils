package net.hellheim.spongeutils.menu.handler;

import org.spongepowered.api.item.inventory.menu.handler.InventoryCallbackHandler;

import net.hellheim.spongeutils.menu.Menu;

public interface MenuInventoryCallbackHandler<M extends Menu<M>> {
	
	InventoryCallbackHandler asDefaultHandler(M menu);
}
