package net.hellheim.spongetools.mixin.world.level.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.BlockPropertiesBridge;
import net.hellheim.spongetools.bridge.ScaffoldingBlockBridge;
import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.common.util.DataUtil;

import java.util.function.IntSupplier;

@Mixin(ScaffoldingBlock.class)
public abstract class ScaffoldingBlockMixin implements ScaffoldingBlockBridge {

    @Unique private @Nullable IntegerProperty spongetools$distanceProperty;
    @Unique private int spongetools$maxDistance;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/ScaffoldingBlock;registerDefaultState(Lnet/minecraft/world/level/block/state/BlockState;)V",
                    unsafe = true
            )
    )
    private void spongetools$setDistanceData(final BlockBehaviour.Properties properties, final CallbackInfo ci) {
        final var data = ((BlockPropertiesBridge) properties).spongetools$bridge$getData();
        if (data != null) {
            this.spongetools$distanceProperty = (IntegerProperty) (Object) data.properties().stream()
                    .filter(BlockTypeUtil.SCAFFOLDING_DISTANCE_PROPERTY_FILTER)
                    .findAny()
                    .orElseThrow();
            this.spongetools$maxDistance = DataUtil.maxi(this.spongetools$distanceProperty);
        }
    }

    @ModifyConstant(
            method = {
                    "<init>",
                    "tick",
                    "canSurvive"
            },
            constant = @Constant(intValue = ScaffoldingBlock.STABILITY_MAX_DISTANCE)
    )
    private int spongetools$useCustomMaxDistance(final int constant) {
        return this.spongetools$maxDistance == -1 ? constant : this.spongetools$maxDistance;
    }

    @WrapOperation(
            method = {
                    "<init>",
                    "createBlockStateDefinition",
                    "getStateForPlacement",
                    "tick",
                    "getCollisionShape"
            },
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/ScaffoldingBlock;DISTANCE:Lnet/minecraft/world/level/block/state/properties/IntegerProperty;"
            )
    )
    private IntegerProperty spongetools$useCustomDistanceProperty(final Operation<IntegerProperty> original) {
        return this.spongetools$distanceProperty == null ? original.call() : this.spongetools$distanceProperty;
    }

    @WrapOperation(
            method = {
                    "getStateForPlacement",
                    "tick",
                    "canSurvive"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/ScaffoldingBlock;getDistance(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I"
            )
    )
    private int spongetools$useCustomDistanceCalculator(
            final BlockGetter getter, final BlockPos pos, final Operation<Integer> original
    ) {
        return this.spongetools$bridge$getDistance(getter, pos, () -> original.call(getter, pos));
    }

    @Override
    public int spongetools$bridge$getMaxDistance() {
        return this.spongetools$maxDistance;
    }

    // Copied from ScaffoldingBlock#getDistance
    @Override
    public int spongetools$bridge$getDistance(
            final BlockGetter getter, final BlockPos pos, final IntSupplier original
    ) {
        if (this.spongetools$distanceProperty == null) {
            return original.getAsInt();
        }

        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable().move(Direction.DOWN);
        BlockState blockstate = getter.getBlockState(blockpos$mutableblockpos);
        int i = this.spongetools$maxDistance;
        if (blockstate.is((Block) (Object) this)) {
            i = blockstate.getValue(this.spongetools$distanceProperty);
        } else if (blockstate.isFaceSturdy(getter, blockpos$mutableblockpos, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = getter.getBlockState(blockpos$mutableblockpos.setWithOffset(pos, direction));
            if (blockstate1.is((Block) (Object) this)) {
                i = Math.min(i, blockstate1.getValue(this.spongetools$distanceProperty) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }
}
