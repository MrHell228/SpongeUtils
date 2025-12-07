package net.hellheim.spongetools.mixin.world.level.block.state;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.hellheim.spongetools.bridge.BlockStateBaseBridge;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.common.behaviour.BlockStateArgs;
import net.hellheim.spongetools.common.behaviour.CommonArgs;
import net.hellheim.spongetools.common.util.BucketUtil;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallback;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.behaviour.BehaviourLayer;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviours;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@Mixin(net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehaviour_BlockStateBaseMixin implements
        BlockStateBaseBridge,
        FakeableNetworkValueBridge,
        BlockStateExtension,
        BehaviourCallbackHolderProxy<BlockStateExtension> {

    @Unique private static final BehaviourCallbackHolderLogic<BlockStateExtension> SPONGETOOLS$EMPTY_HOLDER = BehaviourCallbackHolderLogic.immutable();

    @Shadow protected abstract BlockState shadow$asState();

    @Unique private @MonotonicNonNull BehaviourCallbackHolderLogic<BlockStateExtension> spongetools$callbacks;
    @Unique private @Nullable BlockState spongetools$networkState;

    @Override
    public org.spongepowered.api.block.BlockState state() {
        return (org.spongepowered.api.block.BlockState) this;
    }

    @Override
    public org.spongepowered.api.block.BlockState display() {
        return this.spongetools$networkState == null
                ? this.state()
                : (org.spongepowered.api.block.BlockState) this.spongetools$networkState;
    }

    @Override
    public BehaviourCallbackHolder<BlockStateExtension> getAsBehaviourCallbackHolder() {
        return this.spongetools$callbacks == null ? SPONGETOOLS$EMPTY_HOLDER : this.spongetools$callbacks;
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$networkState;
    }

    @Override
    public void spongetools$bridge$setNetworkState(final BlockState state) {
        this.spongetools$networkState = state;
    }

    @Override
    public void spongetools$bridge$set(final BehaviourCallbackHolderLogic<BlockStateExtension> callbacks) {
        this.spongetools$callbacks = Objects.requireNonNull(callbacks, "callbacks").asImmutable();
    }

    @Override
    public @Nullable BlockEntity spongetools$bridge$newBlockEntity(
            final BlockGetter getter, final BlockPos pos
    ) {
        // TODO add behaviour
        return this.shadow$asState().getBlock() instanceof final EntityBlock block
                ? block.newBlockEntity(pos, this.shadow$asState())
                : null;
    }

    @Override
    public ItemStack spongetools$bridge$bucketPickup$item(
            final LevelAccessor accessor, final BlockPos pos, final @Nullable Player player
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.BUCKET_PICKUP_ITEM);
        final var state = this.shadow$asState();
        final var original = BucketUtil.bucketPickup(state.getBlock());
        return callback == null
                ? original.pickupBlock(player, accessor, pos, state)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.pickupBlock(
                                Converter.asVanilla(args.entity().orElse(null)),
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position()),
                                state
                                )),
                        new BlockStateArgs.LocatableEntity<>(
                                Converter.asSponge(accessor),
                                Converter.asSponge(pos),
                                Optional.ofNullable(Converter.asSponge(player))
                                )
                        ));
    }

    @Override
    public Optional<SoundEvent> spongetools$bridge$bucketPickup$sound() {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.BUCKET_PICKUP_SOUND);
        final var state = this.shadow$asState();
        final var original = BucketUtil.bucketPickup(state.getBlock());
        return callback == null
                ? original.getPickupSound()
                : callback.call(
                        this,
                        args -> original.getPickupSound().map(Converter::asSponge),
                        CommonArgs.empty()
                ).map(Converter::asVanilla);
    }

    @WrapMethod(method = "isValidSpawn")
    private boolean spongetools$wrap$isValidSpawn(
            final BlockGetter getter, final BlockPos pos, final EntityType<?> entityType,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.SPAWN_VALIDATOR);
        return callback == null
                ? original.call(getter, pos, entityType)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position()),
                                Converter.asVanilla(args.entity())
                                ),
                        new BlockStateArgs.LocatableEntity<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos),
                                Converter.asSponge(entityType)
                                )
                        );
    }

    @WrapMethod(method = "getMapColor")
    private MapColor spongetools$wrap$getMapColor(
            final BlockGetter getter, final BlockPos pos,
            final Operation<MapColor> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.MAP_COLOR);
        return callback == null
                ? original.call(getter, pos)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                )),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos)
                                )
                        ));
    }

    @WrapMethod(method = "isRedstoneConductor")
    private boolean spongetools$wrap$isRedstoneConductor(
            final BlockGetter getter, final BlockPos pos,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.SIGNAL_CONDUCTOR);
        return callback == null
                ? original.call(getter, pos)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos)
                                )
                        );
    }

    @WrapMethod(method = "isSignalSource")
    private boolean spongetools$wrap$isSignalSource(
            final Operation<Boolean> original
    ) {
        return this.spongetools$impl$callback(BlockStateBehaviours.DIRECT_SIGNAL) != null
            || this.spongetools$impl$callback(BlockStateBehaviours.INDIRECT_SIGNAL) != null
            || original.call();
    }

    @WrapMethod(method = "getSignal")
    private int spongetools$wrap$getSignal(
            final BlockGetter getter, final BlockPos pos, final Direction dir,
            final Operation<Integer> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.DIRECT_SIGNAL);
        return callback == null
                ? original.call(getter, pos, dir)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position()),
                                Converter.asVanilla(args.direction().opposite())
                                ),
                        new BlockStateArgs.SignalPower(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos),
                                Converter.asSponge(dir).opposite()
                                )
                        );
    }

    @WrapMethod(method = "getDirectSignal")
    private int spongetools$wrap$getDirectSignal(
            final BlockGetter getter, final BlockPos pos, final Direction dir,
            final Operation<Integer> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.INDIRECT_SIGNAL);
        return callback == null
                ? original.call(getter, pos, dir)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position()),
                                Converter.asVanilla(args.direction().opposite())
                                ),
                        new BlockStateArgs.SignalPower(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos),
                                Converter.asSponge(dir).opposite()
                                )
                        );
    }

    @WrapMethod(method = "hasAnalogOutputSignal")
    private boolean spongetools$wrap$hasAnalogOutputSignal(
            final Operation<Boolean> original
    ) {
        return this.spongetools$impl$callback(BlockStateBehaviours.ANALOG_SIGNAL) != null
            || original.call();
    }

    @WrapMethod(method = "getAnalogOutputSignal")
    private int spongetools$wrap$getAnalogOutputSignal(
            final Level level, final BlockPos pos,
            final Operation<Integer> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.ANALOG_SIGNAL);
        return callback == null
                ? original.call(level, pos)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(level),
                                Converter.asSponge(pos)
                                )
                        );
    }

    @WrapMethod(method = "getDestroySpeed")
    private float spongetools$wrap$getAnalogOutputSignal(
            final BlockGetter getter, final BlockPos pos,
            final Operation<Float> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.DESTRUCTION_RESISTANCE);
        return callback == null
                ? original.call(getter, pos)
                : callback.call(
                        this,
                        args -> (double) original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos)
                                )
                        ).floatValue();
    }

    @WrapMethod(method = "getDestroyProgress")
    private float spongetools$wrap$getAnalogOutputSignal(
            final Player player, final BlockGetter getter, final BlockPos pos,
            final Operation<Float> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.DESTRUCTION_INCREMENT);
        return callback == null
                ? original.call(player, getter, pos)
                : callback.call(
                        this,
                        args -> (double) original.call(
                                Converter.asVanilla(args.entity()),
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.LocatableEntity<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos),
                                Converter.asSponge(player)
                                )
                        ).floatValue();
    }

    @WrapMethod(method = "tick")
    private void spongetools$wrap$tick(
            final ServerLevel level, final BlockPos pos, final RandomSource random,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.BASE_TICK);
        if (callback == null) {
            original.call(level, pos, random);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.random())
                            ),
                    new BlockStateArgs.Tick(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(random)
                            )
                    );
        }
    }

    @WrapMethod(method = "isRandomlyTicking")
    private boolean spongetools$wrap$isRandomlyTicking(
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.HAS_RANDOM_TICK);
        return callback == null
                ? original.call()
                : callback.call(
                        this,
                        args -> original.call(),
                        CommonArgs.empty()
                        );
    }

    @WrapMethod(method = "randomTick")
    private void spongetools$wrap$randomTick(
            final ServerLevel level, final BlockPos pos, final RandomSource random,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.RANDOM_TICK);
        if (callback == null) {
            original.call(level, pos, random);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.random())
                            ),
                    new BlockStateArgs.Tick(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(random)
                            )
                    );
        }
    }

    @WrapMethod(method = "entityInside")
    private void spongetools$wrap$entityInside(
            final Level level, final BlockPos pos, final Entity entity,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.ENTITY_INSIDE);
        if (callback == null) {
            original.call(level, pos, entity);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.entity())
                            ),
                    new BlockStateArgs.LocatableEntity<>(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(entity)
                            )
                    );
        }
    }

    @WrapMethod(method = "onPlace")
    private void spongetools$warp$onPlace(
            final Level level, final BlockPos pos, final BlockState newState, final boolean movedByPiston,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.PLACE);
        if (callback == null) {
            original.call(level, pos, newState, movedByPiston);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.otherState()),
                            args.movedByPiston()
                            ),
                    new BlockStateArgs.Replace(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(newState),
                            movedByPiston
                            )
                    );
        }
    }

    @WrapMethod(method = "onRemove")
    private void spongetools$warp$onRemove(
            final Level level, final BlockPos pos, final BlockState newState, final boolean movedByPiston,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.REMOVE);
        if (callback == null) {
            original.call(level, pos, newState, movedByPiston);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.otherState()),
                            args.movedByPiston()
                            ),
                    new BlockStateArgs.Replace(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(newState),
                            movedByPiston
                            )
                    );
        }
    }

    @WrapMethod(method = "onExplosionHit")
    private void spongetools$warp$onExplosionHit(
            final ServerLevel level, final BlockPos position,
            final Explosion explosion, final BiConsumer<ItemStack, BlockPos> drop,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.EXPLOSION);
        if (callback == null) {
            original.call(level, position, explosion, drop);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.explosion()),
                            (BiConsumer<ItemStack, BlockPos>) (item, pos) -> args.drop().accept(Converter.asSponge(item), Converter.asSponge(pos))
                            ),
                    new BlockStateArgs.ExplosionHit(
                            Converter.asSponge(level),
                            Converter.asSponge(position),
                            Converter.asSponge(explosion),
                            (item, pos) -> drop.accept(Converter.asVanilla(item), Converter.asVanilla(pos))
                            )
                    );
        }
    }

    @WrapMethod(method = "updateShape")
    private BlockState spongetools$wrap$updateShape(
            final LevelReader reader, final ScheduledTickAccess ticks, final BlockPos pos,
            final Direction dir, final BlockPos nPos, BlockState nState,
            final RandomSource random,
            final Operation<BlockState> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.SHAPE_UPDATE);
        return callback == null
                ? original.call(reader, ticks, pos, dir, nPos, nState, random)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.updates()),
                                Converter.asVanilla(args.position()),
                                Converter.asVanilla(args.direction().opposite()),
                                Converter.asVanilla(args.neighbourPosition()),
                                Converter.asVanilla(args.neighbourState()),
                                Converter.asVanilla(args.random())
                                )),
                        new BlockStateArgs.ShapeUpdate(
                                Converter.asSponge(reader),
                                Converter.asSponge(ticks),
                                Converter.asSponge(pos),
                                Converter.asSponge(dir).opposite(),
                                Converter.asSponge(nPos),
                                Converter.asSponge(nState),
                                Converter.asSponge(random)
                                )
                        ));
    }

    @WrapMethod(method = "handleNeighborChanged")
    private void spongetools$wrap$handleNeighborChanged(
            final Level level, final BlockPos pos, final Block block,
            final @Nullable Orientation orientation, final boolean movedByPiston,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.SIGNAL_UPDATE);
        if (callback == null) {
            original.call(level, pos, block, orientation, movedByPiston);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.notifier()),
                            Converter.asVanilla(args.orientation().orElse(null)),
                            args.movedByPiston()
                            ),
                    new BlockStateArgs.SignalUpdate(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(block),
                            Optional.ofNullable(Converter.asSponge(orientation)),
                            movedByPiston
                            )
                    );
        }
    }

    @WrapMethod(method = "useItemOn")
    private InteractionResult spongetools$wrap$useItemOn(
            final ItemStack item, final Level level, final Player player,
            final InteractionHand hand, final BlockHitResult hit,
            final Operation<InteractionResult> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.USE_WITH_ITEM);
        return callback == null
                ? original.call(item, level, player, hand, hit)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call(
                                Converter.asVanilla(args.item()),
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.entity()),
                                Converter.asVanilla(args.hand()),
                                Converter.asVanilla(args.hit())
                                )),
                        new BlockStateArgs.UseWithItem(
                                Converter.asSponge(level),
                                Converter.asSponge(player),
                                Converter.asSponge(hit),
                                Converter.asSponge(hand),
                                Converter.asSponge(item)
                                )
                        ));
    }

    @WrapMethod(method = "useWithoutItem")
    private InteractionResult spongetools$wrap$useWithoutItem(
            final Level level, final Player player, final BlockHitResult hit,
            final Operation<InteractionResult> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.USE_WITHOUT_ITEM);
        return callback == null
                ? original.call(level, player, hit)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.entity()),
                                Converter.asVanilla(args.hit())
                                )),
                        new BlockStateArgs.UseWithoutItem(
                                Converter.asSponge(level),
                                Converter.asSponge(player),
                                Converter.asSponge(hit)
                                )
                        ));
    }

    @WrapMethod(method = "attack")
    private void spongetools$wrap$attack(
            final Level level, final BlockPos pos, final Player player,
            final Operation<Void> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.ATTACK);
        if (callback == null) {
            original.call(level, pos, player);
        } else {
            callback.call(
                    this,
                    args -> original.call(
                            Converter.asVanilla(args.volume()),
                            Converter.asVanilla(args.position()),
                            Converter.asVanilla(args.entity())
                            ),
                    new BlockStateArgs.LocatableEntity<>(
                            Converter.asSponge(level),
                            Converter.asSponge(pos),
                            Converter.asSponge(player)
                            )
                    );
        }
    }

    @WrapMethod(method = "getPistonPushReaction")
    private PushReaction spongetools$wrap$getPistonPushReaction(
            final Operation<PushReaction> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.PUSH_REACTION);
        return callback == null
                ? original.call()
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call()),
                        CommonArgs.empty()
                        ));
    }

    @WrapMethod(method = "instrument")
    private NoteBlockInstrument spongetools$wrap$instrument(
            final Operation<NoteBlockInstrument> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.INSTRUMENT);
        return callback == null
                ? original.call()
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call()),
                        CommonArgs.empty()
                        ));
    }

    @WrapMethod(method = "getFluidState")
    private FluidState spongetools$wrap$getFluidState(
            final Operation<FluidState> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.FLUID);
        return callback == null
                ? original.call()
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call()),
                        CommonArgs.empty()
                        ));
    }

    @WrapMethod(method = "requiresCorrectToolForDrops")
    private boolean spongetools$wrap$requiresCorrectToolForDrops(
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.REQUIRE_TOOL);
        return callback == null
                ? original.call()
                : callback.call(
                        this,
                        args -> original.call(),
                        CommonArgs.empty()
                        );
    }

    @WrapMethod(method = "canBeReplaced()Z")
    private boolean spongetools$wrap$canBeReplaced(
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.REPLACEABLE);
        return callback == null
                ? original.call()
                : callback.call(
                        this,
                        args -> original.call(),
                        CommonArgs.empty()
                        );
    }

    @WrapMethod(method = "canBeReplaced(Lnet/minecraft/world/level/material/Fluid;)Z")
    private boolean spongetools$wrap$canBeReplaced(
            final Fluid fluid,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.REPLACEABLE_BY_FLUID);
        return callback == null
                ? original.call(fluid)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.fluid())
                                ),
                        new BlockStateArgs.ReplaceableByFluid(
                                Converter.asSponge(fluid)
                                )
                        );
    }

    @WrapMethod(method = "canBeReplaced(Lnet/minecraft/world/item/context/BlockPlaceContext;)Z")
    private boolean spongetools$wrap$canBeReplaced(
            final BlockPlaceContext context,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.REPLACEABLE_BY_BLOCK);
        return callback == null
                ? original.call(context)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.context())
                                ),
                        new BlockStateArgs.ReplaceableByBlock(
                                Converter.asSponge(context)
                                )
                        );
    }

    @WrapMethod(method = "isSuffocating")
    private boolean spongetools$wrap$isSuffocating(
            final BlockGetter getter, final BlockPos pos,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.SUFFOCATION);
        return callback == null
                ? original.call(getter, pos)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(getter),
                                Converter.asSponge(pos)
                                )
                        );
    }

    @WrapMethod(method = "canSurvive")
    private boolean spongetools$wrap$canSurvive(
            final LevelReader reader, final BlockPos pos,
            final Operation<Boolean> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.CAN_SURVIVE);
        return callback == null
                ? original.call(reader, pos)
                : callback.call(
                        this,
                        args -> original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position())
                                ),
                        new BlockStateArgs.Locatable<>(
                                Converter.asSponge(reader),
                                Converter.asSponge(pos)
                                )
                        );
    }

    @WrapMethod(method = "getCloneItemStack")
    private ItemStack spongetools$wrap$getCloneItemStack(
            final LevelReader reader, final BlockPos pos, final boolean data,
            final Operation<ItemStack> original
    ) {
        final var callback = this.spongetools$impl$callback(BlockStateBehaviours.CLONE_ITEM);
        return callback == null
                ? original.call(reader, pos, data)
                : Converter.asVanilla(callback.call(
                        this,
                        args -> Converter.asSponge(original.call(
                                Converter.asVanilla(args.volume()),
                                Converter.asVanilla(args.position()),
                                data
                                )),
                        new BlockStateArgs.CloneItem(
                                Converter.asSponge(reader),
                                Converter.asSponge(pos),
                                data
                                )
                        ));
    }

    @Unique
    private <R, A extends BehaviourArgs> @Nullable BehaviourCallback<BlockStateExtension, R, A> spongetools$impl$callback(
            final BehaviourType<? extends Behaviour<R, A>> type
    ) {
        return this.spongetools$callbacks == null ? null : this.spongetools$callbacks.callbackOrNull(BehaviourLayer.TOP, type);
    }
}
