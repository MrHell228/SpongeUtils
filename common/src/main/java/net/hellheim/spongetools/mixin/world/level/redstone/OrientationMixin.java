package net.hellheim.spongetools.mixin.world.level.redstone;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.api.util.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.stream.Stream;

@Mixin(Orientation.class)
public abstract class OrientationMixin implements SignalOrientation {

    @Shadow public abstract net.minecraft.core.Direction shadow$getUp();
    @Shadow public abstract net.minecraft.core.Direction shadow$getFront();
    @Shadow public abstract net.minecraft.core.Direction shadow$getSide();
    @Shadow public abstract Orientation.SideBias shadow$getSideBias();
    @Shadow public abstract Orientation shadow$withUp(net.minecraft.core.Direction $$0);
    @Shadow public abstract Orientation shadow$withFront(net.minecraft.core.Direction $$0);
    @Shadow public abstract Orientation shadow$withSideBias(Orientation.SideBias $$0);
    @Shadow public abstract List<net.minecraft.core.Direction> shadow$getDirections();
    @Shadow public abstract List<net.minecraft.core.Direction> shadow$getHorizontalDirections();
    @Shadow public abstract List<net.minecraft.core.Direction> shadow$getVerticalDirections();

    @Override
    public Direction up() {
        return Converter.asSponge(this.shadow$getUp());
    }

    @Override
    public Direction front() {
        return Converter.asSponge(this.shadow$getFront());
    }

    @Override
    public Direction side() {
        return Converter.asSponge(this.shadow$getSide());
    }

    @Override
    public SignalBias bias() {
        return Converter.asSponge(this.shadow$getSideBias());
    }

    @Override
    public SignalOrientation withUp(final Direction direction) {
        return Converter.asSponge(this.shadow$withUp(Converter.asVanilla(direction)));
    }

    @Override
    public SignalOrientation withFront(final Direction direction) {
        return Converter.asSponge(this.shadow$withFront(Converter.asVanilla(direction)));
    }

    @Override
    public SignalOrientation withBias(final SignalBias bias) {
        return Converter.asSponge(this.shadow$withSideBias(Converter.asVanilla(bias)));
    }

    @Override
    public Stream<Direction> allDirections() {
        return this.shadow$getDirections().stream().map(Converter::asSponge);
    }

    @Override
    public Stream<Direction> horizontalDirections() {
        return this.shadow$getHorizontalDirections().stream().map(Converter::asSponge);
    }

    @Override
    public Stream<Direction> verticalDirections() {
        return this.shadow$getVerticalDirections().stream().map(Converter::asSponge);
    }
}
