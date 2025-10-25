package net.hellheim.spongetools.common.util;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.fluid.FluidState;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldLike;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.api.world.volume.game.Region;
import org.spongepowered.api.world.volume.game.UpdatableVolume;
import org.spongepowered.common.util.DirectionUtil;
import org.spongepowered.common.util.VecHelper;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;
import net.kyori.adventure.sound.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class Converter {
	
	public static ResourceKey asSponge(final ResourceLocation location) {
		return (ResourceKey) (Object) location;
	}
	
	public static PrimitiveGameVolume asSponge(final BlockGetter getter) {
		return (PrimitiveGameVolume) getter;
	}
	
	public static Region<?> asSponge(final LevelReader reader) {
		return (Region<?>) reader;
	}
	
	public static WorldLike<?> asSponge(final LevelAccessor accessor) {
		return (WorldLike<?>) accessor;
	}
	
	public static UpdatableVolume asSponge(final ScheduledTickAccess access) {
		// This cast in theory may throw an exception.
		// In vanilla the only inheritor of ScheduledTickAccess is LevelAccessor
		// which implements WorldLike that extends UpdatableVolume so it's fine.
		// May be reconsidered later if this would cause any problems.
		return (UpdatableVolume) access;
	}
	
	public static World<?, ?> asSponge(final Level level) {
		return (World<?, ?>) level;
	}
	
	public static ServerWorld asSponge(final ServerLevel level) {
		return (ServerWorld) level;
	}
	
	public static Explosion asSponge(final net.minecraft.world.level.Explosion explosion) {
		// Will throw if explosion is not ServerExplosion (which is not the case in vanilla)
		return (Explosion) explosion;
	}
	
	public static EntityType<?> asSponge(final net.minecraft.world.entity.EntityType<?> type) {
		return (EntityType<?>) type;
	}
	
	public static Entity asSponge(final net.minecraft.world.entity.Entity entity) {
		return (Entity) entity;
	}
	
	public static Player asSponge(final net.minecraft.world.entity.player.Player player) {
		return (Player) player;
	}
	
	public static PushReaction asSponge(final net.minecraft.world.level.material.PushReaction reaction) {
		return (PushReaction) (Object) reaction;
	}
	
	public static InstrumentType asSponge(final NoteBlockInstrument instrument) {
		return (InstrumentType) (Object) instrument;
	}
	
	public static MapColorType asSponge(final MapColor color) {
		return MapColorUtil.get(color);
	}
	
	public static Direction asSponge(final net.minecraft.core.Direction direction) {
		return DirectionUtil.getFor(direction);
	}
	
	public static Vector3i asSponge(final BlockPos pos) {
		return VecHelper.toVector3i(pos);
	}
	
	public static Vector3d asSponge(final Vec3 vec) {
		return VecHelper.toVector3d(vec);
	}
	
	public static RandomProvider.Source asSponge(final RandomSource random) {
		return (RandomProvider.Source) random;
	}
	
	public static BlockState asSponge(final net.minecraft.world.level.block.state.BlockState state) {
		return (BlockState) state;
	}
	
	public static FluidState asSponge(final net.minecraft.world.level.material.FluidState state) {
		return (FluidState) (Object) state;
	}
	
	public static SignalOrientation asSponge(final Orientation orientation) {
		return (SignalOrientation) orientation;
	}
	
	public static SignalBias asSponge(final Orientation.SideBias bias) {
		return (SignalBias) (Object) bias;
	}
	
	public static BlockType asSponge(final Block block) {
		return (BlockType) block;
	}
	
	public static FluidType asSponge(final Fluid fluid) {
		return (FluidType) fluid;
	}
	
	public static HitResult.EntityHitResult asSponge(final EntityHitResult result) {
		return (HitResult.EntityHitResult) result;
	}
	
	public static HitResult.BlockHitResult asSponge(final BlockHitResult result) {
		return (HitResult.BlockHitResult) result;
	}
	
	public static UseContext asSponge(final UseOnContext context) {
		return (UseContext) context;
	}
	
	public static UseContext.BlockPlace asSponge(final BlockPlaceContext context) {
		return (UseContext.BlockPlace) context;
	}
	
	public static HandType asSponge(final InteractionHand hand) {
		return (HandType) (Object) hand;
	}
	
	public static SwingType asSponge(final net.minecraft.world.InteractionResult.SwingSource swing) {
		return (SwingType) (Object) swing;
	}
	
	public static InteractionResult asSponge(final net.minecraft.world.InteractionResult result) {
		return (InteractionResult) (Object) result;
	}
	
	public static InteractionResult.Success asSponge(final net.minecraft.world.InteractionResult.Success result) {
		return (InteractionResult.Success) (Object) result;
	}
	
	public static ItemStack asSponge(final net.minecraft.world.item.ItemStack item) {
		return (ItemStack) (Object) item;
	}
	
	public static ItemType asSponge(final Item item) {
		return (ItemType) item;
	}
	
	public static Sound asSponge(final SoundEvent sound) {
		return (Sound) (Object) sound;
	}
	
	
	public static ResourceLocation asVanilla(final ResourceKey key) {
		return (ResourceLocation) (Object) key;
	}
	
	public static BlockGetter asVanilla(final PrimitiveGameVolume volume) {
		return (BlockGetter) volume;
	}
	
	public static LevelReader asVanilla(final Region<?> region) {
		return (LevelReader) region;
	}
	
	public static LevelAccessor asVanilla(final WorldLike<?> region) {
		return (LevelAccessor) region;
	}
	
	public static ScheduledTickAccess asVanilla(final UpdatableVolume volume) {
		// Will throw an exception if volume is not WorldLike (so if the volume is Chunk).
		return (ScheduledTickAccess) volume;
	}
	
	public static Level asVanilla(final World<?, ?> world) {
		return (Level) world;
	}
	
	public static ServerLevel asVanilla(final ServerWorld world) {
		return (ServerLevel) world;
	}
	
	public static net.minecraft.world.level.Explosion asVanilla(final Explosion explosion) {
		return (net.minecraft.world.level.Explosion) explosion;
	}
	
	public static net.minecraft.world.entity.EntityType<?> asVanilla(final EntityType<?> type) {
		return (net.minecraft.world.entity.EntityType<?>) type;
	}
	
	public static net.minecraft.world.entity.Entity asVanilla(final Entity entity) {
		return (net.minecraft.world.entity.Entity) entity;
	}
	
	public static net.minecraft.world.entity.player.Player asVanilla(final Player player) {
		return (net.minecraft.world.entity.player.Player) player;
	}
	
	public static net.minecraft.world.level.material.PushReaction asVanilla(final PushReaction reaction) {
		return (net.minecraft.world.level.material.PushReaction) (Object) reaction;
	}
	
	public static NoteBlockInstrument asVanilla(final InstrumentType instrument) {
		return (NoteBlockInstrument) (Object) instrument;
	}
	
	public static MapColor asVanilla(final MapColorType color) {
		return MapColorUtil.get(color);
	}
	
	public static net.minecraft.core.Direction asVanilla(final Direction direction) {
		final net.minecraft.core.@Nullable Direction result = DirectionUtil.getFor(direction);
		if (result == null) {
			throw new IllegalArgumentException("Direction must be cardinal: " + direction);
		}
		return result;
	}
	
	public static BlockPos asVanilla(final Vector3i vector) {
		return VecHelper.toBlockPos(vector);
	}
	
	public static Vec3 asVanilla(final Vector3d vector) {
		return VecHelper.toVanillaVector3d(vector);
	}
	
	public static RandomSource asVanilla(final RandomProvider.Source random) {
		return (RandomSource) random;
	}
	
	public static net.minecraft.world.level.block.state.BlockState asVanilla(final BlockState state) {
		return (net.minecraft.world.level.block.state.BlockState) state;
	}
	
	public static net.minecraft.world.level.material.FluidState asVanilla(final FluidState state) {
		return (net.minecraft.world.level.material.FluidState) (Object) state;
	}
	
	public static Orientation asVanilla(final SignalOrientation orientation) {
		return (Orientation) orientation;
	}
	
	public static Orientation.SideBias asVanilla(final SignalBias bias) {
		return (Orientation.SideBias) (Object) bias;
	}
	
	public static Block asVanilla(final BlockType block) {
		return (Block) block;
	}
	
	public static Fluid asVanilla(final FluidType fluid) {
		return (Fluid) fluid;
	}
	
	public static EntityHitResult asVanilla(final HitResult.EntityHitResult result) {
		return (EntityHitResult) result;
	}
	
	public static BlockHitResult asVanilla(final HitResult.BlockHitResult result) {
		return (BlockHitResult) result;
	}
	
	public static UseOnContext asVanilla(final UseContext context) {
		return (UseOnContext) context;
	}
	
	public static BlockPlaceContext asVanilla(final UseContext.BlockPlace context) {
		return (BlockPlaceContext) context;
	}
	
	public static InteractionHand asVanilla(final HandType hand) {
		return (InteractionHand) (Object) hand;
	}
	
	public static net.minecraft.world.InteractionResult.SwingSource asVanilla(final SwingType swing) {
		return (net.minecraft.world.InteractionResult.SwingSource) (Object) swing;
	}
	
	public static net.minecraft.world.InteractionResult asVanilla(final InteractionResult result) {
		return (net.minecraft.world.InteractionResult) (Object) result;
	}
	
	public static net.minecraft.world.InteractionResult.Success asVanilla(final InteractionResult.Success result) {
		return (net.minecraft.world.InteractionResult.Success) (Object) result;
	}
	
	public static net.minecraft.world.item.ItemStack asVanilla(final ItemStack item) {
		return (net.minecraft.world.item.ItemStack) (Object) item;
	}
	
	public static Item asVanilla(final ItemType item) {
		return (Item) item;
	}
	
	public static SoundEvent asVanilla(final Sound sound) {
		return (SoundEvent) (Object) sound;
	}
	
	private Converter() {
	}
}
