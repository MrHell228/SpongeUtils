package net.hellheim.spongetools.resourcepack.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.state.StateContainer;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface BlockDefinition {
	
	static MultiVariant.Builder multiVariant(final Supplier<? extends StateContainer<?>> containerSupplier) {
		return BlockDefinition.multiVariant(Objects.requireNonNull(containerSupplier, "containerSupplier").get());
	}
	
	static MultiVariant.Builder multiVariant(final StateContainer<?> container) {
		return new MultiVariant.Builder(containerSupplier);
	}
	
	final record MultiVariant(StateDispatch dispatch) implements BlockDefinition {
		
		public static final Codec<MultiVariant> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						StateDispatch.CODEC.fieldOf("variants").forGetter(null)
						).apply(null, null)).validate(null);
		
		public MultiVariant(final StateDispatch dispatch) {
			this.dispatch = Objects.requireNonNull(dispatch, "dispatch");
		}
		
		public Builder toBuilder(final StateContainer<?> container) {
			return BlockDefinition.multiVariant(container).from(this);
		}
		
		public MultiVariant withDispatch(final UnaryOperator<StateDispatch> dispatchOperator) {
			Objects.requireNonNull(dispatchOperator, "dispatchOperator");
			return new MultiVariant(this.block, dispatchOperator.apply(this.dispatch));
		}
		
		public static final class Builder implements BlockDefinition.Builder<MultiVariant, Builder> {
			
			private final StateContainer<?> container;
			private final List<Variant> baseVariants = new ArrayList<>();
			private final List<StateDispatch> dispatches = new ArrayList<>();
			private final Set<StateProperty<?>> seenProperties = new HashSet<>();
			
			private Builder(StateContainer<?> container) {
				this.container = Objects.requireNonNull(container, "container");
				this.reset();
			}
			
			public Builder base(final Variant... variants) {
				for (final Variant variant : Objects.requireNonNull(variants, "variants")) {
					this.baseVariants.add(Objects.requireNonNull(variant, "variant"));
				}
				return this;
			}
			
			public Builder base(final Iterable<? extends Variant> variants) {
				for (final Variant variant : Objects.requireNonNull(variants, "variants")) {
					this.baseVariants.add(Objects.requireNonNull(variant, "variant"));
				}
				return this;
			}
			
			public Builder dispatch(final StateDispatch.Builder<?>... builders) {
				for (final StateDispatch.Builder<?> builder : Objects.requireNonNull(builders, "builders")) {
					this.tryDispatch(Objects.requireNonNull(builder, "builder").build());
				}
				return this;
			}
			
			public Builder dispatch(final StateDispatch... dispatches) {
				for (final StateDispatch dispatch : Objects.requireNonNull(dispatches, "dispatches")) {
					this.tryDispatch(dispatch);
				}
				return this;
			}
			
			public Builder dispatch(final Iterable<? extends StateDispatch> dispatches) {
				for (final StateDispatch dispatch : Objects.requireNonNull(dispatches, "dispatches")) {
					this.tryDispatch(dispatch);
				}
				return this;
			}
			
			private void tryDispatch(final StateDispatch dispatch) {
				Objects.requireNonNull(dispatch, "dispatch");
				for (final StateProperty<?> property : dispatch.properties()) {
					if (this.container.findStateProperty(property.name()).orElse(null) != property) {
						throw new IllegalStateException("Property " + property + " is not defined for container " + this.container);
					} else if (!this.seenProperties.add(property)) {
						throw new IllegalStateException("Values of property " + property + " already defined for container " + this.container);
					}
				}
				
				this.dispatches.add(dispatch);
			}
			
			@Override
			public Builder from(final MultiVariant definition) {
				return this.reset().dispatch(Objects.requireNonNull(definition, "definition").dispatch());
			}
			
			@Override
			public Builder reset() {
				this.baseVariants.clear();
				this.dispatches.clear();
				this.seenProperties.clear();
				return this;
			}
			
			@Override
			public MultiVariant build() {
				Stream<Pair<StateSelector, List<Variant>>> stream = Stream.of(Pair.of(StateSelector.empty(), this.baseVariants));
				for (final StateDispatch dispatch : this.dispatches) {
					final Map<StateSelector, List<Variant>> values = dispatch.values();
					stream = stream.flatMap(pair -> {
						return values.entrySet().stream().map(entry -> {
							final StateSelector selector = pair.getFirst().with(entry.getKey());
							final List<Variant> variants = mergeVariants(pair.getSecond(), entry.getValue());
							return Pair.of(selector, variants);
						});
					});
				}
				
				final var builder = StateDispatch.raw();
				stream.forEach(pair -> builder.add(pair.getFirst(), pair.getSecond()));
				return new MultiVariant(builder.build());
			}
			
			private static List<Variant> mergeVariants(
				final List<Variant> firstVariants, final List<Variant> secondVariants
			) {
				final List<Variant> variants = new ArrayList<>();
				firstVariants.forEach(first ->
						secondVariants.forEach(second ->
								variants.add(first.with(second))));
				return variants;
			}
		}
	}
	
	record MultiPart() implements BlockDefinition {
		
		
		
	}
	
	interface Builder<D extends BlockDefinition, B extends Builder<D, B>> extends
			org.spongepowered.api.util.Builder<D, B>,
			CopyableBuilder<D, Builder<D, B>> {
	}
}
