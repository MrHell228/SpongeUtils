package net.hellheim.spongeutils.source.solid.item;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.ItemUtil;
import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemTypeSource extends IItemSource {
	
	@Override
	default ItemStack getAsItemStack() {
		return ItemUtil.stackOf(this.getAsItemType());
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.getAsItemStack().asImmutable();
	}
	
	@Override
	default ItemBuilder itemBuilder() {
		return this.builderOfType();
	}
}
