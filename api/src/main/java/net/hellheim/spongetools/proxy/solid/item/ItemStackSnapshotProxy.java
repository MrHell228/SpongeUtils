package net.hellheim.spongetools.proxy.solid.item;

import java.util.function.Supplier;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import com.google.common.base.Suppliers;

import net.hellheim.spongetools.object.CachedSupplier;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.util.ItemUtil;

public interface ItemStackSnapshotProxy extends IItemProxy {
	
	static ItemStackSnapshotProxy wrapType(final Supplier<ItemType> typeSupplier) {
		return () -> ItemUtil.snapshotOf(typeSupplier);
	}
	
	static ItemStackSnapshotProxy wrapTypeCached(final Supplier<ItemType> typeSupplier) {
		return CachedSupplier.of(() -> ItemUtil.snapshotOf(typeSupplier))::get;
	}
	
	static ItemStackSnapshotProxy of(final ItemStackLike stack) {
		final ItemStackSnapshot snapshot = stack.asImmutable();
		return () -> snapshot;
	}
	
	static ItemStackSnapshotProxy of(final Supplier<? extends ItemStackLike> stack) {
		return Suppliers.memoize(() -> stack.get().asImmutable())::get;
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
