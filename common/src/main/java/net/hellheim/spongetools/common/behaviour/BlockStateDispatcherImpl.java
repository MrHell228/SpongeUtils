package net.hellheim.spongetools.common.behaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.BlockStateHolder;
import net.hellheim.spongetools.custom.type.block.BlockStateProvider;

public final class BlockStateDispatcherImpl implements BlockStateDispatcher {
	
	private final BiMap<BlockState, BlockStateHolder> dispatched = HashBiMap.create();
	private final Map<BlockStateHolder, BlockStateProvider> undispatched = new HashMap<>();
	
	@Override
	public Optional<BlockStateHolder> get(final BlockState state) {
		return Optional.ofNullable(this.dispatched.get(Objects.requireNonNull(state, "state")));
	}
	
	@Override
	public boolean isOccupied(final BlockState state) {
		return this.dispatched.containsKey(Objects.requireNonNull(state, "state"));
	}
	
	@Override
	public void submit(final BlockStateHolder holder, final BlockStateProvider provider) {
		Objects.requireNonNull(holder, "holder");
		Objects.requireNonNull(provider, "provider");
		if (!this.dispatched.containsValue(holder)) {
			this.undispatched.put(holder, provider);
		}
	}
	
	@Override
	public void dispatch() {
		if (this.undispatched.isEmpty()) {
			return;
		}
		
		this.undispatched.forEach((holder, provider) -> {
			final BlockState state = provider.provide();
			holder.bind(state);
			this.dispatched.put(state, holder);
		});
	}
}
