package net.hellheim.spongetools.proxy.solid.item;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.object.ItemBuilder;

public interface DirectItemStackProxy extends IItemProxy {
	
	ItemStack getAsDirectItemStack();
	
	@Override
	default ItemType getAsItemType() {
		return this.getAsDirectItemStack().type();
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.getAsDirectItemStack().asImmutable();
	}
	
	@Override
	default ItemStack getAsItemStack() {
		return this.getAsDirectItemStack().copy();
	}
	
	@Override
	default ItemStackLike getAsItemStackLike() {
		return this.getAsDirectItemStack();
	}
	
	default ItemBuilder builderOfDirectStack() {
		return ItemBuilder.of(this.getAsDirectItemStack());
	}
}
