package net.hellheim.spongetools.resourcepack;

import java.util.Objects;

import org.spongepowered.api.util.Axis;
import org.spongepowered.math.vector.Vector3d;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.MathCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

public final class ModelPartRotation {
	
	public static final Codec<ModelPartRotation> CODEC = RecordCodecBuilder.<ModelPartRotation>create(
			instance -> instance.group(
					MathCodecs.VECTOR3D.fieldOf("origin").forGetter(ModelPartRotation::origin),
					SpongeCodecs.AXIS.fieldOf("axis").forGetter(ModelPartRotation::axis),
					Codec.DOUBLE.fieldOf("angle").forGetter(ModelPartRotation::angle),
					Codec.BOOL.fieldOf("rescale").forGetter(ModelPartRotation::rescale)
					).apply(instance, ModelPartRotation::new))
			.validate(ModelPartRotation::validate);
	
	private final Vector3d origin;
	private final Axis axis;
	private final double angle;
	private final boolean rescale;
	
	private ModelPartRotation(
		final Vector3d origin, final Axis axis, final double angle, final boolean rescale
	) {
		this.origin = origin;
		this.axis = axis;
		this.angle = angle;
		this.rescale = rescale;
	}
	
	public static ModelPartRotation of(
		final Vector3d origin, final Axis axis, final double angle, final boolean rescale
	) {
		Objects.requireNonNull(origin, "origin");
		Objects.requireNonNull(axis, "axis");
		final ModelPartRotation rotation = new ModelPartRotation(origin, axis, angle, rescale);
		rotation.validate().ifError(error -> {
			throw new IllegalArgumentException(error.message());
		});
		return rotation;
	}
	
	private DataResult<ModelPartRotation> validate() {
		if (this.angle != 0.0F && Math.abs(this.angle) != 22.5D && Math.abs(this.angle) != 45.0D) {
			return DataResult.error(() ->
					"Invalid angle " + this.angle + ", only -45/-22.5/0/22.5/45 allowed");
		} else {
			return DataResult.success(this);
		}
	}
	
	public Vector3d origin() {
		return this.origin;
	}
	
	public Axis axis() {
		return this.axis;
	}
	
	public double angle() {
		return this.angle;
	}
	
	public boolean rescale() {
		return this.rescale;
	}
	
	public ModelPartRotation withOrigin(final Vector3d origin) {
		return ModelPartRotation.of(origin, this.axis, this.angle, this.rescale);
	}
	
	public ModelPartRotation withAxis(final Axis axis) {
		return ModelPartRotation.of(this.origin, axis, this.angle, this.rescale);
	}
	
	public ModelPartRotation withAngle(final double angle) {
		return ModelPartRotation.of(this.origin, this.axis, angle, this.rescale);
	}
	
	public ModelPartRotation withRescale(final boolean rescale) {
		return ModelPartRotation.of(this.origin, this.axis, this.angle, rescale);
	}
}
