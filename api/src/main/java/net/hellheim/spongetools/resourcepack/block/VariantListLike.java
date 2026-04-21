package net.hellheim.spongetools.resourcepack.block;

import java.util.ArrayList;
import java.util.List;

/**
 * Something that can be represented as a {@link VariantList}.
 */
public interface VariantListLike extends Iterable<Variant> {
	
	/**
	 * Gets the {@link VariantList} representation.
	 * 
	 * @return The variant list
	 */
	VariantList asVariantList();
	
	default VariantList merge(final VariantListLike variants) {
		final List<Variant> result = new ArrayList<>();
		this.forEach(first ->
			variants.forEach(second ->
				result.add(first.with(second))));
		return VariantList.of(result);
	}
}
