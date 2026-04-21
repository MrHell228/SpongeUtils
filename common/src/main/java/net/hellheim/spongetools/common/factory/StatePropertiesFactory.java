package net.hellheim.spongetools.common.factory;

import java.util.List;
import java.util.Objects;

import org.spongepowered.api.state.BooleanStateProperty;
import org.spongepowered.api.state.EnumStateProperty;
import org.spongepowered.api.state.IntegerStateProperty;

import net.hellheim.spongetools.custom.type.block.EnumStatePropertyValue;
import net.hellheim.spongetools.custom.type.block.StateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class StatePropertiesFactory implements StateProperties.Factory {

	@Override
	public BooleanStateProperty booleanProperty(final String name) {
		Objects.requireNonNull(name, "name");
		return (BooleanStateProperty) (Object) BooleanProperty.create(name);
	}

	@Override
	public IntegerStateProperty integerProperty(final String name, final int min, final int max) {
		Objects.requireNonNull(name, "name");
		return (IntegerStateProperty) (Object) IntegerProperty.create(name, min, max);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <E extends Enum<E> & EnumStatePropertyValue> EnumStateProperty<E> enumProperty(
		final String name, final Class<E> clazz, final List<E> values
	) {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(clazz, "clazz");
		Objects.requireNonNull(values, "values");
		return (EnumStateProperty<E>) (Object) EnumProperty.create(name, (Class) clazz, (List) values);
	}
	
}
