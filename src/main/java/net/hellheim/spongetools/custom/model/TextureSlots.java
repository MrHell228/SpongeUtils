package net.hellheim.spongetools.custom.model;

import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

public final class TextureSlots {
	
	public static final TextureSlot
	
	ALL = TextureSlots.of("all"),
	TEXTURE = TextureSlots.of("texture", ALL),
	PARTICLE = TextureSlots.of("particle", TEXTURE),
	END = TextureSlots.of("end", ALL),
	BOTTOM = TextureSlots.of("bottom", END),
	TOP = TextureSlots.of("top", END),
	FRONT = TextureSlots.of("front", ALL),
	BACK = TextureSlots.of("back", ALL),
	SIDE = TextureSlots.of("side", ALL),
	NORTH = TextureSlots.of("north", SIDE),
	SOUTH = TextureSlots.of("south", SIDE),
	EAST = TextureSlots.of("east", SIDE),
	WEST = TextureSlots.of("west", SIDE),
	UP = TextureSlots.of("up"),
	DOWN = TextureSlots.of("down"),
	CROSS = TextureSlots.of("cross"),
	CROSS_EMISSIVE = TextureSlots.of("cross_emissive"),
	PLANT = TextureSlots.of("plant"),
	WALL = TextureSlots.of("wall", ALL),
	RAIL = TextureSlots.of("rail"),
	WOOL = TextureSlots.of("wool"),
	PATTERN = TextureSlots.of("pattern"),
	PANE = TextureSlots.of("pane"),
	EDGE = TextureSlots.of("edge"),
	FAN = TextureSlots.of("fan"),
	STEM = TextureSlots.of("stem"),
	UPPER_STEM = TextureSlots.of("upperstem"),
	CROP = TextureSlots.of("crop"),
	DIRT = TextureSlots.of("dirt"),
	FIRE = TextureSlots.of("fire"),
	LANTERN = TextureSlots.of("lantern"),
	PLATFORM = TextureSlots.of("platform"),
	UNSTICKY = TextureSlots.of("unsticky"),
	TORCH = TextureSlots.of("torch"),
	LAYER0 = TextureSlots.of("layer0"),
	LAYER1 = TextureSlots.of("layer1"),
	LAYER2 = TextureSlots.of("layer2"),
	LIT_LOG = TextureSlots.of("lit_log"),
	CANDLE = TextureSlots.of("candle"),
	INSIDE = TextureSlots.of("inside"),
	CONTENT = TextureSlots.of("content"),
	INNER_TOP = TextureSlots.of("inner_top"),
	FLOWERBED = TextureSlots.of("flowerbed")
	;
	
	public static TextureSlot of(final String id, final Optional<TextureSlot> parent) {
		return new TextureSlot(id, parent);
	}
	
	public static TextureSlot of(final String id, final @Nullable TextureSlot parent) {
		return TextureSlots.of(id, Optional.ofNullable(parent));
	}
	
	public static TextureSlot of(final String id) {
		return TextureSlots.of(id, Optional.empty());
	}
	
	private TextureSlots() {
	}
}
