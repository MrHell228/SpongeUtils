package net.hellheim.spongetools.common.util;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.core.IdMap;
import net.minecraft.world.level.block.Block;

import java.util.Iterator;
import java.util.function.UnaryOperator;

public final class NetworkUtil {
	
	public static boolean isBlockStateRegistry(final IdMap<?> map) {
		return map == Block.BLOCK_STATE_REGISTRY;
	}
	
	public static <T> UnaryOperator<T> getPossibleBlockStateMapper(final IdMap<?> map) {
		return NetworkUtil.isBlockStateRegistry(map)
				? FakeableNetworkValueBridge::asNetworkValue
				: UnaryOperator.identity();
	}
	
	public static <T> IdMap<T> idMap(final IdMap<T> base, final UnaryOperator<T> valueMapper) {
		return new IdMap<T>() {
			
			@Override
			public int getId(final T value) {
				return base.getId(valueMapper.apply(value));
			}
			
			@Override
			public T byId(int id) {
				return base.byId(id);
			}
			
			@Override
			public int size() {
				return base.size();
			}
			
			@Override
			public Iterator<T> iterator() {
				return base.iterator();
			}
		};
	}
	
	/*public static <T> IdMapper<T> idMapper(final IdMap<T> base, final Function<T, T> idProvider) {
		final IdMapper<T> mapper = new IdMapper<>() {
			
			@Override
			public int getId(T value) {
				return super.getId(idProvider.apply(value));
			}
		};
		
		base.forEach(value -> {
			final int id = base.getId(value);
			if (id == -1) {
				System.out.println("Unknown id for value " + value + " that already was in map");
			}
			mapper.addMapping(value, base.getId(value));
		});
		
		return mapper;
	}*/
	
	private NetworkUtil() {
	}
}
