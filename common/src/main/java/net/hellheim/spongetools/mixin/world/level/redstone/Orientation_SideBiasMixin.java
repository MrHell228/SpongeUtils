package net.hellheim.spongetools.mixin.world.level.redstone;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Orientation.SideBias.class)
public abstract class Orientation_SideBiasMixin implements SignalBias {

    @Shadow public abstract Orientation.SideBias shadow$getOpposite();

    @Override
    public SignalBias opposite() {
        return Converter.asSponge(this.shadow$getOpposite());
    }
}
