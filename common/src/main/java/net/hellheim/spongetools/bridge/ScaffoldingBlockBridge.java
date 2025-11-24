package net.hellheim.spongetools.bridge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import java.util.function.IntSupplier;
import java.util.function.ToIntBiFunction;

public interface ScaffoldingBlockBridge {

    int spongetools$bridge$getMaxDistance();

    int spongetools$bridge$getDistance(BlockGetter getter, BlockPos pos, IntSupplier original);
}
