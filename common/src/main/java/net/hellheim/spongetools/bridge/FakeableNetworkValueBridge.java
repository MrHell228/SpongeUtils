package net.hellheim.spongetools.bridge;

import net.minecraft.core.Holder;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

import java.util.function.Function;

public interface FakeableNetworkValueBridge {

    static <V> @PolyNull Holder<V> asNetworkHolder(final Holder<V> holder, final Function<V, @PolyNull Holder<V>> holderProvider) {
        final V value = holder.value();
        final V networkValue = FakeableNetworkValueBridge.asNetworkValue(value);
        return value == networkValue
                ? holder
                : holderProvider.apply(networkValue);
    }

    static <V> V asNetworkValue(final V value) {
        if (value instanceof final FakeableNetworkValueBridge bridge) {
            final @Nullable Object fakeValue = bridge.spongetools$bridge$asNetworkValue();
            if (fakeValue != null) {
                return (V) fakeValue;
            }
        }
        return value;
    }

    @Nullable Object spongetools$bridge$asNetworkValue();
}
