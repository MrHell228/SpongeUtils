package net.hellheim.spongetools.mixin.world.entity;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // TODO redirect Block methods in getBlockSpeedFactor & getBlockJumpFactor to BlockStateBaseBridge
}
