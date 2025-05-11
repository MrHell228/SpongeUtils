package net.hellheim.spongetools.resourcepack.block;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.LateBoundIdMapper;

public record VariantProperty<T>(String name, Codec<T> valueCodec, Optional<T> defaultValue)
		implements StringRepresentable {
	
	private static final LateBoundIdMapper<String, VariantProperty<?>> ID_MAPPER = new LateBoundIdMapper<>();
	
	public static final Codec<VariantProperty<?>> CODEC = VariantProperty.ID_MAPPER.codec(Codec.STRING);
	
	public VariantProperty(final String name, final Codec<T> valueCodec, final Optional<T> defaultValue) {
		this.name = Objects.requireNonNull(name, "name").toLowerCase();
		this.valueCodec = Objects.requireNonNull(valueCodec, "valueCodec");
		this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
		VariantProperty.ID_MAPPER.put(this.name, this);
	}
	
	public VariantPropertyValue<T> with(final T value) {
		return VariantPropertyValue.of(this, value);
	}
	
	public VariantPropertyValue<T> with(final Supplier<? extends T> valueSupplier) {
		return VariantPropertyValue.of(this, valueSupplier);
	}
	
	@Override
	public String serializationString() {
		return this.name;
	}
}
