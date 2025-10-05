package net.hellheim.spongetools.custom.type.item;

import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import com.google.common.base.Suppliers;

import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.hellheim.spongetools.proxy.solid.item.IItemProxy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

/**
 * Lazy initialized {@link EitherItemType}.
 */
public final class DeferredItemType implements
		Supplier<EitherItemType>,
		ResourceKeyed, ComponentLike, ValueContainerProxy, IItemProxy {
	
	private final Supplier<EitherItemType> type;
	
	private DeferredItemType(final Supplier<EitherItemType> type) {
		this.type = Suppliers.memoize(type::get);
	}
	
	public static final DeferredItemType common(final Supplier<ItemType> type) {
		return DeferredItemType.of(() -> EitherItemType.common(type));
	}
	
	public static final DeferredItemType common(final ItemType type) {
		return DeferredItemType.common(() -> type);
	}
	
	public static final DeferredItemType custom(final Supplier<CustomItemType> type) {
		return DeferredItemType.of(() -> EitherItemType.custom(type));
	}
	
	public static final DeferredItemType custom(final CustomItemType type) {
		return DeferredItemType.custom(() -> type);
	}
	
	public static final DeferredItemType of(final ItemStackLike stack) {
		return DeferredItemType.of(() -> EitherItemType.of(stack));
	}
	
	public static final DeferredItemType of(final IItemProxy proxy) {
		return DeferredItemType.of(() -> EitherItemType.of(proxy));
	}
	
	public static final DeferredItemType of(final Supplier<EitherItemType> type) {
		return new DeferredItemType(type);
	}
	
	@Override
	public EitherItemType get() {
		return this.type.get();
	}
	
	@Override
	public ResourceKey key() {
		return this.get().key();
	}
	
	@Override
	public Component asComponent() {
		return this.get().asComponent();
	}
	
	@Override
	public ValueContainer getAsData() {
		return this.get().getAsData();
	}
	
	@Override
	public ItemType getAsItemType() {
		return this.get().getAsItemType();
	}
	
	@Override
	public ItemStack getAsItemStack() {
		return this.get().getAsItemStack();
	}
	
	@Override
	public ItemStackSnapshot getAsItemStackSnapshot() {
		return this.get().getAsItemStackSnapshot();
	}
	
	@Override
	public ItemStackLike getAsItemStackLike() {
		return this.get().getAsItemStackLike();
	}
	
	@Override
	public ItemBuilder itemBuilder() {
		return this.get().itemBuilder();
	}
}
