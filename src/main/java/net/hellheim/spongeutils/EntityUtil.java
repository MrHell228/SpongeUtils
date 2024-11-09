package net.hellheim.spongeutils;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongeutils.source.solid.PotionEffectSource;
import net.hellheim.spongeutils.source.solid.PotionEffectTypeSource;
import net.hellheim.spongeutils.source.solid.entity.EntityTypeSource;
import net.hellheim.spongeutils.source.solid.item.IItemSource;

public final class EntityUtil {
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final EntityTypeSource<?> e2) {
		return is(e1.getAsEntityType(), e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final EntityArchetype e2) {
		return is(e1.getAsEntityType(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final EntitySnapshot e2) {
		return is(e1.getAsEntityType(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final Entity e2) {
		return is(e1.getAsEntityType(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final Supplier<EntityType<?>> e2) {
		return is(e1.getAsEntityType(), e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityTypeSource}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityTypeSource<?> e1, final EntityType<?> e2) {
		return is(e1.getAsEntityType(), e2);
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final EntityTypeSource<?> e2) {
		return is(e1.type(), e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final EntityArchetype e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final EntitySnapshot e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final Entity e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final Supplier<EntityType<?>> e2) {
		return is(e1.type(), e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityArchetype}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityArchetype e1, final EntityType<?> e2) {
		return is(e1.type(), e2);
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final EntityTypeSource<?> e2) {
		return is(e1.type(), e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final EntityArchetype e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final EntitySnapshot e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final Entity e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final Supplier<EntityType<?>> e2) {
		return is(e1.type(), e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntitySnapshot}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntitySnapshot e1, final EntityType<?> e2) {
		return is(e1.type(), e2);
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final EntityTypeSource<?> e2) {
		return is(e1.type(), e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final EntityArchetype e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final EntitySnapshot e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final Entity e2) {
		return is(e1.type(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final Supplier<EntityType<?>> e2) {
		return is(e1.type(), e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Entity}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Entity e1, final EntityType<?> e2) {
		return is(e1.type(), e2);
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final EntityTypeSource<?> e2) {
		return is(e1.get(), e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final EntityArchetype e2) {
		return is(e1.get(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final EntitySnapshot e2) {
		return is(e1.get(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final Entity e2) {
		return is(e1.get(), e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final Supplier<EntityType<?>> e2) {
		return is(e1.get(), e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link Supplier EntityType Supplier}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<EntityType<?>> e1, final EntityType<?> e2) {
		return is(e1.get(), e2);
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link EntityTypeSource}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final EntityTypeSource<?> e2) {
		return is(e1, e2.getAsEntityType());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link EntityArchetype}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final EntityArchetype e2) {
		return is(e1, e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link EntitySnapshot}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final EntitySnapshot e2) {
		return is(e1, e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link Entity}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final Entity e2) {
		return is(e1, e2.type());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link Supplier EntityType Supplier}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final Supplier<EntityType<?>> e2) {
		return is(e1, e2.get());
	}
	
	/**
	 * Compares {@link EntityType}s of two arguments.
	 * 
	 * @param e1 The {@link EntityType}
	 * @param e2 The {@link EntityType}
	 * @return True if {@link EntityType}s of the arguments are equal
	 */
	public static boolean is(final EntityType<?> e1, final EntityType<?> e2) {
		return e1 == e2;
	}
	
	
	
	public static void heal(final Living entity, final double health) {
		entity.offer(Keys.HEALTH, Math.min(entity.health().get() + health, entity.maxHealth().get()));
	}
	
	public static void damage(final Living entity, final double health) {
		entity.offer(Keys.HEALTH, Math.max(entity.health().get() - health, 0D));
	}
	
	public static void kill(final Entity entity) {
		entity.damage(999, DamageSources.VOID);
	}
	
	
	
	public static void setCooldown(final ServerPlayer player, final IItemSource type, final Ticks cooldown) {
		setCooldown(player, type.getAsItemType(), cooldown);
	}
	
	public static void setCooldown(final ServerPlayer player, final Supplier<ItemType> type, final Ticks cooldown) {
		setCooldown(player, type.get(), cooldown);
	}
	
	public static void setCooldown(final ServerPlayer player, final ItemType type, final Ticks cooldown) {
		player.cooldownTracker().setCooldown(type, cooldown);
	}
	
	public static void resetCooldown(final ServerPlayer player, final IItemSource type) {
		resetCooldown(player, type.getAsItemType());
	}
	
	public static void resetCooldown(final ServerPlayer player, final Supplier<ItemType> type) {
		resetCooldown(player, type.get());
	}
	
	public static void resetCooldown(final ServerPlayer player, final ItemType type) {
		player.cooldownTracker().resetCooldown(type);
	}
	
	public static boolean hasCooldown(final ServerPlayer player, final IItemSource type) {
		return hasCooldown(player, type.getAsItemType());
	}
	
	public static boolean hasCooldown(final ServerPlayer player, final Supplier<ItemType> type) {
		return hasCooldown(player, type.get());
	}
	
	public static boolean hasCooldown(final ServerPlayer player, final ItemType type) {
		return cooldown(player, type).isPresent();
	}
	
	public static Optional<Ticks> cooldown(final ServerPlayer player, final IItemSource type) {
		return cooldown(player, type.getAsItemType());
	}
	
	public static Optional<Ticks> cooldown(final ServerPlayer player, final Supplier<ItemType> type) {
		return cooldown(player, type.get());
	}
	
	public static Optional<Ticks> cooldown(final ServerPlayer player, final ItemType type) {
		return player.cooldownTracker().cooldown(type);
	}
	
	
	
	public static void setEffect(final Living entity, final PotionEffectSource effect) {
		setEffect(entity, effect.getAsPotionEffect());
	}
	
	public static void setEffect(final Living entity, final PotionEffect effect) {
		addEffect(entity, effect, ef -> true);
	}
	
	public static void addEffect(final Living entity, final PotionEffectSource effect) {
		addEffect(entity, effect.getAsPotionEffect());
	}
	
	public static void addEffect(final Living entity, final PotionEffect effect) {
		addEffect(entity, effect, ef -> false);
	}
	
	public static void addEffect(
		final Living entity, final PotionEffectSource effect, final Predicate<PotionEffect> shouldReplace
	) {
		addEffect(entity, effect.getAsPotionEffect(), shouldReplace);
	}
	
	public static void addEffect(
		final Living entity, final PotionEffect effect, final Predicate<PotionEffect> shouldReplace
	) {
		addEffect(entity, effect, shouldReplace, (e1, e2) -> e2);
	}
	
	public static void addEffect(
		final Living entity, final PotionEffectSource effect,
		final Predicate<PotionEffect> shouldReplace, BinaryOperator<PotionEffect> merger
	) {
		addEffect(entity, effect.getAsPotionEffect(), shouldReplace, merger);
	}
	
	public static void addEffect(
		final Living entity, final PotionEffect effect,
		final Predicate<PotionEffect> shouldReplace, BinaryOperator<PotionEffect> merger
	) {
		final ListValue.Mutable<PotionEffect> list = entity.potionEffects();
		int i = 0;
		for (final PotionEffect ef : list) {
			if (ef.type() != effect.type()) {
				++i;
				continue;
			}
			
			if (shouldReplace.test(ef)) {
				list.remove(i);
				list.add(merger.apply(ef, effect));
				entity.offer(list);
			}
			
			return;
		}
		
		list.add(effect);
		entity.offer(list);
	}
	
	
	
	public static Optional<PotionEffect> effect(final Living entity, final PotionEffectTypeSource type) {
		return effect(entity, type.getAsPotionEffectType());
	}
	
	public static Optional<PotionEffect> effect(final Living entity, final Supplier<PotionEffectType> type) {
		return effect(entity, type);
	}
	
	public static Optional<PotionEffect> effect(final Living entity, final PotionEffectType type) {
		for (final PotionEffect effect : entity.require(Keys.POTION_EFFECTS)) {
			if (effect.type() == type) {
				return Optional.of(effect);
			}
		}
		
		return Optional.empty();
	}
	
	
	
	public static <T extends Entity> T summon(
			final World<?, ?> world, final EntityType<T> type, final Vector3i pos, final Consumer<T> dataSetter
	) {
		return summon(world, type, pos.toDouble(), dataSetter);
	}
	
	public static <T extends Entity> T summon(
			final World<?, ?> world, final EntityType<T> type, final Vector3d pos, final Consumer<T> dataSetter
	) {
		final T entity = world.createEntity(type, pos);
		dataSetter.accept(entity);
		world.spawnEntity(entity);
		return entity;
	}
	
	private EntityUtil() {
	}
}
