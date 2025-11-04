package net.hellheim.spongetools;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryRoots;
import org.spongepowered.api.registry.RegistryType;

import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.block.ModeledBlock;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.LoreProcessor;
import net.hellheim.spongetools.custom.type.item.LoreProvider;
import net.hellheim.spongetools.custom.type.item.ModeledItem;
import net.hellheim.spongetools.custom.type.item.data.CustomConsumeEffect;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;

public final class SpongeTools {
	
	public static final String NAMESPACE = "spongetools";
	
	public static ResourceKey key(final String value) {
		return ResourceKey.of(SpongeTools.NAMESPACE, value);
	}
	
	public static final class Registries {
		
		public static final DefaultedRegistryType<BlockArchetype> BLOCK_ARCHETYPE = Registries.key("block_archetype");
		
		public static final DefaultedRegistryType<ItemArchetype> ITEM_ARCHETYPE = Registries.key("item_archetype");
		
		public static final DefaultedRegistryType<ModeledBlock> MODELED_BLOCK = Registries.key("modeled_block");
		
		public static final DefaultedRegistryType<ModeledItem> MODELED_ITEM = Registries.key("modeled_item");
		
		/**
		 * Definitions from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<BlockDefinition> BLOCK_DEFINITION = Registries.key("blockstates");
		
		/**
		 * Definitions from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<ItemDefinition> ITEM_DEFINITION = Registries.key("items");
		
		/**
		 * Models from this registry will be included in built ResourcePack.
		 */
		public static final DefaultedRegistryType<Model> MODEL = Registries.key("models");
		
		public static final DefaultedRegistryType<MapCodec<? extends LoreProcessor>> LORE_PROCESSOR_TYPE = Registries.key("lore_processor_type");
		
		public static final DefaultedRegistryType<MapCodec<? extends LoreProvider>> LORE_PROVIDER_TYPE = Registries.key("lore_provider_type");
		
		public static final DefaultedRegistryType<MapCodec<? extends CustomConsumeEffect>> CONSUME_EFFECT_TYPE = Registries.key("consume_effect_type");
		
	    private static <V> DefaultedRegistryType<V> key(final String key) {
	        return RegistryType.of(RegistryRoots.SPONGE, SpongeTools.key(key)).asDefaultedType(Sponge::game);
	    }
		
		private Registries() {
		}
	}
	
	public static final class Keys {
		
		public static final Key<Value<LoreProcessor>> LORE_PROCESSOR = Keys.key("lore_processor", LoreProcessor.class);
		
		public static final Key<ListValue<LoreProvider>> LORE_PROVIDERS = Keys.listKey("lore_providers", LoreProvider.class);
		
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
		
		private Queries() {
		}
	}
	
	public static final class Versions {
		
		private Versions() {
		}
	}
	
	private SpongeTools() {
	}
}
