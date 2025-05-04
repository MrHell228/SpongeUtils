package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;

public record TypedBehaviourCallback<H, R, A extends BehaviourArgs>(
		BehaviourType<? extends Behaviour<R, A>> type, BehaviourCallback<H, R, A> callback) {
	
	public TypedBehaviourCallback(
		final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
	) {
		this.type = Objects.requireNonNull(type, "type");
		this.callback = Objects.requireNonNull(callback, "callback");
	}
	
	public static <H, R, A extends BehaviourArgs> TypedBehaviourCallback<H, R, A> of(
		final BehaviourType<? extends Behaviour<R, A>> type, final BehaviourCallback<H, R, A> callback
	) {
		return new TypedBehaviourCallback<>(type, callback);
	}
}
