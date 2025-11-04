package net.hellheim.spongetools.common.factory;

import java.util.Objects;

import org.spongepowered.api.data.type.PortionType;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.Axis;
import org.spongepowered.common.util.AxisUtil;
import org.spongepowered.common.util.DirectionUtil;
import org.spongepowered.common.util.PortionTypeUtil;

import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;

public final class StatePropertyValueFactory implements StatePropertyValue.Factory {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Comparable<T>> StatePropertyValue<T> of(final StateProperty<T> property, final T value) {
		Objects.requireNonNull(property, "property");
		Objects.requireNonNull(value, "value");
		return (StatePropertyValue<T>) (Object) ((Property<T>) property).value(StatePropertyValueFactory.<T, T, T>mapFromApi(property, value));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Property.Value<T> ofRawVanilla(
		final Property<T> property, final Object value
	) {
		return property.value((T) value);
	}
	
	// Copied over from Sponge's StateHolderMixin_API

    @SuppressWarnings("unchecked")
	public static <ApiT extends Comparable<ApiT>, T extends Comparable<?>> ApiT mapToApi(final T value) {
    	return (ApiT) switch (value) {
	    	case final Direction.Axis axis -> AxisUtil.getFor(axis);
	    	case final Direction dir -> DirectionUtil.getFor(dir);
	    	case final BedPart bedPart -> PortionTypeUtil.getFor(bedPart);
	    	case final DoubleBlockHalf half -> PortionTypeUtil.getFor(half);
	    	default -> value;
    	};
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public static <ApiT extends Comparable<ApiT>, T extends Comparable<T>, V extends T> V mapFromApi(
			final StateProperty<ApiT> stateProperty, final ApiT value) {
		return (V) (Object) switch (value) {
			case final Axis axis -> AxisUtil.getFor(axis);
			case final org.spongepowered.api.util.Direction dir -> {
				final Direction mappedValue = DirectionUtil.getFor(dir);
				if (mappedValue == null) {
					throw new UnsupportedOperationException("Unsupported Direction " + dir);
				}
				yield mappedValue;
			}
			case final PortionType portion -> {
				final Object mappedValue;
				if (stateProperty.equals(BlockStateProperties.BED_PART)) {
					mappedValue = PortionTypeUtil.getBedPartFor(portion);
				} else if (stateProperty.equals(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
					mappedValue = PortionTypeUtil.getDoubleBlockHalfFor(portion);
				} else if (stateProperty.equals(BlockStateProperties.HALF)) {
					mappedValue = PortionTypeUtil.getHalfFor(portion);
				} else {
					mappedValue = null;
				}
				if (mappedValue == null) {
					throw new UnsupportedOperationException("Unsupported Portion " + portion + " for " + stateProperty);
				}
				yield mappedValue;
			}
			default -> value;
		};
	}
}
