package net.hellheim.spongetools;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryRoots;
import org.spongepowered.api.registry.RegistryType;

public final class SpongeTools {
	
	public static final String NAMESPACE = "spongetools";
	
	public static ResourceKey key(final String value) {
		return ResourceKey.of(SpongeTools.NAMESPACE, value);
	}
	
	public static final class Registries {
		
	    private static <V> DefaultedRegistryType<V> gameRegistry(final String key) {
	        return RegistryType.of(RegistryRoots.SPONGE, SpongeTools.key(key)).asDefaultedType(Sponge::game);
	    }
		
		private Registries() {
		}
	}
	
	public static final class Keys {
		
		private static <E> Key<Value<E>> key(final String key, final Class<E> type) {
			return Key.from(SpongeTools.key(key), type);
		}
		
		private Keys() {
		}
	}
	
	private SpongeTools() {
	}
}
