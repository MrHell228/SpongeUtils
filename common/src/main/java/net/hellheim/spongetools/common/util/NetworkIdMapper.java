package net.hellheim.spongetools.common.util;

import net.minecraft.core.IdMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.function.ToIntBiFunction;

public final class NetworkIdMapper<T> implements IdMap<T> {
	
	public static final NetworkIdMapper<BlockState> BLOCK_STATE_REGISTRY = new NetworkIdMapper<>(
			Block.BLOCK_STATE_REGISTRY,
			// TODO map custom states to vanilla states
			IdMap::getId);
	
	private final IdMap<T> base;
	private final ToIntBiFunction<IdMap<T>, T> idProvider;
	
	public NetworkIdMapper(final IdMap<T> base, final ToIntBiFunction<IdMap<T>, T> idProvider) {
		this.base = base;
		this.idProvider = idProvider;
	}
	
	@Override
	public int getId(final T value) {
		return this.idProvider.applyAsInt(this.base, value);
	}
	
	@Override
	public @Nullable T byId(final int id) {
		return this.base.byId(id);
	}
	
	@Override
	public int size() {
		return this.base.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		return this.base.iterator();
	}
}
