package net.hellheim.spongetools;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataQuery;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryRoots;
import org.spongepowered.api.registry.RegistryType;

import net.hellheim.spongetools.custom.item.CustomItemType;
import net.hellheim.spongetools.custom.item.model.Item;
import net.hellheim.spongetools.custom.item.model.ItemModel;

public final class SpongeTools {
	
	public static final String NAMESPACE = "spongetools";
	
	public static ResourceKey key(final String value) {
		return ResourceKey.of(SpongeTools.NAMESPACE, value);
	}
	
	public static final class Registries {
		
		public static final DefaultedRegistryType<CustomItemType> CUSTOM_ITEM = Registries.registry("custom_item");
		
		/**
		 * Items from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<Item> ITEM = Registries.registry("items");
		
		/**
		 * Models from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<ItemModel> ITEM_MODEL = Registries.registry("model/item");
		
	    private static <V> DefaultedRegistryType<V> registry(final String key) {
	        return RegistryType.of(RegistryRoots.SPONGE, SpongeTools.key(key)).asDefaultedType(Sponge::server);
	    }
		
		private Registries() {
		}
	}
	
	public static final class Keys {
		
		public static final Key<Value<CustomItemType>> CUSTOM_ITEM = Keys.key("custom_item", CustomItemType.class);
		
		private static <E> Key<Value<E>> key(final String key, final Class<E> type) {
			return Key.from(SpongeTools.key(key), type);
		}
		
		private Keys() {
		}
	}
	
	public static final class Queries {
		
		public static final DataQuery CUSTOM_ITEM = DataQuery.of("custom_item");
		
		private Queries() {
		}
	}
	
	public static final class Versions {
		
		public static final int CUSTOM_ITEM = 0;
		
		private Versions() {
		}
	}
	
	private SpongeTools() {
	}
}
