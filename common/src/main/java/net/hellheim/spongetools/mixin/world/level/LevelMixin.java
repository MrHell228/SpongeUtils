package net.hellheim.spongetools.mixin.world.level;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.type.WorldExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Level.class)
public abstract class LevelMixin<W extends World<?, ?>> implements WorldExtension<W> {

    @Shadow public abstract void shadow$neighborChanged(BlockPos $$0, Block $$1, @Nullable Orientation $$2);
    @Shadow public abstract void shadow$updateNeighborsAt(BlockPos $$0, Block $$1);
    @Shadow public abstract void shadow$updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, net.minecraft.core.Direction $$2, @Nullable Orientation $$3);

    @Override
    public void updateAt(final int x, final int y, final int z, final BlockType notifier) {
        this.shadow$neighborChanged(new BlockPos(x, y, z), (Block) notifier, null);
    }

    @Override
    public void updateAround(final int x, final int y, final int z, final BlockType notifier) {
        this.shadow$updateNeighborsAt(new BlockPos(x, y, z), (Block) notifier);
    }

    @Override
    public void updateAroundExcept(final int x, final int y, final int z, final BlockType notifier, final Direction direction) {
        this.shadow$updateNeighborsAtExceptFromFacing(new BlockPos(x, y, z), (Block) notifier, Converter.asVanilla(direction), null);
    }
}
