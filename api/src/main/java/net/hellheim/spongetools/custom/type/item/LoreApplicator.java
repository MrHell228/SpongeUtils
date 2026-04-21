package net.hellheim.spongetools.custom.type.item;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;

import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * The utility class to apply {@link LoreProcessor} and {@link LoreProvider}s to the item.
 */
public record LoreApplicator(
		/**
		 * Optional lore processor to use if absent on item
		 */
		Optional<LoreProcessor> defaultProcessor,
		/**
		 * Optional lore providers to use if absent on item
		 */
		Optional<List<LoreProvider>> defaultProviders
		) {
	
	private static final LoreApplicator EMPTY = new LoreApplicator(Optional.empty(), Optional.empty());
	
	public LoreApplicator(
		final Optional<LoreProcessor> defaultProcessor, final Optional<List<LoreProvider>> defaultProviders
	) {
		this.defaultProcessor();
		this.defaultProcessor = Objects.requireNonNull(defaultProcessor, "defaultProcessor");
		this.defaultProviders = Objects.requireNonNull(defaultProviders, "defaultProviders");
	}
	
	/**
	 * Returns lore applicator without fallback processor and providers.
	 * 
	 * @return Lore applicator without fallback processor and providers
	 */
	public static LoreApplicator empty() {
		return LoreApplicator.EMPTY;
	}
	
	/**
	 * Returns lore applicator with given fallback processor.
	 * 
	 * @param defaultProcessor Lore processor to use if absent on item
	 * @return Lore applicator with the given arguments
	 */
	public static LoreApplicator of(final LoreProcessor defaultProcessor) {
		return LoreApplicator.of(Optional.of(defaultProcessor), Optional.empty());
	}
	
	/**
	 * Returns lore applicator with given fallback providers.
	 * 
	 * @param defaultProviders Lore providers to use if absent on item
	 * @return Lore applicator with the given arguments
	 */
	public static LoreApplicator of(final List<LoreProvider> defaultProviders) {
		return LoreApplicator.of(Optional.empty(), Optional.of(defaultProviders));
	}
	
	/**
	 * Returns lore applicator with given fallback processor and providers.
	 * 
	 * @param defaultProcessor Lore processor to use if absent on item
	 * @param defaultProviders Lore providers to use if absent on item
	 * @return Lore applicator with the given arguments
	 */
	public static LoreApplicator of(final LoreProcessor defaultProcessor, final List<LoreProvider> defaultProviders) {
		return LoreApplicator.of(Optional.of(defaultProcessor), Optional.of(defaultProviders));
	}
	
	/**
	 * Returns lore applicator with given fallback processor and providers.
	 * 
	 * @param defaultProcessor Optional lore processor to use if absent on item
	 * @param defaultProviders Optional lore providers to use if absent on item
	 * @return Lore applicator with the given arguments
	 */
	public static LoreApplicator of(
		final Optional<LoreProcessor> defaultProcessor, final Optional<List<LoreProvider>> defaultProviders
	) {
		if (defaultProcessor.isEmpty() && defaultProviders.isEmpty()) {
			return LoreApplicator.empty();
		}
		
		return new LoreApplicator(defaultProcessor, defaultProviders);
	}
	
	/**
	 * Processes and applies lore to the given item with the given context. <br>
	 * Given item is modified only if following conditions are met: <br>
	 * - Item contains {@link LoreProcessor#dataKey()} or {@link #defaultProcessor()} is present; <br>
	 * - Item contains {@link LoreProvider#dataKey()} or {@link #defaultProviders()} is present. <br>
	 * Item's processor and providers are preferred over this applicator's ones. <br>
	 * Moreover, modifications (if any) are applied to 
	 * {@link ItemStackLike#asMutable()} of the given <code>stack</code>. <br>
	 * If there are no modifications to apply, the original <code>stack</code> is returned.
	 * 
	 * @param stack The item to apply lore to
	 * @param context The context to use lore providers with
	 * @return The modified item or the original item if no modifications were made
	 */
	public ItemStackLike apply(final ItemStackLike stack, final TypedKeyMap context) {
		return stack.get(LoreProcessor.dataKey())
				.or(this::defaultProcessor)
				.map(processor -> stack.get(LoreProvider.dataKey())
						.or(this::defaultProviders)
						.map(providers -> {
							final ItemStack mutable = stack.asMutable();
							mutable.offer(processor.processValue(stack, context, providers));
							providers.forEach(provider ->
									provider.loreHidingKeys().forEach(key ->
											mutable.offer(key, true)));
							return (ItemStackLike) mutable;
						})
						.orElse(null))
				.orElse(stack);
	}
	
	/**
	 * Processes and applies lore to the given item with empty context. <br>
	 * Given item is modified only if following conditions are met: <br>
	 * - Item contains {@link LoreProcessor#dataKey()} or {@link #defaultProcessor()} is present; <br>
	 * - Item contains {@link LoreProvider#dataKey()} or {@link #defaultProviders()} is present. <br>
	 * Item's processor and providers are preferred over this applicator's ones. <br>
	 * Moreover, modifications (if any) are applied to 
	 * {@link ItemStackLike#asMutable()} of the given <code>stack</code>. <br>
	 * If there are no modifications to apply, the original <code>stack</code> is returned.
	 * 
	 * @param stack The item to apply lore to
	 * @return The modified item or the original item if no modifications were made
	 */
	public ItemStackLike apply(final ItemStackLike stack) {
		return this.apply(stack, TypedKeyMap.empty());
	}
}
