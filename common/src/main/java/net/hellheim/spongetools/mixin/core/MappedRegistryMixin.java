package net.hellheim.spongetools.mixin.core;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.bridge.RegistryBridge;
import net.hellheim.spongetools.common.util.NetworkUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.function.Function;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> implements RegistryBridge<T> {

    @Shadow @Final private ResourceKey<? extends Registry<T>> key;
    @Shadow @Final private Map<T, Holder.Reference<T>> byValue;

    private @Unique boolean spongetools$initNetworkValueIdMap = true;
    private @Unique boolean spongetools$initNetworkHolderIdMap = true;
    // Builtin registries are registered and filled only once so it should be fine to cache these.
    private @Unique @MonotonicNonNull IdMap<T> spongetools$networkValueIdMap = null;
    private @Unique @MonotonicNonNull IdMap<Holder<T>> spongetools$networkHolderIdMap = null;

    @Override
    public IdMap<T> spongetools$bridge$asNetworkValueIdMap(final Function<Registry<T>, IdMap<T>> original) {
        if (this.spongetools$initNetworkValueIdMap) {
            if (this.spongetools$impl$isBuiltinRegistry()) {
                this.spongetools$networkValueIdMap = NetworkUtil.idMap(
                        RegistryBridge.super.spongetools$bridge$asNetworkValueIdMap(original),
                        (value) -> FakeableNetworkValueBridge.asNetworkValue(value)
                );
            }
            this.spongetools$initNetworkValueIdMap = false;
        }

        return this.spongetools$networkValueIdMap == null
                ? RegistryBridge.super.spongetools$bridge$asNetworkValueIdMap(original)
                : this.spongetools$networkValueIdMap;
    }

    @Override
    public IdMap<Holder<T>> spongetools$bridge$asNetworkHolderIdMap(final Function<Registry<T>, IdMap<Holder<T>>> original) {
        if (this.spongetools$initNetworkHolderIdMap) {
            if (this.spongetools$impl$isBuiltinRegistry()) {
                this.spongetools$networkHolderIdMap = NetworkUtil.idMap(
                        RegistryBridge.super.spongetools$bridge$asNetworkHolderIdMap(original),
                        (value) -> FakeableNetworkValueBridge.asNetworkHolder(value, this.byValue::get)
                );
            }
            this.spongetools$initNetworkHolderIdMap = false;
        }

        return this.spongetools$networkHolderIdMap == null
                ? RegistryBridge.super.spongetools$bridge$asNetworkHolderIdMap(original)
                : this.spongetools$networkHolderIdMap;
    }

    @Unique
    private boolean spongetools$impl$isBuiltinRegistry() {
        return BuiltInRegistries.REGISTRY.containsKey(this.key.identifier());
    }
}
