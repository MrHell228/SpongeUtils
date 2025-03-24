package net.hellheim.spongetools.custom.item;

import java.util.Optional;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.custom.item.model.Item;
import net.hellheim.spongetools.object.DeferredValueContainer;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.kyori.adventure.text.Component;

public interface IDefaultedCustomItemType extends CustomItemType, ValueContainerProxy {
	
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
	default DeferredValueContainer getAsData() {
		return this.properties().getAsData();
	}
	
	@Override
	default ItemStackSnapshot getAsIcon() {
		return this.properties().getAsIcon();
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.properties().getAsItemStackSnapshot();
	}
}
