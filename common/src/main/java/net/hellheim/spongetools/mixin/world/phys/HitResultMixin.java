package net.hellheim.spongetools.mixin.world.phys;

import net.hellheim.spongetools.common.util.Converter;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.math.vector.Vector3d;

@Mixin(HitResult.class)
public abstract class HitResultMixin implements net.hellheim.spongetools.custom.behaviour.util.HitResult {

    @Shadow @Final protected Vec3 location;

    @Shadow public abstract HitResult.Type shadow$getType();

    @Override
    public Vector3d hitPosition() {
        return Converter.asSponge(this.location);
    }

    @Override
    public boolean miss() {
        return this.shadow$getType() == HitResult.Type.MISS;
    }
}
