package net.hellheim.spongetools.mixin.world.level.block;

import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ScaffoldingBlock.class)
public interface ScaffoldingBlockAccessor {

    @Invoker("<init>") static ScaffoldingBlock invoker$init(final BlockBehaviour.Properties properties) {
        throw new IllegalStateException();
    }
}
