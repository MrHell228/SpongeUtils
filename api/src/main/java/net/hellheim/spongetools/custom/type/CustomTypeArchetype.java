package net.hellheim.spongetools.custom.type;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.spongepowered.api.registry.DefaultedRegistryValue;
import org.spongepowered.api.registry.Registry;

import net.hellheim.spongetools.custom.type.block.BlockTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeArchetype;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Represents the core of the custom type. <br>
 * The archetype can declare required/supported context keys and supported behaviour.
 * 
 * @see CustomTypeBuilder
 * @see ItemTypeArchetype
 * @see BlockTypeArchetype
 * @see EntityTypeArchetype
 * 
 * @param <T> The type of the value this archetype represents
 * @param <A> The type of this archetype
 */
public interface CustomTypeArchetype<T, A extends CustomTypeArchetype<T, A>> extends DefaultedRegistryValue<A> {
	
	/**
	 * Validates the archetype's base class against the root class and the archetype's parent archetype.
	 * 
	 * @param rootClass The root class of all archetypes' base classes
	 * @param baseClass The base class to validate
	 * @param parent The parent archetype
	 */
	static void validate(
		final Class<?> rootClass, final Class<?> baseClass, final Optional<? extends CustomTypeArchetype<?, ?>> parent
	) {
		if (!rootClass.isAssignableFrom(Objects.requireNonNull(baseClass, "baseClass"))) {
			throw new IllegalArgumentException(String.format(
					"Provided base class (%s) must be a subclass of root class (%s)",
					baseClass, rootClass));
		}
		
		Objects.requireNonNull(parent, "parent").ifPresent(archetype -> {
			if (!archetype.baseClass().isAssignableFrom(baseClass)) {
				throw new IllegalArgumentException(String.format(
						"Parent's base class (%s) must be a parent class of the given base class (%s)",
						archetype.baseClass(), baseClass));
			}
		});
	}
	
	/**
	 * Returns the most appropriate known {@link CustomTypeArchetype} for the given <code>type</code>.
	 * 
	 * @param <T> The type of values archetype exists for
	 * @param <A> The type of archetype
	 * @param archetypes The known archetypes
	 * @param baseArchetype The base archetype
	 * @param type The type
	 * @return The most specific archetype
	 */
	static <T, A extends CustomTypeArchetype<T, A>> A forType(
		final Registry<A> archetypes, final A baseArchetype, final T type
	) {
		A archetype = baseArchetype;
		for (final A arch : archetypes.stream().toList()) {
			if (arch.baseClass().isInstance(type)
					&& archetype.baseClass().isAssignableFrom(arch.baseClass())) {
				archetype = arch;
			}
		}
		return archetype;
	}
	
	/**
	 * Returns the parent archetype of this archetype. <br>
	 * Archetype inherits all the supported and required features of the parent.
	 * 
	 * @return The parent archetype
	 */
	Optional<A> parent();
	
	/**
	 * Returns the base value class this archetype is based on.
	 * 
	 * @return The base class
	 */
	Class<?> baseClass();
	
	/**
	 * Returns the {@link TypedKey}s this archetype requires over all parent archetypes.
	 * 
	 * @return The set of {@link TypedKey}s
	 */
	Set<TypedKey<?>> requiredKeys();
	
	/**
	 * Returns the extractor of {@link TypedKey}s from {@link T type}.
	 * 
	 * @return The {@link TypedKey}s extractor
	 */
	BiConsumer<T, TypedKeyMap.Mutable> contextExtractor();
	
	/**
	 * Returns the total {@link #requiredKeys()} of this and all parent archetypes.
	 * 
	 * @return The stream of {@link TypedKey}s
	 */
	default Stream<TypedKey<?>> cumulativeRequiredKeys() {
		return this.parent().isEmpty()
				? this.requiredKeys().stream()
				: Stream.concat(this.parent().get().cumulativeRequiredKeys(), this.requiredKeys().stream());
	}
	
	/**
	 * Returns the total {@link #contextExtractor()} of this and all parent archetypes.
	 * 
	 * @return The {@link TypedKey}s extractor
	 */
	default BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor() {
		return this.parent().isEmpty()
				? this.contextExtractor()
				: this.parent().get().cumulativeContextExtractor().andThen(this.contextExtractor());
	}
}
