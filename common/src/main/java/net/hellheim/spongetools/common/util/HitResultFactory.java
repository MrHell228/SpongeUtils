package net.hellheim.spongetools.common.util;

import java.util.Objects;

import org.spongepowered.api.entity.Entity;

import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.HitResult.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class HitResultFactory implements HitResult.Factory {
	
	@Override
	public EntityHitResult entity(final Entity entity, final double hitX, final double hitY, final double hitZ) {
		return Converter.asSponge(new net.minecraft.world.phys.EntityHitResult(
				Converter.asVanilla(Objects.requireNonNull(entity, "entity")),
				new Vec3(hitX, hitY, hitZ)
				));
	}
}
