package net.hellheim.spongetools.custom.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.util.ModelUtil;

public final class ModelTemplates {
	
	public static final ModelTemplateProvider.T1 PARTICLE_ONLY = ModelTemplateProvider.t1(of(TextureSlots.PARTICLE));
	
	
	
	public static final ModelTemplateProvider.T1 FLAT_ITEM = ModelTemplateProvider.t1(
			item("generated", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T2 TWO_LAYERED_ITEM = ModelTemplateProvider.t2(
			item("generated", TextureSlots.LAYER0, TextureSlots.LAYER1));
	
	public static final ModelTemplateProvider.T3 THREE_LAYERED_ITEM = ModelTemplateProvider.t3(
			item("generated", TextureSlots.LAYER0, TextureSlots.LAYER1, TextureSlots.LAYER2));
	
	public static final ModelTemplateProvider.T1 BOW = ModelTemplateProvider.t1(
			item("bow", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 CROSSBOW = ModelTemplateProvider.t1(
			item("crossbow", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 HANDHELD = ModelTemplateProvider.t1(
			item("handheld", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 HANDHELD_ROD = ModelTemplateProvider.t1(
			item("handheld_rod", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 HANDHELD_MACE = ModelTemplateProvider.t1(
			item("handheld_mace", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_MUSIC_DISC = ModelTemplateProvider.t1(
			item("template_music_disc", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_SHULKER_BOX = ModelTemplateProvider.t1(
			item("template_shulker_box", TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_BED = ModelTemplateProvider.t1(
			item("template_bed", TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_CHEST = ModelTemplateProvider.t1(
			item("template_chest", TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_BUNDLE_OPEN_FRONT = ModelTemplateProvider.t1(
			item("template_bundle_open_front", "_open_front", TextureSlots.LAYER0));
	
	public static final ModelTemplateProvider.T1 TEMPLATE_BUNDLE_OPEN_BACK = ModelTemplateProvider.t1(
			item("template_bundle_open_back", "_open_back", TextureSlots.LAYER0));
	
	
	
	public static final ModelTemplateProvider.T7 CUBE = ModelTemplateProvider.t7(
			block("cube", TextureSlots.PARTICLE, TextureSlots.NORTH, TextureSlots.SOUTH, TextureSlots.EAST, TextureSlots.WEST, TextureSlots.UP, TextureSlots.DOWN));
			
	public static final ModelTemplateProvider.T7 CUBE_DIRECTIONAL = ModelTemplateProvider.t7(
			block("cube_directional", TextureSlots.PARTICLE, TextureSlots.NORTH, TextureSlots.SOUTH, TextureSlots.EAST, TextureSlots.WEST, TextureSlots.UP, TextureSlots.DOWN));
	
	public static final ModelTemplateProvider.T1 CUBE_ALL = ModelTemplateProvider.t1(
			block("cube_all", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 CUBE_ALL_INNER_FACES = ModelTemplateProvider.t1(
			block("cube_all_inner_faces", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 CUBE_MIRRORED_ALL = ModelTemplateProvider.t1(
			block("cube_mirrored_all", "_mirrored", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 CUBE_NORTH_WEST_MIRRORED_ALL = ModelTemplateProvider.t1(
			block("cube_north_west_mirrored_all", "_north_west_mirrored", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN_UV_LOCKED_X = ModelTemplateProvider.t2(
			block("cube_column_uv_locked_x", "_x", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN_UV_LOCKED_Y = ModelTemplateProvider.t2(
			block("cube_column_uv_locked_y", "_y", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN_UV_LOCKED_Z = ModelTemplateProvider.t2(
			block("cube_column_uv_locked_z", "_z", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN = ModelTemplateProvider.t2(
			block("cube_column", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN_HORIZONTAL = ModelTemplateProvider.t2(
			block("cube_column_horizontal", "_horizontal", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_COLUMN_MIRRORED = ModelTemplateProvider.t2(
			block("cube_column_mirrored", "_mirrored", TextureSlots.END, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 CUBE_TOP = ModelTemplateProvider.t2(
			block("cube_top", TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 CUBE_BOTTOM_TOP = ModelTemplateProvider.t3(
			block("cube_bottom_top", TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 CUBE_BOTTOM_TOP_INNER_FACES = ModelTemplateProvider.t3(
			block("cube_bottom_top_inner_faces", TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 CUBE_ORIENTABLE = ModelTemplateProvider.t3(
			block("orientable", TextureSlots.TOP, TextureSlots.FRONT, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T4 CUBE_ORIENTABLE_TOP_BOTTOM = ModelTemplateProvider.t4(
			block("orientable_with_bottom", TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE, TextureSlots.FRONT));
	
	public static final ModelTemplateProvider.T2 CUBE_ORIENTABLE_VERTICAL = ModelTemplateProvider.t2(
			block("orientable_vertical", "_vertical", TextureSlots.FRONT, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T1 BUTTON = ModelTemplateProvider.t1(
			block("button", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 BUTTON_PRESSED = ModelTemplateProvider.t1(
			block("button_pressed", "_pressed", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 BUTTON_INVENTORY = ModelTemplateProvider.t1(
			block("button_inventory", "_inventory", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T2 DOOR_BOTTOM_LEFT = ModelTemplateProvider.t2(
			block("door_bottom_left", "_bottom_left", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_BOTTOM_LEFT_OPEN = ModelTemplateProvider.t2(
			block("door_bottom_left_open", "_bottom_left_open", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_BOTTOM_RIGHT = ModelTemplateProvider.t2(
			block("door_bottom_right", "_bottom_right", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_BOTTOM_RIGHT_OPEN = ModelTemplateProvider.t2(
			block("door_bottom_right_open", "_bottom_right_open", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_TOP_LEFT = ModelTemplateProvider.t2(
			block("door_top_left", "_top_left", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_TOP_LEFT_OPEN = ModelTemplateProvider.t2(
			block("door_top_left_open", "_top_left_open", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_TOP_RIGHT = ModelTemplateProvider.t2(
			block("door_top_right", "_top_right", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 DOOR_TOP_RIGHT_OPEN = ModelTemplateProvider.t2(
			block("door_top_right_open", "_top_right_open", TextureSlots.TOP, TextureSlots.BOTTOM));
	
	public static final ModelTemplateProvider.T2 CUSTOM_FENCE_POST = ModelTemplateProvider.t2(
			block("custom_fence_post", "_post", TextureSlots.TEXTURE, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T1 CUSTOM_FENCE_SIDE_NORTH = ModelTemplateProvider.t1(
			block("custom_fence_side_north", "_side_north", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CUSTOM_FENCE_SIDE_EAST = ModelTemplateProvider.t1(
			block("custom_fence_side_east", "_side_east", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CUSTOM_FENCE_SIDE_SOUTH = ModelTemplateProvider.t1(
			block("custom_fence_side_south", "_side_south", TextureSlots.TEXTURE));
			
	public static final ModelTemplateProvider.T1 CUSTOM_FENCE_SIDE_WEST = ModelTemplateProvider.t1(
			block("custom_fence_side_west", "_side_west", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CUSTOM_FENCE_INVENTORY = ModelTemplateProvider.t1(
			block("custom_fence_inventory", "_inventory", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_POST = ModelTemplateProvider.t1(
			block("fence_post", "_post", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_SIDE = ModelTemplateProvider.t1(
			block("fence_side", "_side", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_INVENTORY = ModelTemplateProvider.t1(
			block("fence_inventory", "_inventory", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 WALL_POST = ModelTemplateProvider.t1(
			block("template_wall_post", "_post", TextureSlots.WALL));
	
	public static final ModelTemplateProvider.T1 WALL_LOW_SIDE = ModelTemplateProvider.t1(
			block("template_wall_side", "_side", TextureSlots.WALL));
	
	public static final ModelTemplateProvider.T1 WALL_TALL_SIDE = ModelTemplateProvider.t1(
			block("template_wall_side_tall", "_side_tall", TextureSlots.WALL));
	
	public static final ModelTemplateProvider.T1 WALL_INVENTORY = ModelTemplateProvider.t1(
			block("wall_inventory", "_inventory", TextureSlots.WALL));
	
	public static final ModelTemplateProvider.T2 CUSTOM_FENCE_GATE_CLOSED = ModelTemplateProvider.t2(
			block("template_custom_fence_gate", TextureSlots.TEXTURE, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 CUSTOM_FENCE_GATE_OPEN = ModelTemplateProvider.t2(
			block("template_custom_fence_gate_open", "_open", TextureSlots.TEXTURE, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 CUSTOM_FENCE_GATE_WALL_CLOSED = ModelTemplateProvider.t2(
			block("template_custom_fence_gate_wall", "_wall", TextureSlots.TEXTURE, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 CUSTOM_FENCE_GATE_WALL_OPEN = ModelTemplateProvider.t2(
			block("template_custom_fence_gate_wall_open", "_wall_open", TextureSlots.TEXTURE, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T1 FENCE_GATE_CLOSED = ModelTemplateProvider.t1(
			block("template_fence_gate", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_GATE_OPEN = ModelTemplateProvider.t1(
			block("template_fence_gate_open", "_open", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_GATE_WALL_CLOSED = ModelTemplateProvider.t1(
			block("template_fence_gate_wall", "_wall", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 FENCE_GATE_WALL_OPEN = ModelTemplateProvider.t1(
			block("template_fence_gate_wall_open", "_wall_open", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 PRESSURE_PLATE_UP = ModelTemplateProvider.t1(
			block("pressure_plate_up", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 PRESSURE_PLATE_DOWN = ModelTemplateProvider.t1(
			block("pressure_plate_down", "_down", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T3 SLAB_BOTTOM = ModelTemplateProvider.t3(
			block("slab", TextureSlots.BOTTOM, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 SLAB_TOP = ModelTemplateProvider.t3(
			block("slab_top", "_top", TextureSlots.BOTTOM, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T1 LEAVES = ModelTemplateProvider.t1(
			block("leaves", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T3 STAIRS_STRAIGHT = ModelTemplateProvider.t3(
			block("stairs", TextureSlots.BOTTOM, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 STAIRS_INNER = ModelTemplateProvider.t3(
			block("inner_stairs", "_inner", TextureSlots.BOTTOM, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 STAIRS_OUTER = ModelTemplateProvider.t3(
			block("outer_stairs", "_outer", TextureSlots.BOTTOM, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T1 TRAPDOOR_TOP = ModelTemplateProvider.t1(
			block("template_trapdoor_top", "_top", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 TRAPDOOR_BOTTOM = ModelTemplateProvider.t1(
			block("template_trapdoor_bottom", "_bottom", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 TRAPDOOR_OPEN = ModelTemplateProvider.t1(
			block("template_trapdoor_open", "_open", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 ORIENTABLE_TRAPDOOR_TOP = ModelTemplateProvider.t1(
			block("template_orientable_trapdoor_top", "_top", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 ORIENTABLE_TRAPDOOR_BOTTOM = ModelTemplateProvider.t1(
			block("template_orientable_trapdoor_bottom", "_bottom", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 ORIENTABLE_TRAPDOOR_OPEN = ModelTemplateProvider.t1(
			block("template_orientable_trapdoor_open", "_open", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 POINTED_DRIPSTONE = ModelTemplateProvider.t1(
			block("pointed_dripstone", TextureSlots.CROSS));
	
	public static final ModelTemplateProvider.T1 CROSS = ModelTemplateProvider.t1(
			block("cross", TextureSlots.CROSS));
	
	public static final ModelTemplateProvider.T1 TINTED_CROSS = ModelTemplateProvider.t1(
			block("tinted_cross", TextureSlots.CROSS));
	
	public static final ModelTemplateProvider.T2 CROSS_EMISSIVE = ModelTemplateProvider.t2(
			block("cross_emissive", TextureSlots.CROSS, TextureSlots.CROSS_EMISSIVE));
	
	public static final ModelTemplateProvider.T1 FLOWER_POT_CROSS = ModelTemplateProvider.t1(
			block("flower_pot_cross", TextureSlots.PLANT));
	
	public static final ModelTemplateProvider.T1 TINTED_FLOWER_POT_CROSS = ModelTemplateProvider.t1(
			block("tinted_flower_pot_cross", TextureSlots.PLANT));
	
	public static final ModelTemplateProvider.T2 FLOWER_POT_CROSS_EMISSIVE = ModelTemplateProvider.t2(
			block("flower_pot_cross_emissive", TextureSlots.PLANT, TextureSlots.CROSS_EMISSIVE));
	
	public static final ModelTemplateProvider.T1 RAIL_FLAT = ModelTemplateProvider.t1(
			block("rail_flat", TextureSlots.RAIL));
	
	public static final ModelTemplateProvider.T1 RAIL_CURVED = ModelTemplateProvider.t1(
			block("rail_curved", "_corner", TextureSlots.RAIL));
	
	public static final ModelTemplateProvider.T1 RAIL_RAISED_NE = ModelTemplateProvider.t1(
			block("template_rail_raised_ne", "_raised_ne", TextureSlots.RAIL));
	
	public static final ModelTemplateProvider.T1 RAIL_RAISED_SW = ModelTemplateProvider.t1(
			block("template_rail_raised_sw", "_raised_sw", TextureSlots.RAIL));
	
	public static final ModelTemplateProvider.T1 CARPET = ModelTemplateProvider.t1(
			block("carpet", TextureSlots.WOOL));
	
	public static final ModelTemplateProvider.T1 MOSSY_CARPET_SIDE = ModelTemplateProvider.t1(
			block("mossy_carpet_side", TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 FLOWERBED_1 = ModelTemplateProvider.t2(
			block("flowerbed_1", "_1", TextureSlots.FLOWERBED, TextureSlots.STEM));
	
	public static final ModelTemplateProvider.T2 FLOWERBED_2 = ModelTemplateProvider.t2(
			block("flowerbed_2", "_2", TextureSlots.FLOWERBED, TextureSlots.STEM));
	
	public static final ModelTemplateProvider.T2 FLOWERBED_3 = ModelTemplateProvider.t2(
			block("flowerbed_3", "_3", TextureSlots.FLOWERBED, TextureSlots.STEM));
	
	public static final ModelTemplateProvider.T2 FLOWERBED_4 = ModelTemplateProvider.t2(
			block("flowerbed_4", "_4", TextureSlots.FLOWERBED, TextureSlots.STEM));
	
	public static final ModelTemplateProvider.T1 CORAL_FAN = ModelTemplateProvider.t1(
			block("coral_fan", TextureSlots.FAN));
	
	public static final ModelTemplateProvider.T1 CORAL_WALL_FAN = ModelTemplateProvider.t1(
			block("coral_wall_fan", TextureSlots.FAN));
	
	public static final ModelTemplateProvider.T1 GLAZED_TERRACOTTA = ModelTemplateProvider.t1(
			block("template_glazed_terracotta", TextureSlots.PATTERN));
	
	public static final ModelTemplateProvider.T1 CHORUS_FLOWER = ModelTemplateProvider.t1(
			block("template_chorus_flower", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T2 DAYLIGHT_DETECTOR = ModelTemplateProvider.t2(
			block("template_daylight_detector", TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T1 STAINED_GLASS_PANE_NOSIDE = ModelTemplateProvider.t1(
			block("template_glass_pane_noside", "_noside", TextureSlots.PANE));
	
	public static final ModelTemplateProvider.T1 STAINED_GLASS_PANE_NOSIDE_ALT = ModelTemplateProvider.t1(
			block("template_glass_pane_noside_alt", "_noside_alt", TextureSlots.PANE));
	
	public static final ModelTemplateProvider.T2 STAINED_GLASS_PANE_POST = ModelTemplateProvider.t2(
			block("template_glass_pane_post", "_post", TextureSlots.PANE, TextureSlots.EDGE));
	
	public static final ModelTemplateProvider.T2 STAINED_GLASS_PANE_SIDE = ModelTemplateProvider.t2(
			block("template_glass_pane_side", "_side", TextureSlots.PANE, TextureSlots.EDGE));
	
	public static final ModelTemplateProvider.T2 STAINED_GLASS_PANE_SIDE_ALT = ModelTemplateProvider.t2(
			block("template_glass_pane_side_alt", "_side_alt", TextureSlots.PANE, TextureSlots.EDGE));
	
	public static final ModelTemplateProvider.T3 COMMAND_BLOCK = ModelTemplateProvider.t3(
			block("template_command_block", TextureSlots.FRONT, TextureSlots.BACK, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_TOP_LEFT = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_top_left", "_slot_top_left", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_TOP_MID = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_top_mid", "_slot_top_mid", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_TOP_RIGHT = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_top_right", "_slot_top_right", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_bottom_left", "_slot_bottom_left", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_BOTTOM_MID = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_bottom_mid", "_slot_bottom_mid", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT = ModelTemplateProvider.t1(
			block("template_chiseled_bookshelf_slot_bottom_right", "_slot_bottom_right", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 ANVIL = ModelTemplateProvider.t1(
			block("template_anvil", TextureSlots.TOP));
	
	public static final ModelTemplateProvider.T2 ATTACHED_STEM = ModelTemplateProvider.t2(
			block("stem_fruit", TextureSlots.STEM, TextureSlots.UPPER_STEM));
	
	public static final ModelTemplateProvider.T1 CROP = ModelTemplateProvider.t1(
			block("crop", TextureSlots.CROP));
	
	public static final ModelTemplateProvider.T2 FARMLAND = ModelTemplateProvider.t2(
			block("template_farmland", TextureSlots.DIRT, TextureSlots.TOP));
	
	public static final ModelTemplateProvider.T1 FIRE_FLOOR = ModelTemplateProvider.t1(
			block("template_fire_floor", TextureSlots.FIRE));
	
	public static final ModelTemplateProvider.T1 FIRE_SIDE = ModelTemplateProvider.t1(
			block("template_fire_side", TextureSlots.FIRE));
	
	public static final ModelTemplateProvider.T1 FIRE_SIDE_ALT = ModelTemplateProvider.t1(
			block("template_fire_side_alt", TextureSlots.FIRE));
	
	public static final ModelTemplateProvider.T1 FIRE_UP = ModelTemplateProvider.t1(
			block("template_fire_up", TextureSlots.FIRE));
	
	public static final ModelTemplateProvider.T1 FIRE_UP_ALT = ModelTemplateProvider.t1(
			block("template_fire_up_alt", TextureSlots.FIRE));
	
	public static final ModelTemplateProvider.T2 CAMPFIRE = ModelTemplateProvider.t2(
			block("template_campfire", TextureSlots.FIRE, TextureSlots.LIT_LOG));
	
	public static final ModelTemplateProvider.T1 LANTERN = ModelTemplateProvider.t1(
			block("template_lantern", TextureSlots.LANTERN));
	
	public static final ModelTemplateProvider.T1 HANGING_LANTERN = ModelTemplateProvider.t1(
			block("template_hanging_lantern", "_hanging", TextureSlots.LANTERN));
	
	public static final ModelTemplateProvider.T1 TORCH = ModelTemplateProvider.t1(
			block("template_torch", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T1 TORCH_UNLIT = ModelTemplateProvider.t1(
			block("template_torch_unlit", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T1 WALL_TORCH = ModelTemplateProvider.t1(
			block("template_torch_wall", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T1 WALL_TORCH_UNLIT = ModelTemplateProvider.t1(
			block("template_torch_wall_unlit", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T1 REDSTONE_TORCH = ModelTemplateProvider.t1(
			block("template_redstone_torch", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T1 REDSTONE_WALL_TORCH = ModelTemplateProvider.t1(
			block("template_redstone_torch_wall", TextureSlots.TORCH));
	
	public static final ModelTemplateProvider.T3 PISTON = ModelTemplateProvider.t3(
			block("template_piston", TextureSlots.PLATFORM, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 PISTON_HEAD = ModelTemplateProvider.t3(
			block("template_piston_head", TextureSlots.PLATFORM, TextureSlots.SIDE, TextureSlots.UNSTICKY));
	
	public static final ModelTemplateProvider.T3 PISTON_HEAD_SHORT = ModelTemplateProvider.t3(
			block("template_piston_head_short", TextureSlots.PLATFORM, TextureSlots.SIDE, TextureSlots.UNSTICKY));
	
	public static final ModelTemplateProvider.T1 SEAGRASS = ModelTemplateProvider.t1(
			block("template_seagrass", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T1 TURTLE_EGG = ModelTemplateProvider.t1(
			block("template_turtle_egg", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 TWO_TURTLE_EGGS = ModelTemplateProvider.t1(
			block("template_two_turtle_eggs", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 THREE_TURTLE_EGGS = ModelTemplateProvider.t1(
			block("template_three_turtle_eggs", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 FOUR_TURTLE_EGGS = ModelTemplateProvider.t1(
			block("template_four_turtle_eggs", TextureSlots.ALL));
	
	public static final ModelTemplateProvider.T1 SINGLE_FACE = ModelTemplateProvider.t1(
			block("template_single_face", TextureSlots.TEXTURE));
	
	public static final ModelTemplateProvider.T6 CAULDRON_LEVEL1 = ModelTemplateProvider.t6(
			block("template_cauldron_level1", TextureSlots.CONTENT, TextureSlots.INSIDE, TextureSlots.PARTICLE, TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T6 CAULDRON_LEVEL2 = ModelTemplateProvider.t6(
			block("template_cauldron_level2", TextureSlots.CONTENT, TextureSlots.INSIDE, TextureSlots.PARTICLE, TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T6 CAULDRON_FULL = ModelTemplateProvider.t6(
			block("template_cauldron_full", TextureSlots.CONTENT, TextureSlots.INSIDE, TextureSlots.PARTICLE, TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T2 AZALEA = ModelTemplateProvider.t2(
			block("template_azalea", TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 POTTED_AZALEA = ModelTemplateProvider.t3(
			block("template_potted_azalea_bush", TextureSlots.PLANT, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T3 POTTED_FLOWERING_AZALEA = ModelTemplateProvider.t3(
			block("template_potted_azalea_bush", TextureSlots.PLANT, TextureSlots.TOP, TextureSlots.SIDE));
	
	public static final ModelTemplateProvider.T6 SNIFFER_EGG = ModelTemplateProvider.t6(
			block("sniffer_egg", TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.NORTH, TextureSlots.SOUTH, TextureSlots.EAST, TextureSlots.WEST));
	
	public static final ModelTemplateProvider.T2 CANDLE = ModelTemplateProvider.t2(
			block("template_candle", TextureSlots.ALL, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 TWO_CANDLES = ModelTemplateProvider.t2(
			block("template_two_candles", TextureSlots.ALL, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 THREE_CANDLES = ModelTemplateProvider.t2(
			block("template_three_candles", TextureSlots.ALL, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T2 FOUR_CANDLES = ModelTemplateProvider.t2(
			block("template_four_candles", TextureSlots.ALL, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T5 CANDLE_CAKE = ModelTemplateProvider.t5(
			block("template_cake_with_candle", TextureSlots.CANDLE, TextureSlots.BOTTOM, TextureSlots.SIDE, TextureSlots.TOP, TextureSlots.PARTICLE));
	
	public static final ModelTemplateProvider.T5 SCULK_SHRIEKER = ModelTemplateProvider.t5(
			block("template_sculk_shrieker", TextureSlots.BOTTOM, TextureSlots.SIDE, TextureSlots.TOP, TextureSlots.PARTICLE, TextureSlots.INNER_TOP));
	
	public static final ModelTemplateProvider.T4 VAULT = ModelTemplateProvider.t4(
			block("template_vault", TextureSlots.TOP, TextureSlots.BOTTOM, TextureSlots.SIDE, TextureSlots.FRONT));

	public static final ModelTemplateProvider.T1[] STEMS = IntStream.range(0, 8)
			.mapToObj(i -> ModelTemplateProvider.t1(
					block("stem_growth" + i, "_stage" + i, TextureSlots.STEM)))
			.toArray(ModelTemplateProvider.T1[]::new);
	
	
	
	public static ModelTemplate of(
		final Optional<ResourceKey> key, final Optional<String> suffix, final List<TextureSlot> slots
	) {
		return new ModelTemplate(key, suffix, slots);
	}
	
	public static ModelTemplate of(
		final Optional<ResourceKey> key, final Optional<String> suffix, final TextureSlot... slots
	) {
		return ModelTemplates.of(key, suffix, List.of(slots));
	}
	
	public static ModelTemplate of(
		final ResourceKey key, final Optional<String> suffix, final TextureSlot... slots
	) {
		return ModelTemplates.of(Optional.of(key), suffix, slots);
	}
	
	protected static ModelTemplate of(final TextureSlot... slots) {
		return ModelTemplates.of(Optional.empty(), Optional.empty(), slots);
	}
	
	public static ModelTemplate block(final String name, final TextureSlot... slots) {
		return ModelTemplates.of(ModelUtil.withBlockPrefix(name), Optional.empty(), slots);
	}
	
	public static ModelTemplate block(final String name, final String suffix, final TextureSlot... slots) {
		return ModelTemplates.of(ModelUtil.withBlockPrefix(name), Optional.of(suffix), slots);
	}
	
	public static ModelTemplate item(final String name, final TextureSlot... slots) {
		return ModelTemplates.of(ModelUtil.withItemPrefix(name), Optional.empty(), slots);
	}
	
	public static ModelTemplate item(final String name, final String suffix, final TextureSlot... slots) {
		return ModelTemplates.of(ModelUtil.withItemPrefix(name), Optional.of(suffix), slots);
	}
	
	private ModelTemplates() {
	}
}
