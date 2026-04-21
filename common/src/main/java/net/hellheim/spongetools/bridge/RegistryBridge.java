package net.hellheim.spongetools.bridge;

import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public interface RegistryBridge<T> {
	
	default IdMap<T> spongetools$bridge$asNetworkValueIdMap(final Function<Registry<T>, IdMap<T>> original) {
		return original.apply((Registry<T>) this);
	}
	
	default IdMap<Holder<T>> spongetools$bridge$asNetworkHolderIdMap(final Function<Registry<T>, IdMap<Holder<T>>> original) {
		return original.apply((Registry<T>) this);
	}
}
