package net.hellheim.spongetools.custom.item.model;

import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

/**
 * TextureSlot will pick textures from parent slots if its own texture is absent.
 */
public enum TextureSlot implements StringRepresentable {
	ALL("all"),
	TEXTURE("texture", ALL),
	PARTICLE("particle", TEXTURE),
	END("end", ALL),
	BOTTOM("bottom", END),
	TOP("top", END),
	FRONT("front", ALL),
	BACK("back", ALL),
	SIDE("side", ALL),
	NORTH("north", SIDE),
	SOUTH("south", SIDE),
	EAST("east", SIDE),
	WEST("west", SIDE),
	UP("up"),
	DOWN("down"),
	CROSS("cross"),
	CROSS_EMISSIVE("cross_emissive"),
	PLANT("plant"),
	WALL("wall", ALL),
	RAIL("rail"),
	WOOL("wool"),
	PATTERN("pattern"),
	PANE("pane"),
	EDGE("edge"),
	FAN("fan"),
	STEM("stem"),
	UPPER_STEM("upperstem"),
	CROP("crop"),
	DIRT("dirt"),
	FIRE("fire"),
	LANTERN("lantern"),
	PLATFORM("platform"),
	UNSTICKY("unsticky"),
	TORCH("torch"),
	LAYER0("layer0"),
	LAYER1("layer1"),
	LAYER2("layer2"),
	LIT_LOG("lit_log"),
	CANDLE("candle"),
	INSIDE("inside"),
	CONTENT("content"),
	INNER_TOP("inner_top"),
	FLOWERBED("flowerbed"),
	;
	
	public static final Codec<TextureSlot> CODEC = StringRepresentableCodec.fromValues(TextureSlot.values());
	
	private final String id;
	private final Optional<TextureSlot> parent;
	
	private TextureSlot(final String id) {
		this(id, null);
	}
	
	private TextureSlot(final String id, final @Nullable TextureSlot parent) {
		this.id = id;
		this.parent = Optional.ofNullable(parent);
	}
	
	public String id() {
		return this.id;
	}
	
	public Optional<TextureSlot> parent() {
		return this.parent;
	}
	
	@Override
	public String serializationString() {
		return this.id;
	}
}
