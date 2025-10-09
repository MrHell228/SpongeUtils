package net.hellheim.spongetools.custom.type.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.recipe.crafting.Ingredient;

import com.google.common.collect.Streams;

import net.hellheim.spongetools.util.ItemUtil;

/**
 * Utility class to make {@link Ingredient}s using {@link CustomItemType}s. <br>
 * <br>
 * Due to {@link CustomItemType} being built on top of actual {@link ItemType},
 * most ingredients by default would accept custom item if its vanilla type matches. <br>
 * This is fixed for ingredients that only test for {@link ItemType} (e.g. {@link Ingredient#of(ItemType...)}). <br>
 * If this ever becomes an issue for predicate ingredients (e.g. the ones made with
 * {@link Ingredient#of(ResourceKey, Predicate, ItemStackLike...)}, it would require more intrusive fix. <br>
 * <br>
 * Therefore, it's recommended to use methods in this class if {@link CustomItemType}s are involved,
 * because they will always work as expected regardless	of whether the mentioned fix happened or not.
 */
public final class CustomIngredients {
	
	/* TODO tags are not yet loaded during recipe registration
	public static Ingredient of(final DefaultedTag<ItemType> tag) {
		return Ingredient.of(tag.values().toArray(ItemType[]::new));
	}
	*/
	
	private static final Map<CustomItemType, Ingredient> SINGLETONS = new HashMap<>();
	
	public static Ingredient singleton(final CustomItemType item) {
		return CustomIngredients.SINGLETONS.computeIfAbsent(item, $ -> CustomIngredients.of(item.key(), item));
	}
	
	public static Ingredient of(final ResourceKey key, final CustomItemType... items) {
		return CustomIngredients.of(key, CustomIngredients.isAny(items), CustomIngredients.icons(items));
	}
	
	@SafeVarargs
	public static Ingredient of(final ResourceKey key, final Supplier<? extends CustomItemType>... items) {
		return CustomIngredients.of(key, CustomIngredients.isAny(items), CustomIngredients.icons(items));
	}
	
	public static Ingredient of(final ResourceKey key, final Iterable<? extends CustomItemType> items) {
		return CustomIngredients.of(key, CustomIngredients.isAny(items), CustomIngredients.icons(items));
	}
	
	public static Ingredient of(final ResourceKey key, final EitherItemType... items) {
		return CustomIngredients.of(key, CustomIngredients.isAny(items), CustomIngredients.icons(items));
	}
	
	public static Ingredient of(
		final ResourceKey key, final Predicate<? super ItemStackLike> predicate,
		final CustomItemType... exemplaryItems
	) {
		return CustomIngredients.of(key, predicate, CustomIngredients.icons(exemplaryItems));
	}
	
	@SafeVarargs
	public static Ingredient of(
		final ResourceKey key, final Predicate<? super ItemStackLike> predicate,
		final Supplier<? extends CustomItemType>... exemplaryItems
	) {
		return CustomIngredients.of(key, predicate, CustomIngredients.icons(exemplaryItems));
	}
	
	public static Ingredient of(
		final ResourceKey key, final Predicate<? super ItemStackLike> predicate,
		final Iterable<? extends CustomItemType> exemplaryItems
	) {
		return CustomIngredients.of(key, predicate, CustomIngredients.icons(exemplaryItems));
	}
	
	public static Ingredient of(
		final ResourceKey key, final Predicate<? super ItemStackLike> predicate,
		final EitherItemType... exemplaryItems
	) {
		return CustomIngredients.of(key, predicate, CustomIngredients.icons(exemplaryItems));
	}
	
	public static Ingredient of(
		final ResourceKey key, final Predicate<? super ItemStackLike> predicate,
		final ItemStackLike... exemplaryItems
	) {
		// This is subject to change due to reasons described above
		return Ingredient.of(key, predicate, exemplaryItems);
	}
	
	
	
	public static ItemStackSnapshot[] icons(final CustomItemType... items) {
		return Arrays.stream(items)
				.map(CustomIngredients::icon)
				.toArray(ItemStackSnapshot[]::new);
	}
	
	@SafeVarargs
	public static ItemStackSnapshot[] icons(final Supplier<? extends CustomItemType>... items) {
		return Arrays.stream(items)
				.map(Supplier::get)
				.map(CustomIngredients::icon)
				.toArray(ItemStackSnapshot[]::new);
	}
	
	public static ItemStackSnapshot[] icons(final Iterable<? extends CustomItemType> items) {
		return Streams.stream(items)
				.map(CustomIngredients::icon)
				.toArray(ItemStackSnapshot[]::new);
	}
	
	public static ItemStackSnapshot[] icons(final EitherItemType... items) {
		return Arrays.stream(items)
				.map(CustomIngredients::icon)
				.toArray(ItemStackSnapshot[]::new);
	}
	
	private static ItemStackSnapshot icon(final EitherItemType item) {
		return item.apply(ItemUtil::snapshotOf, CustomIngredients::icon);
	}
	
	private static ItemStackSnapshot icon(final CustomItemType item) {
		return item.ingredientIcon();
	}
	
	
	
	public static Predicate<? super ItemStackLike> isAny(final CustomItemType... items) {
		return stack -> CustomItemType.isAny(stack, items);
	}
	
	@SafeVarargs
	public static Predicate<? super ItemStackLike> isAny(final Supplier<? extends CustomItemType>... items) {
		return stack -> CustomItemType.isAny(stack, items);
	}
	
	public static Predicate<? super ItemStackLike> isAny(final Iterable<? extends CustomItemType> items) {
		return stack -> CustomItemType.isAny(stack, items);
	}
	
	public static Predicate<? super ItemStackLike> isAny(final EitherItemType... items) {
		return stack -> EitherItemType.of(stack).isAny(items);
	}
	
	private CustomIngredients() {
	}
}
