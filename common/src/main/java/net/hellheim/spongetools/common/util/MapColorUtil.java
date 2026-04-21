package net.hellheim.spongetools.common.util;

import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.map.color.MapColorTypes;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.world.level.material.MapColor;

public final class MapColorUtil {
	
	private static final BiMap<MapColor, MapColorType> BIMAP = HashBiMap.create();
	
	private static BiMap<MapColor, MapColorType> map() {
		if (BIMAP.isEmpty()) {
			BIMAP.put(MapColor.NONE, MapColorTypes.NONE.get());
			BIMAP.put(MapColor.GRASS, MapColorTypes.GRASS.get());
			BIMAP.put(MapColor.SAND, MapColorTypes.SAND.get());
			BIMAP.put(MapColor.WOOL, MapColorTypes.WOOL.get());
			BIMAP.put(MapColor.FIRE, MapColorTypes.FIRE.get());
			BIMAP.put(MapColor.ICE, MapColorTypes.ICE.get());
			BIMAP.put(MapColor.METAL, MapColorTypes.METAL.get());
			BIMAP.put(MapColor.PLANT, MapColorTypes.PLANT.get());
			BIMAP.put(MapColor.SNOW, MapColorTypes.SNOW.get());
			BIMAP.put(MapColor.CLAY, MapColorTypes.CLAY.get());
			BIMAP.put(MapColor.DIRT, MapColorTypes.DIRT.get());
			BIMAP.put(MapColor.STONE, MapColorTypes.STONE.get());
			BIMAP.put(MapColor.WATER, MapColorTypes.WATER.get());
			BIMAP.put(MapColor.WOOD, MapColorTypes.WOOD.get());
			BIMAP.put(MapColor.QUARTZ, MapColorTypes.QUARTZ.get());
			BIMAP.put(MapColor.COLOR_ORANGE, MapColorTypes.COLOR_ORANGE.get());
			BIMAP.put(MapColor.COLOR_MAGENTA, MapColorTypes.COLOR_MAGENTA.get());
			BIMAP.put(MapColor.COLOR_LIGHT_BLUE, MapColorTypes.COLOR_LIGHT_BLUE.get());
			BIMAP.put(MapColor.COLOR_YELLOW, MapColorTypes.COLOR_YELLOW.get());
			BIMAP.put(MapColor.COLOR_LIGHT_GREEN, MapColorTypes.COLOR_LIGHT_GREEN.get());
			BIMAP.put(MapColor.COLOR_PINK, MapColorTypes.COLOR_PINK.get());
			BIMAP.put(MapColor.COLOR_GRAY, MapColorTypes.COLOR_GRAY.get());
			BIMAP.put(MapColor.COLOR_LIGHT_GRAY, MapColorTypes.COLOR_LIGHT_GRAY.get());
			BIMAP.put(MapColor.COLOR_CYAN, MapColorTypes.COLOR_CYAN.get());
			BIMAP.put(MapColor.COLOR_PURPLE, MapColorTypes.COLOR_PURPLE.get());
			BIMAP.put(MapColor.COLOR_BLUE, MapColorTypes.COLOR_BLUE.get());
			BIMAP.put(MapColor.COLOR_BROWN, MapColorTypes.COLOR_BROWN.get());
			BIMAP.put(MapColor.COLOR_GREEN, MapColorTypes.COLOR_GREEN.get());
			BIMAP.put(MapColor.COLOR_RED, MapColorTypes.COLOR_RED.get());
			BIMAP.put(MapColor.COLOR_BLACK, MapColorTypes.COLOR_BLACK.get());
			BIMAP.put(MapColor.GOLD, MapColorTypes.GOLD.get());
			BIMAP.put(MapColor.DIAMOND, MapColorTypes.DIAMOND.get());
			BIMAP.put(MapColor.LAPIS, MapColorTypes.LAPIS_LAZULI.get());
			BIMAP.put(MapColor.EMERALD, MapColorTypes.EMERALD.get());
			BIMAP.put(MapColor.PODZOL, MapColorTypes.PODZOL.get());
			BIMAP.put(MapColor.NETHER, MapColorTypes.NETHER.get());
			BIMAP.put(MapColor.TERRACOTTA_WHITE, MapColorTypes.TERRACOTTA_WHITE.get());
			BIMAP.put(MapColor.TERRACOTTA_ORANGE, MapColorTypes.TERRACOTTA_ORANGE.get());
			BIMAP.put(MapColor.TERRACOTTA_MAGENTA, MapColorTypes.TERRACOTTA_MAGENTA.get());
			BIMAP.put(MapColor.TERRACOTTA_LIGHT_BLUE, MapColorTypes.TERRACOTTA_LIGHT_BLUE.get());
			BIMAP.put(MapColor.TERRACOTTA_YELLOW, MapColorTypes.TERRACOTTA_YELLOW.get());
			BIMAP.put(MapColor.TERRACOTTA_LIGHT_GREEN, MapColorTypes.TERRACOTTA_LIGHT_GREEN.get());
			BIMAP.put(MapColor.TERRACOTTA_PINK, MapColorTypes.TERRACOTTA_PINK.get());
			BIMAP.put(MapColor.TERRACOTTA_GRAY, MapColorTypes.TERRACOTTA_GRAY.get());
			BIMAP.put(MapColor.TERRACOTTA_LIGHT_GRAY, MapColorTypes.TERRACOTTA_LIGHT_GRAY.get());
			BIMAP.put(MapColor.TERRACOTTA_CYAN, MapColorTypes.TERRACOTTA_CYAN.get());
			BIMAP.put(MapColor.TERRACOTTA_PURPLE, MapColorTypes.TERRACOTTA_PURPLE.get());
			BIMAP.put(MapColor.TERRACOTTA_BLUE, MapColorTypes.TERRACOTTA_BLUE.get());
			BIMAP.put(MapColor.TERRACOTTA_BROWN, MapColorTypes.TERRACOTTA_BROWN.get());
			BIMAP.put(MapColor.TERRACOTTA_GREEN, MapColorTypes.TERRACOTTA_GREEN.get());
			BIMAP.put(MapColor.TERRACOTTA_RED, MapColorTypes.TERRACOTTA_RED.get());
			BIMAP.put(MapColor.TERRACOTTA_BLACK, MapColorTypes.TERRACOTTA_BLACK.get());
			BIMAP.put(MapColor.CRIMSON_NYLIUM, MapColorTypes.CRIMSON_NYLIUM.get());
			BIMAP.put(MapColor.CRIMSON_STEM, MapColorTypes.CRIMSON_STEM.get());
			BIMAP.put(MapColor.CRIMSON_HYPHAE, MapColorTypes.CRIMSON_HYPHAE.get());
			BIMAP.put(MapColor.WARPED_NYLIUM, MapColorTypes.WARPED_NYLIUM.get());
			BIMAP.put(MapColor.WARPED_STEM, MapColorTypes.WARPED_STEM.get());
			BIMAP.put(MapColor.WARPED_HYPHAE, MapColorTypes.WARPED_HYPHAE.get());
			BIMAP.put(MapColor.WARPED_WART_BLOCK, MapColorTypes.WARPED_WART_BLOCK.get());
		}
		
		return BIMAP;
	}
	
	public static MapColor get(final MapColorType color) {
		return MapColorUtil.map().inverse().get(color);
	}
	
	public static MapColorType get(final MapColor color) {
		return MapColorUtil.map().get(color);
	}
	
	private MapColorUtil() {
	}
}
