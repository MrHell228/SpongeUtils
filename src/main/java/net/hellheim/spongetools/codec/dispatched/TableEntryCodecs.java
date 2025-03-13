package net.hellheim.spongetools.codec.dispatched;

import java.util.function.Function;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.weighted.EmptyObject;
import org.spongepowered.api.util.weighted.NestedTableEntry;
import org.spongepowered.api.util.weighted.RandomObjectTable;
import org.spongepowered.api.util.weighted.TableEntry;
import org.spongepowered.api.util.weighted.WeightedObject;
import org.spongepowered.api.util.weighted.WeightedSerializableObject;

import com.mojang.datafixers.Products.P1;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.hellheim.spongetools.codec.CodecMapperDispatcher;
import net.hellheim.spongetools.codec.MapCodecMapper;
import net.hellheim.spongetools.codec.SpongeCodecs;

public final class TableEntryCodecs {
	
	public static final CodecMapperDispatcher<ResourceKey, TableEntry<?>> DISPATCHER = new CodecMapperDispatcher<>();
	
	public static final Function<Codec<?>, Codec<? extends TableEntry<?>>> INLINE = codec -> RecordCodecBuilder.<WeightedObject<?>>create(
			instance -> TableEntryCodecs.builder(instance)
			.and(instance.group(TableEntryCodecs.objectField(codec)).t1())
					.apply(instance, (weight, value) -> new WeightedObject<>(value, weight))
			);
	
	public static final MapCodecMapper.M1<EmptyObject<?>> EMPTY = codec -> RecordCodecBuilder.mapCodec(
			instance -> TableEntryCodecs.builder(instance)
					.apply(instance, EmptyObject::new)
			);
	
	public static final MapCodecMapper.M1<NestedTableEntry<?>> NESTED_TABLE = codec -> RecordCodecBuilder.mapCodec(
			instance -> TableEntryCodecs.builder(instance)
					.and(instance.group(TableEntryCodecs.nestedField(codec)).t1())
					.apply(instance, NestedTableEntry::new)
			);
	
	public static final MapCodecMapper.M1<WeightedObject<?>> OBJECT = codec -> RecordCodecBuilder.mapCodec(
			instance -> TableEntryCodecs.builder(instance)
			.and(instance.group(TableEntryCodecs.objectField(codec)).t1())
					.apply(instance, (weight, value) -> new WeightedObject<>(value, weight))
			);
	
	@SuppressWarnings("unchecked")
	private static <T> RecordCodecBuilder<NestedTableEntry<?>, RandomObjectTable<T>> nestedField(
		final Codec<T> elementCodec
	) {
		return SpongeCodecs.randomObjectTable(elementCodec)
				.fieldOf("table")
				.forGetter(nested -> (RandomObjectTable<T>) nested.getNestedTable());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> RecordCodecBuilder<WeightedObject<?>, T> objectField(
		final Codec<T> elementCodec
	) {
		return elementCodec
				.fieldOf("value")
				.forGetter(object -> ((WeightedObject<T>) object).get());
	}
	
	private static <T extends TableEntry<?>> P1<Mu<T>, Double> builder(final Instance<T> instance) {
		return instance.group(
				Codec.doubleRange(0, Double.MAX_VALUE).optionalFieldOf("weight", 1.0D).forGetter(TableEntry::weight)
				);
	}
	
	public static <E> Codec<TableEntry<E>> inline(final Codec<E> elementCodec) {
		@SuppressWarnings("unchecked")
		final Codec<TableEntry<E>> result = (Codec<TableEntry<E>>) INLINE.apply(elementCodec);
		return result;
	}
	
	public static <E> Codec<TableEntry<E>> full(final Codec<E> elementCodec) {
		return DISPATCHER.castedCodec(SpongeCodecs.SPONGE_RESOURCE_KEY, elementCodec);
	}
	
	public static <E> Codec<TableEntry<E>> codec(final Codec<E> elementCodec) {
		return Codec.withAlternative(inline(elementCodec), full(elementCodec));
	}
	
	static {
		DISPATCHER.put(ResourceKey.sponge("empty"), EMPTY, EmptyObject.class);
		DISPATCHER.put(ResourceKey.sponge("nested"), NESTED_TABLE, NestedTableEntry.class);
		DISPATCHER.put(ResourceKey.sponge("object"), OBJECT, WeightedObject.class, WeightedSerializableObject.class);
	}
	
	private TableEntryCodecs() {
	}
}
