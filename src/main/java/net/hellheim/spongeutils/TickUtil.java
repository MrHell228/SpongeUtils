package net.hellheim.spongeutils;

import org.spongepowered.api.util.Ticks;

public final class TickUtil {
	
	public static Ticks ofSeconds(final int seconds) {
		return Ticks.of(seconds * net.kyori.adventure.util.Ticks.TICKS_PER_SECOND);
	}
	
	public static Ticks max(final Ticks t1, final Ticks t2) {
		return t1.ticks() >= t2.ticks() ? t1 : t2;
	}
	
	public static Ticks min(final Ticks t1, final Ticks t2) {
		return t1.ticks() <= t2.ticks() ? t1 : t2;
	}
	
	public static Ticks add(final Ticks t1, final Ticks t2) {
		return Ticks.of(t1.ticks() + t2.ticks());
	}
	
	public static Ticks sub(final Ticks t1, final Ticks t2) {
		final long resultTicks = t1.ticks() - t2.ticks();
		return resultTicks <= 0 ? Ticks.zero() : Ticks.of(resultTicks);
	}
	
	private TickUtil() {
	}
}
