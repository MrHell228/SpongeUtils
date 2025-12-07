package net.hellheim.spongetools.custom.type;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.DefaultedRegistryValue;
import org.spongepowered.api.registry.Registry;

import net.hellheim.spongetools.custom.behaviour.BehaviourLayer;
import net.hellheim.spongetools.custom.type.block.BlockTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeArchetype;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Represents the core of the custom type used for {@link CustomTypeBuilder}. <br>
 * The archetype can declare required/supported context keys and supported behaviour.
 * 
 * @see ItemTypeArchetype
 * @see BlockTypeArchetype
 * @see EntityTypeArchetype
 * 
 * @param <T> The type of the value this archetype is used to build for
 * @param <I> The type of the corresponding instance with the custom {@link T type}
 * @param <A> The child archetype type
 */
public interface CustomTypeArchetype<T, I, A extends CustomTypeArchetype<T, I, A>>
		extends DefaultedRegistryValue<A>, BehaviourLayer {
	
	/**
	 * Validates the archetype's base class against the root class and the archetype's parent archetype.
	 * 
	 * @param rootClass The root class of all archetypes' base classes
	 * @param baseClass The base class to validate
	 * @param parent The parent archetype
	 */
	static void validate(
		final Class<?> rootClass, final Class<?> baseClass,
		final Optional<? extends CustomTypeArchetype<?, ?, ?>> parent
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
	static <T, A extends CustomTypeArchetype.TypeBased<T, ?, A>> A forType(
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
	 * Returns the most appropriate known {@link CustomTypeArchetype} for the given <code>type</code>.
	 * 
	 * @param <I> The type of value instances archetype exists for
	 * @param <A> The type of archetype
	 * @param archetypes The known archetypes
	 * @param baseArchetype The base archetype
	 * @param type The type
	 * @return The most specific archetype
	 */
	static <I, A extends CustomTypeArchetype.TypeBased<?, I, A>> A forInstance(
		final Registry<A> archetypes, final A baseArchetype, final I instance
	) {
		A archetype = baseArchetype;
		for (final A arch : archetypes.stream().toList()) {
			if (arch.baseClass().isInstance(instance)
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
	 * Returns the total {@link #requiredKeys()} of this and all parent archetypes.
	 * 
	 * @return The stream of {@link TypedKey}s
	 */
	Stream<TypedKey<?>> cumulativeRequiredKeys();
	
	/**
	 * Returns the total {@link #contextExtractor()} of this and all parent archetypes.
	 * 
	 * @return The {@link TypedKey}s extractor
	 */
	BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor();
	
	/**
	 * {@link CustomTypeArchetype} of type which {@link I instance}
	 * logic does not depend on corresponding {@link T type} logic. <br>
	 * 
	 * Usually this means that {@link I instance}s are represented by multiple
	 * classes while {@link T type}s are represented by single class. <br>
	 * This results in {@link #baseClass()} representing some subclass of {@link T type}.
	 * 
	 * @param <T> The type of the value this archetype is used to build for
	 * @param <I> The type of the corresponding instance with the custom {@link T type}
	 * @param <A> The child archetype type
	 */
	interface TypeBased<T, I, A extends TypeBased<T, I, A>> extends CustomTypeArchetype<T, I, A> {
		
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
		
		@Override
		default BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor() {
			return this.parent().isEmpty()
					? this.contextExtractor()
					: this.parent().get().cumulativeContextExtractor().andThen(this.contextExtractor());
		}
		
		@Override
		default Stream<TypedKey<?>> cumulativeRequiredKeys() {
			return this.parent().isEmpty()
					? this.requiredKeys().stream()
					: Stream.concat(this.parent().get().cumulativeRequiredKeys(), this.requiredKeys().stream());
		}
	}
	
	/**
	 * {@link CustomTypeArchetype} for type which {@link I instance}
	 * logic does not depend on corresponding {@link T type} logic. <br>
	 * 
	 * Usually this means that {@link I instance}s are represented by multiple
	 * classes while {@link T type}s are represented by single class.
	 * This results in {@link #baseClass()} representing some subclass of {@link I instance}.
	 * 
	 * @param <T> The type of the value this archetype is used to build for
	 * @param <I> The type of the corresponding instance with the custom {@link T type}
	 * @param <A> The child archetype type
	 */
	interface InstanceBased<T, I, A extends InstanceBased<T, I, A>> extends CustomTypeArchetype<T, I, A> {
		
		/**
		 * Returns the corresponding {@link CustomTypeArchetype.InstanceBased.Factory}
		 * that provides all the required context data.
		 * 
		 * @return The factory class
		 */
		Class<? extends Factory<T>> contextFactory();
		
		@Override
		default Stream<TypedKey<?>> cumulativeRequiredKeys() {
			return Sponge.game().factoryProvider().provide(this.contextFactory()).cumulativeRequiredKeys();
		}
		
		@Override
		default BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor() {
			return Sponge.game().factoryProvider().provide(this.contextFactory()).cumulativeContextExtractor();
		}
		
		interface Factory<T> {
			
			Stream<TypedKey<?>> cumulativeRequiredKeys();
			
			BiConsumer<T, TypedKeyMap.Mutable> cumulativeContextExtractor();
		}
	}
}
