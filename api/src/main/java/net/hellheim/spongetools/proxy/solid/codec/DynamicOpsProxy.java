package net.hellheim.spongetools.proxy.solid.codec;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;

public interface DynamicOpsProxy<T> extends DynamicOps<T> {
	
	DynamicOps<T> getAsOps();
	
	@Override
	default T empty() {
		return this.getAsOps().empty();
	}
	
	@Override
	default T emptyMap() {
		return this.getAsOps().emptyMap();
	}
	
	@Override
	default T emptyList() {
		return this.getAsOps().emptyList();
	}
	
	@Override
	default <U> U convertTo(final DynamicOps<U> outOps, final T input) {
		return this.getAsOps().convertTo(outOps, input);
	}
	
	@Override
	default DataResult<Number> getNumberValue(final T input) {
		return this.getAsOps().getNumberValue(input);
	}
	
	@Override
	default Number getNumberValue(final T input, final Number defaultValue) {
		return this.getAsOps().getNumberValue(input, defaultValue);
	}
	
	@Override
	default T createNumeric(final Number i) {
		return this.getAsOps().createNumeric(i);
	}
	
	@Override
	default T createByte(final byte value) {
		return this.getAsOps().createByte(value);
	}
	
	@Override
	default T createShort(final short value) {
		return this.getAsOps().createShort(value);
	}
	
	@Override
	default T createInt(final int value) {
		return this.getAsOps().createInt(value);
	}
	
	@Override
	default T createLong(final long value) {
		return this.getAsOps().createLong(value);
	}
	
	@Override
	default T createFloat(final float value) {
		return this.getAsOps().createFloat(value);
	}
	
	@Override
	default T createDouble(final double value) {
		return this.getAsOps().createDouble(value);
	}
	
	@Override
	default DataResult<Boolean> getBooleanValue(final T input) {
		return this.getAsOps().getBooleanValue(input);
	}
	
	@Override
	default T createBoolean(final boolean value) {
		return this.getAsOps().createBoolean(value);
	}
	
	@Override
	default DataResult<String> getStringValue(final T input) {
		return this.getAsOps().getStringValue(input);
	}
	
	@Override
	default T createString(final String value) {
		return this.getAsOps().createString(value);
	}
	
	@Override
	default DataResult<T> mergeToList(final T list, final T value) {
		return this.getAsOps().mergeToList(list, value);
	}
	
	@Override
	default DataResult<T> mergeToList(final T list, final List<T> values) {
		return this.getAsOps().mergeToList(list, values);
	}
	
	@Override
	default DataResult<T> mergeToMap(final T map, final T key, final T value) {
		return this.getAsOps().mergeToMap(map, key, value);
	}
	
	@Override
	default DataResult<T> mergeToMap(final T map, final Map<T, T> values) {
		return this.getAsOps().mergeToMap(map, values);
	}
	
	@Override
	default DataResult<T> mergeToMap(final T map, final MapLike<T> values) {
		return this.getAsOps().mergeToMap(map, values);
	}
	
	@Override
	default DataResult<T> mergeToPrimitive(final T prefix, final T value) {
		return this.getAsOps().mergeToPrimitive(prefix, value);
	}
	
	@Override
	default DataResult<Stream<Pair<T, T>>> getMapValues(final T input) {
		return this.getAsOps().getMapValues(input);
	}
	
	@Override
	default DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(final T input) {
		return this.getAsOps().getMapEntries(input);
	}
	
	@Override
	default T createMap(final Stream<Pair<T, T>> map) {
		return this.getAsOps().createMap(map);
	}
	
	@Override
	default DataResult<MapLike<T>> getMap(final T input) {
		return this.getAsOps().getMap(input);
	}
	
	@Override
	default T createMap(final Map<T, T> map) {
		return this.getAsOps().createMap(map);
	}
	
	@Override
	default DataResult<Stream<T>> getStream(final T input) {
		return this.getAsOps().getStream(input);
	}
	
	@Override
	default DataResult<Consumer<Consumer<T>>> getList(final T input) {
		return this.getAsOps().getList(input);
	}
	
	@Override
	default T createList(final Stream<T> input) {
		return this.getAsOps().createList(input);
	}
	
	@Override
	default DataResult<ByteBuffer> getByteBuffer(final T input) {
		return this.getAsOps().getByteBuffer(input);
	}
	
	@Override
	default T createByteList(final ByteBuffer input) {
		return this.getAsOps().createByteList(input);
	}
	
	@Override
	default DataResult<IntStream> getIntStream(final T input) {
		return this.getAsOps().getIntStream(input);
	}
	
	@Override
	default T createIntList(final IntStream input) {
		return this.getAsOps().createIntList(input);
	}
	
	@Override
	default DataResult<LongStream> getLongStream(final T input) {
		return this.getAsOps().getLongStream(input);
	}
	
	@Override
	default T createLongList(final LongStream input) {
		return this.getAsOps().createLongList(input);
	}
	
	@Override
	default T remove(final T input, final String key) {
		return this.getAsOps().remove(input, key);
	}
	
	@Override
	default boolean compressMaps() {
		return this.getAsOps().compressMaps();
	}
	
	@Override
	default DataResult<T> get(final T input, final String key) {
		return this.getAsOps().get(input, key);
	}
	
	@Override
	default DataResult<T> getGeneric(final T input, final T key) {
		return this.getAsOps().getGeneric(input, key);
	}
	
	@Override
	default T set(final T input, final String key, final T value) {
		return this.getAsOps().set(input, key, value);
	}
	
	@Override
	default T update(final T input, final String key, final Function<T, T> function) {
		return this.getAsOps().update(input, key, function);
	}
	
	@Override
	default T updateGeneric(final T input, final T key, final Function<T, T> function) {
		return this.getAsOps().updateGeneric(input, key, function);
	}
	
	@Override
	default ListBuilder<T> listBuilder() {
		return this.getAsOps().listBuilder();
	}
	
	@Override
	default RecordBuilder<T> mapBuilder() {
		return this.getAsOps().mapBuilder();
	}
	
	@Override
	default <E> Function<E, DataResult<T>> withEncoder(final Encoder<E> encoder) {
		return this.getAsOps().withEncoder(encoder);
	}
	
	@Override
	default <E> Function<T, DataResult<Pair<E, T>>> withDecoder(final Decoder<E> decoder) {
		return this.getAsOps().withDecoder(decoder);
	}
	
	@Override
	default <E> Function<T, DataResult<E>> withParser(final Decoder<E> decoder) {
		return this.getAsOps().withParser(decoder);
	}
	
	@Override
	default <U> U convertList(final DynamicOps<U> outOps, final T input) {
		return this.getAsOps().convertList(outOps, input);
	}
	
	@Override
	default <U> U convertMap(final DynamicOps<U> outOps, final T input) {
		return this.getAsOps().convertMap(outOps, input);
	}
}
