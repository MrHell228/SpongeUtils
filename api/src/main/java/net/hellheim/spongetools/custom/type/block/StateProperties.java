package net.hellheim.spongetools.custom.type.block;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.state.BooleanStateProperty;
import org.spongepowered.api.state.EnumStateProperty;
import org.spongepowered.api.state.IntegerStateProperty;
import org.spongepowered.api.state.StateProperty;

import com.google.common.collect.Streams;

/**
 * Class for creating {@link StateProperty}s.
 */
public final class StateProperties {
	
	/**
	 * Creates new {@link BooleanStateProperty}.
	 * 
	 * @param name The property name
	 * @return The new state property
	 */
	public static BooleanStateProperty booleanProperty(final String name) {
		return StateProperties.factory().booleanProperty(name);
	}
	
	/**
	 * Creates new {@link IntegerStateProperty}.
	 * 
	 * @param name The property name
	 * @param min The minimum value of the property
	 * @param max The maximum value of the property
	 * @return The new state property
	 * @throws IllegalArgumentException if min is less than 0, or max is less than or equal to min
	 */
	public static IntegerStateProperty integerProperty(final String name, final int min, final int max) {
		return StateProperties.factory().integerProperty(name, min, max);
	}
	
	/**
	 * Creates new {@link EnumStateProperty} with all the values of the given enum class.
	 * 
	 * @param <E> The type of the property values
	 * @param name The property name
	 * @param clazz The the property values class
	 * @return The new state property
	 * @throws IllegalArgumentException if enum is empty
	 */
	public static <E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(final String name, final Class<E> clazz) {
		return StateProperties.factory().enumProperty(name, clazz, List.of(clazz.getEnumConstants()));
	}
	
	/**
	 * Creates new {@link EnumStateProperty} with all the values of the given enum class with applied filter.
	 * 
	 * @param <E> The type of the property values
	 * @param name The property name
	 * @param clazz The the property values class
	 * @param filter The filter to apply to values
	 * @return The new state property
	 * @throws IllegalArgumentException if final values are empty
	 */
	public static <E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(final String name, final Class<E> clazz, final Predicate<E> filter) {
		return StateProperties.factory().enumProperty(name, clazz, Arrays.stream(clazz.getEnumConstants()).filter(filter).toList());
	}
	
	/**
	 * Creates new {@link EnumStateProperty} with the given values.
	 * 
	 * @param <E> The type of the property values
	 * @param name The property name
	 * @param clazz The the property values class
	 * @param values The allowed property values
	 * @return The new state property
	 * @throws IllegalArgumentException if no values are provided
	 */
	@SafeVarargs
	public static <E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(final String name, final Class<E> clazz, final E... values) {
		return StateProperties.factory().enumProperty(name, clazz, List.of(values));
	}
	
	/**
	 * Creates new {@link EnumStateProperty} with the given values.
	 * 
	 * @param <E> The type of the property values
	 * @param name The property name
	 * @param clazz The the property values class
	 * @param values The allowed property values
	 * @return The new state property
	 * @throws IllegalArgumentException if no values are provided
	 */
	public static <E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(final String name, final Class<E> clazz, final Iterable<E> values) {
		return StateProperties.factory().enumProperty(name, clazz, Streams.stream(values).toList());
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	public interface Factory {
	
		BooleanStateProperty booleanProperty(String name);
		
		IntegerStateProperty integerProperty(String name, int min, int max);
		
		<E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(String name, Class<E> clazz, List<E> values);
	}
	
	private StateProperties() {
	}
}
