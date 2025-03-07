package net.hellheim.spongeutils.proxy.solid.item;

import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.object.ItemBuilder;

public interface DirectItemStackProxy extends ItemStackProxy {
	
	ItemStack getAsDirectItemStack();
	
	@Override
	default ItemStack getAsItemStack() {
		return this.getAsDirectItemStack().copy();
	}
	
	default ItemBuilder builderOfDirectStack() {
		return ItemBuilder.of(this.getAsDirectItemStack());
	}
}
