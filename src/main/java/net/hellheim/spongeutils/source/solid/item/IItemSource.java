package net.hellheim.spongeutils.source.solid.item;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.object.ItemBuilder;

public interface IItemSource {
	
	ItemType getAsItemType();
	
	/**
	 * @return The new ItemStack
	 */
	ItemStack getAsItemStack();
	
	ItemStackSnapshot getAsItemStackSnapshot();
	
	default ItemBuilder builderOfType() {
		return ItemBuilder.of(this.getAsItemType());
	}
	
	default ItemBuilder builderOfStack() {
		return ItemBuilder.of(this);
	}
	
	default ItemBuilder builderOfSnapshot() {
		return ItemBuilder.of(this.getAsItemStackSnapshot());
	}
	
	default ItemBuilder itemBuilder() {
		return this.builderOfStack();
	}
}
