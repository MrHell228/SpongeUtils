package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;

public final class ItemTypeBuilderImpl
		extends CustomTypeBuilderImpl.WithData<ItemType, ItemStackLike, ItemArchetype, ItemTypeBuilder>
		implements ItemTypeBuilder {
	
	public ItemTypeBuilderImpl() {
		this.reset();
	}
	
	@Override
	protected ItemArchetype baseArchetype() {
		return ItemTypeUtil.Archetypes.DEFAULT;
	}
	
	@Override
	protected DefaultedRegistryType<ItemArchetype> archetypeRegistry() {
		return ItemArchetype.registry();
	}
	
	@Override
	protected void extractData(final ItemType value) {
		// Most data only registered for ItemStack (Should be fixed in Sponge to also support ItemType)
		this.addFrom(ItemStack.of(value));
	}
	
	@Override
	protected ItemType build0() {
		// TODO Pass behaviour
		return this.archetype.assembler().apply(this.data.asImmutableManipulator(), this.context, null);
	}
}
