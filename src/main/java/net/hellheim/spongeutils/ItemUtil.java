package net.hellheim.spongeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackComparators;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;

import net.hellheim.spongeutils.source.solid.EnchantmentSource;
import net.hellheim.spongeutils.source.solid.EnchantmentTypeSource;
import net.hellheim.spongeutils.source.solid.item.IItemSource;
import net.kyori.adventure.text.Component;

public final class ItemUtil {
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link IItemSource}
	 * @param i2 The {@link IItemSource}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final IItemSource i1, final IItemSource i2) {
		return is(i1.getAsItemType(), i2.getAsItemType());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link IItemSource}
	 * @param i2 The {@link ItemStackLike}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final IItemSource i1, final ItemStackLike i2) {
		return is(i1.getAsItemType(), i2.type());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link IItemSource}
	 * @param i2 The {@link Supplier ItemType Supplier}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final IItemSource i1, final Supplier<ItemType> i2) {
		return is(i1.getAsItemType(), i2.get());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link IItemSource}
	 * @param i2 The {@link ItemType}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final IItemSource i1, final ItemType i2) {
		return is(i1.getAsItemType(), i2);
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemStackLike}
	 * @param i2 The {@link IItemSource}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemStackLike i1, final IItemSource i2) {
		return is(i1.type(), i2.getAsItemType());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemStackLike}
	 * @param i2 The {@link ItemStackLike}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemStackLike i1, final ItemStackLike i2) {
		return is(i1.type(), i2.type());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemStackLike}
	 * @param i2 The {@link Supplier ItemType Supplier}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemStackLike i1, final Supplier<ItemType> i2) {
		return is(i1.type(), i2.get());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemStackLike}
	 * @param i2 The {@link ItemType}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemStackLike i1, final ItemType i2) {
		return is(i1.type(), i2);
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link Supplier ItemType Supplier}
	 * @param i2 The {@link IItemSource}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<ItemType> i1, final IItemSource i2) {
		return is(i1.get(), i2.getAsItemType());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link Supplier ItemType Supplier}
	 * @param i2 The {@link ItemStackLike}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<ItemType> i1, final ItemStackLike i2) {
		return is(i1.get(), i2.type());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link Supplier ItemType Supplier}
	 * @param i2 The {@link Supplier ItemType Supplier}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<ItemType> i1, final Supplier<ItemType> i2) {
		return is(i1.get(), i2.get());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link Supplier ItemType Supplier}
	 * @param i2 The {@link ItemType}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final Supplier<ItemType> i1, final ItemType i2) {
		return is (i1.get(), i2);
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemType}
	 * @param i2 The {@link IItemSource}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemType i1, final IItemSource i2) {
		return is(i1, i2.getAsItemType());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemType}
	 * @param i2 The {@link ItemStackLike}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemType i1, final ItemStackLike i2) {
		return is(i1, i2.type());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemType}
	 * @param i2 The {@link Supplier ItemType Supplier}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemType i1, final Supplier<ItemType> i2) {
		return is(i1, i2.get());
	}
	
	/**
	 * Compares {@link ItemType}s of two arguments.
	 * 
	 * @param i1 The {@link ItemType}
	 * @param i2 The {@link ItemType}
	 * @return True if {@link ItemType}s of the arguments are equal
	 */
	public static boolean is(final ItemType i1, final ItemType i2) {
		return i1 == i2;
	}
	
	
	
	public static boolean similar(final ItemStack i1, final ItemStack i2) {
		return ItemStackComparators.TYPE.get().compare(i1, i2) == 0;//TODO replace TYPE with IGNORE_SIZE
	}
	
	
	
	public static Component displayName(Supplier<ItemType> type) {
		return displayName(type.get());
	}
	public static Component displayName(ItemType type) {
		return displayName(stackOf(type));
	}
	public static Component displayName(DataHolder stack) {
		return stack.require(Keys.DISPLAY_NAME);
	}
	
	public static String plainDisplayName(DataHolder stack) {
		return CompUtil.toPlain(displayName(stack));
	}
	
	
	
	public static ItemStack stackOf(final IItemSource source, final int quantity) {
		final ItemStack stack = source.getAsItemStack();
		stack.setQuantity(quantity);
		return stack;
	}
	public static ItemStack stackOf(final Supplier<ItemType> type, final int quantity) {
		return ItemStack.of(type, quantity);
	}
	public static ItemStack stackOf(final ItemType type, final int quantity) {
		return ItemStack.of(type, quantity);
	}
	public static ItemStack stackOf(final IItemSource source) {
		return source.getAsItemStack();
	}
	public static ItemStack stackOf(final Supplier<ItemType> type) {
		return ItemStack.of(type);
	}
	public static ItemStack stackOf(final ItemType type) {
		return ItemStack.of(type);
	}
	
	public static ItemStackSnapshot snapshotOf(final IItemSource source, final int quantity) {
		return stackOf(source, quantity).asImmutable();
	}
	public static ItemStackSnapshot snapshotOf(final Supplier<ItemType> type, final int quantity) {
		return stackOf(type, quantity).asImmutable();
	}
	public static ItemStackSnapshot snapshotOf(final ItemType type, final int quantity) {
		return stackOf(type, quantity).asImmutable();
	}
	public static ItemStackSnapshot snapshotOf(final IItemSource source) {
		return source.getAsItemStackSnapshot();
	}
	public static ItemStackSnapshot snapshotOf(final Supplier<ItemType> type) {
		return stackOf(type).asImmutable();
	}
	public static ItemStackSnapshot snapshotOf(final ItemType type) {
		return stackOf(type).asImmutable();
	}
	
	
	public static ItemStack changeDurability(final ItemStackLike stack, final int durability) {
		final ItemStack mutable = stack.asMutable();
		mutable.get(Keys.ITEM_DURABILITY).ifPresent(current -> {
			int newDurability = current + durability;
			if (newDurability <= 0) {
				mutable.setQuantity(0);
			} else {
				mutable.offer(Keys.ITEM_DURABILITY, current + durability);
			}
		});
		return mutable;
	}
	
	
	
	public static void enchant(final ItemStack stack, final EnchantmentTypeSource type) {
		enchant(stack, type.getAsEnchantmentType());
	}
	public static void enchant(final ItemStack stack, final Supplier<EnchantmentType> type) {
		enchant(stack, type.get());
	}
	public static void enchant(final ItemStack stack, final EnchantmentType type) {
		enchantIfAbsent(stack, type, type.minimumLevel());
	}
	
	public static void enchantIfHigher(final ItemStack stack, final EnchantmentTypeSource type, final int level) {
		enchantIfHigher(stack, type.getAsEnchantmentType(), level);
	}
	public static void enchantIfHigher(final ItemStack stack, final Supplier<EnchantmentType> type, final int level) {
		enchantIfHigher(stack, type.get(), level);
	}
	public static void enchantIfHigher(final ItemStack stack, final EnchantmentType type, final int level) {
		enchantIfHigher(stack, Enchantment.of(type, level));
	}
	public static void enchantIfHigher(final ItemStack stack, final EnchantmentSource source) {
		enchantIfHigher(stack, source.getAsEnchantment());
	}
	public static void enchantIfHigher(final ItemStack stack, final Enchantment ench) {
		enchant(stack, ench, level -> level > ench.level());
	}
	
	public static void enchantIfLower(final ItemStack stack, final EnchantmentTypeSource type, final int level) {
		enchantIfLower(stack, type.getAsEnchantmentType(), level);
	}
	public static void enchantIfLower(final ItemStack stack, final Supplier<EnchantmentType> type, final int level) {
		enchantIfLower(stack, type.get(), level);
	}
	public static void enchantIfLower(final ItemStack stack, final EnchantmentType type, final int level) {
		enchantIfLower(stack, Enchantment.of(type, level));
	}
	public static void enchantIfLower(final ItemStack stack, EnchantmentSource source) {
		enchantIfLower(stack, source.getAsEnchantment());
	}
	public static void enchantIfLower(final ItemStack stack, Enchantment ench) {
		enchant(stack, ench, level -> level < ench.level());
	}
	
	public static void enchantIfAbsent(final ItemStack stack, final EnchantmentTypeSource type, final int level) {
		enchantIfAbsent(stack, type.getAsEnchantmentType(), level);
	}
	public static void enchantIfAbsent(final ItemStack stack, final Supplier<EnchantmentType> type, final int level) {
		enchantIfAbsent(stack, type.get(), level);
	}
	public static void enchantIfAbsent(final ItemStack stack, final EnchantmentType type, final int level) {
		enchantIfAbsent(stack, Enchantment.of(type, level));
	}
	public static void enchantIfAbsent(final ItemStack stack, final EnchantmentSource source) {
		enchantIfAbsent(stack, source.getAsEnchantment());
	}
	public static void enchantIfAbsent(final ItemStack stack, final Enchantment ench) {
		enchant(stack, ench, level -> false);
	}
	
	public static void enchantOrReplace(final ItemStack stack, final Supplier<EnchantmentType> type, final int level) {
		enchantOrReplace(stack, type.get(), level);
	}
	public static void enchantOrReplace(final ItemStack stack, final EnchantmentTypeSource type, final int level) {
		enchantOrReplace(stack, type.getAsEnchantmentType(), level);
	}
	public static void enchantOrReplace(final ItemStack stack, final EnchantmentType type, final int level) {
		enchantOrReplace(stack, Enchantment.of(type, level));
	}
	public static void enchantOrReplace(final ItemStack stack, final EnchantmentSource source) {
		enchantOrReplace(stack, source.getAsEnchantment());
	}
	public static void enchantOrReplace(final ItemStack stack, final Enchantment ench) {
		enchant(stack, ench, level -> true);
	}
	
	private static void enchant(final ItemStack stack, final Enchantment ench, final Predicate<Integer> shouldReplace) {
		EnchantmentType type = ench.type();
		List<Enchantment> list = stack.get(Keys.APPLIED_ENCHANTMENTS).orElseGet(ArrayList::new);
		for (int i = 0, s = list.size(); i < s; ++i) {
			Enchantment e = list.get(i);
			if (e.type() == type) {
				if (shouldReplace.test(e.level())) {
					list.set(i, ench);
					stack.offer(Keys.APPLIED_ENCHANTMENTS, list);
				}
				return;
			}
		}
		
		list.add(ench);
		stack.offer(Keys.APPLIED_ENCHANTMENTS, list);
	}
	
	
	public static boolean has(DataHolder stack, EnchantmentTypeSource type) {
		return has(stack, type.getAsEnchantmentType());
	}
	public static boolean has(DataHolder stack, Supplier<EnchantmentType> type) {
		return has(stack, type.get());
	}
	public static boolean has(DataHolder stack, EnchantmentType type) {
		return has(stack, type, 1);
	}
	
	public static boolean has(DataHolder stack, EnchantmentTypeSource type, int level) {
		return has(stack, type.getAsEnchantmentType(), level);
	}
	public static boolean has(DataHolder stack, Supplier<EnchantmentType> type, int level) {
		return has(stack, type.get(), level);
	}
	public static boolean has(DataHolder stack, EnchantmentType type, int level) {
		return level(stack, type) >= level;
	}
	
	public static int level(DataHolder stack, EnchantmentTypeSource type) {
		return level(stack, type.getAsEnchantmentType());
	}
	public static int level(DataHolder stack, Supplier<EnchantmentType> type) {
		return level(stack, type.get());
	}
	public static int level(DataHolder stack, EnchantmentType type) {
		Optional<List<Enchantment>> opt = stack.get(Keys.APPLIED_ENCHANTMENTS);
		if (opt.isEmpty()) {
			return 0;
		}
		
		for (Enchantment ench : opt.get()) {
			if (ench.type() == type) {
				return ench.level();
			}
		}
		
		return 0;
	}
	
	public static int levelFirst(DataHolder stack, EnchantmentTypeSource... types) {
		return levelFirst(stack, unpack(types));
	}
	@SafeVarargs
	public static int levelFirst(DataHolder stack, Supplier<EnchantmentType>... types) {
		return levelFirst(stack, unpack(types));
	}
	public static int levelFirst(DataHolder stack, EnchantmentType... types) {
		Optional<List<Enchantment>> opt = stack.get(Keys.APPLIED_ENCHANTMENTS);
		if (opt.isEmpty()) {
			return 0;
		}
		
		for (EnchantmentType type : types) {
			for (Enchantment ench : opt.get()) {
				if (ench.type() == type) {
					return ench.level();
				}
			}
		}
		
		return 0;
	}
	
	public static int levelMax(DataHolder stack, EnchantmentTypeSource... types) {
		return levelMax(stack, unpack(types));
	}
	@SafeVarargs
	public static int levelMax(DataHolder stack, Supplier<EnchantmentType>... types) {
		return levelMax(stack, unpack(types));
	}
	public static int levelMax(DataHolder stack, EnchantmentType... types) {		
		Optional<List<Enchantment>> opt = stack.get(Keys.APPLIED_ENCHANTMENTS);
		if (opt.isEmpty()) {
			return 0;
		}
		
		int max = 0;
		for (Enchantment ench : opt.get()) {
			if (ench.level() <= max) {
				continue;
			}
			
			for (EnchantmentType type : types) {
				if (ench.type() == type) {
					max = ench.level();
					break;
				}
			}
		}
		
		return 0;
	}
	
	private static EnchantmentType[] unpack(EnchantmentTypeSource... sources) {
		int size = sources.length;
		EnchantmentType[] types = new EnchantmentType[size];
		for (int i = 0; i < size; ++i) {
			types[i] = sources[i].getAsEnchantmentType();
		}
		return types;
	}
	@SafeVarargs
	private static EnchantmentType[] unpack(Supplier<EnchantmentType>... sups) {
		int size = sups.length;
		EnchantmentType[] types = new EnchantmentType[size];
		for (int i = 0; i < size; ++i) {
			types[i] = sups[i].get();
		}
		return types;
	}
	
	
	
	public static boolean tryShrink(Inventory inv, IItemSource type) {
		return tryShrinkAndCheck(inv, type, 1);
	}
	public static boolean tryShrink(Inventory inv, Supplier<ItemType> type) {
		return tryShrinkAndCheck(inv, type, 1);
	}
	public static boolean tryShrink(Inventory inv, ItemType type) {
		return tryShrinkAndCheck(inv, type, 1);
	}
	public static boolean tryShrink(Inventory inv, Predicate<ItemStack> pred) {
		return tryShrinkAndCheck(inv, pred, 1);
	}
	
	public static boolean tryShrinkAndCheck(Inventory inv, IItemSource type, int amount) {
		return tryShrink(inv, type, amount) == amount;
	}
	public static boolean tryShrinkAndCheck(Inventory inv, Supplier<ItemType> type, int amount) {
		return tryShrink(inv, type, amount) == amount;
	}
	public static boolean tryShrinkAndCheck(Inventory inv, ItemType type, int amount) {
		return tryShrink(inv, type, amount) == amount;
	}
	public static boolean tryShrinkAndCheck(Inventory inv, Predicate<ItemStack> pred, int amount) {
		return tryShrink(inv, pred, amount) == amount;
	}
	
	public static int tryShrink(Inventory inv, IItemSource type, int amount) {
		return tryShrink(inv, type.getAsItemType(), amount);
	}
	public static int tryShrink(Inventory inv, Supplier<ItemType> type, int amount) {
		return tryShrink(inv, type.get(), amount);
	}
	public static int tryShrink(Inventory inv, ItemType type, int amount) {
		return tryShrink(inv, stack -> is(stack, type), amount);
	}
	public static int tryShrink(Inventory inv, Predicate<ItemStack> pred, int amount) {
		if (inv == null) {
			return 0;
		}
		
		int consumed = 0;
		for (Slot slot : inv.slots()) {
			ItemStack stack = slot.peek();
			if (pred.test(stack)) {
				int quantity = stack.quantity();
				int toConsume = Math.min(amount - consumed, quantity);
				
				consumed += toConsume;
				stack.setQuantity(quantity - toConsume);
				slot.set(stack);
				
				if (consumed == amount) {
					break;
				}
			}
		}
		
		return consumed;
	}
	
	
	public static boolean contains(Inventory inv, Supplier<ItemType> type) {
		return contains(inv, type, 1);
	}
	public static boolean contains(Inventory inv, ItemType type) {
		return contains(inv, type, 1);
	}
	public static boolean contains(Inventory inv, Predicate<ItemStack> pred) {
		return contains(inv, pred, 1);
	}
	
	public static boolean contains(Inventory inv, Supplier<ItemType> type, int amount) {
		return contains(inv, type.get(), amount);
	}
	public static boolean contains(Inventory inv, ItemType type, int amount) {
		return contains(inv, stack -> is(stack, type), amount);
	}
	public static boolean contains(Inventory inv, Predicate<ItemStack> pred, int amount) {
		if (inv == null) {
			return false;
		}
		
		int count = 0;
		for (Slot slot : inv.slots()) {
			ItemStack stack = slot.peek();
			if (pred.test(stack)) {
				count += stack.quantity();
				if (count >= amount) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private ItemUtil() {
	}
}
