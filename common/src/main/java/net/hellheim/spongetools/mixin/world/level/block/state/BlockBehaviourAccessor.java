package net.hellheim.spongetools.mixin.world.level.block.state;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviourAccessor {

    @Accessor("hasCollision") boolean accessor$hasCollision();

    @Invoker("getSignal") int invoker$getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction);

    @Invoker("getDirectSignal") int invoker$getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction);

    @Invoker("getAnalogOutputSignal") int invoker$getAnalogOutputSignal(BlockState state, Level level, BlockPos pos);

    @Invoker("canBeReplaced") boolean invoker$canBeReplaced(BlockState state, BlockPlaceContext useContext);

    @Invoker("canBeReplaced") boolean invoker$canBeReplaced(BlockState state, Fluid fluid);

    @Invoker("getDestroyProgress") float invoker$getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos);

    @Invoker("tick") void invoker$tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random);

    @Invoker("randomTick") void invoker$randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random);

    @Invoker("isRandomlyTicking") boolean invoker$isRandomlyTicking(BlockState state);

    @Invoker("entityInside") void invoker$entityInside(BlockState state, Level level, BlockPos pos, Entity entity);

    @Invoker("onPlace") void invoker$onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston);

    @Invoker("onRemove") void invoker$onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston);

    @Invoker("onExplosionHit") void invoker$onExplosionHit(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer);

    @Invoker("updateShape") BlockState invoker$updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    );

    @Invoker("getFluidState") FluidState invoker$getFluidState(BlockState state);

    @Invoker("neighborChanged") void invoker$neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston);

    @Invoker("useItemOn") InteractionResult invoker$useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult);

    @Invoker("useWithoutItem") InteractionResult invoker$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);

    @Invoker("attack") void invoker$attack(BlockState state, Level level, BlockPos pos, Player player);

    @Invoker("canSurvive") boolean invoker$canSurvive(BlockState state, LevelReader level, BlockPos pos);

    @Invoker("getCloneItemStack") ItemStack invoker$getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData);
}
