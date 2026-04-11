package net.hellheim.spongetools;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryRoots;
import org.spongepowered.api.registry.RegistryType;

import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.custom.type.block.BlockTypeArchetype;
import net.hellheim.spongetools.custom.type.block.ModeledBlock;
import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.ModeledEntity;
import net.hellheim.spongetools.custom.type.item.CustomItemAction;
import net.hellheim.spongetools.custom.type.item.ItemTypeArchetype;
import net.hellheim.spongetools.custom.type.item.LoreProcessor;
import net.hellheim.spongetools.custom.type.item.LoreProvider;
import net.hellheim.spongetools.custom.type.item.ModeledItem;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;

public final class SpongeTools {
	
	public static final String NAMESPACE = "spongetools";
	
	public static ResourceKey key(final String value) {
		return ResourceKey.of(SpongeTools.NAMESPACE, value);
	}
	
	public static final class Registries {
		
		// Archetypes
		
		public static final DefaultedRegistryType<BlockTypeArchetype> BLOCK_TYPE_ARCHETYPE = Registries.key("block_type_archetype");
		
		public static final DefaultedRegistryType<EntityTypeArchetype> ENTITY_TYPE_ARCHETYPE = Registries.key("entity_type_archetype");
		
		public static final DefaultedRegistryType<ItemTypeArchetype> ITEM_TYPE_ARCHETYPE = Registries.key("item_type_archetype");
		
		// Modeled types
		
		public static final DefaultedRegistryType<ModeledBlock> MODELED_BLOCK = Registries.key("modeled_block");
		
		public static final DefaultedRegistryType<ModeledEntity> MODELED_ENTITY = Registries.key("modeled_entity");
		
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
		
		public static final DefaultedRegistryType<MapCodec<? extends CustomItemAction.Config>> ITEM_ACTION_CONFIG_TYPE = Registries.key("item_action_config_type");
		
	    private static <V> DefaultedRegistryType<V> key(final String key) {
	        return RegistryType.of(RegistryRoots.SPONGE, SpongeTools.key(key)).asDefaultedType(Sponge::game);
	    }
		
		private Registries() {
		}
	}
	
	public static final class Keys {
		
		public static final Key<Value<BlockSoundGroup>> BLOCK_SOUND_GROUP = Keys.key("block_sound_group", BlockSoundGroup.class);
		
		public static final Key<Value<ResourceKey>> EQUIPMENT_ASSET = Keys.key("equipment_asset", ResourceKey.class);
		
		public static final Key<Value<ResourceKey>> EQUIPMENT_CAMERA_OVERLAY = Keys.key("equipment_camera_overlay", ResourceKey.class);
		
		public static final Key<Value<Boolean>> EQUIPMENT_DAMAGEABLE = Keys.key("equipment_damageable", Boolean.class);
		
		public static final Key<Value<Boolean>> EQUIPMENT_DISPENSABLE = Keys.key("equipment_dispensable", Boolean.class);
		
		public static final Key<Value<SoundType>> EQUIPMENT_SOUND = Keys.key("equipment_sound", SoundType.class);
		
		public static final Key<Value<Boolean>> EQUIPMENT_SWAPPABLE = Keys.key("equipment_swappable", Boolean.class);
		
		public static final Key<Value<EquipmentType>> EQUIPMENT_TYPE = Keys.key("equipment_type", EquipmentType.class);
		
		public static final Key<Value<Double>> FRICTION_FACTOR = Keys.key("friction_factor", Double.class);
		
		public static final Key<Value<Boolean>> HAS_COLLISION = Keys.key("collision", Boolean.class);
		
		public static final Key<Value<Boolean>> HAS_DYNAMIC_SHAPE = Keys.key("dynamic_shape", Boolean.class);
		
		public static final Key<Value<Boolean>> HAS_OCCLUSION = Keys.key("occlusion", Boolean.class);
		
		public static final Key<Value<Double>> JUMP_FACTOR = Keys.key("jump_factor", Double.class);
		
		public static final Key<Value<ResourceKey>> LOOT_TABLE_KEY = Keys.key("loot_table_key", ResourceKey.class);
		
		public static final Key<Value<LoreProcessor>> LORE_PROCESSOR = Keys.key("lore_processor", LoreProcessor.class);
		
		public static final Key<ListValue<LoreProvider>> LORE_PROVIDERS = Keys.listKey("lore_providers", LoreProvider.class);
		
		public static final Key<Value<MapColorType>> MAP_COLOR_TYPE = Keys.key("map_color_type", MapColorType.class);
		
		public static final Key<Value<Boolean>> REQUIRE_TOOL = Keys.key("require_tool", Boolean.class);
		
		public static final Key<Value<Double>> SPEED_FACTOR = Keys.key("speed_factor", Double.class);
		
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
