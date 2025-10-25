package net.hellheim.spongetools.common.util;

import java.util.Objects;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.object.ValueSetBuilder;

public final class ItemTypeBuilderImpl implements
		ItemTypeBuilder,
		DataOperator.Proxy<ItemTypeBuilder>,
		TypedKeyMap.Operator.MutableProxy<ItemTypeBuilder> {
	
	private ItemArchetype archetype;
	private final ValueSetBuilder data = new ValueSetBuilder();
	private final TypedKeyMap.Impl.Mutable context = TypedKeyMap.create();
	// TODO behaviour
	
	public ItemTypeBuilderImpl() {
		this.reset();
	}
	
	@Override
	public DataOperator<?> getAsData() {
		return this.data;
	}
	
	@Override
	public TypedKeyMap.Mutable context() {
		return this.context;
	}
	
	@Override
	public ItemTypeBuilder archetype(final ItemArchetype archetype) {
		this.archetype = Objects.requireNonNull(archetype, "archetype");
		return this;
	}
	
	@Override
	public ItemTypeBuilder from(final ItemType item) {
		Objects.requireNonNull(item, "item");
		this.reset();
		
		this.archetype = ItemArchetype.forType(item);
		
		// Most data only registered for ItemStack (Should be fixed in Sponge to also support ItemType)
		this.addFrom(ItemStack.of(item));
		
		this.archetype.cumulativeContextExtractor().accept(item, this.context);
		
		// In perfect scenario, all the Items that has unique behaviour should have their own archetypes.
		// Therefore behaviour should not be copied here since it should already be applied from archetype.
		
		return this;
	}
	
	@Override
	public ItemTypeBuilder reset() {
		this.archetype = ItemTypeUtil.Archetypes.PLAIN;
		this.data.reset();
		this.context.clear();
		// TODO clear behaviour
		return this;
	}
	
	@Override
	public ItemType build() {
		final String missingKeys = this.archetype.cumulativeRequiredKeys()
				.filter(key -> !this.context.has(key))
				.map(TypedKey::key)
				.map(ResourceKey::asString)
				.collect(Collectors.joining(", "));
		
		if (!missingKeys.isEmpty()) {
			throw new IllegalStateException(String.format(
					"Archetype %s requires keys that are not present: %s",
					this.archetype.key(ItemArchetype.registry()), missingKeys));
		}
		
		// TODO Pass behaviour
		return this.archetype.assembler().apply(this.data.asImmutableManipulator(), this.context, null);
	}
}
