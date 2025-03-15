package net.hellheim.spongetools.custom.item;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.custom.item.model.Item;
import net.hellheim.spongetools.proxy.solid.data.DataHolderProxy;
import net.kyori.adventure.text.Component;

public interface IDefaultedCustomItemType extends CustomItemType, DataHolderProxy {
	
	CustomItemTypeProperties properties();
	
	@Override
	default ResourceKey key() {
		return this.properties().key();
	}
	
	@Override
	default Component asComponent() {
		return this.properties().asComponent();
	}
	
	@Override
	default ItemType base() {
		return this.properties().base();
	}
	
	@Override
	default Optional<Item> model() {
		return this.properties().model();
	}
	
	@Override
	default Set<Value<?>> data() {
		return this.properties().data();
	}
	
	@Override
	default ItemStackSnapshot getAsIcon() {
		return this.properties().getAsIcon();
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.properties().getAsItemStackSnapshot();
	}
	
	@Override
	default DataHolder getAsDataHolder() {
		return this.properties().getAsDataHolder();
	}
}
