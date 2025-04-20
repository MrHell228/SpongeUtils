package net.hellheim.spongetools.custom.block;

import java.util.Arrays;
import java.util.Collection;
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
	
	@SafeVarargs
	static Any any(final Supplier<? extends BlockType>... blocks) {
		return new Any(Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toUnmodifiableSet()));
	}
	
	static Any any(final BlockType... blocks) {
		return new Any(Set.of(blocks));
	}
	
	static Any any(final Collection<? extends BlockType> blocks) {
		return new Any(Set.copyOf(blocks));
	}
	
	static Slab slab() {
		return Slab.INSTANCE;
	}
	
	
	default BlockState provide() throws NoAvailableStateException {
		return this.provide($ -> true);
	}
	
	default BlockState provide(final Predicate<? super BlockState> predicate) throws NoAvailableStateException {
		return this.availableStates()
				.filter(predicate)
				.findAny()
				.orElseThrow(this::createException);
	}
	
	default Stream<BlockState> availableStates() {
		return this.allStates().filter(BlockStateRegistrar::isAvailable);
	}
	
	Stream<BlockState> allStates();
	
	default NoAvailableStateException createException() {
		return new NoAvailableStateException();
	}
	
	
	record Any(Set<BlockType> blocks) implements BlockStateProvider {
		
		public Any(final Set<BlockType> blocks) {
			this.blocks = Set.copyOf(blocks);
		}
		
		@Override
		public Stream<BlockState> allStates() {
			return this.blocks.stream().flatMap(block -> block.validStates().stream());
		}
		
		@Override
		public NoAvailableStateException createException() {
			return new NoAvailableStateException(this.blocks.toString());
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
