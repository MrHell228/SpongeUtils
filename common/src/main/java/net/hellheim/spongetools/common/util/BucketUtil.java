package net.hellheim.spongetools.common.util;

import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;

public final class BucketUtil {
	
	public static final BucketPickup EMPTY_BUCKET_PICKUP = new BucketPickup() {
		
		@Override
		public ItemStack pickupBlock(
			final @Nullable LivingEntity user, final LevelAccessor levelAccessor,
			final BlockPos blockPos, final BlockState blockState
		) {
			return ItemStack.EMPTY;
		}
		
		@Override
		public Optional<SoundEvent> getPickupSound() {
			return Optional.empty();
		}
	};
	
	public static BucketPickup bucketPickup(final Block block) {
		return block instanceof final BucketPickup pickup ? pickup : EMPTY_BUCKET_PICKUP;
	}
}
