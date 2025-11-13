package net.hellheim.spongetools.resourcepack.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;

/**
 * @see <a href=https://minecraft.wiki/w/Blockstates_definition> Minecraft Wiki </a>
 */
public interface BlockDefinition {
	
	Codec<BlockDefinition> CODEC = StateCodec.BLOCK_DEFINITION;
	
	public static DefaultedRegistryType<BlockDefinition> registry() {
		return SpongeTools.Registries.BLOCK_DEFINITION;
	}
	
	public static Codec<BlockDefinition> registryCodec() {
		return RegistryCodecs.BLOCK_DEFINITION;
	}
	
	static MultiVariant.Builder variant(final Supplier<? extends BlockType> blockSupplier) {
		return BlockDefinition.variant(Objects.requireNonNull(blockSupplier, "blockSupplier").get());
	}
	
	static MultiVariant.Builder variant(final BlockType block) {
		return new MultiVariant.Builder(block);
	}
	
	static MultiPart.Builder part(final Supplier<? extends BlockType> blockSupplier) {
		return BlockDefinition.part(Objects.requireNonNull(blockSupplier, "blockSupplier").get());
	}
	
	static MultiPart.Builder part(final BlockType block) {
		return new MultiPart.Builder(block);
	}
	
	BlockType block();
	
	Builder<?, ?> toBuilder();
	
	BlockDefinition expandWith(BlockState state, Variant variant);
	
	final class MultiVariant implements BlockDefinition {
		
		public static final Codec<MultiVariant> CODEC = StateCodec.MULTI_VARIANT;
		
		private final BlockType block;
		private final StateDispatch<Variant> dispatch;
		
		private MultiVariant(final BlockType block, final StateDispatch<Variant> dispatch) {
			this.block = block;
			this.dispatch = dispatch;
		}
		
		@Override
		public BlockType block() {
			return this.block;
		}
		
		public StateDispatch<Variant> dispatch() {
			return this.dispatch;
		}
		
		@Override
		public Builder toBuilder() {
			return BlockDefinition.variant(this.block()).from(this);
		}
		
		@Override
		public MultiVariant expandWith(final BlockState state, final Variant variant) {
			return this.withDispatchBuilder(b -> b.expand(state, variant));
		}
		
		public MultiVariant withDispatchBuilder(final UnaryOperator<StateDispatch.Builder<Variant, ?>> dispatchBuilderOperator) {
			Objects.requireNonNull(dispatchBuilderOperator, "dispatchBuilderOperator");
			return this.withDispatch(dispatch -> dispatchBuilderOperator.apply(dispatch.toBuilder()).build());
		}
		
		public MultiVariant withDispatch(final UnaryOperator<StateDispatch<Variant>> dispatchOperator) {
			Objects.requireNonNull(dispatchOperator, "dispatchOperator");
			return new MultiVariant(this.block, dispatchOperator.apply(this.dispatch));
		}
		
		public static final class Builder implements BlockDefinition.Builder<MultiVariant, Builder> {
			
			private final BlockType block;
			private Variant baseVariant;
			private final List<StateDispatch<Variant>> dispatches = new ArrayList<>();
			private final Set<StateProperty<?>> seenProperties = new HashSet<>();
			
			private Builder(BlockType block) {
				this.block = Objects.requireNonNull(block, "block");
				this.reset();
			}
			
			public final Builder base(final Variant variant) {
				this.baseVariant = Objects.requireNonNull(variant, "variant");
				return this;
			}
			
			@SafeVarargs
			public final Builder dispatch(final StateDispatch.Builder<Variant, ?>... builders) {
				for (final StateDispatch.Builder<Variant, ?> builder : Objects.requireNonNull(builders, "builders")) {
					this.tryDispatch(Objects.requireNonNull(builder, "builder").build());
				}
				return this;
			}
			
			@SafeVarargs
			public final Builder dispatch(final StateDispatch<Variant>... dispatches) {
				for (final StateDispatch<Variant> dispatch : Objects.requireNonNull(dispatches, "dispatches")) {
					this.tryDispatch(dispatch);
				}
				return this;
			}
			
			public final Builder dispatch(final Iterable<? extends StateDispatch<Variant>> dispatches) {
				for (final StateDispatch<Variant> dispatch : Objects.requireNonNull(dispatches, "dispatches")) {
					this.tryDispatch(dispatch);
				}
				return this;
			}
			
			private final void tryDispatch(final StateDispatch<Variant> dispatch) {
				Objects.requireNonNull(dispatch, "dispatch");
				for (final StateProperty<?> property : dispatch.properties()) {
					if (!this.block.stateProperties().contains(property)) {
						throw new IllegalStateException("Property " + property + " is not defined for block " + this.block);
					} else if (!this.seenProperties.add(property)) {
						throw new IllegalStateException("Values of property " + property + " already defined for block " + this.block);
					}
				}
				
				this.dispatches.add(dispatch);
			}
			
			@Override
			public final Builder from(final MultiVariant definition) {
				return this.reset().dispatch(Objects.requireNonNull(definition, "definition").dispatch());
			}
			
			@Override
			public final Builder reset() {
				this.baseVariant = Variant.empty();
				this.dispatches.clear();
				this.seenProperties.clear();
				return this;
			}
			
			@Override
			public final MultiVariant build() {
				Stream<Pair<StateSelector, Variant>> stream = Stream.of(Pair.of(StateSelector.empty(), this.baseVariant));
				for (final StateDispatch<Variant> dispatch : this.dispatches) {
					final Map<StateSelector, Variant> values = dispatch.values();
					stream = stream.flatMap(pair -> {
						return values.entrySet().stream().map(entry -> {
							final StateSelector selector = pair.getFirst().with(entry.getKey());
							final Variant variant = pair.getSecond().with(entry.getValue());
							return Pair.of(selector, variant);
						});
					});
				}
				
				final StateDispatch.Builder<Variant, ?> builder = StateDispatch.raw();
				stream.forEach(pair -> builder.add(pair.getFirst(), pair.getSecond()));
				return new MultiVariant(this.block, builder.build());
			}
		}
	}
	
	final class MultiPart implements BlockDefinition {
		
		private final BlockType block;
		private final List<StatePart> parts;
		
		private MultiPart(final BlockType block, final List<StatePart> parts) {
			this.block = block;
			this.parts = List.copyOf(parts);
		}
		
		@Override
		public BlockType block() {
			return this.block;
		}
		
		public List<StatePart> parts() {
			return this.parts;
		}
		
		@Override
		public Builder toBuilder() {
			return BlockDefinition.part(this.block()).from(this);
		}
		
		@Override
		public BlockDefinition expandWith(final BlockState state, final Variant variant) {
			final Builder builder = BlockDefinition.part(this.block);
			final StateCondition isState = StateCondition.is(state);
			builder.add(StatePart.of(isState, variant));
			
			final StateCondition notState = isState.negate();
			this.parts.forEach(part -> {
				final Optional<StateCondition> oldCondition = part.condition();
				final StateCondition newCondition = oldCondition.isEmpty()
						? notState
						: StateCondition.and(oldCondition.get(), notState);
				builder.add(StatePart.of(newCondition, part.variant()));
			});
			
			return builder.build();
		}
		
		public static final class Builder implements BlockDefinition.Builder<MultiPart, Builder> {
			
			private final BlockType block;
			private final List<StatePart> parts = new ArrayList<>();
			private final Set<StateProperty<?>> seenProperties = new HashSet<>();
			
			private Builder(final BlockType block) {
				this.block = Objects.requireNonNull(block, "block");
			}
			
			public Builder add(final StatePart... parts) {
				for (final StatePart part : Objects.requireNonNull(parts, "parts")) {
					this.addPart(part);
				}
				
				return this;
			}
			
			public Builder add(final Iterable<? extends StatePart> parts) {
				for (final StatePart part : Objects.requireNonNull(parts, "parts")) {
					this.addPart(part);
				}
				
				return this;
			}
			
			private void addPart(final StatePart part) {
				Objects.requireNonNull(part, "part");
				if (part.condition().orElse(null) == StateCondition.alwaysFalse()) {
					// @see #build
					// return;
				}
				
				for (final StateProperty<?> property : part.properties()) {
					if (!this.block.stateProperties().contains(property)) {
						throw new IllegalStateException("Property " + property + " is not defined for block " + this.block);
					}
				}
			}
			
			@Override
			public Builder from(final MultiPart definition) {
				return this.reset().add(Objects.requireNonNull(definition, "definition").parts());
			}
			
			@Override
			public Builder reset() {
				this.parts.clear();
				this.seenProperties.clear();
				return this;
			}
			
			@Override
			public MultiPart build() {
				if (this.parts.isEmpty()) {
					// Client will throw exception if there is no
					// at least one StatePart with at least one Variant
					// even if this part will never apply (e.g. with StateCondition#alwaysFalse).
					// Maybe it's fine to just add always-false part if no parts are provided.
					throw new IllegalStateException("At least one StatePart must be provided");
				}
				
				return new MultiPart(this.block, this.parts);
			}
		}
	}
	
	interface Builder<D extends BlockDefinition, B extends Builder<D, B>> extends
			org.spongepowered.api.util.Builder<D, B>,
			CopyableBuilder<D, Builder<D, B>> {
	}
}
