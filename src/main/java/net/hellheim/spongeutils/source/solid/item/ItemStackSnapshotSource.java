package net.hellheim.spongeutils.source.solid.item;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemStackSnapshotSource extends IItemSource {
	
	@Override
	default ItemType getAsItemType() {
		return this.getAsItemStackSnapshot().type();
	}
	
	@Override
	default ItemStack getAsItemStack() {
		return this.getAsItemStackSnapshot().createStack();
	}
	
	@Override
	default ItemBuilder itemBuilder() {
		return this.builderOfSnapshot();
	}
}
