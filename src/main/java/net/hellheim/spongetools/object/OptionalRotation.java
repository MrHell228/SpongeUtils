package net.hellheim.spongetools.object;

import java.util.Objects;
import java.util.OptionalDouble;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.math.vector.Vector3d;

import net.hellheim.spongetools.math.optional.vector.OptionalVector3d;

public record OptionalRotation(OptionalVector3d rotation) {
	
	public OptionalRotation(final OptionalVector3d rotation) {
		this.rotation = Objects.requireNonNull(rotation, "rotation");
	}
	
	public static OptionalRotation of(final OptionalVector3d rotation) {
		return new OptionalRotation(rotation);
	}
	
	public static OptionalRotation ofPitch(final double pitch) {
		return OptionalRotation.of(OptionalVector3d.x(pitch));
	}
	
	public static OptionalRotation ofYaw(final double yaw) {
		return OptionalRotation.of(OptionalVector3d.y(yaw));
	}
	
	public static OptionalRotation of(final double pitch, final double yaw) {
		return OptionalRotation.of(OptionalVector3d.xy(pitch, yaw));
	}
	
	public static OptionalRotation of(final double pitch, final double yaw, final double roll) {
		return OptionalRotation.of(OptionalVector3d.of(pitch, yaw, roll));
	}
	
	public Vector3d transform(final Vector3d rotation) {
		return this.rotation.set(rotation);
	}
	
	public Vector3d transform(final Entity entity) {
		return this.transform(entity.rotation());
	}
	
	public void apply(final Entity entity) {
		entity.setRotation(this.transform(entity));
	}
	
	public OptionalDouble pitch() {
		return this.rotation.x();
	}
	
	public OptionalDouble yaw() {
		return this.rotation.y();
	}
	
	public OptionalDouble roll() {
		return this.rotation.z();
	}
}
