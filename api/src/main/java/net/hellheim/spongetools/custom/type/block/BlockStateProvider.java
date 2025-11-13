package net.hellheim.spongetools.custom.type.block;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.SlabPortions;
import org.spongepowered.api.state.BooleanStateProperties;
import org.spongepowered.api.state.EnumStateProperties;

public interface BlockStateProvider/* extends MapCodecProxy<BlockStateProvider>*/ {
	
	static Any any(final BlockStateProvider... providers) {
		return new Any(List.of(providers));
	}
	
	static Any any(final Collection<? extends BlockStateProvider> providers) {
		return new Any(List.copyOf(providers));
	}
	
	static AnyState anyState(final BlockState... states) {
		return new AnyState(Set.of(states));
	}
	
	static AnyState anyState(final Collection<? extends BlockState> states) {
		return new AnyState(Set.copyOf(states));
	}
	
	@SafeVarargs
	static AnyBlock anyBlock(final Supplier<? extends BlockType>... blocks) {
		return new AnyBlock(Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toSet()));
	}
	
	static AnyBlock anyBlock(final BlockType... blocks) {
		return new AnyBlock(Set.of(blocks));
	}
	
	static AnyBlock anyBlock(final Collection<? extends BlockType> blocks) {
		return new AnyBlock(Set.copyOf(blocks));
	}
	
	static Slab slab() {
		return Slab.INSTANCE;
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
	
	
	record Any(List<? extends BlockStateProvider> providers) implements BlockStateProvider {
		
		public Any(final List<? extends BlockStateProvider> providers) {
			this.providers = List.copyOf(providers);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.providers.stream().flatMap(BlockStateProvider::allStates).distinct();
		}
		
		@Override
		public final String toString() {
			return "AnyProvider[" + this.providers.toString() + "]";
		}
	}
	
	record AnyState(Set<BlockState> states) implements BlockStateProvider {
		
		public AnyState(final Set<BlockState> states) {
			this.states = Set.copyOf(states);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.states.stream();
		}
		
		@Override
		public String toString() {
			return "AnyState[" + this.states.toString() + "]";
		}
	}
	
	record AnyBlock(Set<BlockType> blocks) implements BlockStateProvider {
		
		public AnyBlock(final Set<BlockType> blocks) {
			this.blocks = Set.copyOf(blocks);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.blocks.stream().flatMap(block -> block.validStates().stream());
		}
		
		@Override
		public String toString() {
			return "AnyBlock[" + this.blocks.toString() + "]";
		}
	}
	
	record Slab() implements BlockStateProvider {
		
		private static final Slab INSTANCE = new Slab();
		
		@Override
		public Stream<BlockState> allStates() {
			return BlockTypes.registry().stream()
					.flatMap(block -> block.validStates().stream())
					.filter(state -> state
							.stateProperty(EnumStateProperties.property_SLAB_TYPE()).orElse(null) == SlabPortions.DOUBLE.get())
					.filter(state -> state
							.stateProperty(BooleanStateProperties.property_WATERLOGGED()).orElse(false));
		}
		
		@Override
		public final String toString() {
			return "Slab";
		}
	}
}
