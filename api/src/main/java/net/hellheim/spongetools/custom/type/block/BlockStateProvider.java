package net.hellheim.spongetools.custom.type.block;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.SlabPortions;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.state.BooleanStateProperties;
import org.spongepowered.api.state.EnumStateProperties;
import org.spongepowered.api.state.IntegerStateProperties;

import net.hellheim.spongetools.resourcepack.block.StateCondition;

public interface BlockStateProvider/* extends MapCodecProxy<BlockStateProvider>*/ {
	
	static All all() {
		return All.INSTANCE;
	}
	
	static Any any(final BlockStateProvider... providers) {
		return new Any(List.of(providers));
	}
	
	static Any any(final Collection<? extends BlockStateProvider> providers) {
		return new Any(List.copyOf(providers));
	}
	
	@SafeVarargs
	static AnyBlock blocks(final RegistryKey<? extends BlockType>... blocks) {
		return new AnyBlock(Arrays.stream(blocks).map(RegistryKey::location).collect(Collectors.toSet()));
	}
	
	static AnyBlock blocks(final ResourceKey... blocks) {
		return new AnyBlock(Set.of(blocks));
	}
	
	static WithCondition withCondition(final BlockStateProvider provider, final StateCondition condition) {
		return new WithCondition(provider, condition);
	}
	
	static WithCondition slab() {
		return WithCondition.SLAB;
	}
	
	static WithCondition scaffolding(final boolean waterlogged, final boolean bottom) {
		return BlockStateProvider.withCondition(BlockStateProvider.blocks(BlockTypes.SCAFFOLDING),
				StateCondition.and(
						StateCondition.not(IntegerStateProperties.property_STABILITY_DISTANCE(), 0),
						StateCondition.is(BooleanStateProperties.property_WATERLOGGED(), waterlogged),
						StateCondition.is(BooleanStateProperties.property_BOTTOM(), bottom)
						)
				);
	}
	
	
	/**
	 * @throws NoAvailableStateException
	 */
	default BlockState provide() {
		return this.provide($ -> true);
	}
	
	/**
	 * @throws NoAvailableStateException
	 */
	default BlockState provide(final Predicate<? super BlockState> predicate) {
		return this.availableStates()
				.filter(predicate)
				.findAny()
				.orElseThrow(() -> new IllegalStateException(
						"No available states for provider " + this.toString()));
	}
	
	default Stream<BlockState> availableStates() {
		return this.allStates().filter(BlockStateDispatcher.get()::isAvailable);
	}
	
	Stream<BlockState> allStates();
	
	@Override
	String toString();
	
	
	record All() implements BlockStateProvider {
		
		private static final All INSTANCE = new All();
		
		@Override
		public Stream<BlockState> allStates() {
			return BlockTypes.registry().stream().flatMap(block -> block.validStates().stream());
		}
		
		@Override
		public final String toString() {
			return "All";
		}
	}
	
	record Any(List<BlockStateProvider> providers) implements BlockStateProvider {
		
		public Any(final List<BlockStateProvider> providers) {
			this.providers = List.copyOf(providers);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.providers.stream().flatMap(BlockStateProvider::allStates).distinct();
		}
		
		@Override
		public final String toString() {
			return String.format("AnyProvider[%s]",
					this.providers.stream().map(BlockStateProvider::toString).collect(Collectors.joining(",")));
		}
	}
	
	record AnyBlock(Set<ResourceKey> blocks) implements BlockStateProvider {
		
		public AnyBlock(final Set<ResourceKey> blocks) {
			this.blocks = Set.copyOf(blocks);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.blocks.stream()
					.<BlockType>map(BlockTypes.registry()::value)
					.flatMap(block -> block.validStates().stream());
		}
		
		@Override
		public String toString() {
			return String.format("AnyBlock[%s]",
					this.blocks.stream().map(ResourceKey::asString).collect(Collectors.joining(",")));
		}
	}
	
	record WithCondition(BlockStateProvider provider, StateCondition condition) implements BlockStateProvider {
		
		private static final WithCondition SLAB = withCondition(all(), StateCondition.and(
				StateCondition.is(EnumStateProperties.property_SLAB_TYPE(), SlabPortions.DOUBLE.get()),
				StateCondition.is(BooleanStateProperties.property_WATERLOGGED(), true)
				));
		
		public WithCondition(final BlockStateProvider provider, final StateCondition condition) {
			this.provider = Objects.requireNonNull(provider, "provider");
			this.condition = Objects.requireNonNull(condition, "condition");
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.provider.allStates().filter(this.condition::test);
		}
		
		@Override
		public final String toString() {
			return String.format("WithCondition[provider=%s;condition=%s]",
					this.provider, this.condition);
		}
	}
}
