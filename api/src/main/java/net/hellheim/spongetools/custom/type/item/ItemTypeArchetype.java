package net.hellheim.spongetools.custom.type.item;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.type.CustomTypeArchetype;
import net.hellheim.spongetools.function.TriFunction;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * {@link CustomTypeArchetype} of {@link ItemType} used for {@link ItemTypeBuilder}.
 */
@CatalogedBy(ItemArchetypes.class)
public record ItemTypeArchetype(
		Optional<ItemTypeArchetype> parent,
		Class<?> baseClass,
		Set<TypedKey<?>> requiredKeys,
		BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
		) implements CustomTypeArchetype.TypeBased<ItemType, ItemStack, ItemTypeArchetype> {
	
	public ItemTypeArchetype(
		final Optional<ItemTypeArchetype> parent,
		final Class<?> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
	) {
		CustomTypeArchetype.validate(ItemType.class, baseClass, parent);
		this.parent = parent;
		this.baseClass = baseClass;
		this.requiredKeys = Objects.requireNonNull(requiredKeys, "requiredKeys");
		this.contextExtractor = Objects.requireNonNull(contextExtractor, "contextExtractor");
		this.assembler = Objects.requireNonNull(assembler, "assembler");
	}
	
	public static DefaultedRegistryType<ItemTypeArchetype> registry() {
		return SpongeTools.Registries.ITEM_TYPE_ARCHETYPE;
	}
	
	/**
	 * @param item The {@link ItemType}
	 * @return The most appropriate known {@link ItemTypeArchetype}
	 * @see CustomTypeArchetype#forType(org.spongepowered.api.registry.Registry, CustomTypeArchetype, Object)
	 */
	public static ItemTypeArchetype forType(final ItemType item) {
		return CustomTypeArchetype.forType(ItemArchetypes.registry(), ItemArchetypes.DEFAULT.get(), item);
	}
	
	@SuppressWarnings("unchecked")
	public static <I> ItemTypeArchetype of(
		final Optional<ItemTypeArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, I> assembler
	) {
		return new ItemTypeArchetype(
				parent, baseClass, requiredKeys,
				(item, context) -> contextExtractor.accept((I) item, context),
				(data, context, behaviour) -> (ItemType) assembler.apply(data, context, behaviour)
				);
	}
	
	@Override
	public ItemType extractType(final ItemStack instance) {
		return instance.type();
	}
}
