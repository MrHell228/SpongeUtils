package net.hellheim.spongetools.mixin.world.level.block;

import net.hellheim.spongetools.custom.behaviour.type.BlockTypeExtension;
import net.minecraft.world.level.block.Block;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockTypeExtension {

    @Override
    public BlockType type() {
        return (BlockType) this;
    }
}
