package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;
import java.util.Set;

public interface BehaviourCallbackHolderProxy<H> extends BehaviourCallbackHolder<H> {
	
	BehaviourCallbackHolder<H> getAsBehaviourCallbackHolder();
	
	@Override
	default Set<TypedBehaviourCallback<H, ?, ?>> callbacks() {
		return this.getAsBehaviourCallbackHolder().callbacks();
	}
	
	@Override
	default <R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(
		final BehaviourLayer layer, final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return this.getAsBehaviourCallbackHolder().callback(layer, type);
	}
	
	interface Mutable<H, M extends BehaviourCallbackHolder.Mutable<H, M>> extends
			BehaviourCallbackHolderProxy<H>,
			BehaviourCallbackHolder.Mutable<H, M> {
		
		@Override
		BehaviourCallbackHolder.Mutable<H, ?> getAsBehaviourCallbackHolder();
		
		@Override
		default <R, A extends BehaviourArgs> M offer(
			final BehaviourLayer layer,
			final BehaviourType<? extends Behaviour<R, A>> type,
			final BehaviourCallback<H, R, A> callback
		) {
			this.getAsBehaviourCallbackHolder().offer(layer, type, callback);
			@SuppressWarnings("unchecked")
			final M $this = (M) this;
			return $this;
		}
	}
}
