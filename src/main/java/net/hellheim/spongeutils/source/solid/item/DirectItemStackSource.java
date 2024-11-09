package net.hellheim.spongeutils.source.solid.item;

import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.object.ItemBuilder;

public interface DirectItemStackSource extends ItemStackSource {
	
	ItemStack getAsDirectItemStack();
	
	@Override
	default ItemStack getAsItemStack() {
		return this.getAsDirectItemStack().copy();
	}
	
	default ItemBuilder builderOfDirectStack() {
		return ItemBuilder.of(this.getAsDirectItemStack());
	}
}
