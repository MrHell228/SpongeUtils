package net.hellheim.spongeutils.menu.pagination;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.checkerframework.common.returnsreceiver.qual.This;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.menu.Menu;
import net.hellheim.spongeutils.source.solid.item.IItemSource;

/**
 * Provides content for pages in {@link Pagination}.
 * 
 * @param <M> The {@link Menu}
 * @param <T> The type of elements in this pagination
 */
public class PageContentProvider<M extends Menu<M>, T> {
	
	private final Function<M, ? extends List<T>> elementsProvider;
	private final Function<M, Function<T, ? extends ItemStackLike>> elementStackProvider;
	private final Function<M, ? extends ItemStackLike> emptyStackProvider;
	
	protected PageContentProvider(
		final Function<M, ? extends List<T>> elements,
		final Function<M, Function<T, ? extends ItemStackLike>> elementStack,
		final Function<M, ? extends ItemStackLike> emptyStack
	) {
		this.elementsProvider = elements;
		this.elementStackProvider = elementStack;
		this.emptyStackProvider = emptyStack;
	}
	
	/**
	 * Returns the elements this pagination should have in provided {@link Menu}.
	 * 
	 * @param menu The menu
	 * @return The elements
	 */
	public List<T> elements(final M menu) {
		return this.elementsProvider.apply(menu);
	}
	
	/**
	 * Returns function that provides {@link ItemStackLike} for the element in the given {@link Menu}.
	 * 
	 * @param menu The menu
	 * @param element The element
	 * @return The ItemStack
	 */
	public Function<T, ? extends ItemStackLike> elementStackProvider(final M menu) {
		return this.elementStackProvider.apply(menu);
	}
	
	/**
	 * Returns the {@link ItemStackLike} that should be placed when there is not enough
	 * {@link #elements(Menu)} to fill the page.
	 * 
	 * @param menu The menu
	 * @return The ItemStack
	 */
	public ItemStackLike emptyStack(final M menu) {
		return this.emptyStackProvider.apply(menu);
	}
	
	
	
	public static <M extends Menu<M>, T> Builder<M, T> builder() {
		return new Builder<>();
	}
	
	public static <M extends Menu<M>> Builder<M, ItemStack> builderOfStack() {
		return new Builder<M, ItemStack>().elementStack(stack -> stack);
	}
	
	public static <M extends Menu<M>> Builder<M, ItemStackSnapshot> builderOfSnapshot() {
		return new Builder<M, ItemStackSnapshot>().elementStack(snapshot -> snapshot.asMutable());
	}
	
	public static <M extends Menu<M>, T extends IItemSource> Builder<M, T> builderOfSource() {
		return new Builder<M, T>().elementStack(source -> source.getAsItemStack());
	}
	
	
	
	public static class Builder<M extends Menu<M>, T> {
		
		private Function<M, ? extends List<T>> elementsProvider = null;
		private Function<M, Function<T, ? extends ItemStackLike>> elementStackProvider = null;
		private Function<M, ? extends ItemStackLike> emptyStackProvider = null;
		
		public Builder() {
		}
		
		private static final <T> Collector<T, ?, List<T>> collector() {
			return Collectors.toUnmodifiableList();
		}
		
		public @This Builder<M, T> elementsFilterProvided(final Collection<T> elements, final Function<M, Predicate<T>> filterProvider) {
			return this.elements((menu) -> {
				final Predicate<T> filter = filterProvider.apply(menu);
				return elements.stream()
						.filter(filter)
						.collect(collector());
			});
		}
		
		public @This Builder<M, T> elementsFilterProvided(final Supplier<? extends Collection<T>> elements, final Function<M, Predicate<T>> filterProvider) {
			return this.elements((menu) -> {
				final Predicate<T> filter = filterProvider.apply(menu);
				return elements.get().stream()
						.filter(filter)
						.collect(collector());
			});
		}
		
		public @This Builder<M, T> elementsFilterProvided(final Function<M, ? extends Collection<T>> elements, final Function<M, Predicate<T>> filterProvider) {
			return this.elements((menu) -> {
				final Predicate<T> filter = filterProvider.apply(menu);
				return elements.apply(menu).stream()
						.filter(filter)
						.collect(collector());
			});
		}
		
		// Filtered & sorted elements
		
		public @This Builder<M, T> elements(final Collection<T> elements, final Predicate<T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.stream()
					.filter(filter)
					.sorted(comparator)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Supplier<? extends Collection<T>> elements, final Predicate<T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.get().stream()
					.filter(filter)
					.sorted(comparator)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Function<M, ? extends Collection<T>> elements, final Predicate<T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.apply(menu).stream()
					.filter(filter)
					.sorted(comparator)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Collection<T> elements, final BiPredicate<M, T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.stream()
					.filter(e -> filter.test(menu, e))
					.sorted(comparator)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Supplier<? extends Collection<T>> elements, final BiPredicate<M, T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.get().stream()
					.filter(e -> filter.test(menu, e))
					.sorted(comparator)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Function<M, ? extends Collection<T>> elements, final BiPredicate<M, T> filter, final Comparator<T> comparator) {
			return this.elements((menu) -> elements.apply(menu).stream()
					.filter(e -> filter.test(menu, e))
					.sorted(comparator)
					.collect(collector()));
		}
		
		// Filtered elements
		
		public @This Builder<M, T> elements(final Collection<T> elements, final Predicate<T> filter) {
			return this.elements((menu) -> elements.stream()
					.filter(filter)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Supplier<? extends Collection<T>> elements, final Predicate<T> filter) {
			return this.elements((menu) -> elements.get().stream()
					.filter(filter)
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Function<M, ? extends Collection<T>> elements, final Predicate<T> filter) {
			return this.elements((menu) -> elements.apply(menu).stream()
					.filter(filter).
					collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Collection<T> elements, final BiPredicate<M, T> filter) {
			return this.elements((menu) -> elements.stream()
					.filter(e -> filter.test(menu, e))
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Supplier<? extends Collection<T>> elements, final BiPredicate<M, T> filter) {
			return this.elements((menu) -> elements.get().stream()
					.filter(e -> filter.test(menu, e))
					.collect(collector()));
		}
		
		public @This Builder<M, T> elements(final Function<M, ? extends Collection<T>> elements, final BiPredicate<M, T> filter) {
			return this.elements((menu) -> elements.apply(menu).stream()
					.filter(e -> filter.test(menu, e))
					.collect(collector()));
		}
		
		// Collection-based elemenets
		
		public @This Builder<M, T> elementsCollection(final Collection<T> elements) {
			return this.elements((menu) -> List.copyOf(elements));
		}
		
		public @This Builder<M, T> elementsCollection(final Supplier<? extends Collection<T>> elements) {
			return this.elements((menu) -> List.copyOf(elements.get()));
		}
		
		public @This Builder<M, T> elementsCollection(final Function<M, ? extends Collection<T>> elements) {
			return this.elements((menu) -> List.copyOf(elements.apply(menu)));
		}
		
		// List-based elements
		
		public @This Builder<M, T> elements(final List<T> elements) {
			return this.elements((menu) -> elements);
		}
		
		public @This Builder<M, T> elements(final Supplier<? extends List<T>> elements) {
			return this.elements((menu) -> elements.get());
		}
		
		public @This Builder<M, T> elements(final Function<M, ? extends List<T>> elements) {
			this.elementsProvider = elements;
			return this;
		}
		
		// Element stack provider
		
		public @This Builder<M, T> elementStack(final Function<T, ? extends ItemStackLike> elementStack) {
			return this.elementStackProvider((menu) -> ((element) -> elementStack.apply(element)));
		}
		
		public @This Builder<M, T> elementStack(final BiFunction<M, T, ? extends ItemStackLike> elementStack) {
			return this.elementStackProvider((menu) -> ((element) -> elementStack.apply(menu, element)));
		}
		
		public @This Builder<M, T> elementStackProvider(final Function<M, Function<T, ? extends ItemStackLike>> elementStack) {
			this.elementStackProvider = elementStack;
			return this;
		}
		
		// Empty stack provider
		
		public @This Builder<M, T> emptyStack() {
			return this.emptyStack(ItemStack::empty);
		}
		
		public @This Builder<M, T> emptyStack(final IItemSource item) {
			return this.emptyStack(item::getAsItemStack);
		}
		
		public @This Builder<M, T> emptyStack(final ItemStackSnapshot item) {
			return this.emptyStack(item::asMutable);
		}
		
		public @This Builder<M, T> emptyStack(final ItemStack item) {
			return this.emptyStack(item.asImmutable());
		}
		
		public @This Builder<M, T> emptyStack(final Supplier<ItemStack> emptyStack) {
			return this.emptyStack($ -> emptyStack.get());
		}
		
		public @This Builder<M, T> emptyStack(final Function<M, ItemStack> emptyStack) {
			this.emptyStackProvider = emptyStack;
			return this;
		}
		
		public PageContentProvider<M, T> build() {
			if (this.elementsProvider == null) {
				throw new IllegalStateException("Elements provider is not set");
			} else if (this.elementStackProvider == null) {
				throw new IllegalStateException("Element stack provider is not set");
			} else {
				return new PageContentProvider<>(this.elementsProvider, this.elementStackProvider,
						(this.emptyStackProvider == null ? this.emptyStack() : this).emptyStackProvider);
			}
		}
	}
}
