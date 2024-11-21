package net.hellheim.spongeutils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.util.Axis;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.mirror.Mirror;
import org.spongepowered.api.util.rotation.Rotation;
import org.spongepowered.api.util.rotation.Rotations;

public final class RotationUtil {
	
	// Relies on implementation where these are enum values meaning they should always be available.
	public static final Rotation NONE = Rotations.NONE.get();
	public static final Rotation CLOCKWISE_90 = Rotations.CLOCKWISE_90.get();
	public static final Rotation CLOCKWISE_180 = Rotations.CLOCKWISE_180.get();
	public static final Rotation COUNTERCLOCKWISE_90 = Rotations.COUNTERCLOCKWISE_90.get();
	private static final List<Rotation> ROTATION_VALUES =
			List.of(NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90);
	
	public static boolean is(final Supplier<Rotation> r1, final Rotation r2) {
		return RotationUtil.is(r1.get(), r2);
	}
	
	public static boolean is(final Rotation r1, final Supplier<Rotation> r2) {
		return RotationUtil.is(r1, r2.get());
	}
	
	public static boolean is(final Rotation r1, final Rotation r2) {
		return r1 == r2;
	}
	
	public static boolean is(final Supplier<Mirror> m1, final Mirror m2) {
		return RotationUtil.is(m1.get(), m2);
	}
	
	public static boolean is(final Mirror m1, final Supplier<Mirror> m2) {
		return RotationUtil.is(m1, m2.get());
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
		return  RotationUtil.is(axis, Axis.X) || RotationUtil.is(axis, Axis.Z);
	}
	
	public static boolean isHorizontal(final Direction direction) {
		return !direction.isUpright() && !RotationUtil.is(direction, Direction.NONE);
	}
	
	public static List<Rotation> rotations() {
		return ROTATION_VALUES;
	}
	
	public static Optional<Rotation> rotation(final Direction from, final Direction to) {
		if (!RotationUtil.isHorizontal(from)) {
			throw new IllegalArgumentException("'from' direction must be horizontal");
		}
		
		if (!RotationUtil.isHorizontal(to)) {
			throw new IllegalArgumentException("'to' direction must be horizontal");
		}
		
		if (RotationUtil.is(from, to)) {
			return Optional.of(RotationUtil.NONE);
		} else if (from.isOpposite(to)) {
			return Optional.of(RotationUtil.CLOCKWISE_180);
		} else if (DIR_CLOCKWISE_90.get(from) == to) {
			return Optional.of(RotationUtil.CLOCKWISE_90);
		} else if (DIR_COUNTERCLOCKWISE_90.get(from) == to) {
			return Optional.of(RotationUtil.COUNTERCLOCKWISE_90);
		}
		
		return Optional.empty();
	}
	
	public static Direction rotate(final Direction direction, final Rotation rotation) {
		if (!RotationUtil.isHorizontal(direction)) {
			throw new IllegalArgumentException("direction must be horizontal");
		}
		
		if (RotationUtil.is(rotation, RotationUtil.NONE)) {
			return direction;
		} else if (RotationUtil.is(rotation, RotationUtil.CLOCKWISE_180)) {
			return direction.opposite();
		} else if (RotationUtil.is(rotation, RotationUtil.CLOCKWISE_90)) {
			return DIR_CLOCKWISE_90.get(direction);
		} else if (RotationUtil.is(rotation, RotationUtil.COUNTERCLOCKWISE_90)) {
			return DIR_COUNTERCLOCKWISE_90.get(direction);
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
	
	private RotationUtil() {
	}
}
