package net.hellheim.spongetools.custom.type.block;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.type.CustomTypeArchetype;
import net.hellheim.spongetools.function.TriFunction;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * {@link CustomTypeArchetype} of {@link BlockType} used for {@link BlockTypeBuilder}.
 */
@CatalogedBy(BlockArchetypes.class)
public record BlockTypeArchetype(
		Optional<BlockTypeArchetype> parent,
		Class<?> baseClass,
		Set<TypedKey<?>> requiredKeys,
		BiConsumer<BlockType, TypedKeyMap.Mutable> contextExtractor,
		TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<BlockType>, BlockType> assembler
		) implements CustomTypeArchetype.TypeBased<BlockType, BlockState, BlockTypeArchetype> {
	
	public BlockTypeArchetype(
		final Optional<BlockTypeArchetype> parent,
		final Class<?> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<BlockType, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<BlockType>, BlockType> assembler
	) {
		CustomTypeArchetype.validate(BlockType.class, baseClass, parent);
		this.parent = parent;
		this.baseClass = baseClass;
		this.requiredKeys = Objects.requireNonNull(requiredKeys, "requiredKeys");
		this.contextExtractor = Objects.requireNonNull(contextExtractor, "contextExtractor");
		this.assembler = Objects.requireNonNull(assembler, "assembler");
	}
	
	public static DefaultedRegistryType<BlockTypeArchetype> registry() {
		return SpongeTools.Registries.BLOCK_TYPE_ARCHETYPE;
	}
	
	/**
	 * @param block The {@link BlockType}
	 * @return The most appropriate known {@link BlockTypeArchetype}
	 * @see CustomTypeArchetype#forType(org.spongepowered.api.registry.Registry, CustomTypeArchetype, Object)
	 */
	public static BlockTypeArchetype forType(final BlockType block) {
		return CustomTypeArchetype.forType(BlockArchetypes.registry(), BlockArchetypes.DEFAULT.get(), block);
	}
	
	@SuppressWarnings("unchecked")
	public static <I> BlockTypeArchetype of(
		final Optional<BlockTypeArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<BlockType>, I> assembler
	) {
		return new BlockTypeArchetype(
				parent, baseClass, requiredKeys,
				(item, context) -> contextExtractor.accept((I) item, context),
				(data, context, behaviour) -> (BlockType) assembler.apply(data, context, behaviour)
				);
	}
}
