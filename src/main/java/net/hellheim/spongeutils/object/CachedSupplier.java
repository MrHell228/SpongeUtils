package net.hellheim.spongeutils.object;

import java.util.function.Supplier;

public class CachedSupplier<T> {
	
	private final Supplier<T> supplier;
	private boolean requested = false;
	private T cached;
	
	public CachedSupplier(final Supplier<T> supplier) {
		this.supplier = supplier;
	}
	
	public static <T> CachedSupplier<T> of(final Supplier<T> supplier) {
		return new CachedSupplier<>(supplier);
	}
	
	public T get() {
		if (!this.requested) {
			this.requested = true;
			this.cached = this.supplier.get();
		}
		
		return this.cached;
	}
}
