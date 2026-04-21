package net.hellheim.spongetools.common.builder;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.util.Direction;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.HitResult.BlockHitResult.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class BlockHitResultBuilder implements HitResult.BlockHitResult.Builder {
	
	private @Nullable Vec3 hitPosition;
	private @Nullable BlockPos blockPosition;
	private net.minecraft.core.@Nullable Direction direction;
	private boolean miss;
	private boolean worldBorder;
	private boolean inside;
	
	public BlockHitResultBuilder() {
		this.reset();
	}
	
	@Override
	public Builder hitPosition(final double x, final double y, final double z) {
		this.hitPosition = new Vec3(x, y, z);
		return this;
	}
	
	@Override
	public Builder blockPosition(final int x, final int y, final int z) {
		this.blockPosition = new BlockPos(x, y, z);
		return this;
	}
	
	@Override
	public Builder direction(final Direction direction) {
		this.direction = Converter.asVanilla(direction);
		return this;
	}
	
	@Override
	public Builder miss(final boolean miss) {
		this.miss = miss;
		return this;
	}
	
	@Override
	public Builder inside(final boolean inside) {
		this.inside = inside;
		return this;
	}
	
	@Override
	public Builder worldBorder(final boolean worldBorder) {
		this.worldBorder = worldBorder;
		return this;
	}
	
	@Override
	public Builder from(final HitResult.BlockHitResult value) {
		this.hitPosition = Converter.asVanilla(value.hitPosition());
		this.blockPosition = Converter.asVanilla(value.blockPosition());
		this.direction = Converter.asVanilla(value.direction());
		this.miss = value.miss();
		this.worldBorder = value.worldBorder();
		this.inside = value.inside();
		return this;
	}
	
	@Override
	public Builder reset() {
		this.hitPosition = null;
		this.blockPosition = null;
		this.direction = null;
		this.miss = false;
		this.worldBorder = false;
		this.inside = false;
		return this;
	}
	
	@Override
	public HitResult.BlockHitResult build() {
		if (this.hitPosition == null) {
			throw new IllegalStateException("hitPosition must be set");
		} else if (this.blockPosition == null) {
			throw new IllegalStateException("blockPosition must be set");
		} else if (this.direction == null) {
			throw new IllegalStateException("direction must be set");
		}
		
		if (this.miss) {
			return Converter.asSponge(BlockHitResult.miss(this.hitPosition, this.direction, this.blockPosition));
		} else {
			return Converter.asSponge(new BlockHitResult(this.hitPosition, this.direction, this.blockPosition, this.inside, this.worldBorder));
		}
	}
}
