package net.hellheim.spongetools.mixin.world.item.context;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.api.util.Direction;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.List;

@Mixin(BlockPlaceContext.class)
@Implements(@Interface(iface = UseContext.BlockPlace.class, prefix = "context$"))
public abstract class BlockPlaceContextMixin implements UseContext.BlockPlace {

    @Shadow public abstract boolean shadow$canPlace();
    @Shadow public abstract boolean shadow$replacingClickedOnBlock();
    @Shadow public abstract net.minecraft.core.Direction shadow$getNearestLookingDirection();
    @Shadow public abstract net.minecraft.core.Direction shadow$getNearestLookingVerticalDirection();
    @Shadow public abstract net.minecraft.core.Direction[] shadow$getNearestLookingDirections();

    @Intrinsic
    public boolean context$canPlace() {
        return this.shadow$canPlace();
    }

    @Override
    public boolean canReplaceClickedPosition() {
        return this.shadow$replacingClickedOnBlock();
    }

    @Override
    public Direction nearestDirection() {
        return Converter.asSponge(this.shadow$getNearestLookingDirection());
    }

    @Override
    public Direction nearestVerticalDirection() {
        return Converter.asSponge(this.shadow$getNearestLookingVerticalDirection());
    }

    @Override
    public List<Direction> nearestDirections() {
        return Arrays.stream(this.shadow$getNearestLookingDirections()).map(Converter::asSponge).toList();
    }
}
