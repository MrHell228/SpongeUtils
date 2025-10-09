package net.hellheim.spongetools.resourcepack;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.rotation.Rotation;
import org.spongepowered.math.matrix.Matrix2d;
import org.spongepowered.math.vector.Vector2d;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.MathCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.util.GeomUtil;

public final class ModelPartFace {
	
	public static final int DEFAULT_TINT = -1;
	public static final Supplier<Rotation> DEFAULT_ROTATION = GeomUtil.ROT_0;
	public static final Codec<ModelPartFace> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(
			instance -> instance.group(
					TextureSlot.CODEC_HASHED.fieldOf("texture").forGetter(ModelPartFace::texture),
					MathCodecs.MATRIX2D.optionalFieldOf("uv").forGetter(ModelPartFace::uv),
					SpongeCodecs.CARDINAL_DIRECTION.optionalFieldOf("cullface").forGetter(ModelPartFace::cullface),
					SpongeCodecs.ROTATION_BY_ANGLE.optionalFieldOf("rotation", ModelPartFace.DEFAULT_ROTATION.get()).forGetter(ModelPartFace::rotation),
					Codec.INT.optionalFieldOf("tintindex", ModelPartFace.DEFAULT_TINT).forGetter(ModelPartFace::tintIndex)
					).apply(instance, ModelPartFace::new)));
	
	private final TextureSlot texture;
	private final Optional<Matrix2d> uv;
	private final Optional<Direction> cullface;
	private final Rotation rotation;
	private final int tintIndex;
	
	private ModelPartFace(
		final TextureSlot texture,
		final Optional<Matrix2d> uv, final Optional<Direction> cullface,
		final Rotation rotation, final int tintIndex
	) {
		this.texture = texture;
		this.uv = uv;
		this.cullface = cullface;
		this.rotation = rotation;
		this.tintIndex = tintIndex;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public TextureSlot texture() {
		return this.texture;
	}
	
	public Optional<Matrix2d> uv() {
		return this.uv;
	}
	
	public Optional<Direction> cullface() {
		return this.cullface;
	}
	
	public Rotation rotation() {
		return this.rotation;
	}
	
	public int tintIndex() {
		return this.tintIndex;
	}
	
	public static final class Builder implements
			org.spongepowered.api.util.Builder<ModelPartFace, Builder>,
			CopyableBuilder<ModelPartFace, Builder> {
		
		private @Nullable Matrix2d uv;
		private @Nullable TextureSlot texture;
		private @Nullable Direction cullface;
		private @Nullable Rotation rotation;
		private int tintIndex;
		
		private Builder() {
			this.reset();
		}
		
		public Builder texture(final String textureId) {
			return this.texture(TextureSlot.of(textureId));
		}
		
		public Builder texture(final TextureSlot texture) {
			this.texture = Objects.requireNonNull(texture, "texture");
			return this;
		}
		
		public Builder uv(final double x1, final double y1, final double x2, final double y2) {
			return this.uv(new Matrix2d(x1, y1, x2, y2));
		}
		
		public Builder uv(final Vector2d u, final Vector2d v) {
			return this.uv(new Matrix2d(u.x(), u.y(), v.x(), v.y()));
		}
		
		public Builder uv(final Matrix2d uv) {
			this.uv = Objects.requireNonNull(uv, "uv");
			return this;
		}
		
		public Builder cullface(final Direction cullface) {
			Objects.requireNonNull(cullface, "cullface");
			if (!cullface.isCardinal()) {
				throw new IllegalArgumentException("cullface must be cardinal");
			}
			
			this.cullface = cullface;
			return this;
		}
		
		public Builder rotation(final Rotation rotation) {
			this.rotation = Objects.requireNonNull(rotation, "rotation");
			return this;
		}
		
		public Builder rotation(final Supplier<? extends Rotation> rotationSupplier) {
			return this.rotation(Objects.requireNonNull(rotationSupplier, "rotationSupplier").get());
		}
		
		public Builder tint(final int tintIndex) {
			this.tintIndex = tintIndex;
			return this;
		}
		
		@Override
		public Builder from(final ModelPartFace face) {
			Objects.requireNonNull(face, "face");
			this.reset();
			face.uv().ifPresent(this::uv);
			face.cullface.ifPresent(this::cullface);
			return this
					.texture(face.texture())
					.rotation(face.rotation())
					.tint(face.tintIndex());
		}
		
		@Override
		public Builder reset() {
			this.texture = null;
			this.uv = null;
			this.cullface = null;
			this.rotation = ModelPartFace.DEFAULT_ROTATION.get();
			this.tintIndex = ModelPartFace.DEFAULT_TINT;
			return this;
		}
		
		@Override
		public ModelPartFace build() {
			return new ModelPartFace(
					this.texture,
					Optional.ofNullable(this.uv), Optional.ofNullable(this.cullface),
					this.rotation, this.tintIndex);
		}
	}
}
