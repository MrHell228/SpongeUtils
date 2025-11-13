package net.hellheim.spongetools.resourcepack.block;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.util.ModelUtil;

public final class Variant implements VariantListLike {
	
	public static final Codec<Variant> CODEC = VariantPropertyValue.LIST_CODEC.xmap(
			list -> Variant.builder().addAll(list).build(),
			variant -> List.copyOf(variant.values())
			);
	
	private static final Variant EMPTY = new Variant(Map.of());
	
	private final Map<VariantProperty<?>, VariantPropertyValue<?>> values;
	
	private Variant(final Map<VariantProperty<?>, VariantPropertyValue<?>> values) {
		this.values = Map.copyOf(values);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static Variant empty() {
		return Variant.EMPTY;
	}
	
	public static Variant model(final ResourceKey model) {
		return Variant.builder().add(VariantProperties.MODEL, model).build();
	}
	
	public static Variant prefixedModel(final ResourceKey model) {
		return Variant.model(ModelUtil.withBlockPrefix(model));
	}
	
	public Set<VariantProperty<?>> properties() {
		return this.values.keySet();
	}
	
	public Collection<VariantPropertyValue<?>> values() {
		return this.values.values();
	}
	
	public Builder toBuilder() {
		return Variant.builder().from(this);
	}
	
	public <T> Variant with(final VariantProperty<T> property, final T value) {
		return this.toBuilder().add(property, value).build();
	}
	
	public <T> Variant with(final VariantProperty<T> property, final Supplier<? extends T> valueSupplier) {
		return this.toBuilder().add(property, valueSupplier).build();
	}
	
	public Variant with(final VariantPropertyValue<?> value) {
		return this.toBuilder().add(value).build();
	}
	
	public Variant with(final Variant variant) {
		return this.toBuilder().addAll(variant).build();
	}
	
	@Override
	public VariantList asVariantList() {
		return VariantList.of(this);
	}
	
	@Override
	public int hashCode() {
		return this.values.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj.getClass() != this.getClass()) {
			return false;
		} else {
			return this.values.equals(((Variant) obj).values);
		}
	}
	
	@Override
	public String toString() {
		return this.values.values().stream()
				.map(VariantPropertyValue::toString)
				.collect(Collectors.joining(",", "Variant[", "]"));
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<Variant, Builder>,
			CopyableBuilder<Variant, Builder> {
		
		private final Map<VariantProperty<?>, VariantPropertyValue<?>> values = new HashMap<>();
		
		public Builder() {
			this.reset();
		}
		
		public <T> Builder add(final VariantProperty<T> property, final T value) {
			return this.add(VariantPropertyValue.of(property, value));
		}
		
		public <T> Builder add(final VariantProperty<T> property, final Supplier<? extends T> valueSupplier) {
			return this.add(VariantPropertyValue.of(property, valueSupplier));
		}
		
		public Builder add(final VariantPropertyValue<?> value) {
			Objects.requireNonNull(value, "value");
			this.values.put(value.property(), value);
			return this;
		}
		
		public Builder addAll(final VariantPropertyValue<?>... values) {
			for (final VariantPropertyValue<?> value : Objects.requireNonNull(values, "values")) {
				this.add(value);
			}
			return this;
		}
		
		public Builder addAll(final Iterable<? extends VariantPropertyValue<?>> values) {
			for (final VariantPropertyValue<?> value : Objects.requireNonNull(values, "values")) {
				this.add(value);
			}
			return this;
		}
		
		public Builder addAll(final Variant variant) {
			return this.addAll(Objects.requireNonNull(variant, "variant").values());
		}
		
		@Override
		public Builder from(final Variant variant) {
			return this.reset().addAll(variant);
		}
		
		@Override
		public Builder reset() {
			this.values.clear();
			return this;
		}
		
		@Override
		public Variant build() {
			return new Variant(this.values);
		}
	}
}
