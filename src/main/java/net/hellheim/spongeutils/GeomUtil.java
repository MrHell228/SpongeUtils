package net.hellheim.spongeutils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.spongepowered.api.util.Axis;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.mirror.Mirror;
import org.spongepowered.api.util.rotation.Rotation;
import org.spongepowered.api.util.rotation.Rotations;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;

import net.hellheim.spongeutils.function.IntBiConsumer;
import net.hellheim.spongeutils.function.IntTriConsumer;

public final class GeomUtil {
	
	// Relies on implementation where these are enum values meaning they should always be available.
	public static final Rotation ROT_0 = Rotations.NONE.get();
	public static final Rotation ROT_90 = Rotations.CLOCKWISE_90.get();
	public static final Rotation ROT_180 = Rotations.CLOCKWISE_180.get();
	public static final Rotation ROT_270 = Rotations.COUNTERCLOCKWISE_90.get();
	private static final List<Rotation> ROTATION_VALUES =
			List.of(ROT_0, ROT_90, ROT_180, ROT_270);
	
	public static boolean is(final Supplier<Rotation> r1, final Rotation r2) {
		return GeomUtil.is(r1.get(), r2);
	}
	
	public static boolean is(final Rotation r1, final Supplier<Rotation> r2) {
		return GeomUtil.is(r1, r2.get());
	}
	
	public static boolean is(final Rotation r1, final Rotation r2) {
		return r1 == r2;
	}
	
	public static boolean is(final Supplier<Mirror> m1, final Mirror m2) {
		return GeomUtil.is(m1.get(), m2);
	}
	
	public static boolean is(final Mirror m1, final Supplier<Mirror> m2) {
		return GeomUtil.is(m1, m2.get());
	}
	
	public static boolean is(final Mirror m1, final Mirror m2) {
		return m1 == m2;
	}
	
	public static boolean is(final Axis a1, final Axis a2) {
		return a1 == a2;
	}
	
	public static boolean is(final Direction d1, final Direction d2) {
		return d1 == d2;
	}
	
	public static boolean isHorizontal(final Axis axis) {
		return  GeomUtil.is(axis, Axis.X) || GeomUtil.is(axis, Axis.Z);
	}
	
	public static boolean isHorizontal(final Direction direction) {
		return !direction.isUpright() && !GeomUtil.is(direction, Direction.NONE);
	}
	
	public static boolean is0(final Rotation rotation) {
		return GeomUtil.is(rotation, GeomUtil.ROT_0);
	}
	
	public static boolean is90(final Rotation rotation) {
		return GeomUtil.is(rotation, GeomUtil.ROT_90);
	}
	
	public static boolean is180(final Rotation rotation) {
		return GeomUtil.is(rotation, GeomUtil.ROT_180);
	}
	
	public static boolean is270(final Rotation rotation) {
		return GeomUtil.is(rotation, GeomUtil.ROT_270);
	}
	
	public static List<Rotation> rotations() {
		return ROTATION_VALUES;
	}
	
	public static Optional<Rotation> rotation(final Direction from, final Direction to) {
		if (!GeomUtil.isHorizontal(from)) {
			throw new IllegalArgumentException("'from' direction must be horizontal");
		}
		
		if (!GeomUtil.isHorizontal(to)) {
			throw new IllegalArgumentException("'to' direction must be horizontal");
		}
		
		if (GeomUtil.is(from, to)) {
			return Optional.of(GeomUtil.ROT_0);
		} else if (from.isOpposite(to)) {
			return Optional.of(GeomUtil.ROT_180);
		} else if (DIR_CLOCKWISE_90.get(from) == to) {
			return Optional.of(GeomUtil.ROT_90);
		} else if (DIR_COUNTERCLOCKWISE_90.get(from) == to) {
			return Optional.of(GeomUtil.ROT_270);
		}
		
		return Optional.empty();
	}
	
	public static Direction rotate(final Direction direction, final Rotation rotation) {
		if (!GeomUtil.isHorizontal(direction)) {
			throw new IllegalArgumentException("direction must be horizontal");
		}
		
		if (GeomUtil.is0(rotation)) {
			return direction;
		} else if (GeomUtil.is180(rotation)) {
			return direction.opposite();
		} else if (GeomUtil.is90(rotation)) {
			return DIR_CLOCKWISE_90.get(direction);
		} else if (GeomUtil.is270(rotation)) {
			return DIR_COUNTERCLOCKWISE_90.get(direction);
		}
		
		throw new AssertionError();
	}
	
	public static Vector2i rotate(final Vector2i vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector2i(vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector2i(-vector.x(), -vector.y());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector2i(-vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector2l rotate(final Vector2l vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector2l(vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector2l(-vector.x(), -vector.y());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector2l(-vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector2f rotate(final Vector2f vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector2f(vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector2f(-vector.x(), -vector.y());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector2f(-vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector2d rotate(final Vector2d vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector2d(vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector2d(-vector.x(), -vector.y());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector2d(-vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector3i rotate(final Vector3i vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector3i(vector.z(), vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector3i(-vector.x(), vector.y(), -vector.z());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector3i(-vector.z(), vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector3l rotate(final Vector3l vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector3l(vector.z(), vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector3l(-vector.x(), vector.y(), -vector.z());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector3l(-vector.z(), vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector3f rotate(final Vector3f vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector3f(vector.z(), vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector3f(-vector.x(), vector.y(), -vector.z());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector3f(-vector.z(), vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	public static Vector3d rotate(final Vector3d vector, final Rotation rotation) {
		if (GeomUtil.is0(rotation)) {
			return vector;
		} else if (GeomUtil.is90(rotation)) {
			return new Vector3d(vector.z(), vector.y(), -vector.x());
		} else if (GeomUtil.is180(rotation)) {
			return new Vector3d(-vector.x(), vector.y(), -vector.z());
		} else if (GeomUtil.is270(rotation)) {
			return new Vector3d(-vector.z(), vector.y(), vector.x());
		}
		
		throw new AssertionError();
	}
	
	
	
	private static final Map<Direction, Direction> DIR_CLOCKWISE_90 = new EnumMap<>(Direction.class);
	private static final Map<Direction, Direction> DIR_COUNTERCLOCKWISE_90 = new EnumMap<>(Direction.class);
	static {
		DIR_CLOCKWISE_90       .put(Direction.NORTH,           Direction.EAST);
		DIR_CLOCKWISE_90       .put(Direction.NORTH_NORTHEAST, Direction.EAST_SOUTHEAST);
		DIR_CLOCKWISE_90       .put(Direction.NORTHEAST,       Direction.SOUTHEAST);
		DIR_CLOCKWISE_90       .put(Direction.EAST_NORTHEAST,  Direction.SOUTH_SOUTHEAST);
		
		DIR_CLOCKWISE_90       .put(Direction.EAST,            Direction.SOUTH);
		DIR_CLOCKWISE_90       .put(Direction.EAST_SOUTHEAST,  Direction.SOUTH_SOUTHWEST);
		DIR_CLOCKWISE_90       .put(Direction.SOUTHEAST,       Direction.SOUTHWEST);
		DIR_CLOCKWISE_90       .put(Direction.SOUTH_SOUTHEAST, Direction.WEST_SOUTHWEST);
		
		DIR_CLOCKWISE_90       .put(Direction.SOUTH,           Direction.WEST);
		DIR_CLOCKWISE_90       .put(Direction.SOUTH_SOUTHWEST, Direction.WEST_NORTHWEST);
		DIR_CLOCKWISE_90       .put(Direction.SOUTHWEST,       Direction.NORTHWEST);
		DIR_CLOCKWISE_90       .put(Direction.WEST_SOUTHWEST,  Direction.NORTH_NORTHWEST);
		
		DIR_CLOCKWISE_90       .put(Direction.WEST,            Direction.NORTH);
		DIR_CLOCKWISE_90       .put(Direction.WEST_NORTHWEST,  Direction.NORTH_NORTHEAST);
		DIR_CLOCKWISE_90       .put(Direction.NORTHWEST,       Direction.NORTHEAST);
		DIR_CLOCKWISE_90       .put(Direction.NORTH_NORTHWEST, Direction.EAST_NORTHEAST);
		
		
		
		DIR_COUNTERCLOCKWISE_90.put(Direction.NORTH,           Direction.WEST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.NORTH_NORTHEAST, Direction.WEST_NORTHWEST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.NORTHEAST,       Direction.NORTHWEST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.EAST_NORTHEAST,  Direction.NORTH_NORTHWEST);
		
		DIR_COUNTERCLOCKWISE_90.put(Direction.EAST,            Direction.NORTH);
		DIR_COUNTERCLOCKWISE_90.put(Direction.EAST_SOUTHEAST,  Direction.NORTH_NORTHEAST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.SOUTHEAST,       Direction.NORTHEAST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.SOUTH_SOUTHEAST, Direction.EAST_NORTHEAST);
		
		DIR_COUNTERCLOCKWISE_90.put(Direction.SOUTH,           Direction.EAST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.SOUTH_SOUTHWEST, Direction.EAST_SOUTHEAST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.SOUTHWEST,       Direction.SOUTHEAST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.WEST_SOUTHWEST,  Direction.SOUTH_SOUTHEAST);
		
		DIR_COUNTERCLOCKWISE_90.put(Direction.WEST,            Direction.SOUTH);
		DIR_COUNTERCLOCKWISE_90.put(Direction.WEST_NORTHWEST,  Direction.SOUTH_SOUTHWEST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.NORTHWEST,       Direction.SOUTHWEST);
		DIR_COUNTERCLOCKWISE_90.put(Direction.NORTH_NORTHWEST, Direction.WEST_SOUTHWEST);
	}
	
	
	
	public static void forEachInSphere(final Vector3i center, final int radius, final Consumer<Vector3i> action) {
		GeomUtil.forEachInSphere(center.x(), center.y(), center.z(), radius, IntTriConsumer.fromVector3i(action));
	}
	
	public static void forEachInSphere(final int x0, final int y0, final int z0, final int radius, final IntTriConsumer action) {
		final int rad2 = radius * radius;
		for (int x = -radius; x <= radius; ++x) {
			final int x2 = x * x;
			for (int z = -radius; z <= radius; ++z) {
				final int z2 = z * z;
				final int y2max = rad2 - x2 - z2;
				if (y2max < 0) {
					break;
				}
				
				action.accept(x0 + x, y0, z0 + z);
				for (int y = 1; y*y <= y2max; ++y) {
					action.accept(x0 + x, y0 + y, z0 + z);
					action.accept(x0 + x, y0 - y, z0 + z);
				}
			}
		}
	}
	
	public static void forEachInCircle(final Vector2i center, final int radius, final Consumer<Vector2i> action) {
		GeomUtil.forEachInCircle(center.x(), center.y(), radius, IntBiConsumer.fromVector2i(action));
	}
	
	public static void forEachInCircle(final int x0, final int y0, final int radius, final IntBiConsumer action) {
		final int rad2 = radius * radius;
		for (int x = -radius; x <= radius; ++x) {
			final int x2 = x * x;
			final int y2max = rad2 - x2;
			if (y2max < 0) {
				break;
			}
			
			action.accept(x0, y0);
			for (int y = 1; y*y <= y2max; ++y) {
				action.accept(x0 + x, y0 + y);
				action.accept(x0 + x, y0 - y);
			}
		}
	}
	
	private GeomUtil() {
	}
}
