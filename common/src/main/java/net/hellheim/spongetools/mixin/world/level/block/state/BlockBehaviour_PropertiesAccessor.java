package net.hellheim.spongetools.mixin.world.level.block.state;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockBehaviour_PropertiesAccessor {

    @Accessor("mapColor") Function<BlockState, MapColor> accessor$mapColor();

    @Accessor("requiresCorrectToolForDrops") boolean accessor$requiresCorrectToolForDrops();

    @Accessor("pushReaction") PushReaction accessor$pushReaction();

    @Accessor("instrument") NoteBlockInstrument accessor$instrument();

    @Accessor("replaceable") boolean accessor$replaceable();

    @Accessor("isValidSpawn") BlockBehaviour.StateArgumentPredicate<EntityType<?>> accessor$isValidSpawn();

    @Accessor("isRedstoneConductor") BlockBehaviour.StatePredicate accessor$isRedstoneConductor();

    @Accessor("isSuffocating") BlockBehaviour.StatePredicate accessor$isSuffocating();

    @Accessor("dynamicShape") void accessor$dynamicShape(boolean value);

    @Accessor("canOcclude") void accessor$canOcclude(boolean value);

    @Accessor("hasCollision") void accessor$hasCollision(boolean value);
}
