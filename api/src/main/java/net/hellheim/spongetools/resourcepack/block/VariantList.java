package net.hellheim.spongetools.resourcepack.block;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.hellheim.spongetools.codec.list.ExtraCodecs;

public record VariantList(Set<Variant> variants) implements VariantListLike {
	
	public static final Codec<VariantList> CODEC = ExtraCodecs.setOf(Variant.CODEC)
			.xmap(VariantList::new, VariantList::variants)
			.validate(VariantList::validate);
	
	public VariantList(final Set<Variant> variants) {
		this.variants = Set.copyOf(variants);
	}
	
	public static VariantList of(final Stream<Variant> variants) {
		return VariantList.validate(new VariantList(variants.collect(Collectors.toSet())))
				.getOrThrow(IllegalArgumentException::new);
	}
	
	public static VariantList of(final Iterable<Variant> variants) {
		return VariantList.of(Streams.stream(variants));
	}
	
	public static VariantList of(final Variant... variants) {
		return VariantList.of(Arrays.stream(variants));
	}
	
	private static DataResult<VariantList> validate(final VariantList variants) {
		return variants.variants().isEmpty()
				? DataResult.error(() -> "At least one variant must be provided")
				: DataResult.success(variants);
	}
	
	@Override
	public VariantList asVariantList() {
		return this;
	}
}
