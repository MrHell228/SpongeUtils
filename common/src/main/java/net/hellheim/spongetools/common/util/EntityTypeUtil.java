package net.hellheim.spongetools.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.commons.lang3.mutable.MutableInt;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.entity.Aerial;
import org.spongepowered.api.entity.Angerable;
import org.spongepowered.api.entity.EntityCategory;
import org.spongepowered.api.entity.Ranger;
import org.spongepowered.api.entity.living.Hostile;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.SuperMethodCall;
import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.EntityTypeKeys;
import net.hellheim.spongetools.mixin.world.entity.EntityType_BuilderAccessor;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.object.TypedKeyMap.Mutable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;

public final class EntityTypeUtil {
	
	private static final MutableInt NO_SAVE_COUNTER = new MutableInt();
	private static final Map<?, ?> GROUPS = Map.of(
			Hostile.class, Enemy.class, // No behaviour
			Ranger.class, RangedAttackMob.class, // #performRangedAttack
			org.spongepowered.api.entity.Leashable.class, Leashable.class, // A lot of behaviour
			org.spongepowered.api.entity.Saddleable.class, Saddleable.class, // Some behaviour
			null, Shearable.class, // Few behaviour
			Angerable.class, NeutralMob.class, // A lot of behaviour
			Aerial.class, FlyingAnimal.class, // #isFlying
			//null, Bucketable.class,
			null, ItemSteerable.class, // #boost
			null, PlayerRideableJumping.class, // Some behaviour
			null, HasCustomInventoryScreen.class // #openCustomInventoryScreen
			);
	
	public static final <E extends Entity> EntityType.EntityFactory<E> factory(
		final Class<E> baseClass, final Object behaviour
	) {
		final var builder = new ByteBuddy()
				.subclass(baseClass)
				.defineConstructor(Visibility.PUBLIC)
				.withParameters(EntityType.class, Level.class)
				.intercept(SuperMethodCall.INSTANCE);
		
		// TODO add interfaces
		
		try {
			final var constructor = builder
					.make()
					.load(Entity.class.getClassLoader())
					.getLoaded()
					.getConstructor(EntityType.class, Level.class);
			
			return (type, world) -> {
				try {
					return (E) constructor.newInstance(type, world);
				} catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException("Could not create new custom entity", e);
				}
			};
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException("Could not find custom entity constructor (this should not happen)", e);
		}
	}
	
	public static final <E extends Entity> EntityType<E> type(
		final Class<E> baseClass, final ValueContainer data, final TypedKeyMap context, final Object behaviour
	) {
		final EntityType.Builder<E> builder = EntityType.Builder.of(
				EntityTypeUtil.factory(baseClass, behaviour), 
				(MobCategory) (Object) context.require(EntityTypeKeys.CATEGORY));
		
		final EntityType_BuilderAccessor accessor = (EntityType_BuilderAccessor) builder;
		context.get(EntityTypeKeys.TRANSLATION_KEY).ifPresent(v -> accessor.accessor$descriptionId(t -> v));
		accessor.accessor$lootTable(t -> context.get(EntityTypeKeys.LOOT_TABLE)
				.map(Converter::asVanilla)
				.map(v -> ResourceKey.create(Registries.LOOT_TABLE, v)));
		
		final UnaryOperator<Boolean> negate = b -> !b;
		context.get(EntityTypeKeys.FLAMMABLE).map(negate).ifPresent(accessor::accessor$fireImmune);
		context.get(EntityTypeKeys.SPAWN_AWAY_FROM_PLAYER).ifPresent(accessor::accessor$canSpawnFarFromPlayer);
		context.get(EntityTypeKeys.SUMMONABLE).ifPresent(accessor::accessor$summon);
		
		if (context.get(EntityTypeKeys.SERIALIZATION_KEY).isEmpty()) {
			builder.noSave();
		}
		
		return builder.build(ResourceKey.create(Registries.ENTITY_TYPE, Converter.asVanilla(
				context.get(EntityTypeKeys.SERIALIZATION_KEY)
					.orElseGet(() -> SpongeTools.key("nosave_" + EntityTypeUtil.NO_SAVE_COUNTER.incrementAndGet()))
				)));
	}
	
	@SuppressWarnings("unchecked")
	public static final EntityType<?> type(
		final EntityTypeArchetype archetype, final ValueContainer data, final TypedKeyMap context, final Object behaviour
	) {
		return EntityTypeUtil.type((Class<Entity>) archetype.baseClass(), data, context, behaviour);
	}
	
	public static final <E extends Entity> EntityTypeArchetype archetype(
		final @Nullable EntityTypeArchetype parent, final Class<E> baseClass
	) {
		return EntityTypeArchetype.of(Optional.ofNullable(parent), baseClass);
	}
	
	public static final class Archetypes {
		
		public static final EntityTypeArchetype ENTITY = EntityTypeUtil.archetype(null, Entity.class);
		
		public static final EntityTypeArchetype LIVING = EntityTypeUtil.archetype(ENTITY, LivingEntity.class);
		
		public static final EntityTypeArchetype AGENT = EntityTypeUtil.archetype(LIVING, Mob.class);
		
		public static final EntityTypeArchetype PATHFINDER_AGENT = EntityTypeUtil.archetype(AGENT, PathfinderMob.class);
		
		public static final EntityTypeArchetype MONSTER = EntityTypeUtil.archetype(PATHFINDER_AGENT, Monster.class);
		
		private Archetypes() {
		}
	}
	
	public static final class ContextFactory implements EntityTypeArchetype.Factory {
		
		@Override
		public Stream<TypedKey<?>> cumulativeRequiredKeys() {
			return Stream.of(EntityTypeKeys.CATEGORY, EntityTypeKeys.TRANSLATION_KEY);
		}
		
		@Override
		public BiConsumer<org.spongepowered.api.entity.EntityType<?>, Mutable> cumulativeContextExtractor() {
			return (spongeType, context) -> {
				final EntityType<?> type = (EntityType<?>) spongeType;
				context.set(EntityTypeKeys.CATEGORY, (EntityCategory) (Object) type.getCategory());
				context.set(EntityTypeKeys.FLAMMABLE, !type.fireImmune());
				context.set(EntityTypeKeys.SPAWN_AWAY_FROM_PLAYER, type.canSpawnFarFromPlayer());
				context.set(EntityTypeKeys.SUMMONABLE, type.canSummon());
			};
		}
		
	}
	
	private EntityTypeUtil() {
	}
}
