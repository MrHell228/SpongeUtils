package net.hellheim.spongeutils.source.solid.item;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemStackSource extends IItemSource {
	
	@Override
	default ItemType getAsItemType() {
		return this.getAsItemStack().type();
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.getAsItemStack().createSnapshot();
	}
	
	@Override
	default ItemBuilder itemBuilder() {
		return this.builderOfStack();
	}
}
