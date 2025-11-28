package net.hellheim.spongetools.common.util;

import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.api.entity.EntityCategory;

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
import net.minecraft.world.entity.MobCategory;

public final class EntityTypeUtil {
	
	private static final MutableInt NO_SAVE_COUNTER = new MutableInt();
	
	public static final <E extends Entity> EntityType<E> type(
		final TypedKeyMap context,
		final EntityType.EntityFactory<E> factory
	) {
		final EntityType.Builder<E> builder = EntityType.Builder.of(factory, 
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
	
	public static final class Archetypes {
		
		
		
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
