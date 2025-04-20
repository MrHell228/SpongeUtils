package net.hellheim.spongetools.custom.behaviour.block.state;

import java.util.Optional;

import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.custom.behaviour.Behaviour;

public interface BlockStateBehaviour<C extends BlockStateBehaviour.Callback<?>> extends Behaviour.Extendable<C> {
	
	interface Callback<R> extends Behaviour.SerializableCallback<R> {
		
		@Override
		default Optional<MapCodec<? extends Callback<R>>> codec() {
			return Optional.empty();
		}
	}
}
