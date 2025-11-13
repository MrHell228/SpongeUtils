package net.hellheim.spongetools.resourcepack.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.mojang.serialization.Codec;

public interface VariantListLike extends Iterable<Variant> {
	
	Codec<VariantListLike> CODEC = VariantList.CODEC.xmap(Function.identity(), VariantListLike::asVariantList);
	
	VariantList asVariantList();
	
	@Override
	default Iterator<Variant> iterator() {
		return this.asVariantList().variants().iterator();
	}
	
	default VariantList merge(final VariantListLike variants) {
		final List<Variant> result = new ArrayList<>();
		this.forEach(first ->
			variants.forEach(second ->
				result.add(first.with(second))));
		return VariantList.of(result);
	}
}
