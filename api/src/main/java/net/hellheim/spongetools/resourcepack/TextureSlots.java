package net.hellheim.spongetools.resourcepack;

public final class TextureSlots {
	
	public static final TextureSlot
	
	ALL = TextureSlot.of("all"),
	TEXTURE = TextureSlot.of("texture", ALL),
	PARTICLE = TextureSlot.of("particle", TEXTURE),
	END = TextureSlot.of("end", ALL),
	BOTTOM = TextureSlot.of("bottom", END),
	TOP = TextureSlot.of("top", END),
	FRONT = TextureSlot.of("front", ALL),
	BACK = TextureSlot.of("back", ALL),
	SIDE = TextureSlot.of("side", ALL),
	NORTH = TextureSlot.of("north", SIDE),
	SOUTH = TextureSlot.of("south", SIDE),
	EAST = TextureSlot.of("east", SIDE),
	WEST = TextureSlot.of("west", SIDE),
	UP = TextureSlot.of("up"),
	DOWN = TextureSlot.of("down"),
	CROSS = TextureSlot.of("cross"),
	CROSS_EMISSIVE = TextureSlot.of("cross_emissive"),
	PLANT = TextureSlot.of("plant"),
	WALL = TextureSlot.of("wall", ALL),
	RAIL = TextureSlot.of("rail"),
	WOOL = TextureSlot.of("wool"),
	PATTERN = TextureSlot.of("pattern"),
	PANE = TextureSlot.of("pane"),
	EDGE = TextureSlot.of("edge"),
	FAN = TextureSlot.of("fan"),
	STEM = TextureSlot.of("stem"),
	UPPER_STEM = TextureSlot.of("upperstem"),
	CROP = TextureSlot.of("crop"),
	DIRT = TextureSlot.of("dirt"),
	FIRE = TextureSlot.of("fire"),
	LANTERN = TextureSlot.of("lantern"),
	PLATFORM = TextureSlot.of("platform"),
	UNSTICKY = TextureSlot.of("unsticky"),
	TORCH = TextureSlot.of("torch"),
	LAYER0 = TextureSlot.of("layer0"),
	LAYER1 = TextureSlot.of("layer1"),
	LAYER2 = TextureSlot.of("layer2"),
	LIT_LOG = TextureSlot.of("lit_log"),
	CANDLE = TextureSlot.of("candle"),
	INSIDE = TextureSlot.of("inside"),
	CONTENT = TextureSlot.of("content"),
	INNER_TOP = TextureSlot.of("inner_top"),
	FLOWERBED = TextureSlot.of("flowerbed")
	;
	
	private TextureSlots() {
	}
}
