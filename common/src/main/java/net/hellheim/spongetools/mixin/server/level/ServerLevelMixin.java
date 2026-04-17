package net.hellheim.spongetools.mixin.server.level;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @WrapOperation(
            method = "levelEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/players/PlayerList;broadcast(Lnet/minecraft/world/entity/player/Player;DDDDLnet/minecraft/resources/ResourceKey;Lnet/minecraft/network/protocol/Packet;)V"
            )
    )
    private void spongetools$sendBlockBreakEffectsToBreaker(
            final PlayerList instance, final @Nullable Player except,
            final double x, final double y, final double z, final double distance,
            final ResourceKey<Level> dimension, final Packet<?> packet,
            final Operation<Void> original,
            final @Nullable Entity source, final int eventType,
            final BlockPos pos, final int data
    ) {
        if (eventType == LevelEvent.PARTICLES_DESTROY_BLOCK && SpongeToolsPlugin.customMiningEnabled()) {
            final BlockState brokenState = Block.stateById(data);
            final BlockState networkState = FakeableNetworkValueBridge.asNetworkValue(brokenState);
            final Packet<?> finalPacket;
            if (brokenState != networkState) {
                finalPacket = new ClientboundLevelEventPacket(eventType, pos, Block.getId(networkState), false);
                // Idea to test: Get vanilla BlockState per wanted BreakSound and send it instead.
                // Problem: What to do with bad particles from that BlockState?
                // Problem: There may be no states for wanted break sound, so fallback to current way?
                // Sounds like a bad idea already...
                final SoundType sound = brokenState.getSoundType();
                if (sound.getBreakSound() != networkState.getSoundType().getBreakSound()) {
                    // Copied over from LevelEventHandler#levelEvent for LevelEvent.PARTICLES_DESTROY_BLOCK
                    // Sound from networkState is still played. Is this a problem?
                    ((ServerLevel) (Object) this).playSound(null, pos, sound.getBreakSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                }
            } else {
                finalPacket = packet;
            }
            original.call(instance, null, x, y, z, distance, dimension, finalPacket);
        } else {
            original.call(instance, except, x, y, z, distance, dimension, packet);
        }
    }
}
