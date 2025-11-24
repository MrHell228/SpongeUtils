package net.hellheim.spongetools.mixin.world.level.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.BlockPropertiesBridge;
import net.hellheim.spongetools.bridge.BlockStateBaseBridge;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.type.BlockTypeExtension;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockTypeExtension, FakeableNetworkValueBridge {

    @Shadow @Final protected StateDefinition<Block, BlockState> stateDefinition;

    private @Unique BlockTypeUtil.@Nullable AdditionalData spongetools$data;

    @Override
    public BlockType type() {
        return (BlockType) this;
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$data == null ? null : this.spongetools$data.networkBlock();
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;createBlockStateDefinition(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V",
                    unsafe = true
            )
    )
    private void spongetools$setData(final BlockBehaviour.Properties properties, final CallbackInfo ci) {
        this.spongetools$data = ((BlockPropertiesBridge) properties).spongetools$bridge$getData();
    }

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;createBlockStateDefinition(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V"
            )
    )
    private void spongetools$useCustomStateDefinition(
            final Block instance, final StateDefinition.Builder<Block, BlockState> builder,
            final Operation<Void> original
    ) {
        if (this.spongetools$data == null) {
            original.call(instance, builder);
        } else {
            for (final StateProperty<?> property : this.spongetools$data.properties()) {
                builder.add(Converter.asVanilla(property));
            }
        }
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Block;stateDefinition:Lnet/minecraft/world/level/block/state/StateDefinition;",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER,
                    unsafe = true
            )
    )
    private void spongetools$setBaseNetworkStates(final BlockBehaviour.Properties properties, final CallbackInfo ci) {
        if (this.spongetools$data != null) {
            this.stateDefinition.getPossibleStates().forEach(state ->
                    ((BlockStateBaseBridge) state).spongetools$bridge$setNetworkState(
                            Converter.asVanilla(BlockTypeUtil.DEFAULT_STATE.get())));
        }
    }

    @WrapOperation(
            method = "registerDefaultState",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Block;defaultBlockState:Lnet/minecraft/world/level/block/state/BlockState;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void spongetools$useCustomDefaultState(
            final Block instance, final BlockState value,
            final Operation<Void> original
    ) {
        if (this.spongetools$data == null || this.spongetools$data.defautProperties().isEmpty()) {
            original.call(instance, value);
        } else {
            BlockState result = this.stateDefinition.any();
            for (final StatePropertyValue<?> property : this.spongetools$data.defautProperties()) {
                result = spongetools$impl$setProperty(result, Converter.asVanilla(property));
            }
            original.call(instance, result);
        }
    }

    private static <T extends Comparable<T>> BlockState spongetools$impl$setProperty(
            final BlockState state, final Property.Value<T> property
    ) {
        return state.setValue(property.property(), property.value());
    }
}
