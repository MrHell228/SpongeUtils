package net.hellheim.spongetools.custom.block;

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
import org.spongepowered.api.data.type.SlabPortions;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.state.BooleanStateProperties;
import org.spongepowered.api.state.EnumStateProperties;
import org.spongepowered.api.tag.BlockTypeTags;

public interface BlockStateProvider/* extends MapCodecProxy<BlockStateProvider>*/ {
	
	static Any any(final BlockStateProvider... providers) {
		return new Any(List.of(providers));
	}
	
	static Any any(final Collection<? extends BlockStateProvider> providers) {
		return new Any(List.copyOf(providers));
	}
	
	@SafeVarargs
	static AnyBlock anyBlock(final Supplier<? extends BlockType>... blocks) {
		return new AnyBlock(Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toUnmodifiableSet()));
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
				.orElseThrow(() -> new NoAvailableStateException(this.toString()));
	}
	
	default Stream<BlockState> availableStates() {
		return this.allStates().filter(BlockStateRegistrar::isAvailable);
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
			return this.providers.toString();
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
			return this.blocks.toString();
		}
	}
	
	record Slab() implements BlockStateProvider {
		
		public static final Slab INSTANCE = new Slab();
		
		@Override
		public Stream<BlockState> allStates() {
			return RegistryTypes.BLOCK_TYPE.get().taggedValues(BlockTypeTags.SLABS).stream()
					.flatMap(block -> block.validStates().stream())
					.filter(state -> state
							.stateProperty(EnumStateProperties.property_SLAB_TYPE()).orElse(null) == SlabPortions.DOUBLE.get())
					.filter(state -> state
							.stateProperty(BooleanStateProperties.property_WATERLOGGED()).orElse(false));
		}
	}
}
