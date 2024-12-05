package net.hellheim.spongeutils.object;

import java.util.Optional;
import java.util.function.UnaryOperator;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.vector.Vector3d;

public class OptionalVector3d {
	
	protected final Optional<Double> x;
	protected final Optional<Double> y;
	protected final Optional<Double> z;
	protected final UnaryOperator<Vector3d> transformer;
	
	protected OptionalVector3d(
		final Optional<Double> x, final Optional<Double> y, final Optional<Double> z
	) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		if (x.isPresent()) {
			if (y.isPresent()) {
				if (z.isPresent()) {
					final double x0 = x.get(), y0 = y.get(), z0 = z.get();
					final Vector3d result = new Vector3d(x0, y0, z0);
					this.transformer = v -> result;
				} else {
					final double x0 = x.get(), y0 = y.get();
					this.transformer = v -> new Vector3d(x0, y0, v.z());
				}
			} else if (z.isPresent()) {
				final double x0 = x.get(), z0 = z.get();
				this.transformer = v -> new Vector3d(x0, v.y(), z0);
			} else {
				final double x0 = x.get();
				this.transformer = v -> new Vector3d(x0, v.y(), v.z());
			}
		} else if (y.isPresent()) {
			if (z.isPresent()) {
				final double y0 = y.get(), z0 = z.get();
				this.transformer = v -> new Vector3d(v.x(), y0, z0);
			} else {
				final double y0 = y.get();
				this.transformer = v -> new Vector3d(v.x(), y0, v.z());
			}
		} else if (z.isPresent()) {
			final double z0 = z.get();
			this.transformer = v -> new Vector3d(v.x(), v.y(), z0);
		} else {
			this.transformer = v -> v;
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static OptionalVector3d empty() {
		return OptionalVector3d.builder().build();
	}
	
	public static OptionalVector3d x(final double x) {
		return OptionalVector3d.builder().x(x).build();
	}
	
	public static OptionalVector3d y(final double y) {
		return OptionalVector3d.builder().y(y).build();
	}
	
	public static OptionalVector3d z(final double z) {
		return OptionalVector3d.builder().z(z).build();
	}
	
	public static OptionalVector3d xy(final double x, final double y) {
		return OptionalVector3d.builder().x(x).y(y).build();
	}
	
	public static OptionalVector3d xz(final double x, final double z) {
		return OptionalVector3d.builder().x(x).z(z).build();
	}
	
	public static OptionalVector3d yz(final double y, final double z) {
		return OptionalVector3d.builder().y(y).z(z).build();
	}
	
	public static OptionalVector3d xyz(final double x, final double y, final double z) {
		return OptionalVector3d.builder().x(x).y(y).z(z).build();
	}
	
	public Vector3d transform(final Vector3d v) {
		return this.transformer.apply(v);
	}
	
	public Optional<Double> x() {
		return this.x;
	}
	
	public Optional<Double> y() {
		return this.y;
	}
	
	public Optional<Double> z() {
		return this.z;
	}
	
	public static class Builder implements org.spongepowered.api.util.Builder<OptionalVector3d, Builder> {
		
		protected @Nullable Double x;
		protected @Nullable Double y;
		protected @Nullable Double z;
		
		public Builder x(final @Nullable Double x) {
			this.x = x;
			return this;
		}
		
		public Builder y(final @Nullable Double y) {
			this.y = y;
			return this;
		}
		
		public Builder z(final @Nullable Double z) {
			this.z = z;
			return this;
		}
		
		@Override
		public Builder reset() {
			this.x = null;
			this.y = null;
			this.z = null;
			return this;
		}
		
		@Override
		public OptionalVector3d build() {
			return new OptionalVector3d(
					Optional.ofNullable(this.x),
					Optional.ofNullable(this.y),
					Optional.ofNullable(this.z)
					);
		}
	}
}
