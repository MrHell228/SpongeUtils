package net.hellheim.spongetools.object;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.ValueContainer;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;

public class DeferredValueContainer implements ValueContainerProxy {
	
	public static final DeferredValueContainer EMPTY = DeferredValueContainer.of($ -> {});
	
	private final Consumer<ValueSetBuilder> configurator;
	private final Supplier<? extends ValueContainer> data;
	
	public DeferredValueContainer(final Consumer<ValueSetBuilder> configurator) {
		this.configurator = Objects.requireNonNull(configurator, "configurator");
		this.data = Suppliers.memoize(() -> {
			final ValueSetBuilder builder = new ValueSetBuilder();
			configurator.accept(builder);
			return builder.asImmutableManipulator();
		});
	}
	
	public static DeferredValueContainer of(final Consumer<ValueSetBuilder> configurator) {
		return new DeferredValueContainer(configurator);
	}
	
	public static Codec<DeferredValueContainer> codec(final ValueContainer keyLookupProvider) {
		return DeferredValueContainer.codec(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<DeferredValueContainer> codec(final Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
		return DataCodecs.valueSet(keyLookup).xmap(
				set -> DeferredValueContainer.of(builder -> builder.addAll(set)),
				data -> data.getAsData().getValues());
	}
	
	@Override
	public ValueContainer getAsData() {
		return this.data.get();
	}
	
	public Consumer<ValueSetBuilder> configurator() {
		return this.configurator;
	}
	
	public DeferredValueContainer withBefore(final Consumer<ValueSetBuilder> before) {
		Objects.requireNonNull(before, "before");
		return DeferredValueContainer.of(before.andThen(this.configurator));
	}
	
	public DeferredValueContainer withAfter(final Consumer<ValueSetBuilder> after) {
		Objects.requireNonNull(after, "after");
		return DeferredValueContainer.of(this.configurator.andThen(after));
	}
}
