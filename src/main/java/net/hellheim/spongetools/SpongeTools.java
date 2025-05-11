package net.hellheim.spongetools;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataQuery;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryRoots;
import org.spongepowered.api.registry.RegistryType;

import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.custom.item.CustomItemType;
import net.hellheim.spongetools.custom.item.EitherItemType;
import net.hellheim.spongetools.custom.item.data.CustomConsumeEffect;
import net.hellheim.spongetools.resourcepack.item.Item;
import net.hellheim.spongetools.resourcepack.item.ItemModel;

public final class SpongeTools {
	
	public static final String NAMESPACE = "spongetools";
	
	public static ResourceKey key(final String value) {
		return ResourceKey.of(SpongeTools.NAMESPACE, value);
	}
	
	public static final class Registries {
		
		public static final DefaultedRegistryType<CustomItemType> CUSTOM_ITEM_TYPE = Registries.key("item");
		
		public static final DefaultedRegistryType<EitherItemType> EITHER_ITEM_TYPE = Registries.key("either_item");
		
		/**
		 * Items from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<Item> ITEM = Registries.key("items");
		
		/**
		 * Models from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<ItemModel> ITEM_MODEL = Registries.key("models/item");
		
		public static final DefaultedRegistryType<MapCodec<? extends CustomConsumeEffect>> CONSUME_EFFECT_TYPE = Registries.key("consume_effect_type");
		
	    private static <V> DefaultedRegistryType<V> key(final String key) {
	        return RegistryType.of(RegistryRoots.SPONGE, SpongeTools.key(key)).asScopedType();
	    }
		
		private Registries() {
		}
	}
	
	public static final class Keys {
		
		public static final Key<Value<CustomItemType>> ITEM_TYPE = Keys.key("item", CustomItemType.class);
		
		public static final Key<ListValue<CustomConsumeEffect>> CONSUME_EFFECTS = Keys.listKey("consume_effects", CustomConsumeEffect.class);
		
		private static <E> Key<Value<E>> key(final String key, final Class<E> type) {
			return Key.from(SpongeTools.key(key), type);
		}
		
		private static <E> Key<ListValue<E>> listKey(final String key, final Class<E> type) {
			return Key.fromList(SpongeTools.key(key), type);
		}
		
		private Keys() {
		}
	}
	
	public static final class Queries {
		
		public static final DataQuery ITEM_TYPE = DataQuery.of("item");
		
		private Queries() {
		}
	}
	
	public static final class Versions {
		
		public static final int ITEM_TYPE = 0;
		
		private Versions() {
		}
	}
	
	private SpongeTools() {
	}
}
