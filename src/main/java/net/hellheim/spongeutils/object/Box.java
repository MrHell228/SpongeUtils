package net.hellheim.spongeutils.object;

import java.util.function.Consumer;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.util.Direction;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongeutils.function.IntTriConsumer;

public final class Box {
	
	private final int left;
	private final int right;
	private final int up;
	private final int down;
	private final int forth;
	private final int back;
	
	private Box(final int left, final int right, final int up, final int down, final int forth, final int back) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.forth = forth;
		this.back = back;
	}
	
	
	
	public static Box of(final int left, final int right, final int up, final int down, final int forth, final int back) {
		return new Box(left, right, up, down, forth, back);
	}
	
	public static Box of(final int left, final int up, final int forth) {
		return of(left, left, up, up, forth, forth);
	}
	
	
	
	public void forEach(final Entity entity, final Consumer<Vector3i> action) {
		this.forEach(entity, intToVec(action));
	}
	
	public void forEach(final Vector3i position, final Entity entity, final Consumer<Vector3i> action) {
		this.forEach(position, entity, intToVec(action));
	}
	
	public void forEach(final int x, final int y, final int z, final Entity entity, final Consumer<Vector3i> action) {
		this.forEach(x, y, z, entity, intToVec(action));
	}
	
	public void forEach(final Vector3i position, final Vector3d rotation, final Consumer<Vector3i> action) {
		this.forEach(position, rotation, intToVec(action));
	}
	
	public void forEach(final Vector3i position, final double yaw, final double pitch, final Consumer<Vector3i> action) {
		this.forEach(position, yaw, pitch, intToVec(action));
	}
	
	public void forEach(final int x, final int y, final int z, final Vector3d rotation, final Consumer<Vector3i> action) {
		this.forEach(x, y, z, rotation, intToVec(action));
	}
	
	public void forEach(final int x, final int y, final int z, final double yaw, final double pitch, final Consumer<Vector3i> action) {
		this.forEach(x, y, z, yaw, pitch, intToVec(action));
	}
	
	public void forEach(final Entity entity, final IntTriConsumer action) {
		this.forEach(entity.blockPosition(), entity, action);
	}
	
	public void forEach(final Vector3i position, final Entity entity, final IntTriConsumer action) {
		this.forEach(position, entity.rotation(), action);
	}
	
	public void forEach(final int x, final int y, final int z, final Entity entity, final IntTriConsumer action) {
		this.forEach(x, y, z, entity.rotation(), action);
	}
	
	public void forEach(final Vector3i position, final Vector3d rotation, final IntTriConsumer action) {
		this.forEach(position.x(), position.y(), position.z(), yaw(rotation), pitch(rotation), action);
	}
	
	public void forEach(final Vector3i position, final double yaw, final double pitch, final IntTriConsumer action) {
		this.forEach(position.x(), position.y(), position.z(), yaw, pitch, action);
	}
	
	public void forEach(final int x, final int y, final int z, final Vector3d rotation, final IntTriConsumer action) {
		this.forEach(x, y, z, yaw(rotation) , pitch(rotation), action);
	}
	
	public void forEach(final int x, final int y, final int z, final double yaw, final double pitch, final IntTriConsumer action) {
		final Direction hor = horizontal(yaw);
		final Direction ver = vertical(pitch);
		
		if (ver.isUpright()) {
			this.forEach(x, y, z, action, previousCardinal(hor), hor.opposite(), ver);
		} else {
			this.forEach(x, y, z, action, previousCardinal(hor), Direction.UP, hor);
		}
	}
	
	private void forEach(final int originX, final int originY, final int originZ, final IntTriConsumer action,
			final Direction left, final Direction up, final Direction forth) {
		final int offsetLeftX  = left .asBlockOffset().x();
		final int offsetLeftY  = left .asBlockOffset().y();
		final int offsetLeftZ  = left .asBlockOffset().z();
		final int offsetUpX    = up   .asBlockOffset().x();
		final int offsetUpY    = up   .asBlockOffset().y();
		final int offsetUpZ    = up   .asBlockOffset().z();
		final int offsetForthX = forth.asBlockOffset().x();
		final int offsetForthY = forth.asBlockOffset().y();
		final int offsetForthZ = forth.asBlockOffset().z();
		
		final int Ax =   offsetLeftX * this.left  + offsetUpX * this.up   + offsetForthX * this.forth;
		final int Ay =   offsetLeftY * this.left  + offsetUpY * this.up   + offsetForthY * this.forth;
		final int Az =   offsetLeftZ * this.left  + offsetUpZ * this.up   + offsetForthZ * this.forth;
		final int Bx = - offsetLeftX * this.right - offsetUpX * this.down - offsetForthX * this.back;
		final int By = - offsetLeftY * this.right - offsetUpY * this.down - offsetForthY * this.back;
		final int Bz = - offsetLeftZ * this.right - offsetUpZ * this.down - offsetForthZ * this.back;
		
		final int xMin = originX + Math.min(Ax, Bx);
		final int xMax = originX + Math.max(Ax, Bx);
		final int yMin = originY + Math.min(Ay, By);
		final int yMax = originY + Math.max(Ay, By);
		final int zMin = originZ + Math.min(Az, Bz);
		final int zMax = originZ + Math.max(Az, Bz);
		
		for (int x = xMin; x <= xMax; ++x) {
			for (int y = yMin; y <= yMax; ++y) {
				for (int z = zMin; z <= zMax; ++z) {
					action.accept(x, y, z);
				}
			}
		}
	}
	
	private static double pitch(final Vector3d rotation) {
		return rotation.x();
	}
	
	private static double yaw(final Vector3d rotation) {
		return rotation.y();
	}
	
	private static Direction horizontal(final double yaw) {
		final int trueYaw = Math.floorMod((int) yaw, 360);
		if (trueYaw < 45 || trueYaw >= 315) {
			return Direction.SOUTH;
		} else if (trueYaw < 135) {
			return Direction.WEST;
		} else if (trueYaw < 225) {
			return Direction.NORTH;
		} else {
			return Direction.EAST;
		}
	}
	
	private static Direction vertical(final double pitch) {
		if (pitch < -45) {
			return Direction.UP;
		} else if (pitch > 45) {
			return Direction.DOWN;
		} else {
			return Direction.NONE;
		}
	}
	
	private static Direction previousCardinal(final Direction direction) {
		switch (direction) {
			case SOUTH: return Direction.EAST;
			case WEST: return Direction.SOUTH;
			case NORTH: return Direction.WEST;
			case EAST: return Direction.NORTH;
			default: return Direction.NONE;
		}
	}
	
	private IntTriConsumer intToVec(final Consumer<Vector3i> action) {
		return (x, y, z) -> action.accept(new Vector3i(x, y, z));
	}
}
