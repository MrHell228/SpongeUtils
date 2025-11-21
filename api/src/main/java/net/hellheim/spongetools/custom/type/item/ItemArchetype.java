package net.hellheim.spongetools.custom.type.item;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.type.CustomArchetype;
import net.hellheim.spongetools.function.TriFunction;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * @see CustomArchetype
 * @see ItemTypeBuilder
 */
@CatalogedBy(ItemArchetypes.class)
public record ItemArchetype(
		Optional<ItemArchetype> parent,
		Class<?> baseClass,
		Set<TypedKey<?>> requiredKeys,
		BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
		) implements CustomArchetype<ItemType, ItemArchetype> {
	
	public ItemArchetype(
		final Optional<ItemArchetype> parent,
		final Class<?> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
	) {
		CustomArchetype.validate(ItemType.class, baseClass, parent);
		this.parent = parent;
		this.baseClass = baseClass;
		this.requiredKeys = Objects.requireNonNull(requiredKeys, "requiredKeys");
		this.contextExtractor = Objects.requireNonNull(contextExtractor, "contextExtractor");
		this.assembler = Objects.requireNonNull(assembler, "assembler");
	}
	
	public static DefaultedRegistryType<ItemArchetype> registry() {
		return SpongeTools.Registries.ITEM_ARCHETYPE;
	}
	
	/**
	 * @param item The {@link ItemType}
	 * @return The most appropriate known {@link ItemArchetype}
	 * @see CustomArchetype#forType(org.spongepowered.api.registry.Registry, CustomArchetype, Object)
	 */
	public static ItemArchetype forType(final ItemType item) {
		return CustomArchetype.forType(ItemArchetypes.registry(), ItemArchetypes.DEFAULT.get(), item);
	}
	
	@SuppressWarnings("unchecked")
	public static <I> ItemArchetype of(
		final Optional<ItemArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, I> assembler
	) {
		return new ItemArchetype(
				parent, baseClass, requiredKeys,
				(item, context) -> contextExtractor.accept((I) item, context),
				(data, context, behaviour) -> (ItemType) assembler.apply(data, context, behaviour)
				);
	}
}
