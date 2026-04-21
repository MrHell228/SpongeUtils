package net.hellheim.spongetools.mixin.network.codec;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import com.mojang.serialization.Codec;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.bridge.RegistryBridge;
import net.hellheim.spongetools.common.util.NetworkComponentSanitizer;
import net.hellheim.spongetools.common.util.NetworkUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Function;

@SuppressWarnings("unchecked")
@Mixin(ByteBufCodecs.class)
public interface ByteBufCodecsMixin {

    @ModifyVariable(
            method = {
                    "fromCodec(Lcom/mojang/serialization/Codec;Ljava/util/function/Supplier;)Lnet/minecraft/network/codec/StreamCodec;",
                    "fromCodecWithRegistries(Lcom/mojang/serialization/Codec;Ljava/util/function/Supplier;)Lnet/minecraft/network/codec/StreamCodec;"
            },
            at = @At("HEAD"),
            argsOnly = true
    )
    private static <T> Codec<T> spongetools$adjustCodecBasedStreamCodecs(final Codec<T> codec) {
        return Codec.lazyInitialized(() -> {
            if (codec == Style.Serializer.CODEC) {
                return (Codec<T>) ((Codec<Style>) codec).xmap(
                        Function.identity(),
                        NetworkComponentSanitizer::style);
            } else if (codec == ComponentSerialization.CODEC) {
                return (Codec<T>) ((Codec<Component>) codec).xmap(
                        Function.identity(),
                        NetworkComponentSanitizer::component);
            } else {
                return codec;
            }
        });
    }

    @ModifyVariable(
            method = "idMapper(Lnet/minecraft/core/IdMap;)Lnet/minecraft/network/codec/StreamCodec;",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static <T> IdMap<T> spongetools$adjustBlockStateIdMap(final IdMap<T> map) {
        return NetworkUtil.isBlockStateRegistry(map)
                ? NetworkUtil.idMap(map, FakeableNetworkValueBridge::asNetworkValue)
                : map;
    }

    @WrapOperation(
            method = "registry(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/network/codec/StreamCodec;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/codec/ByteBufCodecs;registry(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;)Lnet/minecraft/network/codec/StreamCodec;"
            )
    )
    private static <T> StreamCodec<RegistryFriendlyByteBuf, T> spongetools$adjustRegistryValueIdMap(
            final ResourceKey<? extends Registry<T>> registryKey,
            final Function<Registry<T>, IdMap<T>> idGetter,
            final Operation<StreamCodec<RegistryFriendlyByteBuf, T>> original
    ) {
        return original.call(registryKey, (Function<Registry<T>, IdMap<T>>) registry ->
                ((RegistryBridge<T>) registry).spongetools$bridge$asNetworkValueIdMap(idGetter));
    }

    @WrapOperation(
            method = "holderRegistry",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/codec/ByteBufCodecs;registry(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;)Lnet/minecraft/network/codec/StreamCodec;"
            )
    )
    private static <T> StreamCodec<RegistryFriendlyByteBuf, Holder<T>> spongetools$adjustRegistryHolderIdMap(
            final ResourceKey<? extends Registry<T>> registryKey,
            final Function<Registry<T>, IdMap<Holder<T>>> idGetter,
            final Operation<StreamCodec<RegistryFriendlyByteBuf, Holder<T>>> original
    ) {
        return original.call(registryKey, (Function<Registry<T>, IdMap<Holder<T>>>) registry ->
                ((RegistryBridge<T>) registry).spongetools$bridge$asNetworkHolderIdMap(idGetter));
    }
}
