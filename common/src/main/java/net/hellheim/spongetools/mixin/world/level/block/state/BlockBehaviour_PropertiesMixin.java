package net.hellheim.spongetools.mixin.world.level.block.state;

import net.hellheim.spongetools.bridge.BlockPropertiesBridge;
import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockBehaviour.Properties.class)
public class BlockBehaviour_PropertiesMixin implements BlockPropertiesBridge {

    private @Unique BlockTypeUtil.@Nullable AdditionalData spongetools$data;

    @Override
    public void spongetools$bridge$applyData(final BlockTypeUtil.AdditionalData data) {
        this.spongetools$data = data;
    }

    @Override
    public BlockTypeUtil.@Nullable AdditionalData spongetools$bridge$getData() {
        return this.spongetools$data;
    }
}
