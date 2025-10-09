package net.hellheim.spongetools.mixin.world.phys;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.api.util.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.math.vector.Vector3i;

@Mixin(BlockHitResult.class)
public abstract class BlockHitResultMixin implements HitResult.BlockHitResult {

    @Shadow @Final private BlockPos blockPos;
    @Shadow @Final private net.minecraft.core.Direction direction;
    @Shadow @Final private boolean inside;
    @Shadow @Final private boolean worldBorderHit;

    @Override
    public Vector3i blockPosition() {
        return Converter.asSponge(this.blockPos);
    }

    @Override
    public Direction direction() {
        return Converter.asSponge(this.direction).opposite();
    }

    @Override
    public boolean inside() {
        return this.inside;
    }

    @Override
    public boolean worldBorder() {
        return this.worldBorderHit;
    }
}
