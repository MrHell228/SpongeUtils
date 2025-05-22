package net.hellheim.spongetools.object;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

public interface Streamable<T> extends Iterable<T> {
	
	Stream<T> stream();
	
	static <T> Streamable<T> stream(final Supplier<? extends Stream<T>> streamSupplier) {
		Objects.requireNonNull(streamSupplier, "streamSupplier");
		return new Streamable<T>() {
			@Override
			public Stream<T> stream() {
				return streamSupplier.get();
			}
			
			@Override
			public Iterator<T> iterator() {
				return this.stream().iterator();
			}
			
			@Override
			public Spliterator<T> spliterator() {
				return this.stream().spliterator();
			}
			
			@Override
			public void forEach(Consumer<? super T> action) {
				this.stream().forEach(action);
			}
		};
	}
	
	static <T> Streamable<T> iterator(final Supplier<? extends Iterator<T>> iteratorSupplier) {
		Objects.requireNonNull(iteratorSupplier, "iteratorSupplier");
		return new Streamable<T>() {
			@Override
			public Iterator<T> iterator() {
				return iteratorSupplier.get();
			}
			
			@Override
			public Stream<T> stream() {
				return Streams.stream(this.iterator());
			}
		};
	}
	
	static <T> Streamable<T> iterable(final Iterable<T> iterable) {
		Objects.requireNonNull(iterable, "iterable");
		return new Streamable<T>() {
			@Override
			public Iterator<T> iterator() {
				return iterable.iterator();
			}
			
			@Override
			public Stream<T> stream() {
				return Streams.stream(iterable);
			}
		};
	}
}
