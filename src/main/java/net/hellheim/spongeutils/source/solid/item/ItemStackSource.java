package net.hellheim.spongeutils.source.solid.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.ItemUtil;
import net.hellheim.spongeutils.object.CachedSupplier;
import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemStackSource extends IItemSource {
	
	static ItemStackSource wrapType(final Supplier<ItemType> typeSupplier) {
		return () -> ItemUtil.stackOf(typeSupplier);
	}
	
	static ItemStackSource wrapTypeCached(final Supplier<ItemType> typeSupplier) {
		final CachedSupplier<ItemType> cached = CachedSupplier.of(typeSupplier);
		return () -> ItemUtil.stackOf(cached);
	}
	
	@Override
	default ItemType getAsItemType() {
		return this.getAsItemStack().type();
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
		return this.builderOfStack();
	}
}
