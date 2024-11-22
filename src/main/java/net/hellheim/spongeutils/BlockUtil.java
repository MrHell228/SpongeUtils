package net.hellheim.spongeutils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.api.world.server.ServerWorld;

import com.google.common.collect.ImmutableBiMap;

import net.hellheim.spongeutils.source.solid.block.BlockTypeSource;

/**
 * Block related utilities
 */
public final class BlockUtil {
	
	private static final Logger LOG = LogManager.getLogger("BlockUtil");
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final BlockTypeSource b2) {
		return is(b1.getAsBlockType(), b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final Location<?, ?> b2) {
		return is(b1.getAsBlockType(), b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final BlockSnapshot b2) {
		return is(b1.getAsBlockType(), b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final BlockState b2) {
		return is(b1.getAsBlockType(), b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final Supplier<BlockType> b2) {
		return is(b1.getAsBlockType(), b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockTypeSource}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockTypeSource b1, final BlockType b2) {
		return is(b1.getAsBlockType(), b2);
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final BlockTypeSource b2) {
		return is(b1.blockType(), b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final Location<?, ?> b2) {
		return is(b1.blockType(), b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final BlockSnapshot b2) {
		return is(b1.blockType(), b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final BlockState b2) {
		return is(b1.blockType(), b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final Supplier<BlockType> b2) {
		return is(b1.blockType(), b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Location}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Location<?, ?> b1, final BlockType b2) {
		return is(b1.blockType(), b2);
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final BlockTypeSource b2) {
		return is(b1.state().type(), b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final Location<?, ?> b2) {
		return is(b1.state().type(), b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final BlockSnapshot b2) {
		return is(b1.state().type(), b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final BlockState b2) {
		return is(b1.state().type(), b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final Supplier<BlockType> b2) {
		return is(b1.state().type(), b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockSnapshot}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockSnapshot b1, final BlockType b2) {
		return is(b1.state().type(), b2);
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final BlockTypeSource b2) {
		return is(b1.type(), b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final Location<?, ?> b2) {
		return is(b1.type(), b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final BlockSnapshot b2) {
		return is(b1.type(), b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final BlockState b2) {
		return is(b1.type(), b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final Supplier<BlockType> b2) {
		return is(b1.type(), b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockState}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockState b1, final BlockType b2) {
		return is(b1.type(), b2);
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final BlockTypeSource b2) {
		return is(b1.get(), b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final Location<?, ?> b2) {
		return is(b1.get(), b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final BlockSnapshot b2) {
		return is(b1.get(), b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final BlockState b2) {
		return is(b1.get(), b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final Supplier<BlockType> b2) {
		return is(b1.get(), b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link Supplier BlockType Supplier}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<BlockType> b1, final BlockType b2) {
		return is(b1.get(), b2);
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link BlockTypeSource}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final BlockTypeSource b2) {
		return is(b1, b2.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link Location}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final Location<?, ?> b2) {
		return is(b1, b2.blockType());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link BlockSnapshot}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final BlockSnapshot b2) {
		return is(b1, b2.state().type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link BlockState}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final BlockState b2) {
		return is(b1, b2.type());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final Supplier<BlockType> b2) {
		return is(b1, b2.get());
	}
	
	/**
	 * Compares {@link BlockType}s of two arguments.
	 * 
	 * @param b1 The {@link BlockType}
	 * @param b2 The {@link BlockType}
	 * @return True if {@link BlockType}s of the arguments are equal
	 */
	public static boolean is(final BlockType b1, final BlockType b2) {
		return b1 == b2;
	}
	
	
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link BlockTypeSource}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	
	public static boolean isAir(final BlockTypeSource b) {
		return isAir(b.getAsBlockType());
	}
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link Location}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	public static boolean isAir(final Location<?, ?> b) {
		return isAir(b.blockType());
	}
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link BlockSnapshot}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	public static boolean isAir(final BlockSnapshot b) {
		return isAir(b.state().type());
	}
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link BlockState}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	public static boolean isAir(final BlockState b) {
		return isAir(b.type());
	}
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link Supplier BlockType Supplier}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	public static boolean isAir(final Supplier<BlockType> b) {
		return isAir(b.get());
	}
	
	/**
	 * Compares {@link BlockType} of the argument with air {@link BlockType}s.
	 * 
	 * @param b The {@link BlockType}
	 * @return True if {@link BlockType} of the argument is one of the air {@link BlockType}s
	 */
	public static boolean isAir(final BlockType b) {
		return is(b, BlockTypes.AIR) || is(b, BlockTypes.CAVE_AIR) || is(b, BlockTypes.VOID_AIR);
	}
	
	
	
	public static BlockState stateOfAir() {
		return stateOf(BlockTypes.AIR);
	}
	public static BlockState stateOf(final Supplier<BlockType> b) {
		return stateOf(b.get());
	}
	public static BlockState stateOf(final BlockType b) {
		return b.defaultState();
	}
	
	
	
	public static final ImmutableBiMap<BlockType, String> LEGACY_TYPE_ID = new Builder()
			.put("0", BlockTypes.AIR)
			.put("1", BlockTypes.STONE)
			.put("1:1", BlockTypes.GRANITE)
			.put("1:2", BlockTypes.POLISHED_GRANITE)
			.put("1:3", BlockTypes.DIORITE)
			.put("1:4", BlockTypes.POLISHED_DIORITE)
			.put("1:5", BlockTypes.ANDESITE)
			.put("1:6", BlockTypes.POLISHED_ANDESITE)
			.put("2", BlockTypes.GRASS_BLOCK)
			.put("3", BlockTypes.DIRT)
			.put("3:1", BlockTypes.COARSE_DIRT)
			.put("3:2", BlockTypes.PODZOL)
			.put("4", BlockTypes.COBBLESTONE)
			.put("5", BlockTypes.OAK_PLANKS)
			.put("5:1", BlockTypes.SPRUCE_PLANKS)
			.put("5:2", BlockTypes.BIRCH_PLANKS)
			.put("5:3", BlockTypes.JUNGLE_PLANKS)
			.put("5:4", BlockTypes.ACACIA_PLANKS)
			.put("5:5", BlockTypes.DARK_OAK_PLANKS)
			.put("6", BlockTypes.OAK_SAPLING)
			.put("6:1", BlockTypes.SPRUCE_SAPLING)
			.put("6:2", BlockTypes.BIRCH_SAPLING)
			.put("6:3", BlockTypes.JUNGLE_SAPLING)
			.put("6:4", BlockTypes.ACACIA_SAPLING)
			.put("6:5", BlockTypes.DARK_OAK_SAPLING)
			.put("7", BlockTypes.BEDROCK)
			.put("12", BlockTypes.SAND)
			.put("12:1", BlockTypes.RED_SAND)
			.put("13", BlockTypes.GRAVEL)
			.put("14", BlockTypes.GOLD_ORE)
			.put("15", BlockTypes.IRON_ORE)
			.put("16", BlockTypes.COAL_ORE)
			.put("17", BlockTypes.OAK_LOG)
			.put("17:1", BlockTypes.SPRUCE_LOG)
			.put("17:2", BlockTypes.BIRCH_LOG)
			.put("17:3", BlockTypes.JUNGLE_LOG)
			.put("162", BlockTypes.ACACIA_LOG)
			.put("162:1", BlockTypes.DARK_OAK_LOG)
			.put("18", BlockTypes.OAK_LEAVES)
			.put("18:1", BlockTypes.SPRUCE_LEAVES)
			.put("18:2", BlockTypes.BIRCH_LEAVES)
			.put("18:3", BlockTypes.JUNGLE_LEAVES)
			.put("161", BlockTypes.ACACIA_LEAVES)
			.put("161:1", BlockTypes.DARK_OAK_LEAVES)
			.put("19", BlockTypes.SPONGE)
			.put("19:1", BlockTypes.WET_SPONGE)
			.put("20", BlockTypes.GLASS)
			.put("21", BlockTypes.LAPIS_ORE)
			.put("22", BlockTypes.LAPIS_BLOCK)
			.put("23", BlockTypes.DISPENSER)
			.put("24", BlockTypes.SANDSTONE)
			.put("24:1", BlockTypes.CHISELED_SANDSTONE)
			.put("24:2", BlockTypes.CUT_SANDSTONE)
			.put("25", BlockTypes.NOTE_BLOCK)
			.put("27", BlockTypes.POWERED_RAIL)
			.put("28", BlockTypes.DETECTOR_RAIL)
			.put("29", BlockTypes.STICKY_PISTON)
			.put("30", BlockTypes.COBWEB)
			.put("31:2", BlockTypes.FERN)
			.put("32", BlockTypes.DEAD_BUSH)
			.put("33", BlockTypes.PISTON)
			.put("35", BlockTypes.WHITE_WOOL)
			.put("35:1", BlockTypes.ORANGE_WOOL)
			.put("35:2", BlockTypes.MAGENTA_WOOL)
			.put("35:3", BlockTypes.LIGHT_BLUE_WOOL)
			.put("35:4", BlockTypes.YELLOW_WOOL)
			.put("35:5", BlockTypes.LIME_WOOL)
			.put("35:6", BlockTypes.PINK_WOOL)
			.put("35:7", BlockTypes.GRAY_WOOL)
			.put("35:8", BlockTypes.LIGHT_GRAY_WOOL)
			.put("35:9", BlockTypes.CYAN_WOOL)
			.put("35:10", BlockTypes.PURPLE_WOOL)
			.put("35:11", BlockTypes.BLUE_WOOL)
			.put("35:12", BlockTypes.BROWN_WOOL)
			.put("35:13", BlockTypes.GREEN_WOOL)
			.put("35:14", BlockTypes.RED_WOOL)
			.put("35:15", BlockTypes.BLACK_WOOL)
			.put("37", BlockTypes.DANDELION)
			.put("38", BlockTypes.POPPY)
			.put("38:1", BlockTypes.BLUE_ORCHID)
			.put("38:2", BlockTypes.ALLIUM)
			.put("38:3", BlockTypes.AZURE_BLUET)
			.put("38:4", BlockTypes.RED_TULIP)
			.put("38:5", BlockTypes.ORANGE_TULIP)
			.put("38:6", BlockTypes.WHITE_TULIP)
			.put("38:7", BlockTypes.PINK_TULIP)
			.put("38:8", BlockTypes.OXEYE_DAISY)
			.put("39", BlockTypes.BROWN_MUSHROOM)
			.put("40", BlockTypes.RED_MUSHROOM)
			.put("41", BlockTypes.GOLD_BLOCK)
			.put("42", BlockTypes.IRON_BLOCK)
			.put("126", BlockTypes.OAK_SLAB)
			.put("126:1", BlockTypes.SPRUCE_SLAB)
			.put("126:2", BlockTypes.BIRCH_SLAB)
			.put("126:3", BlockTypes.JUNGLE_SLAB)
			.put("126:4", BlockTypes.ACACIA_SLAB)
			.put("126:5", BlockTypes.DARK_OAK_SLAB)
			.put("44:2", BlockTypes.SMOOTH_STONE_SLAB)
			.put("44:1", BlockTypes.SANDSTONE_SLAB)
			.put("44:3", BlockTypes.COBBLESTONE_SLAB)
			.put("44:4", BlockTypes.BRICK_SLAB)
			.put("44:5", BlockTypes.STONE_BRICK_SLAB)
			.put("44:6", BlockTypes.NETHER_BRICK_SLAB)
			.put("44:7", BlockTypes.QUARTZ_SLAB)
			.put("182", BlockTypes.RED_SANDSTONE_SLAB)
			.put("205", BlockTypes.PURPUR_SLAB)
			.put("43:7", BlockTypes.SMOOTH_QUARTZ)
			.put("43:8", BlockTypes.SMOOTH_STONE)
			.put("45", BlockTypes.BRICKS)
			.put("46", BlockTypes.TNT)
			.put("47", BlockTypes.BOOKSHELF)
			.put("48", BlockTypes.MOSSY_COBBLESTONE)
			.put("49", BlockTypes.OBSIDIAN)
			.put("50", BlockTypes.TORCH)
			.put("198", BlockTypes.END_ROD)
			.put("199", BlockTypes.CHORUS_PLANT)
			.put("200", BlockTypes.CHORUS_FLOWER)
			.put("201", BlockTypes.PURPUR_BLOCK)
			.put("202", BlockTypes.PURPUR_PILLAR)
			.put("203", BlockTypes.PURPUR_STAIRS)
			.put("52", BlockTypes.SPAWNER)
			.put("53", BlockTypes.OAK_STAIRS)
			.put("54", BlockTypes.CHEST)
			//TODO
			.build();
	
	private static final class Builder {
		
		private final ImmutableBiMap.Builder<BlockType, String> builder = ImmutableBiMap.builder();
		
		private Builder put(final String id, final Supplier<BlockType> type) {
			this.builder.put(type.get(), id);
			return this;
		}
		
		private ImmutableBiMap<BlockType, String> build() {
			return this.builder.build();
		}
	}
	
	public static void forEachState(final Consumer<BlockState> action) {
		BlockTypes.registry().stream().forEach(type -> forEachState(type, action));
	}
	public static void forEachState(final BlockType type, final Consumer<BlockState> action) {
		type.validStates().forEach(action);
	}
	
	
	
	private static final Map<String, BlockState> STRING_TO_STATE = new HashMap<>();
	public static void registerStateRepresentation(final String str, final BlockState state) {
		final BlockState oldState = STRING_TO_STATE.put(str, state);
		if (oldState != null) {
			LOG.warn(String.format("Overridden BlockState representation for key \"%s\": \"%s\" -> \"%s\"",
					str, oldState.asString(), state.asString()));
		}
	}
	public static void clearStateRepresentations() {
		STRING_TO_STATE.clear();
	}
	
	private static final Map<BlockType, Function<BlockState, String>> STATE_TO_STRING = new HashMap<>();
	public static void registerStateSerializer(final BlockType type, final Function<BlockState, String> serializer) {
		if (STATE_TO_STRING.put(type, serializer) != null) {
			LOG.warn(String.format("Overridden BlockState serializer for type \"%s\"", type.toString()));
		}
	}
	public static void clearStateSerializers() {
		STATE_TO_STRING.clear();
	}
	
	public static void registerUniqueState(final String str, final BlockTypeSource type) {
		registerUniqueState(str, type.getAsBlockType());
	}
	public static void registerUniqueState(final String str, final Supplier<BlockType> type) {
		registerUniqueState(str, type.get());
	}
	public static void registerUniqueState(final String str, final BlockType type) {
		registerStateRepresentation(str, stateOf(type));
		registerStateSerializer(type, $ -> str);
	}
	
	public static void registerAllStates() {
		final MutableInt idCounter = new MutableInt();
		BlockTypes.registry().stream().forEach(type -> {
			final int id = idCounter.getAndIncrement();
			final Map<String, String> stateToId = new HashMap<>();
			final MutableInt subIdCounter = new MutableInt();
			
			BlockUtil.forEachState(type, state -> {
				final int subId = subIdCounter.getAndIncrement();
				String stringId = subId == 0 ? Integer.toString(id) : (id + ":" + subId);
				stateToId.put(state.asString(), stringId);
				BlockUtil.registerStateRepresentation(stringId, state);
			});
			
			BlockUtil.registerStateSerializer(type, state -> {
				String stringId = stateToId.get(state.asString());
				if (stringId != null) {
					return stringId;
				} else {
					return BlockUtil.stateToString(state);
				}
			});
		});
	}
	
	public static void clearStates() {
		clearStateRepresentations();
		clearStateSerializers();
	}
	
	public static BlockState stateOfCustomString(final String str, final String... prefixes) {
		BlockState state = STRING_TO_STATE.get(str);
		if (state != null) {
			return state;
		}
		
		state = stateOfString(str, prefixes);
		if (state != null) {
			registerStateRepresentation(str, state);
		}
		
		return state;
	}
	
	public static BlockState stateOfString(final String str, final String... prefixes) {
		if (str.indexOf(':') != -1) {
			return BlockState.fromString(str);
		}
		
		if (prefixes.length == 0) {
			try {
				return BlockState.fromString("minecraft:" + str);
			} catch (Exception e) {
				LOG.error("Invalid state string: " + str);
				return null;
			}
		}
		
		for (String prefix : prefixes) {
			String full = prefix + ":" + str;
			try {
				return BlockState.fromString(full);
			} catch (Exception e) {
				continue;
			}
		}
		
		LOG.error("Invalid state string: " + str);
		return null;
	}
	
	public static String stateToCustomString(final BlockState state, final String... prefixesToCut) {
		final Function<BlockState, String> serializer = STATE_TO_STRING.get(state.type());
		if (serializer != null) {
			return serializer.apply(state);
		} else {
			return stateToString(state, prefixesToCut);
		}
	}
	public static String stateToString(final BlockState state, final String... prefixesToCut) {
		String str = state.asString();
		int colon = str.indexOf(':');
		String prefix = str.substring(0, colon);
		
		for (String prefixToCut : prefixesToCut) {
			if (prefix.equals(prefixToCut)) {
				return formatStateString(str.substring(colon+1, str.length()));
			}
		}
		
		return formatStateString(str);
	}
	private static String formatStateString(final String str) {
		return str.replace(" ", "");
	}
	
	
	
	public static void forEachNonAirInSphere(final ServerLocation center, final int radius, final Consumer<ServerLocation> action) {
		BlockUtil.forEachInSphere(center, radius, loc -> {
			if (!BlockUtil.isAir(loc.blockType())) {
				action.accept(loc);
			}
		});
	}
	
	public static void forEachInSphere(final ServerLocation center, final int radius, final Consumer<ServerLocation> action) {
		final ServerWorld world = center.world();
		GeomUtil.forEachInSphere(center.blockPosition(), radius, v -> action.accept(world.location(v)));
	}
	
	private BlockUtil() {
	}
}
