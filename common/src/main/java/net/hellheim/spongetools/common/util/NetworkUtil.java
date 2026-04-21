package net.hellheim.spongetools.common.util;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.core.IdMap;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.google.common.base.Suppliers;

public final class NetworkUtil {
	
	private static final Supplier<ClientboundUpdateAttributesPacket.AttributeSnapshot> MINING_SPEED_ATTRIBUTE =
			Suppliers.memoize(() -> new ClientboundUpdateAttributesPacket.AttributeSnapshot(
					Attributes.BLOCK_BREAK_SPEED, 0, Collections.emptyList()));
	
	// In vanilla Block#getId is only used for network so it's modified to always return id for network state.
	// However there are special handling in some places where we need to pass the actual state id.
	public static int getBlockStateId(final BlockState state) {
		return Block.BLOCK_STATE_REGISTRY.getId(state);
	}
	
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
	
	public static ClientboundUpdateAttributesPacket.AttributeSnapshot miningSpeedAttribute() {
		return NetworkUtil.MINING_SPEED_ATTRIBUTE.get();
	}
	
	private NetworkUtil() {
	}
}
