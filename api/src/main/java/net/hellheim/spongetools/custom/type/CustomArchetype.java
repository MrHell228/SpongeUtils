package net.hellheim.spongetools.custom.type;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.spongepowered.api.registry.DefaultedRegistryValue;
import org.spongepowered.api.registry.Registry;

import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Represents the core of the custom type. <br>
 * The archetype can declare required/supported context keys and supported behaviour.
 * 
 * @see CustomTypeBuilder
 * @see ItemArchetype
 * @see BlockArchetype
 */
public interface CustomArchetype<T, A extends CustomArchetype<T, A>> extends DefaultedRegistryValue<A> {
	
	static <T, A extends CustomArchetype<T, A>> void validate(
		final Class<T> rootClass, final Class<?> baseClass, final Optional<A> parent
	) {
		if (!rootClass.isAssignableFrom(Objects.requireNonNull(baseClass, "baseClass"))) {
			throw new IllegalArgumentException(String.format(
					"Provided base class must be a subclass of ItemType: %s",
					baseClass));
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
	 * Returns the most appropriate known {@link CustomArchetype} for the given <code>type</code>.
	 * 
	 * @param <T> The type of values archetype exists for
	 * @param <A> The type of archetype
	 * @param archetypes The known archetypes
	 * @param baseArchetype The base archetype
	 * @param type The type
	 * @return The most specific archetype
	 */
	static <T, A extends CustomArchetype<T, A>> A forType(
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
	 * Returns the base class of t
	 * 
	 * @return
	 */
	Class<?> baseClass();
	
	Set<TypedKey<?>> requiredKeys();
	
	BiConsumer<T, TypedKeyMap.Mutable> contextExtractor();
	
	default Stream<TypedKey<?>> cumulativeRequiredKeys() {
		return this.parent().isEmpty()
				? this.requiredKeys().stream()
				: Stream.concat(this.parent().get().cumulativeRequiredKeys(), this.requiredKeys().stream());
	}
	
	default BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor() {
		return this.parent().isEmpty()
				? this.contextExtractor()
				: this.parent().get().cumulativeContextExtractor().andThen(this.contextExtractor());
	}
}
