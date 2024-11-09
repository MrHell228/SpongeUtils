package net.hellheim.spongeutils.collection;

import java.util.Iterator;

/**
 * Unmodifiable wrapper of iterator.
 *
 * @param <E> The type of elements returned by this element
 */
public class UnmodifiableIterator<E> extends com.google.common.collect.UnmodifiableIterator<E> {
	
	private final Iterator<E> iterator;
	
	public UnmodifiableIterator(final Iterable<E> collection) {
		this(collection.iterator());
	}
	
	public UnmodifiableIterator(final Iterator<E> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}
	
	@Override
	public E next() {
		return this.iterator.next();
	}
}
