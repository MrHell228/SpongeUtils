package net.hellheim.spongetools.resourcepack.block;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;

import com.mojang.serialization.Codec;

public interface StatePropertyValue<T extends Comparable<T>> extends StringRepresentable {
	
	public static final Codec<StatePropertyValue<?>> CODEC = StateCodec.STATE_PROPERTY_VALUE;
	
	static <T extends Comparable<T>> StatePropertyValue<T> of(
		final StateProperty<T> property, final T value
	) {
		return Sponge.game().factoryProvider().provide(Factory.class).of(property, value);
	}
	
	static <T extends Comparable<T>> StatePropertyValue<T> of(
		final StateProperty<T> property, final Supplier<? extends T> valueSupplier
	) {
		return StatePropertyValue.of(property, Objects.requireNonNull(valueSupplier, "valueSupplier").get());
	}
	
	@SuppressWarnings("unchecked")
	static <T extends Comparable<T>> StatePropertyValue<T> ofRaw(
		final StateProperty<T> property, final Object value
	) {
		return StatePropertyValue.of(property, (T) value);
	}
	
	static Stream<StatePropertyValue<?>> allOf(final State<?> state) {
		return state.statePropertyMap().entrySet().stream()
				.map(e -> StatePropertyValue.ofRaw(e.getKey(), e.getValue()));
	}
	
	StateProperty<T> property();
	
	T value();
	
	String valueName();
	
	interface Factory {
		
		<T extends Comparable<T>> StatePropertyValue<T> of(StateProperty<T> property, T value);
	}
}
