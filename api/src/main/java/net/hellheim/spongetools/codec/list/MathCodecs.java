package net.hellheim.spongetools.codec.list;

import java.util.List;

import org.spongepowered.api.util.Transform;
import org.spongepowered.math.imaginary.Complexd;
import org.spongepowered.math.imaginary.Complexf;
import org.spongepowered.math.imaginary.Quaterniond;
import org.spongepowered.math.imaginary.Quaternionf;
import org.spongepowered.math.matrix.Matrix2d;
import org.spongepowered.math.matrix.Matrix2f;
import org.spongepowered.math.matrix.Matrix3d;
import org.spongepowered.math.matrix.Matrix3f;
import org.spongepowered.math.matrix.Matrix4d;
import org.spongepowered.math.matrix.Matrix4f;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;
import org.spongepowered.math.vector.Vector4d;
import org.spongepowered.math.vector.Vector4f;
import org.spongepowered.math.vector.Vector4i;
import org.spongepowered.math.vector.Vector4l;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class MathCodecs {
	
	public static final Codec<Vector2i> VECTOR2I = list(Codec.INT, 2).xmap(
			l -> new Vector2i(l.get(0), l.get(1)), v -> List.of(v.x(), v.y()));
	public static final Codec<Vector3i> VECTOR3I = list(Codec.INT, 3).xmap(
			l -> new Vector3i(l.get(0), l.get(1), l.get(2)), v -> List.of(v.x(), v.y(), v.z()));
	public static final Codec<Vector4i> VECTOR4I = list(Codec.INT, 4).xmap(
			l -> new Vector4i(l.get(0), l.get(1), l.get(2), l.get(3)), v -> List.of(v.x(), v.y(), v.z(), v.w()));
	
	public static final Codec<Vector2l> VECTOR2L = list(Codec.LONG, 2).xmap(
			l -> new Vector2l(l.get(0), l.get(1)), v -> List.of(v.x(), v.y()));
	public static final Codec<Vector3l> VECTOR3L = list(Codec.LONG, 3).xmap(
			l -> new Vector3l(l.get(0), l.get(1), l.get(2)), v -> List.of(v.x(), v.y(), v.z()));
	public static final Codec<Vector4l> VECTOR4L = list(Codec.LONG, 4).xmap(
			l -> new Vector4l(l.get(0), l.get(1), l.get(2), l.get(3)), v -> List.of(v.x(), v.y(), v.z(), v.w()));
	
	public static final Codec<Vector2f> VECTOR2F = list(Codec.FLOAT, 2).xmap(
			l -> new Vector2f(l.get(0), l.get(1)), v -> List.of(v.x(), v.y()));
	public static final Codec<Vector3f> VECTOR3F = list(Codec.FLOAT, 3).xmap(
			l -> new Vector3f(l.get(0), l.get(1), l.get(2)), v -> List.of(v.x(), v.y(), v.z()));
	public static final Codec<Vector4f> VECTOR4F = list(Codec.FLOAT, 4).xmap(
			l -> new Vector4f(l.get(0), l.get(1), l.get(2), l.get(3)), v -> List.of(v.x(), v.y(), v.z(), v.w()));
	
	public static final Codec<Vector2d> VECTOR2D = list(Codec.DOUBLE, 2).xmap(
			l -> new Vector2d(l.get(0), l.get(1)), v -> List.of(v.x(), v.y()));
	public static final Codec<Vector3d> VECTOR3D = list(Codec.DOUBLE, 3).xmap(
			l -> new Vector3d(l.get(0), l.get(1), l.get(2)), v -> List.of(v.x(), v.y(), v.z()));
	public static final Codec<Vector4d> VECTOR4D = list(Codec.DOUBLE, 4).xmap(
			l -> new Vector4d(l.get(0), l.get(1), l.get(2), l.get(3)), v -> List.of(v.x(), v.y(), v.z(), v.w()));
	
	public static final Codec<Matrix2f> MATRIX2F = list(Codec.FLOAT, 4).xmap(
			l -> Matrix2f.from(
					l.get(0), l.get(1),
					l.get(2), l.get(3)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1),
					m.get(1, 0), m.get(1, 1)
					));
	public static final Codec<Matrix3f> MATRIX3F = list(Codec.FLOAT, 9).xmap(
			l -> Matrix3f.from(
					l.get(0), l.get(1), l.get(2),
					l.get(3), l.get(4), l.get(5),
					l.get(6), l.get(7), l.get(9)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1), m.get(0, 2),
					m.get(1, 0), m.get(1, 1), m.get(1, 2),
					m.get(2, 0), m.get(2, 1), m.get(2, 2)
					));
	public static final Codec<Matrix4f> MATRIX4F = list(Codec.FLOAT, 16).xmap(
			l -> Matrix4f.from(
					l.get(0),  l.get(1),  l.get(2),  l.get(3),
					l.get(4),  l.get(5),  l.get(6),  l.get(7),
					l.get(8),  l.get(9),  l.get(10), l.get(11),
					l.get(12), l.get(13), l.get(14), l.get(15)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(0, 3),
					m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(1, 3),
					m.get(2, 0), m.get(2, 1), m.get(2, 2), m.get(2, 3),
					m.get(3, 0), m.get(3, 1), m.get(3, 2), m.get(3, 3)
					));
	
	public static final Codec<Matrix2d> MATRIX2D = list(Codec.DOUBLE, 4).xmap(
			l -> Matrix2d.from(
					l.get(0), l.get(1),
					l.get(2), l.get(3)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1),
					m.get(1, 0), m.get(1, 1)
					));
	public static final Codec<Matrix3d> MATRIX3D = list(Codec.DOUBLE, 9).xmap(
			l -> Matrix3d.from(
					l.get(0), l.get(1), l.get(2),
					l.get(3), l.get(4), l.get(5),
					l.get(6), l.get(7), l.get(9)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1), m.get(0, 2),
					m.get(1, 0), m.get(1, 1), m.get(1, 2),
					m.get(2, 0), m.get(2, 1), m.get(2, 2)
					));
	public static final Codec<Matrix4d> MATRIX4D = list(Codec.DOUBLE, 16).xmap(
			l -> Matrix4d.from(
					l.get(0),  l.get(1),  l.get(2),  l.get(3),
					l.get(4),  l.get(5),  l.get(6),  l.get(7),
					l.get(8),  l.get(9),  l.get(10), l.get(11),
					l.get(12), l.get(13), l.get(14), l.get(15)
					),
			m -> List.of(
					m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(0, 3),
					m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(1, 3),
					m.get(2, 0), m.get(2, 1), m.get(2, 2), m.get(2, 3),
					m.get(3, 0), m.get(3, 1), m.get(3, 2), m.get(3, 3)
					));
	
	public static final Codec<Complexf> COMPLEXF = list(Codec.FLOAT, 2).xmap(
			l -> Complexf.from(l.get(0), l.get(1)), c -> List.of(c.x(), c.y()));
	public static final Codec<Complexd> COMPLEXD = list(Codec.DOUBLE, 2).xmap(
			l -> Complexd.from(l.get(0), l.get(1)), c -> List.of(c.x(), c.y()));
	
	public static final Codec<Quaternionf> QUATERNIONF = list(Codec.FLOAT, 4).xmap(
			l -> Quaternionf.from(l.get(0), l.get(1), l.get(2), l.get(3)), c -> List.of(c.x(), c.y(), c.z(), c.w()));
	public static final Codec<Quaterniond> QUATERNIOND = list(Codec.DOUBLE, 4).xmap(
			l -> Quaterniond.from(l.get(0), l.get(1), l.get(2), l.get(3)), c -> List.of(c.x(), c.y(), c.z(), c.w()));
	
	public static final Codec<Transform> TRANSFORM = RecordCodecBuilder.create(
			instance -> instance.group(
					MathCodecs.VECTOR3D.optionalFieldOf("translation", Vector3d.ZERO).forGetter(Transform::position),
					MathCodecs.VECTOR3D.optionalFieldOf("rotation", Vector3d.ZERO).forGetter(Transform::rotation),
					MathCodecs.VECTOR3D.optionalFieldOf("scale", Vector3d.ONE).forGetter(Transform::scale)
					).apply(instance, Transform::of));
	
	private static <N extends Number> Codec<List<N>> list(final Codec<N> codec, final int size) {
		return ExtraCodecs.sizeLimitedCollection(codec.listOf(), size);
	}
	
	private MathCodecs() {
	}
}
