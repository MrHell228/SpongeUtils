package net.hellheim.spongetools.mixin.world.phys;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityHitResult.class)
public abstract class EntityHitResultMixin implements HitResult.EntityHitResult {

    @Shadow @Final private net.minecraft.world.entity.Entity entity;

    @Override
    public Entity entity() {
        return Converter.asSponge(this.entity);
    }
}
