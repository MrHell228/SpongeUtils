package net.hellheim.spongetools.resourcepack.block;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

public record VariantPropertyValue<T>(VariantProperty<T> property, T value) {
	
	private static final Codec<Map<VariantProperty<?>, Object>> MAP_CODEC = Codec.dispatchedMap(VariantProperty.CODEC, VariantProperty::valueCodec);
	
	public static final Codec<List<VariantPropertyValue<?>>> LIST_CODEC = VariantPropertyValue.MAP_CODEC.xmap(
			map -> map.entrySet().stream()
					.<VariantPropertyValue<?>>map(e -> {
						@SuppressWarnings("unchecked")
						final var property = (VariantProperty<Object>) e.getKey();
						return VariantPropertyValue.of(property, e.getValue());
					})
					.filter(v -> !v.isDefault())
					.collect(Collectors.toUnmodifiableList()),
			list -> list.stream()
					.filter(v -> !v.isDefault())
					.collect(Collectors.toUnmodifiableMap(VariantPropertyValue::property, VariantPropertyValue::value))
			);
	
	public VariantPropertyValue(final VariantProperty<T> property, final T value) {
		this.property = Objects.requireNonNull(property, "property");
		this.value = Objects.requireNonNull(value, "value");
	}
	
	public static <T> VariantPropertyValue<T> of(
		final VariantProperty<T> property, final T value
	) {
		return new VariantPropertyValue<>(property, value);
	}
	
	public static <T> VariantPropertyValue<T> of(
		final VariantProperty<T> property, final Supplier<? extends T> valueSupplier
	) {
		return VariantPropertyValue.of(property, Objects.requireNonNull(valueSupplier, "valueSupplier").get());
	}
	
	public boolean isDefault() {
		return this.property.defaultValue().map(this.value::equals).orElse(false);
	}
}
