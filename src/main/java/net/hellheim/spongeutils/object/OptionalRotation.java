package net.hellheim.spongeutils.object;

import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.math.vector.Vector3d;

public class OptionalRotation extends OptionalVector3d {
	
	protected OptionalRotation(
		final Optional<Double> pitch, final Optional<Double> yaw, final Optional<Double> roll
	) {
		super(pitch, yaw, roll);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static OptionalRotation ofPitch(final double pitch) {
		return OptionalRotation.builder().pitch(pitch).build();
	}
	
	public static OptionalRotation ofYaw(final double yaw) {
		return OptionalRotation.builder().yaw(yaw).build();
	}
	
	public static OptionalRotation of(final double pitch, final double yaw) {
		return OptionalRotation.builder().pitch(pitch).yaw(yaw).build();
	}
	
	public static OptionalRotation of(final double pitch, final double yaw, final double roll) {
		return OptionalRotation.builder().pitch(pitch).yaw(yaw).roll(roll).build();
	}
	
	public Vector3d transform(final Entity entity) {
		return this.transform(entity.rotation());
	}
	
	public void apply(final Entity entity) {
		entity.setRotation(this.transform(entity));
	}
	
	public Optional<Double> pitch() {
		return this.x;
	}
	
	public Optional<Double> yaw() {
		return this.y;
	}
	
	public Optional<Double> roll() {
		return this.z;
	}
	
	public static class Builder extends OptionalVector3d.Builder {
		
		@Override
		public Builder x(final @Nullable Double x) {
			super.x(x);
			return this;
		}
		
		@Override
		public Builder y(final @Nullable Double y) {
			super.y(y);
			return this;
		}
		
		@Override
		public Builder z(final @Nullable Double z) {
			super.z(z);
			return this;
		}
		
		public Builder pitch(final @Nullable Double pitch) {
			return this.x(pitch);
		}
		
		public Builder yaw(final @Nullable Double yaw) {
			return this.y(yaw);
		}
		
		public Builder roll(final @Nullable Double roll) {
			return this.z(roll);
		}
		
		@Override
		public Builder reset() {
			super.reset();
			return this;
		}
		
		@Override
		public OptionalRotation build() {
			return new OptionalRotation(
					Optional.ofNullable(this.x),
					Optional.ofNullable(this.y),
					Optional.ofNullable(this.z)
					);
		}
	}
}
