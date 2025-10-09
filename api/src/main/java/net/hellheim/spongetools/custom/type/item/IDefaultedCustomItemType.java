package net.hellheim.spongetools.custom.type.item;

import java.util.Optional;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.DefaultedRegistryReference;

import net.hellheim.spongetools.object.DeferredValueContainer;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
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
	default DefaultedRegistryReference<ItemType> base() {
		return this.properties().base();
	}
	
	@Override
	default Optional<ItemDefinition> model() {
		return this.properties().model();
	}
	
	@Override
	default DeferredValueContainer getAsData() {
		return this.properties().getAsData();
	}
	
	@Override
	default ItemStackSnapshot icon() {
		return this.properties().icon();
	}
	
	@Override
	default ItemStackSnapshot ingredientIcon() {
		return this.properties().ingredientIcon();
	}
	
	@Override
	default ItemStackSnapshot getAsItemStackSnapshot() {
		return this.properties().getAsItemStackSnapshot();
	}
	
	default ItemBuilder builderOfIcon() {
		return ItemBuilder.of(this.icon());
	}
}
