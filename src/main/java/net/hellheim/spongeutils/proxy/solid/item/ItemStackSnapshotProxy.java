package net.hellheim.spongeutils.proxy.solid.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;

import net.hellheim.spongeutils.ItemUtil;
import net.hellheim.spongeutils.object.CachedSupplier;
import net.hellheim.spongeutils.object.ItemBuilder;

public interface ItemStackSnapshotProxy extends IItemProxy {
	
	static ItemStackSnapshotProxy wrapType(final Supplier<ItemType> typeSupplier) {
		return () -> ItemUtil.snapshotOf(typeSupplier);
	}
	
	static ItemStackSnapshotProxy wrapTypeCached(final Supplier<ItemType> typeSupplier) {
		return CachedSupplier.of(() -> ItemUtil.snapshotOf(typeSupplier))::get;
	}
	
	@Override
	default ItemType getAsItemType() {
		return this.getAsItemStackSnapshot().type();
	}
	
	@Override
	default ItemStack getAsItemStack() {
		return this.getAsItemStackSnapshot().asMutable();
	}
	
	@Override
	default ItemStackLike getAsItemStackLike() {
		return this.getAsItemStackSnapshot();
	}
	
	@Override
	default ItemBuilder itemBuilder() {
		return this.builderOfSnapshot();
	}
}
