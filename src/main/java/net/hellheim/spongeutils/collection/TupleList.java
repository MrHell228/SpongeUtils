package net.hellheim.spongeutils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.spongepowered.api.util.Tuple;

@SuppressWarnings("serial")
public class TupleList<L, R> extends ArrayList<Tuple<L, R>> {
	
	public TupleList() {
		super();
	}
	
	public TupleList(Collection<? extends Tuple<L, R>> c) {
		super(c);
	}
	
	public static <L, R> TupleList<L, R> ofFirst(final List<L> list, final Function<L, R> function) {
		final TupleList<L, R> res = new TupleList<>();
		for (final L first : list) {
			res.add(first, function.apply(first));
		}
		return res;
	}
	
	public static <L, R> TupleList<L, R> ofSecond(final List<R> list, final Function<R, L> function) {
		final TupleList<L, R> res = new TupleList<>();
		for (final R second : list) {
			res.add(function.apply(second), second);
		}
		return res;
	}
	
	
	public void add(final L l, final R r) {
		this.add(Tuple.of(l, r));
	}
	
	public List<L> firstList() {
		final List<L> list = new ArrayList<>();
		for (final Tuple<L, R> tuple : this) {
			list.add(tuple.first());
		}
		return list;
	}
	
	public List<R> secondList() {
		final List<R> list = new ArrayList<>();
		for (final Tuple<L, R> tuple : this) {
			list.add(tuple.second());
		}
		return list;
	}
	
	public void forEach(final BiConsumer<L, R> action) {
		this.forEach(pair -> action.accept(pair.first(), pair.second()));
	}
}
