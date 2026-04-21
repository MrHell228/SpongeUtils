package net.hellheim.spongetools.mixin.world.entity;

import net.minecraft.resources.DependantName;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(EntityType.Builder.class)
public interface EntityType_BuilderAccessor {

    @Accessor("serialize") void accessor$serialize(boolean value);

    @Accessor("summon") void accessor$summon(boolean value);

    @Accessor("fireImmune") void accessor$fireImmune(boolean value);

    @Accessor("canSpawnFarFromPlayer") void accessor$canSpawnFarFromPlayer(boolean value);

    @Accessor("lootTable") void accessor$lootTable(DependantName<EntityType<?>, Optional<ResourceKey<LootTable>>> value);

    @Accessor("descriptionId") void accessor$descriptionId(DependantName<EntityType<?>, String> value);
}
