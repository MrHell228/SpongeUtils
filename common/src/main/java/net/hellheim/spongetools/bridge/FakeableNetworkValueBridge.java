package net.hellheim.spongetools.bridge;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

import java.util.function.Function;

public interface FakeableNetworkValueBridge {
	
	static Holder<Item> asNetworkItemHolder(final Holder<Item> holder) {
		return FakeableNetworkValueBridge.asNetworkHolder(holder, Item::builtInRegistryHolder);
	}

    static <V> @PolyNull Holder<V> asNetworkHolder(final Holder<V> holder, final Function<V, @PolyNull Holder<V>> holderProvider) {
        final V value = holder.value();
        final V networkValue = FakeableNetworkValueBridge.asNetworkValue(value);
        return value == networkValue
                ? holder
                : holderProvider.apply(networkValue);
    }

    @SuppressWarnings("unchecked")
	static <V> V asNetworkValue(final V value) {
        if (value instanceof final FakeableNetworkValueBridge bridge) {
            final @Nullable Object fakeValue = bridge.spongetools$bridge$asNetworkValue();
            if (fakeValue != null) {
                return (V) fakeValue;
            }
        }
        return value;
    }

    static boolean isNetworkFaked(final Object value) {
        return FakeableNetworkValueBridge.asNetworkValue(value) != value;
    }

    @Nullable Object spongetools$bridge$asNetworkValue();
}
