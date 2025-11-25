package net.hellheim.spongetools.common.util;

import java.util.Objects;
import java.util.function.Function;

import org.spongepowered.api.data.type.ItemActionType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.common.item.util.ItemStackUtil;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.custom.type.item.CustomItemAction;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public record CustomConsumeEffect(Config config) implements ConsumeEffect, CustomItemAction {
	
	@SuppressWarnings("unchecked")
	private static final MapCodec<CustomConsumeEffect> MAP_CODEC =
			Codec.lazyInitialized(() -> ((Registry<MapCodec<? extends Config>>) CustomItemAction.registry().get()).byNameCodec())
					.dispatchMap("config", Config::mapCodec, Function.identity())
					.xmap(CustomConsumeEffect::new, CustomConsumeEffect::config);
	// This StreamCodec should never be used
	private static final StreamCodec<RegistryFriendlyByteBuf, CustomConsumeEffect> STREAM_CODEC =
			ByteBufCodecs.fromCodecWithRegistries(CustomConsumeEffect.MAP_CODEC.codec());
	public static final Type<? extends ConsumeEffect> TYPE = new ConsumeEffect.Type<>(
			CustomConsumeEffect.MAP_CODEC, CustomConsumeEffect.STREAM_CODEC);
	
	public CustomConsumeEffect(final Config config) {
		this.config = Objects.requireNonNull(config, "config");
	}
	
	@Override
	public Type<? extends ConsumeEffect> getType() {
		return CustomConsumeEffect.TYPE;
	}
	
	@Override
	public boolean apply(final Level level, ItemStack stack, LivingEntity entity) {
		return this.apply((Living) entity, ItemStackUtil.fromNative(stack));
	}

	@Override
	public ItemActionType type() {
		return (ItemActionType) (Object) CustomConsumeEffect.TYPE;
	}

	@Override
	public boolean apply(final Living entity, final ItemStackLike stack) {
		return this.config.apply(entity, stack);
	}
	
	public static final class FactoryImpl implements CustomItemAction.Factory {
		
		@Override
		public CustomItemAction of(final Config config) {
			return new CustomConsumeEffect(config);
		}
	}
}
