package net.hellheim.spongeutils.object;

import java.util.function.Supplier;

public class RequestedSupplier<T> {
	
	private final Supplier<T> supplier;
	
	private boolean update = true;
	private T cached;
	
	private RequestedSupplier(final Supplier<T> supplier) {
		this.supplier = supplier;
	}
	
	public static <T> RequestedSupplier<T> of(final Supplier<T> supplier) {
		return new RequestedSupplier<>(supplier);
	}
	
	
	
	private void update() {
		this.cached = this.supplier.get();
		this.update = false;
	}
	
	public T get() {
		if (this.update) {
			this.update();
		}
		
		return this.cached;
	}
	
	public T require() {
		this.update();
		return this.cached;
	}
	
	public void markUpdate() {
		this.update = true;
	}
}
