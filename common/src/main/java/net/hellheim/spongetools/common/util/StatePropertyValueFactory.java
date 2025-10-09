package net.hellheim.spongetools.common.util;

import java.util.Objects;

import org.spongepowered.api.state.StateProperty;

import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.world.level.block.state.properties.Property;

public final class StatePropertyValueFactory implements StatePropertyValue.Factory {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Comparable<T>> StatePropertyValue<T> of(StateProperty<T> property, T value) {
		Objects.requireNonNull(property, "property");
		Objects.requireNonNull(value, "value");
		return (StatePropertyValue<T>) (Object) ((Property<T>) property).value(value);
	}
}
