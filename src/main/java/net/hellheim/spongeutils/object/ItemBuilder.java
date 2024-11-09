package net.hellheim.spongeutils.object;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongeutils.CompUtil;
import net.hellheim.spongeutils.ItemUtil;
import net.hellheim.spongeutils.source.solid.EnchantmentSource;
import net.hellheim.spongeutils.source.solid.EnchantmentTypeSource;
import net.hellheim.spongeutils.source.solid.data.TransitiveMutableDataHolderSource;
import net.hellheim.spongeutils.source.solid.item.IItemSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;

public class ItemBuilder implements IItemSource, TransitiveMutableDataHolderSource<ItemBuilder> {
	
	private static final Style FALLBACK_STYLE = Style.empty()
			.color(NamedTextColor.WHITE)
			.decoration(TextDecoration.ITALIC, false);
	
	private Component applyFallback(Component line) {
		if (line.color() == null || line.decoration(TextDecoration.ITALIC) == State.NOT_SET) {
			return line.applyFallbackStyle(FALLBACK_STYLE);
		}
		
		return line;
	}
	
	
	
	private final ItemStack stack;
	
	private ItemBuilder(final ItemStack stack) {
		this.stack = stack;
		this.hideFlags();
	}
	private ItemBuilder(final ItemType type) {
		this(ItemUtil.stackOf(type));
	}
	private ItemBuilder(final ItemStackSnapshot snapshot) {
		this(snapshot.createStack());
	}
	
	
	
	public static ItemBuilder of(final ItemType type) {
		return new ItemBuilder(type);
	}
	
	public static ItemBuilder of(final Supplier<ItemType> type) {
		return new ItemBuilder(type.get());
	}
	
	public static ItemBuilder of(final ItemStack stack) {
		return new ItemBuilder(stack.copy());
	}
	
	public static ItemBuilder of(final ItemStackSnapshot snapshot) {
		return new ItemBuilder(snapshot);
	}
	
	public static ItemBuilder of(final IItemSource source) {
		return new ItemBuilder(source.getAsItemStack());
	}
	
	
	
	
	@Override
	public ItemType getAsItemType() {
		return this.stack.type();
	}
	
	@Override
	public ItemStack getAsItemStack() {
		return this.stack.copy();
	}
	
	@Override
	public ItemStackSnapshot getAsItemStackSnapshot() {
		return this.stack.createSnapshot();
	}
	
	@Override
	public ItemBuilder itemBuilder() {
		return new ItemBuilder(this.stack.copy());
	}
	
	@Override
	public DataHolder.Mutable getAsDataHolder() {
		return this.stack;
	}
	
	public ItemStack get() {
		return this.stack;
	}
	
	
	private ItemBuilder hideFlags() {
		this.stack.offer(Keys.HIDE_ATTRIBUTES, true);
		this.stack.offer(Keys.HIDE_CAN_DESTROY, true);
		this.stack.offer(Keys.HIDE_CAN_PLACE, true);
		//this.stack.offer(Keys.HIDE_ENCHANTMENTS, true);
		this.stack.offer(Keys.HIDE_UNBREAKABLE, true);
		this.stack.offer(Keys.HIDE_MISCELLANEOUS, true);
		return this;
	}
	
	
	
	public ItemBuilder colorDisplayName(TextColor color) {
		this.stack.transform(Keys.CUSTOM_NAME, name -> name.color(color));
		return this;
	}
	
	public ItemBuilder colorDisplayNameIfAbsent(TextColor color) {
		this.stack.transform(Keys.CUSTOM_NAME, name -> name.colorIfAbsent(color));
		return this;
	}
	
	public ItemBuilder appendDisplayName(Component name) {
		this.offer(Keys.CUSTOM_NAME, this.getOrElse(Keys.CUSTOM_NAME, Component.empty()).append(name));
		return this;
	}
	
	public ItemBuilder miniDisplayName(String name) {
		return this.displayName(CompUtil.fromMini(name));
	}
	
	public ItemBuilder plainDisplayName(String name) {
		return this.displayName(CompUtil.fromPlain(name));
	}
	
	public ItemBuilder displayName(Component name) {
		this.offer(Keys.CUSTOM_NAME, applyFallback(name));
		return this;
	}
	
	
	
	public ItemBuilder loreMini(String... lore) {
		return this.lore(CompUtil.fromMinis(lore));
	}
	
	public ItemBuilder lorePlain(String... lore) {
		return this.lore(CompUtil.fromPlains(lore));
	}
	
	public ItemBuilder lore(Component... lore) {
		List<Component> formatted = new ArrayList<>();
		for (Component line : lore) {
			formatted.add(applyFallback(line));
		}
		return this.offerLore(formatted);
	}
	
	public ItemBuilder lore(List<? extends Component> lore) {
		List<Component> formatted = new ArrayList<>();
		for (Component line : lore) {
			formatted.add(applyFallback(line));
		}
		return this.offerLore(formatted);
	}
	
	
	public ItemBuilder addLoreMini(String... lore) {
		return this.addLore(CompUtil.fromMinis(lore));
	}
	
	public ItemBuilder addLoreMini(List<String> lore) {
		return this.addLore(CompUtil.fromMinis(lore));
	}
	
	public ItemBuilder addLorePlain(String... lore) {
		return this.addLore(CompUtil.fromPlains(lore));
	}
	
	public ItemBuilder addLorePlain(List<String> lore) {
		return this.addLore(CompUtil.fromPlains(lore));
	}
	
	public ItemBuilder addLoreEmptyLine() {
		List<Component> lore = this.lore();
		lore.add(Component.empty());
		return this.offerLore(lore);
	}
	
	public ItemBuilder addLore(Component... lore) {
		List<Component> list = this.lore();
		for (Component line : lore) {
			list.add(applyFallback(line));
		}
		return this.offerLore(list);
	}
	
	public ItemBuilder addLore(List<? extends Component> lore) {
		List<Component> list = this.lore();
		for (Component line : lore) {
			list.add(applyFallback(line));
		}
		return this.offerLore(list);
	}
	
	
	
	private ItemBuilder offerLore(List<Component> lore) {
		this.offer(Keys.LORE, lore);
		return this;
	}
	
	public List<Component> lore() {
		return this.stack.get(Keys.LORE).orElseGet(ArrayList::new);
	}
	
	
	
	public ItemBuilder setGlow() {
		this.offer(Keys.HIDE_ENCHANTMENTS, true);
		this.offerSingle(Keys.APPLIED_ENCHANTMENTS, Enchantment.of(EnchantmentTypes.CHANNELING.get(), 1));
		return this;
	}
	
	public ItemBuilder quantity(int quantity) {
		this.stack.setQuantity(Math.min(this.stack.maxStackQuantity(), quantity));
		return this;
	}
	
	
	
	// Enchantment
	
	public ItemBuilder enchant(final EnchantmentTypeSource type) {
		ItemUtil.enchant(this.stack, type);
		return this;
	}
	public ItemBuilder enchant(final Supplier<EnchantmentType> type) {
		ItemUtil.enchant(this.stack, type);
		return this;
	}
	public ItemBuilder enchant(final EnchantmentType type) {
		ItemUtil.enchant(this.stack, type);
		return this;
	}
	
	public ItemBuilder enchantIfHigher(final EnchantmentTypeSource type, final int level) {
		ItemUtil.enchantIfHigher(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfHigher(final Supplier<EnchantmentType> type, final int level) {
		ItemUtil.enchantIfHigher(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfHigher(final EnchantmentType type, final int level) {
		ItemUtil.enchantIfHigher(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfHigher(final EnchantmentSource source) {
		ItemUtil.enchantIfHigher(this.stack, source);
		return this;
	}
	public ItemBuilder enchantIfHigher(final Enchantment ench) {
		ItemUtil.enchantIfHigher(this.stack, ench);
		return this;
	}
	
	public ItemBuilder enchantIfLower(final EnchantmentTypeSource type, final int level) {
		ItemUtil.enchantIfLower(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfLower(final Supplier<EnchantmentType> type, final int level) {
		ItemUtil.enchantIfLower(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfLower(final EnchantmentType type, final int level) {
		ItemUtil.enchantIfLower(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfLower(final EnchantmentSource source) {
		ItemUtil.enchantIfLower(this.stack, source);
		return this;
	}
	public ItemBuilder enchantIfLower(final Enchantment ench) {
		ItemUtil.enchantIfLower(this.stack, ench);
		return this;
	}
	
	public ItemBuilder enchantIfAbsent(final EnchantmentTypeSource type, final int level) {
		ItemUtil.enchantIfAbsent(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfAbsent(final Supplier<EnchantmentType> type, final int level) {
		ItemUtil.enchantIfAbsent(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfAbsent(final EnchantmentType type, final int level) {
		ItemUtil.enchantIfAbsent(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantIfAbsent(final EnchantmentSource source) {
		ItemUtil.enchantIfAbsent(this.stack, source);
		return this;
	}
	public ItemBuilder enchantIfAbsent(final Enchantment ench) {
		ItemUtil.enchantIfAbsent(this.stack, ench);
		return this;
	}
	
	public ItemBuilder enchantOrReplace(final EnchantmentTypeSource type, final int level) {
		ItemUtil.enchantOrReplace(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantOrReplace(final Supplier<EnchantmentType> type, final int level) {
		ItemUtil.enchantOrReplace(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantOrReplace(final EnchantmentType type, final int level) {
		ItemUtil.enchantOrReplace(this.stack, type, level);
		return this;
	}
	public ItemBuilder enchantOrReplace(final EnchantmentSource source) {
		ItemUtil.enchantOrReplace(this.stack, source);
		return this;
	}
	public ItemBuilder enchantOrReplace(final Enchantment ench) {
		ItemUtil.enchantOrReplace(this.stack, ench);
		return this;
	}
}
