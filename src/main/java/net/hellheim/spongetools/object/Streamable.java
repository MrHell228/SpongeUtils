package net.hellheim.spongetools.object;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

public interface Streamable<T> extends Iterable<T>, Stream<T> {
	
	Stream<T> stream();
	
	@Override
	Iterator<T> iterator();
	
	@Override
	Spliterator<T> spliterator();
	
	@Override
	void forEach(Consumer<? super T> action);
	
	@Override
	default boolean isParallel() {
		return this.stream().isParallel();
	}
	
	@Override
	default Streamable<T> sequential() {
		return of(() -> this.stream().sequential());
	}
	
	@Override
	default Streamable<T> parallel() {
		return of(() -> this.stream().parallel());
	}
	
	@Override
	default Streamable<T> unordered() {
		return of(() -> this.stream().unordered());
	}
	
	@Override
	default Streamable<T> onClose(final Runnable closeHandler) {
		return of(() -> this.stream().onClose(closeHandler));
	}
	
	@Override
	default void close() {
	}
	
	@Override
	default Streamable<T> filter(final Predicate<? super T> predicate) {
		return of(() -> this.stream().filter(predicate));
	}
	
	@Override
	default <R> Streamable<R> map(final Function<? super T, ? extends R> mapper) {
		return of(() -> this.stream().map(mapper));
	}
	
	@Override
	default IntStream mapToInt(final ToIntFunction<? super T> mapper) {
		return this.stream().mapToInt(mapper);
	}
	
	@Override
	default LongStream mapToLong(final ToLongFunction<? super T> mapper) {
		return this.stream().mapToLong(mapper);
	}
	
	@Override
	default DoubleStream mapToDouble(final ToDoubleFunction<? super T> mapper) {
		return this.stream().mapToDouble(mapper);
	}
	
	@Override
	default <R> Streamable<R> flatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
		return of(() -> this.stream().flatMap(mapper));
	}
	
	@Override
	default IntStream flatMapToInt(final Function<? super T, ? extends IntStream> mapper) {
		return this.stream().flatMapToInt(mapper);
	}
	
	@Override
	default LongStream flatMapToLong(final Function<? super T, ? extends LongStream> mapper) {
		return this.stream().flatMapToLong(mapper);
	}
	
	@Override
	default DoubleStream flatMapToDouble(final Function<? super T, ? extends DoubleStream> mapper) {
		return this.stream().flatMapToDouble(mapper);
	}
	
	@Override
	default <R> Streamable<R> mapMulti(final BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return of(() -> this.stream().mapMulti(mapper));
	}
	
	@Override
	default IntStream mapMultiToInt(final BiConsumer<? super T, ? super IntConsumer> mapper) {
		return this.stream().mapMultiToInt(mapper);
	}
	
	@Override
	default LongStream mapMultiToLong(final BiConsumer<? super T, ? super LongConsumer> mapper) {
		return this.stream().mapMultiToLong(mapper);
	}
	
	@Override
	default DoubleStream mapMultiToDouble(final BiConsumer<? super T, ? super DoubleConsumer> mapper) {
		return this.stream().mapMultiToDouble(mapper);
	}
	
	@Override
	default Streamable<T> distinct() {
		return of(() -> this.stream().distinct());
	}
	
	@Override
	default Streamable<T> sorted() {
		return of(() -> this.stream().sorted());
	}
	
	@Override
	default Streamable<T> sorted(final Comparator<? super T> comparator) {
		return of(() -> this.stream().sorted(comparator));
	}
	
	@Override
	default Streamable<T> peek(final Consumer<? super T> action) {
		return of(() -> this.stream().peek(action));
	}
	
	@Override
	default Streamable<T> limit(final long maxSize) {
		return of(() -> this.stream().limit(maxSize));
	}
	
	@Override
	default Streamable<T> skip(final long n) {
		return of(() -> this.stream().skip(n));
	}
	
	@Override
	default Streamable<T> takeWhile(final Predicate<? super T> predicate) {
		return of(() -> this.stream().takeWhile(predicate));
	}
	
	@Override
	default Streamable<T> dropWhile(final Predicate<? super T> predicate) {
		return of(() -> this.stream().dropWhile(predicate));
	}
	
	@Override
	default void forEachOrdered(final Consumer<? super T> action) {
		this.stream().forEachOrdered(action);
	}
	
	@Override
	default Object[] toArray() {
		return this.stream().toArray();
	}
	
	@Override
	default <A> A[] toArray(final IntFunction<A[]> generator) {
		return this.stream().toArray(generator);
	}
	
	@Override
	default T reduce(final T identity, final BinaryOperator<T> accumulator) {
		return this.stream().reduce(identity, accumulator);
	}
	
	@Override
	default Optional<T> reduce(final BinaryOperator<T> accumulator) {
		return this.stream().reduce(accumulator);
	}
	
	@Override
	default <U> U reduce(
		final U identity,
		final BiFunction<U, ? super T, U> accumulator,
		final BinaryOperator<U> combiner
	) {
		return this.stream().reduce(identity, accumulator, combiner);
	}
	
	@Override
	default <R> R collect(
		final Supplier<R> supplier,
		final BiConsumer<R, ? super T> accumulator,
		final BiConsumer<R, R> combiner
	) {
		return this.stream().collect(supplier, accumulator, combiner);
	}
	
	@Override
	default <R, A> R collect(final Collector<? super T, A, R> collector) {
		return this.stream().collect(collector);
	}
	
	@Override
	default List<T> toList() {
		return this.stream().toList();
	}
	
	@Override
	default Optional<T> min(final Comparator<? super T> comparator) {
		return this.stream().min(comparator);
	}
	
	@Override
	default Optional<T> max(Comparator<? super T> comparator) {
		return this.stream().max(comparator);
	}
	
	@Override
	default long count() {
		return this.stream().count();
	}
	
	@Override
	default boolean anyMatch(final Predicate<? super T> predicate) {
		return this.stream().anyMatch(predicate);
	}
	
	@Override
	default boolean allMatch(final Predicate<? super T> predicate) {
		return this.stream().allMatch(predicate);
	}
	
	@Override
	default boolean noneMatch(final Predicate<? super T> predicate) {
		return this.stream().noneMatch(predicate);
	}
	
	@Override
	default Optional<T> findFirst() {
		return this.stream().findFirst();
	}
	
	@Override
	default Optional<T> findAny() {
		return this.stream().findAny();
	}
	
	static <T> Streamable<T> of(final Supplier<? extends Stream<T>> streamSupplier) {
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
			public void forEach(final Consumer<? super T> action) {
				this.stream().forEach(action);
			}
		};
	}
	
	static <T> Streamable<T> of(final Iterable<T> iterable) {
		Objects.requireNonNull(iterable, "iterable");
		if (iterable instanceof final Streamable<T> streamable) {
			return streamable;
		}
		
		return new Streamable<T>() {
			@Override
			public Stream<T> stream() {
				return Streams.stream(iterable);
			}
			
			@Override
			public Iterator<T> iterator() {
				return iterable.iterator();
			}
			
			@Override
			public Spliterator<T> spliterator() {
				return iterable.spliterator();
			}
			
			@Override
			public void forEach(final Consumer<? super T> action) {
				iterable.forEach(action);
			}
		};
	}
}
