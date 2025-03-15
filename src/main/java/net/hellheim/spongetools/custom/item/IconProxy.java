package net.hellheim.spongetools.custom.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;

/**
 * Proxy that has some generic {@link ItemStackSnapshot}
 * which is assumed as default display item.
 */
public interface IconProxy {
	
	static IconProxy wrapType(final Supplier<ItemType> typeSupplier) {
		return ItemStackSnapshotProxy.wrapType(typeSupplier)::getAsItemStackSnapshot;
	}
	
	static IconProxy wrapTypeCached(final Supplier<ItemType> typeSupplier) {
		return ItemStackSnapshotProxy.wrapTypeCached(typeSupplier)::getAsItemStackSnapshot;
	}
	
	ItemStackSnapshot getAsIcon();
	
	default ItemBuilder builderOfIcon() {
		return ItemBuilder.of(this.getAsIcon());
	}
}
