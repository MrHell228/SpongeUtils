package net.hellheim.spongetools.proxy.solid.item;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.object.ItemBuilder;

public interface IItemProxy {
	
	ItemType getAsItemType();
	
	/**
	 * @return The new ItemStack
	 */
	ItemStack getAsItemStack();
	
	ItemStackSnapshot getAsItemStackSnapshot();
	
	ItemStackLike getAsItemStackLike();
	
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
