package net.hellheim.spongeutils.source.solid.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.ItemUtil;
import net.hellheim.spongeutils.object.CachedSupplier;
import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemTypeSource extends IItemSource {
	
	static ItemTypeSource wrapType(final Supplier<ItemType> typeSupplier) {
		return typeSupplier::get;
	}
	
	static ItemTypeSource wrapTypeCached(final Supplier<ItemType> typeSupplier) {
		return CachedSupplier.of(typeSupplier)::get;
	}
	
	@Override
	default ItemStack getAsItemStack() {
		return ItemUtil.stackOf(this.getAsItemType());
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.getAsItemStack().asImmutable();
	}
	
	@Override
	default ItemStackLike getAsItemStackLike() {
		return this.getAsItemStack();
	}
	
	@Override
	default ItemBuilder itemBuilder() {
		return this.builderOfType();
	}
}
