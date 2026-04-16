package net.hellheim.spongetools.common.codec;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.resourcepack.item.ConditionalProperty;
import net.hellheim.spongetools.resourcepack.item.ItemModel;
import net.hellheim.spongetools.resourcepack.item.RangeSelectProperty;
import net.hellheim.spongetools.resourcepack.item.SelectProperty;
import net.hellheim.spongetools.resourcepack.item.SpecialModel;
import net.hellheim.spongetools.resourcepack.item.TintSource;
import net.hellheim.spongetools.resourcepack.meta.GuiScaling;
import net.hellheim.spongetools.resourcepack.meta.MetadataSection;

public final class SpongeToolsCodecs {
	
	public static void bootstrap() {
		// Resource pack Metadata
		guiScalings();
		metadataSections();
		
		// Item models
		specialModels();
		tintSources();
		conditionalProperties();
		selectProperties();
		rangeSelectProperties();
		itemDefinitions();
	}
	
	private static void guiScalings() {
		GuiScaling.ID_MAPPER.put("stretch", GuiScaling.Stretch.CODEC);
		GuiScaling.ID_MAPPER.put("tile", GuiScaling.Tile.CODEC);
		GuiScaling.ID_MAPPER.put("nine_slice", GuiScaling.NineSlice.CODEC);
	}
	
	private static void metadataSections() {
		MetadataSection.ID_MAPPER_CLIENT.put("pack", MetadataSection.Pack.CODEC_CLIENT);
		MetadataSection.ID_MAPPER_CLIENT.put("overlays", MetadataSection.Overlays.CODEC_CLIENT);
		MetadataSection.ID_MAPPER_SERVER.put("filter", MetadataSection.Filter.CODEC);
		MetadataSection.ID_MAPPER_CLIENT.put("villager", MetadataSection.Villager.CODEC);
		MetadataSection.ID_MAPPER_CLIENT.put("gui", MetadataSection.Gui.CODEC);
		MetadataSection.ID_MAPPER_CLIENT.put("texture", MetadataSection.Texture.CODEC);
		MetadataSection.ID_MAPPER_CLIENT.put("animation", MetadataSection.Animation.CODEC);
		
		MetadataSection.ID_MAPPER_SERVER.put("pack", MetadataSection.Pack.CODEC_SERVER);
		MetadataSection.ID_MAPPER_SERVER.put("overlays", MetadataSection.Overlays.CODEC_SERVER);
		MetadataSection.ID_MAPPER_SERVER.put("filter", MetadataSection.Filter.CODEC);
	}
	
	private static void specialModels() {
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("bed"), SpecialModel.Bed.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("banner"), SpecialModel.Banner.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("conduit"), SpecialModel.Conduit.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("chest"), SpecialModel.Chest.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("head"), SpecialModel.Skull.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("shulker_box"), SpecialModel.ShulkerBox.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("shield"), SpecialModel.Shield.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("trident"), SpecialModel.Trident.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("decorated_pot"), SpecialModel.DecoratedPot.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("standing_sign"), SpecialModel.StandingSign.CODEC);
		SpecialModel.ID_MAPPER.put(ResourceKey.minecraft("hanging_sign"), SpecialModel.HangingSign.CODEC);
	}
	
	private static void tintSources() {
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("custom_model_data"), TintSource.CustomModelData.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("constant"), TintSource.Constant.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("dye"), TintSource.Dye.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("grass"), TintSource.Grass.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("firework"), TintSource.Firework.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("potion"), TintSource.Potion.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("map_color"), TintSource.Map.CODEC);
		TintSource.ID_MAPPER.put(ResourceKey.minecraft("team"), TintSource.Team.CODEC);
	}
	
	private static void conditionalProperties() {
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("custom_model_data"), ConditionalProperty.CustomModelData.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("using_item"), ConditionalProperty.Using.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("broken"), ConditionalProperty.Broken.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("damaged"), ConditionalProperty.Damaged.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("fishing_rod/cast"), ConditionalProperty.FishingRodCast.CODEC);
		// TODO
		// ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("has_component"), ConditionalProperty.HasComponent.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("bundle/has_selected_item"), ConditionalProperty.BundleHasSelectedItem.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("selected"), ConditionalProperty.Selected.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("carried"), ConditionalProperty.Carried.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("extended_view"), ConditionalProperty.ExtendedView.CODEC);
		// TODO
		// ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("keybind_down"), ConditionalProperty.KeybindDown.CODEC);
		ConditionalProperty.ID_MAPPER.put(ResourceKey.minecraft("view_entity"), ConditionalProperty.ViewingEntity.CODEC);
	}
	
	private static void selectProperties() {
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("custom_model_data"), SelectProperty.CustomModelData.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("main_hand"), SelectProperty.MainHand.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("charge_type"), SelectProperty.Charge.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("trim_material"), SelectProperty.Trim.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("block_state"), SelectProperty.StateProperty.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("display_context"), SelectProperty.Display.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("local_time"), SelectProperty.LocalTime.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("context_entity_type"), SelectProperty.Entity.SWITCH);
		SelectProperty.ID_MAPPER.put(ResourceKey.minecraft("context_dimension"), SelectProperty.World.SWITCH);
	}
	
	private static void rangeSelectProperties() {
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("custom_model_data"), RangeSelectProperty.CustomModelData.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("bundle/fullness"), RangeSelectProperty.BundleFullness.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("damage"), RangeSelectProperty.Damage.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("cooldown"), RangeSelectProperty.Cooldown.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("time"), RangeSelectProperty.Time.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("compass"), RangeSelectProperty.CompassAngle.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("crossbow/pull"), RangeSelectProperty.CrossbowPull.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("use_cycle"), RangeSelectProperty.UseCycle.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("use_duration"), RangeSelectProperty.UseDuration.CODEC);
		RangeSelectProperty.ID_MAPPER.put(ResourceKey.minecraft("count"), RangeSelectProperty.Quantity.CODEC);
	}
	
	private static void itemDefinitions() {
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("empty"), ItemModel.Empty.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("model"), ItemModel.Simple.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("range_dispatch"), ItemModel.RangeSelect.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("special"), ItemModel.Special.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("composite"), ItemModel.Composite.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("bundle/selected_item"), ItemModel.BundleSelectedItem.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("select"), ItemModel.Select.CODEC);
		ItemModel.ID_MAPPER.put(ResourceKey.minecraft("condition"), ItemModel.Conditional.CODEC);
	}
	
	private SpongeToolsCodecs() {
	}
}
