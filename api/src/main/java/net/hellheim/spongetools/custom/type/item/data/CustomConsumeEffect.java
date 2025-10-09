package net.hellheim.spongetools.custom.type.item.data;

import java.util.function.Function;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.object.CodecDataSerializable;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;

public interface CustomConsumeEffect extends MapCodecProxy<CustomConsumeEffect>, CodecDataSerializable<CustomConsumeEffect> {
	
	Codec<CustomConsumeEffect> CODEC = RegistryCodecs.CUSTOM_CONSUME_EFFECT_TYPE
			.dispatch(CustomConsumeEffect::mapCodec, Function.identity());
	
	static DefaultedRegistryType<MapCodec<? extends CustomConsumeEffect>> registry() {
		return SpongeTools.Registries.CONSUME_EFFECT_TYPE;
	}
	
	static Codec<MapCodec<? extends CustomConsumeEffect>> registryCodec() {
		return RegistryCodecs.CUSTOM_CONSUME_EFFECT_TYPE;
	}
	
	static Key<ListValue<CustomConsumeEffect>> dataKey() {
		return SpongeTools.Keys.CONSUME_EFFECTS;
	}
	
	static DataBuilder<CustomConsumeEffect> dataBuilder() {
		return DataCodecs.dataBuilder(CODEC);
	}
	
	@Override
	default Codec<CustomConsumeEffect> codec() {
		return CODEC;
	}
	
	void apply(Living entity, ItemStackSnapshot stack);
	
	interface PlayerOnly extends CustomConsumeEffect {
		
		@Override
		default void apply(final Living entity, final ItemStackSnapshot stack) {
			if (entity instanceof final ServerPlayer player) {
				this.apply(player, stack);
			}
		}
		
		void apply(ServerPlayer player, ItemStackSnapshot stack);
	}
}
