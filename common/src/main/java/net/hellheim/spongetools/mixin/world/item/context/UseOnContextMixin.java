package net.hellheim.spongetools.mixin.world.item.context;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.math.vector.Vector3i;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(UseOnContext.class)
public abstract class UseOnContextMixin implements UseContext {

    @Shadow @Final @Nullable private net.minecraft.world.entity.player.Player player;
    @Shadow @Final private InteractionHand hand;
    @Shadow @Final private BlockHitResult hitResult;
    @Shadow @Final private Level level;
    @Shadow @Final private net.minecraft.world.item.ItemStack itemStack;

    @Shadow public abstract BlockPos shadow$getClickedPos();
    @Shadow public abstract net.minecraft.core.Direction shadow$getClickedFace();
    @Shadow public abstract float shadow$getRotation();
    @Shadow public abstract net.minecraft.core.Direction shadow$getHorizontalDirection();
    @Shadow public abstract boolean shadow$isSecondaryUseActive();

    @Override
    public Optional<Player> player() {
        return Optional.ofNullable(Converter.asSponge(this.player));
    }

    @Override
    public HandType hand() {
        return Converter.asSponge(this.hand);
    }

    @Override
    public HitResult.BlockHitResult hit() {
        return Converter.asSponge(this.hitResult);
    }

    @Override
    public World<?, ?> world() {
        return Converter.asSponge(this.level);
    }

    @Override
    public ItemStack item() {
        return Converter.asSponge(this.itemStack);
    }

    @Override
    public Vector3i clickedPosition() {
        return Converter.asSponge(this.shadow$getClickedPos());
    }

    @Override
    public Direction clickedDirection() {
        return Converter.asSponge(this.shadow$getClickedFace()).opposite();
    }

    @Override
    public double rotation() {
        return this.shadow$getRotation();
    }

    @Override
    public Direction horizontalDirection() {
        return Converter.asSponge(this.shadow$getHorizontalDirection()).opposite();
    }

    @Override
    public boolean isSecondary() {
        return this.shadow$isSecondaryUseActive();
    }
}
