package net.hellheim.spongeutils.object;

import java.util.function.Supplier;

public class UpdatableSupplier<T> {
	
	private final long cooldown;
	private final Supplier<T> supplier;
	
	private long nextUpdate = 0;
	private T cached;
	
	public UpdatableSupplier(final long cooldown, final Supplier<T> supplier) {
		this.cooldown = cooldown;
		this.supplier = supplier;
	}
	
	public static <T> UpdatableSupplier<T> of(final long cooldown, final Supplier<T> supplier) {
		return new UpdatableSupplier<>(cooldown, supplier);
	}
	
	
	
	private void update() {
		this.nextUpdate = System.currentTimeMillis() + this.cooldown;
		this.cached = this.supplier.get();
	}
	
	public T get() {
		if (System.currentTimeMillis() > this.nextUpdate) {
			this.update();
		}
		
		return this.cached;
	}
	
	public T require() {
		this.update();
		return this.cached;
	}
	
	public void markUpdate() {
		this.nextUpdate = 0;
	}
}
