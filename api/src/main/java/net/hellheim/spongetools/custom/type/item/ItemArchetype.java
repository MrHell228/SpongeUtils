package net.hellheim.spongetools.custom.type.item;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.DefaultedRegistryValue;
import org.spongepowered.api.util.annotation.CatalogedBy;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.function.TriFunction;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Represents the core of the {@link ItemType}. <br>
 * The archetype can declare required/supported context keys and supported behaviour.
 * 
 * @see ItemTypeBuilder
 */
@CatalogedBy(ItemArchetypes.class)
public record ItemArchetype(
		Optional<ItemArchetype> parent,
		Class<?> baseClass,
		Set<TypedKey<?>> requiredKeys,
		BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
		) implements DefaultedRegistryValue<ItemArchetype> {
	
	public ItemArchetype(
		final Optional<ItemArchetype> parent,
		final Class<?> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<ItemType, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<ItemType>, ItemType> assembler
	) {
		if (!ItemType.class.isAssignableFrom(Objects.requireNonNull(baseClass, "baseClass"))) {
			throw new IllegalArgumentException(String.format(
					"Provided base class must be a subclass of ItemType: %s",
					baseClass));
		}
		
		Objects.requireNonNull(parent, "parent").ifPresent(archetype -> {
			if (!archetype.baseClass.isAssignableFrom(baseClass)) {
				throw new IllegalArgumentException(String.format(
						"Parent's base class (%s) must be a parent class of the given base class (%s)",
						archetype.baseClass, baseClass));
			}
		});
		
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
	 * Returns the most appropriate known {@link ItemArchetype} for the given {@link ItemType}.
	 * 
	 * @param item The item type
	 * @return The item archetype
	 */
	public static ItemArchetype forType(final ItemType item) {
		ItemArchetype archetype = ItemArchetypes.PLAIN.get();
		for (final ItemArchetype arch : ItemArchetypes.registry().stream().toList()) {
			if (arch.baseClass().isInstance(item)
					// Choosing the most specific item class
					&& archetype.baseClass().isAssignableFrom(arch.baseClass())) {
				archetype = arch;
			}
		}
		return archetype;
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
	
	
	
	public Stream<TypedKey<?>> cumulativeRequiredKeys() {
		return this.parent.isEmpty()
				? this.requiredKeys.stream()
				: Stream.concat(this.parent.get().cumulativeRequiredKeys(), this.requiredKeys.stream());
	}
	
	public BiConsumer<ItemType, TypedKeyMap.Mutable> cumulativeContextExtractor() {
		return this.parent.isEmpty()
				? this.contextExtractor
				: this.parent.get().cumulativeContextExtractor().andThen(this.contextExtractor);
	}
}
