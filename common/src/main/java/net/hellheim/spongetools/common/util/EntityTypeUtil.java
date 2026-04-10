package net.hellheim.spongetools.common.util;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.commons.lang3.mutable.MutableInt;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.entity.EntityCategory;
import org.spongepowered.api.entity.EntityTypes;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
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
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;

public final class EntityTypeUtil {
	
	private static final MutableInt NO_SAVE_COUNTER = new MutableInt();
	
	private static final Supplier<? extends org.spongepowered.api.entity.EntityType<?>> NETWORK_ENTITY = EntityTypes.PIG;
	
	public static final <E extends Entity> EntityType<E> type(
		final Class<E> baseClass,
		final ValueContainer data, final TypedKeyMap context,
		final BehaviourCallbackHolderLogic<org.spongepowered.api.entity.Entity> behaviour
	) {
		@SuppressWarnings("unchecked")
		final EntityType.Builder<E> builder = EntityType.Builder.of(
				EntityFactoryUtil.create(
						baseClass,
						context.getOrElse(EntityTypeKeys.FLAGS, Collections.emptyList()),
						(BehaviourCallbackHolderLogic<Entity>) (Object) behaviour), 
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
		
		final EntityType<E> type = builder.build(ResourceKey.create(Registries.ENTITY_TYPE, Converter.asVanilla(
				context.get(EntityTypeKeys.SERIALIZATION_KEY)
					.orElseGet(() -> SpongeTools.key("nosave_" + EntityTypeUtil.NO_SAVE_COUNTER.incrementAndGet()))
				)));
		
		((EntityTypeBridge) type).spongetools$bridge$setData(new AdditionalData(
				EntityTypeUtil.NETWORK_ENTITY.get()
				));
		
		((EntityTypeBridge) type).spongetools$bridge$setCallbacks(behaviour);
		
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static final org.spongepowered.api.entity.EntityType<?> type(
		final EntityTypeArchetype archetype,
		final ValueContainer data, final TypedKeyMap context,
		final BehaviourCallbackHolderLogic<org.spongepowered.api.entity.Entity> behaviour
	) {
		return (org.spongepowered.api.entity.EntityType<?>) EntityTypeUtil.type(
				(Class<Entity>) archetype.baseClass(), data, context, behaviour);
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
		
		public static final EntityTypeArchetype AERIAL = EntityTypeUtil.archetype(AGENT, FlyingMob.class);
		
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
	
	public record AdditionalData(org.spongepowered.api.entity.EntityType<?> networkType) {
		
	}
	
	private EntityTypeUtil() {
	}
}
