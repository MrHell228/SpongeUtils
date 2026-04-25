package net.hellheim.spongetools.mixin.world.level.chunk;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.util.NetworkUtil;
import net.minecraft.core.IdMap;
import net.minecraft.world.level.chunk.HashMapPalette;
import net.minecraft.world.level.chunk.LinearPalette;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.SingleValuePalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Mixin({HashMapPalette.class, LinearPalette.class, SingleValuePalette.class})
public abstract class Palette_TraitMixin<T> implements Palette<T> {

    // This makes trait mixin invalid.
    // Is it fine to init mapper in #write or is it too bad for performance?
    //@Shadow @Final private IdMap<T> registry;

    private @Unique UnaryOperator<T> spongetools$networkMapper;

    /*@Inject(method = "write", at = @At("HEAD"))
    private void spongetools$setNetworkMapper(final CallbackInfo ci) {
        if (this.spongetools$networkMapper == null) {
            this.spongetools$networkMapper = NetworkUtil.getPossibleBlockStateMapper(this.registry);
        }
    }*/

    @WrapOperation(
            method = {"write", "getSerializedSize"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/IdMap;getId(Ljava/lang/Object;)I"
            )
    )
    private int spongetools$mapNetworkValue(
            final IdMap<T> instance, final T t, final Operation<Integer> original
    ) {
        if (this.spongetools$networkMapper == null) {
            this.spongetools$networkMapper = NetworkUtil.getPossibleBlockStateMapper(instance);
        }
        return original.call(instance, this.spongetools$networkMapper.apply(t));
    }
}
