package net.hellheim.spongetools.custom.item.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.spongepowered.api.ResourceKey;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import net.hellheim.spongetools.util.ModelUtil;

public record ModelTemplate(Optional<ResourceKey> key, Optional<String> suffix, Set<TextureSlot> slots) {
	
	private static final Multimap<ResourceKey, ModelTemplate> MULTIMAP = LinkedHashMultimap.create();
	
	public static final ModelTemplate
	
	PARTICLE_ONLY = of(TextureSlot.PARTICLE),
			
	FLAT_ITEM = item("generated", TextureSlot.LAYER0),
	TWO_LAYERED_ITEM = item("generated", TextureSlot.LAYER0, TextureSlot.LAYER1),
	THREE_LAYERED_ITEM = item("generated", TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2),
	BOW = item("bow", TextureSlot.LAYER0),
	HANDHELD = item("handheld", TextureSlot.LAYER0),
	HANDHELD_ROD = item("handheld_rod", TextureSlot.LAYER0),
	HANDHELD_MACE = item("handheld_mace", TextureSlot.LAYER0),
	TEMPLATE_MUSIC_DISC = item("template_music_disc", TextureSlot.LAYER0),
	TEMPLATE_SHULKER_BOX = item("template_shulker_box", TextureSlot.PARTICLE),
	TEMPLATE_BED = item("template_bed", TextureSlot.PARTICLE),
	TEMPLATE_CHEST = item("template_chest", TextureSlot.PARTICLE),
	TEMPLATE_BUNDLE_OPEN_FRONT = item("template_bundle_open_front", "_open_front", TextureSlot.LAYER0),
	TEMPLATE_BUNDLE_OPEN_BACK = item("template_bundle_open_back", "_open_back", TextureSlot.LAYER0),
	
	CUBE = block("cube", TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN),
	CUBE_DIRECTIONAL = block("cube_directional", TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN),
	CUBE_ALL = block("cube_all", TextureSlot.ALL),
	CUBE_ALL_INNER_FACES = block("cube_all_inner_faces", TextureSlot.ALL),
	CUBE_MIRRORED_ALL = block("cube_mirrored_all", "_mirrored", TextureSlot.ALL),
	CUBE_NORTH_WEST_MIRRORED_ALL = block("cube_north_west_mirrored_all", "_north_west_mirrored", TextureSlot.ALL),
	CUBE_COLUMN_UV_LOCKED_X = block("cube_column_uv_locked_x", "_x", TextureSlot.END, TextureSlot.SIDE),
	CUBE_COLUMN_UV_LOCKED_Y = block("cube_column_uv_locked_y", "_y", TextureSlot.END, TextureSlot.SIDE),
	CUBE_COLUMN_UV_LOCKED_Z = block("cube_column_uv_locked_z", "_z", TextureSlot.END, TextureSlot.SIDE),
	CUBE_COLUMN = block("cube_column", TextureSlot.END, TextureSlot.SIDE),
	CUBE_COLUMN_HORIZONTAL = block("cube_column_horizontal", "_horizontal", TextureSlot.END, TextureSlot.SIDE),
	CUBE_COLUMN_MIRRORED = block("cube_column_mirrored", "_mirrored", TextureSlot.END, TextureSlot.SIDE),
	CUBE_TOP = block("cube_top", TextureSlot.TOP, TextureSlot.SIDE),
	CUBE_BOTTOM_TOP = block("cube_bottom_top", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE),
	CUBE_BOTTOM_TOP_INNER_FACES = block("cube_bottom_top_inner_faces", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE),
	CUBE_ORIENTABLE = block("orientable", TextureSlot.TOP, TextureSlot.FRONT, TextureSlot.SIDE),
	CUBE_ORIENTABLE_TOP_BOTTOM = block("orientable_with_bottom", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT),
	CUBE_ORIENTABLE_VERTICAL = block("orientable_vertical", "_vertical", TextureSlot.FRONT, TextureSlot.SIDE),
	BUTTON = block("button", TextureSlot.TEXTURE),
	BUTTON_PRESSED = block("button_pressed", "_pressed", TextureSlot.TEXTURE),
	BUTTON_INVENTORY = block("button_inventory", "_inventory", TextureSlot.TEXTURE),
	DOOR_BOTTOM_LEFT = block("door_bottom_left", "_bottom_left", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_BOTTOM_LEFT_OPEN = block("door_bottom_left_open", "_bottom_left_open", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_BOTTOM_RIGHT = block("door_bottom_right", "_bottom_right", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_BOTTOM_RIGHT_OPEN = block("door_bottom_right_open", "_bottom_right_open", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_TOP_LEFT = block("door_top_left", "_top_left", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_TOP_LEFT_OPEN = block("door_top_left_open", "_top_left_open", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_TOP_RIGHT = block("door_top_right", "_top_right", TextureSlot.TOP, TextureSlot.BOTTOM),
	DOOR_TOP_RIGHT_OPEN = block("door_top_right_open", "_top_right_open", TextureSlot.TOP, TextureSlot.BOTTOM),
	CUSTOM_FENCE_POST = block("custom_fence_post", "_post", TextureSlot.TEXTURE, TextureSlot.PARTICLE),
	CUSTOM_FENCE_SIDE_NORTH = block("custom_fence_side_north", "_side_north", TextureSlot.TEXTURE),
	CUSTOM_FENCE_SIDE_EAST = block("custom_fence_side_east", "_side_east", TextureSlot.TEXTURE),
	CUSTOM_FENCE_SIDE_SOUTH = block("custom_fence_side_south", "_side_south", TextureSlot.TEXTURE),
	CUSTOM_FENCE_SIDE_WEST = block("custom_fence_side_west", "_side_west", TextureSlot.TEXTURE),
	CUSTOM_FENCE_INVENTORY = block("custom_fence_inventory", "_inventory", TextureSlot.TEXTURE),
	FENCE_POST = block("fence_post", "_post", TextureSlot.TEXTURE),
	FENCE_SIDE = block("fence_side", "_side", TextureSlot.TEXTURE),
	FENCE_INVENTORY = block("fence_inventory", "_inventory", TextureSlot.TEXTURE),
	WALL_POST = block("template_wall_post", "_post", TextureSlot.WALL),
	WALL_LOW_SIDE = block("template_wall_side", "_side", TextureSlot.WALL),
	WALL_TALL_SIDE = block("template_wall_side_tall", "_side_tall", TextureSlot.WALL),
	WALL_INVENTORY = block("wall_inventory", "_inventory", TextureSlot.WALL),
	CUSTOM_FENCE_GATE_CLOSED = block("template_custom_fence_gate", TextureSlot.TEXTURE, TextureSlot.PARTICLE),
	CUSTOM_FENCE_GATE_OPEN = block("template_custom_fence_gate_open", "_open", TextureSlot.TEXTURE, TextureSlot.PARTICLE),
	CUSTOM_FENCE_GATE_WALL_CLOSED = block("template_custom_fence_gate_wall", "_wall", TextureSlot.TEXTURE, TextureSlot.PARTICLE),
	CUSTOM_FENCE_GATE_WALL_OPEN = block("template_custom_fence_gate_wall_open", "_wall_open", TextureSlot.TEXTURE, TextureSlot.PARTICLE),
	FENCE_GATE_CLOSED = block("template_fence_gate", TextureSlot.TEXTURE),
	FENCE_GATE_OPEN = block("template_fence_gate_open", "_open", TextureSlot.TEXTURE),
	FENCE_GATE_WALL_CLOSED = block("template_fence_gate_wall", "_wall", TextureSlot.TEXTURE),
	FENCE_GATE_WALL_OPEN = block("template_fence_gate_wall_open", "_wall_open", TextureSlot.TEXTURE),
	PRESSURE_PLATE_UP = block("pressure_plate_up", TextureSlot.TEXTURE),
	PRESSURE_PLATE_DOWN = block("pressure_plate_down", "_down", TextureSlot.TEXTURE),
	SLAB_BOTTOM = block("slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE),
	SLAB_TOP = block("slab_top", "_top", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE),
	LEAVES = block("leaves", TextureSlot.ALL),
	STAIRS_STRAIGHT = block("stairs", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE),
	STAIRS_INNER = block("inner_stairs", "_inner", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE),
	STAIRS_OUTER = block("outer_stairs", "_outer", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE),
	TRAPDOOR_TOP = block("template_trapdoor_top", "_top", TextureSlot.TEXTURE),
	TRAPDOOR_BOTTOM = block("template_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE),
	TRAPDOOR_OPEN = block("template_trapdoor_open", "_open", TextureSlot.TEXTURE),
	ORIENTABLE_TRAPDOOR_TOP = block("template_orientable_trapdoor_top", "_top", TextureSlot.TEXTURE),
	ORIENTABLE_TRAPDOOR_BOTTOM = block("template_orientable_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE),
	ORIENTABLE_TRAPDOOR_OPEN = block("template_orientable_trapdoor_open", "_open", TextureSlot.TEXTURE),
	POINTED_DRIPSTONE = block("pointed_dripstone", TextureSlot.CROSS),
	CROSS = block("cross", TextureSlot.CROSS),
	TINTED_CROSS = block("tinted_cross", TextureSlot.CROSS),
	CROSS_EMISSIVE = block("cross_emissive", TextureSlot.CROSS, TextureSlot.CROSS_EMISSIVE),
	FLOWER_POT_CROSS = block("flower_pot_cross", TextureSlot.PLANT),
	TINTED_FLOWER_POT_CROSS = block("tinted_flower_pot_cross", TextureSlot.PLANT),
	FLOWER_POT_CROSS_EMISSIVE = block("flower_pot_cross_emissive", TextureSlot.PLANT, TextureSlot.CROSS_EMISSIVE),
	RAIL_FLAT = block("rail_flat", TextureSlot.RAIL),
	RAIL_CURVED = block("rail_curved", "_corner", TextureSlot.RAIL),
	RAIL_RAISED_NE = block("template_rail_raised_ne", "_raised_ne", TextureSlot.RAIL),
	RAIL_RAISED_SW = block("template_rail_raised_sw", "_raised_sw", TextureSlot.RAIL),
	CARPET = block("carpet", TextureSlot.WOOL),
	MOSSY_CARPET_SIDE = block("mossy_carpet_side", TextureSlot.SIDE),
	FLOWERBED_1 = block("flowerbed_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM),
	FLOWERBED_2 = block("flowerbed_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM),
	FLOWERBED_3 = block("flowerbed_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM),
	FLOWERBED_4 = block("flowerbed_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM),
	CORAL_FAN = block("coral_fan", TextureSlot.FAN),
	CORAL_WALL_FAN = block("coral_wall_fan", TextureSlot.FAN),
	GLAZED_TERRACOTTA = block("template_glazed_terracotta", TextureSlot.PATTERN),
	CHORUS_FLOWER = block("template_chorus_flower", TextureSlot.TEXTURE),
	DAYLIGHT_DETECTOR = block("template_daylight_detector", TextureSlot.TOP, TextureSlot.SIDE),
	STAINED_GLASS_PANE_NOSIDE = block("template_glass_pane_noside", "_noside", TextureSlot.PANE),
	STAINED_GLASS_PANE_NOSIDE_ALT = block("template_glass_pane_noside_alt", "_noside_alt", TextureSlot.PANE),
	STAINED_GLASS_PANE_POST = block("template_glass_pane_post", "_post", TextureSlot.PANE, TextureSlot.EDGE),
	STAINED_GLASS_PANE_SIDE = block("template_glass_pane_side", "_side", TextureSlot.PANE, TextureSlot.EDGE),
	STAINED_GLASS_PANE_SIDE_ALT = block("template_glass_pane_side_alt", "_side_alt", TextureSlot.PANE, TextureSlot.EDGE),
	COMMAND_BLOCK = block("template_command_block", TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.SIDE),
	CHISELED_BOOKSHELF_SLOT_TOP_LEFT = block("template_chiseled_bookshelf_slot_top_left", "_slot_top_left", TextureSlot.TEXTURE),
	CHISELED_BOOKSHELF_SLOT_TOP_MID = block("template_chiseled_bookshelf_slot_top_mid", "_slot_top_mid", TextureSlot.TEXTURE),
	CHISELED_BOOKSHELF_SLOT_TOP_RIGHT = block("template_chiseled_bookshelf_slot_top_right", "_slot_top_right", TextureSlot.TEXTURE),
	CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT = block("template_chiseled_bookshelf_slot_bottom_left", "_slot_bottom_left", TextureSlot.TEXTURE),
	CHISELED_BOOKSHELF_SLOT_BOTTOM_MID = block("template_chiseled_bookshelf_slot_bottom_mid", "_slot_bottom_mid", TextureSlot.TEXTURE),
	CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT = block("template_chiseled_bookshelf_slot_bottom_right", "_slot_bottom_right", TextureSlot.TEXTURE),
	ANVIL = block("template_anvil", TextureSlot.TOP),
	ATTACHED_STEM = block("stem_fruit", TextureSlot.STEM, TextureSlot.UPPER_STEM),
	CROP = block("crop", TextureSlot.CROP),
	FARMLAND = block("template_farmland", TextureSlot.DIRT, TextureSlot.TOP),
	FIRE_FLOOR = block("template_fire_floor", TextureSlot.FIRE),
	FIRE_SIDE = block("template_fire_side", TextureSlot.FIRE),
	FIRE_SIDE_ALT = block("template_fire_side_alt", TextureSlot.FIRE),
	FIRE_UP = block("template_fire_up", TextureSlot.FIRE),
	FIRE_UP_ALT = block("template_fire_up_alt", TextureSlot.FIRE),
	CAMPFIRE = block("template_campfire", TextureSlot.FIRE, TextureSlot.LIT_LOG),
	LANTERN = block("template_lantern", TextureSlot.LANTERN),
	HANGING_LANTERN = block("template_hanging_lantern", "_hanging", TextureSlot.LANTERN),
	TORCH = block("template_torch", TextureSlot.TORCH),
	TORCH_UNLIT = block("template_torch_unlit", TextureSlot.TORCH),
	WALL_TORCH = block("template_torch_wall", TextureSlot.TORCH),
	WALL_TORCH_UNLIT = block("template_torch_wall_unlit", TextureSlot.TORCH),
	REDSTONE_TORCH = block("template_redstone_torch", TextureSlot.TORCH),
	REDSTONE_WALL_TORCH = block("template_redstone_torch_wall", TextureSlot.TORCH),
	PISTON = block("template_piston", TextureSlot.PLATFORM, TextureSlot.BOTTOM, TextureSlot.SIDE),
	PISTON_HEAD = block("template_piston_head", TextureSlot.PLATFORM, TextureSlot.SIDE, TextureSlot.UNSTICKY),
	PISTON_HEAD_SHORT = block("template_piston_head_short", TextureSlot.PLATFORM, TextureSlot.SIDE, TextureSlot.UNSTICKY),
	SEAGRASS = block("template_seagrass", TextureSlot.TEXTURE),
	TURTLE_EGG = block("template_turtle_egg", TextureSlot.ALL),
	TWO_TURTLE_EGGS = block("template_two_turtle_eggs", TextureSlot.ALL),
	THREE_TURTLE_EGGS = block("template_three_turtle_eggs", TextureSlot.ALL),
	FOUR_TURTLE_EGGS = block("template_four_turtle_eggs", TextureSlot.ALL),
	SINGLE_FACE = block("template_single_face", TextureSlot.TEXTURE),
	CAULDRON_LEVEL1 = block("template_cauldron_level1", TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE),
	CAULDRON_LEVEL2 = block("template_cauldron_level2", TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE),
	CAULDRON_FULL = block("template_cauldron_full", TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE),
	AZALEA = block("template_azalea", TextureSlot.TOP, TextureSlot.SIDE),
	POTTED_AZALEA = block("template_potted_azalea_bush", TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE),
	POTTED_FLOWERING_AZALEA = block("template_potted_azalea_bush", TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE),
	SNIFFER_EGG = block("sniffer_egg", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST),
	CROSSBOW = item("crossbow", TextureSlot.LAYER0),
	CANDLE = block("template_candle", TextureSlot.ALL, TextureSlot.PARTICLE),
	TWO_CANDLES = block("template_two_candles", TextureSlot.ALL, TextureSlot.PARTICLE),
	THREE_CANDLES = block("template_three_candles", TextureSlot.ALL, TextureSlot.PARTICLE),
	FOUR_CANDLES = block("template_four_candles", TextureSlot.ALL, TextureSlot.PARTICLE),
	CANDLE_CAKE = block("template_cake_with_candle", TextureSlot.CANDLE, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE),
	SCULK_SHRIEKER = block("template_sculk_shrieker", TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE, TextureSlot.INNER_TOP),
	VAULT = block("template_vault", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT);
	
	public static final ModelTemplate[] STEMS = IntStream.range(0, 8)
			.mapToObj(i -> block("stem_growth" + i, "_stage" + i, TextureSlot.STEM))
			.toArray(ModelTemplate[]::new);
	
	public ModelTemplate(
		final Optional<ResourceKey> key,
		final Optional<String> suffix,
		final Set<TextureSlot> slots
	) {
		this.key = Objects.requireNonNull(key, "key");
		this.suffix = Objects.requireNonNull(suffix, "suffix");
		this.slots = Set.copyOf(slots);
		
		this.key.ifPresent(k -> ModelTemplate.MULTIMAP.put(k, this));
	}
	
	public static Collection<ModelTemplate> get(final ResourceKey key) {
		return ModelTemplate.MULTIMAP.get(Objects.requireNonNull(key, "key"));
	}
	
	public static ModelTemplate of(
		final Optional<ResourceKey> key, final Optional<String> suffix, final Set<TextureSlot> slots
	) {
		return new ModelTemplate(key, suffix, slots);
	}
	
	public static ModelTemplate of(
		final Optional<ResourceKey> key, final Optional<String> suffix, final TextureSlot... slots
	) {
		return ModelTemplate.of(key, suffix, Set.of(slots));
	}
	
	public static ModelTemplate of(
		final ResourceKey key, final Optional<String> suffix, final TextureSlot... slots
	) {
		return ModelTemplate.of(Optional.of(key), suffix, slots);
	}
	
	private static ModelTemplate of(final TextureSlot... slots) {
		return ModelTemplate.of(Optional.empty(), Optional.empty(), slots);
	}
	
	public static ModelTemplate block(final String name, final TextureSlot... slots) {
		return ModelTemplate.of(ModelUtil.withBlockPrefix(name), Optional.empty(), slots);
	}
	
	public static ModelTemplate block(final String name, final String suffix, final TextureSlot... slots) {
		return ModelTemplate.of(ModelUtil.withBlockPrefix(name), Optional.of(suffix), slots);
	}
	
	public static ModelTemplate item(final String name, final TextureSlot... slots) {
		return ModelTemplate.of(ModelUtil.withItemPrefix(name), Optional.empty(), slots);
	}
	
	public static ModelTemplate item(final String name, final String suffix, final TextureSlot... slots) {
		return ModelTemplate.of(ModelUtil.withItemPrefix(name), Optional.of(suffix), slots);
	}
}
