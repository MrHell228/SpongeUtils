package net.hellheim.spongetools.resourcepack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.util.annotation.CatalogedBy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.hellheim.spongetools.codec.LateBoundIdMapper;

/**
 * TextureSlot will pick textures from parent slots if its own texture is absent.
 */
@CatalogedBy(TextureSlots.class)
public final class TextureSlot implements StringRepresentable {
	
	private static final Map<String, TextureSlot> VALUES = new HashMap<>();
	private static final LateBoundIdMapper<String, TextureSlot> ID_MAPPER = new LateBoundIdMapper<>();
	
	public static final Codec<TextureSlot> CODEC = TextureSlot.ID_MAPPER.codec(Codec.STRING);
	public static final Codec<TextureSlot> CODEC_HASHED = TextureSlot.ID_MAPPER.codec(Codec.STRING
			.flatComapMap(
					id -> "#" + id,
					id -> !id.isEmpty() && id.charAt(0) == '#'
							? DataResult.success(id.substring(1))
							: DataResult.error(() -> "id must have # in the begining")));
	
	private final String id;
	private final Optional<TextureSlot> parent;
	
	private TextureSlot(final String id, final Optional<TextureSlot> parent) {
		this.id = id;
		this.parent = parent;
		TextureSlot.ID_MAPPER.put(id, this);
	}
	
	public static TextureSlot of(final String id) {
		return TextureSlot.of(id, Optional.empty());
	}
	
	public static TextureSlot of(final String id, final TextureSlot parent) {
		return TextureSlot.of(id, Optional.of(parent));
	}
	
	public static TextureSlot of(final String id, final Optional<TextureSlot> parent) {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(parent, "parent");
		return TextureSlot.VALUES.computeIfAbsent(id, $ -> new TextureSlot(id, parent));
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
