package net.hellheim.spongeutils;

import java.util.List;
import java.util.Random;

import net.kyori.adventure.text.format.TextColor;

public final class RNG {
	
	public static final Random RNG = new Random();
	
	public static int nextInt(final int bound) {
		return RNG.nextInt(bound);
	}
	
	public static int nextInt(final int lower, final int upper) {
		return lower + RNG.nextInt(upper-lower);
	}
	
	public static int nextIntIncluded(final int lower, final int upper) {
		return nextInt(lower, upper + 1);
	}
	
	
	public static float nextFloat() {
		return RNG.nextFloat();
	}
	
	public static float nextFloat(final float bound) {
		return RNG.nextFloat() * bound;
	}
	
	public static float nextFloat(final float lower, final float upper) {
		return lower + (upper-lower) * RNG.nextFloat();
	}
	
	
	public static double nextDouble() {
		return RNG.nextDouble();
	}
	
	public static double nextDouble(final double bound) {
		return RNG.nextDouble() * bound;
	}
	
	public static double nextDouble(final double lower, final double upper) {
		return lower + (upper-lower) * RNG.nextDouble();
	}
	
	
	public static int nextSign() {
		return nextInt(2) == 0 ? -1 : 1;
	}
	
	public static int nextSignedInt(final int lower, final int upper) {
		return nextSign() * nextInt(lower, upper);
	}
	
	
	public static int nextColor() {
		final float r = nextFloat();
		final float g = nextFloat();
		final float b = nextFloat();
		return TextColor.color(r, g, b).value();
	}
	
	
	public static <T> T nextElement(final List<T> list) {
		return list.isEmpty() ? null : list.get(nextInt(list.size()));
	}
	
	public static <T> T nextElement(final T[] array) {
		return array.length == 0 ? null : array[nextInt(array.length)];
	}
	
	
	private static final double DEFAULT_EPSILON = 1E-2D;
	
	public static int toInt(final double value) {
		return toInt(value, DEFAULT_EPSILON);
	}
	
	public static int toInt(final double value, final double epsilon) {
		int result = (int) value;
		double fraction = value - result;
		if (epsilon < fraction && nextFloat() < fraction) {
			++result;
		}
		
		return result;
	}
	
	public static long toLong(final double value) {
		return toLong(value, DEFAULT_EPSILON);
	}
	
	public static long toLong(final double value, final double epsilon) {
		long result = (long) value;
		double fraction = value - result;
		if (epsilon < fraction && nextFloat() < fraction) {
			++result;
		}
		
		return result;
	}
	
	private RNG() {
	}
}
