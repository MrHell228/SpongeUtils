package net.hellheim.spongetools.mixin.world.level.block.state.properties;

import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Property.Value.class)
@Implements(@Interface(iface = StatePropertyValue.class, prefix = "value$"))
public abstract class Property_ValueMixin<T extends Comparable<T>> implements StatePropertyValue<T> {

    @Shadow @Final private Property<T> property;
    @Shadow @Final private T value;

    @SuppressWarnings("unchecked")
	@Intrinsic
    public StateProperty<T> value$property() {
        return (StateProperty<T>) this.property;
    }

    @Intrinsic
    public T value$value() {
        return this.value;
    }

    @Override
    public String valueName() {
        return this.property.getName(this.value);
    }

    @Override
    public String serializationString() {
        return this.toString();
    }
}
