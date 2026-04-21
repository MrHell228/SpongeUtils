package net.hellheim.spongetools.mixin.spongetools;

import net.hellheim.spongetools.custom.type.block.EnumStatePropertyValue;
import net.minecraft.util.StringRepresentable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnumStatePropertyValue.class)
public interface EnumStatePropertyValueMixin extends StringRepresentable {

    @Override
    default String getSerializedName() {
        return ((EnumStatePropertyValue) this).serializationString();
    }
}
