package net.hellheim.spongetools.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Suppliers;
import org.spongepowered.common.item.recipe.ingredient.SpongeIngredient;
import org.spongepowered.common.item.recipe.ingredient.SpongeItemList;

import com.mojang.datafixers.util.Either;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class SpongeTypeItemList extends SpongeItemList {
	
	public static final String TYPE_ITEM = "spongetools:item";
	
	private final HolderSet<Item> set;
    private final Supplier<List<ItemStack>> display;
	
	public SpongeTypeItemList(final HolderSet<Item> set, final Supplier<List<ItemStack>> display) {
		super();
		this.set = set;
        this.display = Suppliers.memoize(display::get);
	}
	
	public static SpongeIngredient ingredient(final HolderSet<Item> set, final Supplier<List<ItemStack>> display) {
		return new SpongeIngredient(
				SpongeTypeItemList.TYPE_ITEM,
				new SpongeTypeItemList(set, display),
				set.stream()
						.map(holder -> holder.unwrapKey().orElseThrow().identifier().toString())
						.collect(Collectors.joining(";")));
	}
	
	@Override
	public Stream<Holder<Item>> stream() {
		return this.set.stream();
	}
	
	@Override
	public int size() {
		return this.set.size();
	}
	
	@Override
	public boolean isBound() {
		return this.set.isBound();
	}
	
	@Override
	public Either<TagKey<Item>, List<Holder<Item>>> unwrap() {
		return this.set.unwrap();
	}
	
	@Override
	public Optional<Holder<Item>> getRandomElement(final RandomSource source) {
		return this.set.getRandomElement(source);
	}
	
	@Override
	public Holder<Item> get(final int index) {
		return this.set.get(index);
	}
	
	@Override
	public boolean contains(Holder<Item> holder) {
		return this.set.contains(holder);
	}
	
	@Override
	public boolean canSerializeIn(final HolderOwner<Item> owner) {
		return this.set.canSerializeIn(owner);
	}
	
	@Override
	public Optional<TagKey<Item>> unwrapKey() {
		return this.set.unwrapKey();
	}
	
	@Override
	public Iterator<Holder<Item>> iterator() {
		return this.set.iterator();
	}
	
	@Override
	public boolean test(final ItemStack stack) {
		return this.set.contains(stack.typeHolder());
	}

    @Override
    public Collection<ItemStack> getItems() {
        return this.display.get();
    }
}
