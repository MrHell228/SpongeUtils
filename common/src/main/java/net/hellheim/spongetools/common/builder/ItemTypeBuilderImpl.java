package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.object.ValueSetBuilder;

public final class ItemTypeBuilderImpl extends CustomTypeBuilderImpl<ItemType, ItemArchetype, ItemTypeBuilder>
		implements
		ItemTypeBuilder,
		DataOperator.Proxy<ItemTypeBuilder>,
		TypedKeyMap.Operator.MutableProxy<ItemTypeBuilder> {
	
	private ValueSetBuilder data;
	// TODO behaviour
	
	@Override
	public DataOperator<?> getAsData() {
		return this.data;
	}
	
	@Override
	public ItemTypeBuilder from(final ItemType item) {
		super.from(item);
		
		// Most data only registered for ItemStack (Should be fixed in Sponge to also support ItemType)
		this.addFrom(ItemStack.of(item));
		
		// In perfect scenario, all the Items that has unique behaviour should have their own archetypes.
		// Therefore behaviour should not be copied here since it should already be applied from archetype.
		
		return this;
	}
	
	@Override
	public ItemTypeBuilder reset() {
		super.reset();
		this.data = new ValueSetBuilder();
		// TODO clear behaviour
		return this;
	}
	
	@Override
	public ItemType build0() {
		// TODO Pass behaviour
		return this.archetype.assembler().apply(this.data.asImmutableManipulator(), this.context, null);
	}
	
	@Override
	protected ItemArchetype baseArchetype() {
		return ItemTypeUtil.Archetypes.PLAIN;
	}
	
	@Override
	protected DefaultedRegistryType<ItemArchetype> archetypeRegistry() {
		return ItemArchetype.registry();
	}
}
