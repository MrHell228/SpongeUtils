package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import org.checkerframework.checker.nullness.qual.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public interface BlockStateBaseBridge {
	
	void spongetools$bridge$set(BehaviourCallbackHolderLogic<BlockStateExtension> callbacks);
	
	@Nullable BlockEntity spongetools$bridge$newBlockEntity(BlockGetter getter, BlockPos pos);

    ItemStack spongetools$bridge$bucketPickup$item(LevelAccessor accessor, BlockPos pos, @Nullable Player player);

    Optional<SoundEvent> spongetools$bridge$bucketPickup$sound();
}
