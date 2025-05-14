package net.hellheim.spongetools.resourcepack;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.Direction;
import org.spongepowered.math.vector.Vector3d;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.MathCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

public final class ModelPart {
	
	public static final boolean DEFAULT_SHADE = true;
	public static final int DEFAULT_LIGHT_EMISSION = 0;
	public static final Codec<ModelPart> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					MathCodecs.VECTOR3D.validate(ModelPart::validatePoint).fieldOf("from").forGetter(ModelPart::from),
					MathCodecs.VECTOR3D.validate(ModelPart::validatePoint).fieldOf("to").forGetter(ModelPart::from),
					ModelPartRotation.CODEC.fieldOf("rotation").forGetter(ModelPart::rotation),
					Codec.BOOL.optionalFieldOf("shade", ModelPart.DEFAULT_SHADE).forGetter(ModelPart::shade),
					Codec.INT.validate(ModelPart::validateLight).optionalFieldOf("light_emission", ModelPart.DEFAULT_LIGHT_EMISSION).forGetter(ModelPart::lightEmission),
					Codec.unboundedMap(SpongeCodecs.CARDINAL_DIRECTION, ModelPartFace.CODEC).optionalFieldOf("faces", Map.of()).forGetter(ModelPart::faces)
					).apply(instance, ModelPart::new));
	
	private final Vector3d from;
	private final Vector3d to;
	private final ModelPartRotation rotation;
	private final boolean shade;
	private final int lightEmission;
	private final Map<Direction, ModelPartFace> faces;
	
	private ModelPart(
		final Vector3d from, final Vector3d to, final ModelPartRotation rotation,
		final boolean shade, final int lightEmission, final Map<Direction, ModelPartFace> faces
	) {
		this.from = from;
		this.to = to;
		this.rotation = rotation;
		this.shade = shade;
		this.lightEmission = lightEmission;
		this.faces = Map.copyOf(faces);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	private static DataResult<Vector3d> validatePoint(final Vector3d vec) {
		return ModelPart.validatePointValue(vec, vec.x())
				.flatMap($ -> ModelPart.validatePointValue(vec, vec.y()))
				.flatMap($ -> ModelPart.validatePointValue(vec, vec.z()));
	}
	
	private static DataResult<Vector3d> validatePointValue(final Vector3d vec, final double value) {
		return value < -16.0D || value > 32.0D
				? DataResult.error(() -> "Point values must be in [-16;32] range: " + vec.toString())
				: DataResult.success(vec);
	}
	
	private static DataResult<Integer> validateLight(final int lightEmission) {
		return lightEmission < 0 || lightEmission > 15
				? DataResult.error(() -> "Light emission must be between 0 and 15: " + lightEmission)
				: DataResult.success(lightEmission);
	}
	
	public Vector3d from() {
		return this.from;
	}
	
	public Vector3d to() {
		return this.to;
	}
	
	public ModelPartRotation rotation() {
		return this.rotation;
	}
	
	public boolean shade() {
		return this.shade;
	}
	
	public int lightEmission() {
		return this.lightEmission;
	}
	
	public Map<Direction, ModelPartFace> faces() {
		return this.faces;
	}
	
	public static final class Builder implements
			org.spongepowered.api.util.Builder<ModelPart, Builder>,
			CopyableBuilder<ModelPart, Builder> {
		
		private @Nullable Vector3d from;
		private @Nullable Vector3d to;
		private @Nullable ModelPartRotation rotation;
		private boolean shade;
		private int lightEmission;
		private final Map<Direction, ModelPartFace> faces = new EnumMap<>(Direction.class);
		
		private Builder() {
			this.reset();
		}
		
		private Vector3d validatePoint(final Vector3d vec) {
			return ModelPart.validatePoint(vec).getOrThrow(IllegalArgumentException::new);
		}
		
		private int validateLight(final int lightEmission) {
			return ModelPart.validateLight(lightEmission).getOrThrow(IllegalArgumentException::new);
		}
		
		public Builder from(final Vector3d from) {
			this.from = this.validatePoint(Objects.requireNonNull(from, "from"));
			return this;
		}
		
		public Builder to(final Vector3d to) {
			this.to = this.validatePoint(Objects.requireNonNull(to, "to"));
			return this;
		}
		
		public Builder rotation(final ModelPartRotation rotation) {
			this.rotation = Objects.requireNonNull(rotation, "rotation");
			return this;
		}
		
		public Builder shade(final boolean shade) {
			this.shade = shade;
			return this;
		}
		
		public Builder lightEmission(final int lightEmission) {
			this.lightEmission = this.validateLight(lightEmission);
			return this;
		}
		
		public Builder face(final Direction direction, final ModelPartFace face) {
			Objects.requireNonNull(direction, "direction");
			Objects.requireNonNull(face, "face");
			if (!direction.isCardinal()) {
				throw new IllegalArgumentException("direction must be cardinal");
			}
			
			this.faces.put(direction, face);
			return this;
		}
		
		public Builder faces(final Map<Direction, ModelPartFace> faces) {
			Objects.requireNonNull(faces, "faces").forEach(this::face);
			return this;
		}
		
		@Override
		public Builder from(final ModelPart element) {
			Objects.requireNonNull(element, "element");
			return this
					.from(element.from())
					.to(element.to())
					.rotation(element.rotation())
					.shade(element.shade())
					.lightEmission(element.lightEmission())
					.faces(element.faces());
		}
		
		@Override
		public Builder reset() {
			this.from = null;
			this.to = null;
			this.rotation = null;
			this.shade = ModelPart.DEFAULT_SHADE;
			this.lightEmission = ModelPart.DEFAULT_LIGHT_EMISSION;
			this.faces.clear();
			return this;
		}
		
		@Override
		public ModelPart build() {
			if (this.from == null) {
				throw new IllegalStateException("from must be set");
			} else if (this.to == null) {
				throw new IllegalStateException("to must be set");
			} else if (this.rotation == null) {
				throw new IllegalStateException("rotation must be set");
			}
			
			return new ModelPart(
					this.from, this.to, this.rotation, this.shade, this.lightEmission, this.faces);
		}
	}
}
