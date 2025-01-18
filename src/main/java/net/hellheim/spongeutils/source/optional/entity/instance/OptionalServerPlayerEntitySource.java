package net.hellheim.spongeutils.source.optional.entity.instance;

import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Ticks;

import net.hellheim.spongeutils.EntityUtil;
import net.hellheim.spongeutils.source.solid.item.IItemSource;
import net.kyori.adventure.text.Component;

public interface OptionalServerPlayerEntitySource extends OptionalPlayerEntitySource<ServerPlayer> {
	
	default void setCooldown(final IItemSource type, final Ticks cooldown) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setCooldown(entity, type, cooldown));
	}
	
	default void setCooldown(final Supplier<ItemType> type, final Ticks cooldown) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setCooldown(entity, type, cooldown));
	}
	
	default void setCooldown(final ItemType type, final Ticks cooldown) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setCooldown(entity, type, cooldown));
	}
	
	default void setCooldown(final ItemStack stack, final Ticks cooldown) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setCooldown(entity, stack, cooldown));
	}
	
	default void setCooldown(final ResourceKey key, final Ticks cooldown) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.setCooldown(entity, key, cooldown));
	}
	
	default void resetCooldown(final IItemSource type) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.resetCooldown(entity, type));
	}
	
	default void resetCooldown(final Supplier<ItemType> type) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.resetCooldown(entity, type));
	}
	
	default void resetCooldown(final ItemType type) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.resetCooldown(entity, type));
	}
	
	default void resetCooldown(final ItemStack stack) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.resetCooldown(entity, stack));
	}
	
	default void resetCooldown(final ResourceKey key) {
		this.getAsEntity().ifPresent(entity -> EntityUtil.resetCooldown(entity, key));
	}
	
	default boolean hasCooldown(final IItemSource type) {
		return this.getAsEntity().map(entity -> EntityUtil.hasCooldown(entity, type)).orElse(false);
	}
	
	default boolean hasCooldown(final Supplier<ItemType> type) {
		return this.getAsEntity().map(entity -> EntityUtil.hasCooldown(entity, type)).orElse(false);
	}
	
	default boolean hasCooldown(final ItemType type) {
		return this.getAsEntity().map(entity -> EntityUtil.hasCooldown(entity, type)).orElse(false);
	}
	
	default boolean hasCooldown(final ItemStack stack) {
		return this.getAsEntity().map(entity -> EntityUtil.hasCooldown(entity, stack)).orElse(false);
	}
	
	default boolean hasCooldown(final ResourceKey key) {
		return this.getAsEntity().map(entity -> EntityUtil.hasCooldown(entity, key)).orElse(false);
	}
	
	default Optional<Ticks> cooldown(final IItemSource type) {
		return this.getAsEntity().map(entity -> EntityUtil.cooldown(entity, type)).orElse(Optional.empty());
	}
	
	default Optional<Ticks> cooldown(final Supplier<ItemType> type) {
		return this.getAsEntity().map(entity -> EntityUtil.cooldown(entity, type)).orElse(Optional.empty());
	}
	
	default Optional<Ticks> cooldown(final ItemType type) {
		return this.getAsEntity().map(entity -> EntityUtil.cooldown(entity, type)).orElse(Optional.empty());
	}
	
	default Optional<Ticks> cooldown(final ItemStack stack) {
		return this.getAsEntity().map(entity -> EntityUtil.cooldown(entity, stack)).orElse(Optional.empty());
	}
	
	default Optional<Ticks> cooldown(final ResourceKey key) {
		return this.getAsEntity().map(entity -> EntityUtil.cooldown(entity, key)).orElse(Optional.empty());
	}
	
	default void kick() {
		this.getAsEntity().ifPresent(entity -> entity.kick());
	}
	
	default void kick(final Component reason) {
		this.getAsEntity().ifPresent(entity -> entity.kick(reason));
	}
	
}
