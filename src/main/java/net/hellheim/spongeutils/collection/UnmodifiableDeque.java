package net.hellheim.spongeutils.collection;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

/**
 * Unmodifiable view of the deque.
 *
 * @param <E> The type of elements held in this deque
 */
public class UnmodifiableDeque<E> implements Deque<E> {
	
	private final Deque<E> deque;
	
	public UnmodifiableDeque(final Deque<E> deque) {
		Objects.requireNonNull(deque);
		this.deque = deque;
	}
	
	private static UnsupportedOperationException uoe() { return new UnsupportedOperationException(); }
	
	@Override public boolean isEmpty() { return this.deque.isEmpty(); }
	@Override public Object[] toArray() { return this.deque.toArray(); }
	@Override public <T> T[] toArray(T[] a) { return this.deque.toArray(a); }
	@Override public boolean containsAll(Collection<?> c) { return this.deque.containsAll(c); }
	
	@Override public E getFirst() { return this.deque.getFirst(); }
	@Override public E getLast() { return this.deque.getLast(); }
	@Override public E peekFirst() { return this.deque.peekFirst(); }
	@Override public E peekLast() { return this.deque.peekLast(); }
	@Override public E element() { return this.deque.element(); }
	@Override public E peek() { return this.deque.peek(); }
	
	@Override public boolean contains(Object o) { return this.deque.contains(o); }
	@Override public int size() { return this.deque.size(); }
	
	@Override public Iterator<E> iterator() { return new UnmodifiableIterator<>(this.deque.iterator()); }
	@Override public Iterator<E> descendingIterator() { return new UnmodifiableIterator<>(this.deque.descendingIterator()); }
	
	@Override public boolean equals(Object obj) { return this.deque.equals(obj); }
	@Override public int hashCode() { return this.deque.hashCode(); }

	@Override public boolean removeAll(Collection<?> c) { throw uoe(); }
	@Override public boolean retainAll(Collection<?> c) { throw uoe(); }
	@Override public void clear() { throw uoe(); }
	@Override public void addFirst(E e) { throw uoe(); }
	@Override public void addLast(E e) { throw uoe(); }
	@Override public boolean offerFirst(E e) { throw uoe(); }
	@Override public boolean offerLast(E e) { throw uoe(); }
	@Override public E removeFirst() { throw uoe(); }
	@Override public E removeLast() { throw uoe(); }
	@Override public E pollFirst() { throw uoe(); }
	@Override public E pollLast() { throw uoe(); }
	@Override public boolean removeFirstOccurrence(Object o) { throw uoe(); }
	@Override public boolean removeLastOccurrence(Object o) { throw uoe(); }
	@Override public boolean add(E e) { throw uoe(); }
	@Override public boolean offer(E e) { throw uoe(); }
	@Override public E remove() { throw uoe(); }
	@Override public E poll() { throw uoe(); }
	@Override public boolean addAll(Collection<? extends E> c) { throw uoe(); }
	@Override public void push(E e) { throw uoe(); }
	@Override public E pop() { throw uoe(); }
	@Override public boolean remove(Object o) { throw uoe(); }
}
