package net.hellheim.spongetools.custom.behaviour;

import java.util.Collection;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface BehaviourCallbackHolderProxy<H> extends BehaviourCallbackHolder<H> {
	
	BehaviourCallbackHolder<H> getAsBehaviourCallbackHolder();
	
	@Override
	default Collection<TypedBehaviourCallback<H, ?, ?>> callbacks() {
		return this.getAsBehaviourCallbackHolder().callbacks();
	}
	
	@Override
	default <R, A extends BehaviourArgs> Optional<BehaviourCallback<H, R, A>> callback(
		final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return this.getAsBehaviourCallbackHolder().callback(type);
	}
	
	@Override
	default <R, A extends BehaviourArgs> @Nullable BehaviourCallback<H, R, A> callbackOrNull(
		final BehaviourType<? extends Behaviour<R, A>> type
	) {
		return this.getAsBehaviourCallbackHolder().callbackOrNull(type);
	}
	
	interface Mutable<H, M extends BehaviourCallbackHolder.Mutable<H, M>> extends
			BehaviourCallbackHolderProxy<H>,
			BehaviourCallbackHolder.Mutable<H, M> {
		
		@Override
		BehaviourCallbackHolder.Mutable<H, ?> getAsBehaviourCallbackHolder();
		
		@Override
		default <R, A extends BehaviourArgs> M offer(
			final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
		) {
			this.getAsBehaviourCallbackHolder().offer(type, callback);
			@SuppressWarnings("unchecked")
			final M $this = (M) this;
			return $this;
		}
	}
}
