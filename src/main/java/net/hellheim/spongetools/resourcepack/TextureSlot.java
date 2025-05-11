package net.hellheim.spongetools.resourcepack;

import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.util.annotation.CatalogedBy;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.LateBoundIdMapper;

/**
 * TextureSlot will pick textures from parent slots if its own texture is absent.
 */
@CatalogedBy(TextureSlots.class)
public record TextureSlot(String id, Optional<TextureSlot> parent) implements StringRepresentable {
	
	private static final LateBoundIdMapper<String, TextureSlot> ID_MAPPER = new LateBoundIdMapper<>();
	
	public static final Codec<TextureSlot> CODEC = ID_MAPPER.codec(Codec.STRING);
	
	public TextureSlot(final String id, final Optional<TextureSlot> parent) {
		this.id = Objects.requireNonNull(id, "id");
		this.parent = Objects.requireNonNull(parent, "parent");
		TextureSlot.ID_MAPPER.put(id, this);
	}
	
	@Override
	public String serializationString() {
		return this.id;
	}
}
