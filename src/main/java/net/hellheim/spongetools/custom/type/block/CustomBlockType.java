package net.hellheim.spongetools.custom.type.block;

import java.util.Optional;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.custom.type.CustomType;

public interface CustomBlockType extends
		CustomType,
		CustomBlockTypeLike,
		BlockStateHolder {
	
	static DefaultedRegistryType<CustomBlockType> registry() {
		return SpongeTools.Registries.CUSTOM_BLOCK_TYPE;
	}
	
	static Codec<CustomBlockType> registryCodec() {
		return RegistryCodecs.CUSTOM_BLOCK_TYPE;
	}
	
	static Optional<CustomBlockType> resolve(final ResourceKey key) {
		return CustomBlockType.registry()
				.get()
				.findValue(key);
	}
	
	static Optional<CustomBlockType> get(final BlockState state) {
		return BlockStateDispatcher.get().get(state)
				.map(holder -> holder instanceof final CustomBlockType block ? block : null);
	}
}
