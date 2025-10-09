package net.hellheim.spongetools.custom.behaviour;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * Represents some action a {@link BehaviourHolder} can do.
 * 
 * @param <R> Return type
 * @param <A> Behaviour arguments
 */
@FunctionalInterface
public interface Behaviour<R, A extends BehaviourArgs> {
	
	R call(A args);
	
	@FunctionalInterface
	interface SimpleAction extends Behaviour<Void, BehaviourArgs>, Runnable {
		
		@Override
		default Void call(BehaviourArgs args) {
			this.call();
			return null;
		}
		
		@Override
		default void run() {
			this.call();
		}
		
		void call();
	}
	
	@FunctionalInterface
	interface SimpleBoolean extends Behaviour<Boolean, BehaviourArgs>, BooleanSupplier {
		
		@Override
		default Boolean call(final BehaviourArgs args) {
			return this.call();
		}
		
		@Override
		default boolean getAsBoolean() {
			return this.call();
		}
		
		boolean call();
	}
	
	@FunctionalInterface
	interface SimpleInteger extends Behaviour<Integer, BehaviourArgs>, IntSupplier {
		
		@Override
		default Integer call(final BehaviourArgs args) {
			return this.call();
		}
		
		@Override
		default int getAsInt() {
			return this.call();
		};
		
		int call();
	}
	
	@FunctionalInterface
	interface SimpleLong extends Behaviour<Long, BehaviourArgs>, LongSupplier {
		
		@Override
		default Long call(final BehaviourArgs args) {
			return this.call();
		}
		
		@Override
		default long getAsLong() {
			return this.call();
		};
		
		long call();
	}
	
	@FunctionalInterface
	interface SimpleDouble extends Behaviour<Double, BehaviourArgs>, DoubleSupplier {
		
		@Override
		default Double call(final BehaviourArgs args) {
			return this.call();
		}
		
		@Override
		default double getAsDouble() {
			return this.call();
		}
		
		double call();
	}
	
	@FunctionalInterface
	interface SimpleObject<V> extends Behaviour<V, BehaviourArgs>, Supplier<V> {
		
		@Override
		default V call(final BehaviourArgs args) {
			return this.call();
		}
		
		@Override
		default V get() {
			return this.call();
		}
		
		V call();
	}
}
