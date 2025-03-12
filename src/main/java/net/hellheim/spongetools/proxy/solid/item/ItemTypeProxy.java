package net.hellheim.spongetools.proxy.solid.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.object.CachedSupplier;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.util.ItemUtil;

public interface ItemTypeProxy extends IItemProxy {
	
	static ItemTypeProxy wrapType(final Supplier<ItemType> typeSupplier) {
		return typeSupplier::get;
	}
	
	static ItemTypeProxy wrapTypeCached(final Supplier<ItemType> typeSupplier) {
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
