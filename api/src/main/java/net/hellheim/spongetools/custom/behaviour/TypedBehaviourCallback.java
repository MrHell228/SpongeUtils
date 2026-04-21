package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;

public record TypedBehaviourCallback<H, R, A extends BehaviourArgs>(
		BehaviourLayer layer,
		BehaviourType<? extends Behaviour<R, A>> type,
		BehaviourCallback<H, R, A> callback
		) {
	
	public TypedBehaviourCallback(
		final BehaviourLayer layer,
		final BehaviourType<? extends Behaviour<R, A>> type,
		final BehaviourCallback<H, R, A> callback
	) {
		this.layer = Objects.requireNonNull(layer, "layer");
		this.type = Objects.requireNonNull(type, "type");
		this.callback = Objects.requireNonNull(callback, "callback");
	}
	
	public static <H, R, A extends BehaviourArgs> TypedBehaviourCallback<H, R, A> of(
		final BehaviourLayer layer,
		final BehaviourType<? extends Behaviour<R, A>> type,
		final BehaviourCallback<H, R, A> callback
	) {
		return new TypedBehaviourCallback<>(layer, type, callback);
	}
	
	public static <H, R, A extends BehaviourArgs> TypedBehaviourCallback<H, R, A> of(
		final BehaviourType<? extends Behaviour<R, A>> type,
		final BehaviourCallback<H, R, A> callback
	) {
		return TypedBehaviourCallback.of(BehaviourLayer.TOP, type, callback);
	}
	
	@Override
	public final int hashCode() {
		return Objects.hash(this.layer, this.type);
	}
	
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		final TypedBehaviourCallback<?, ?, ?> that = (TypedBehaviourCallback<?, ?, ?>) obj;
		return this.layer.equals(that.layer) && this.type.equals(that.type);
	}
}
