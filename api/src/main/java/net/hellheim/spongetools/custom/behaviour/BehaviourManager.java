package net.hellheim.spongetools.custom.behaviour;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;

import net.hellheim.spongetools.function.TriConsumer;

public interface BehaviourManager {
	
	static BehaviourManager get() {
		return Sponge.game().factoryProvider().provide(BehaviourManager.class);
	}
	
	<H> boolean supportsBehaviour(H holder, BehaviourType<?> type);
	
	<H, B extends Behaviour<?, ?>> Optional<B> behaviour(H holder, BehaviourType<B> type);
	
	default <H, B extends Behaviour<?, ?>> B requireBehaviour(
		final H holder, final BehaviourType<B> type
	) {
		return this.behaviour(holder, type)
				.orElseThrow(() -> new NoSuchElementException(String.format(
						"No behaviour of type %s is present for holder %s",
						type, holder
						)));
	}
	
	
	<H, E> Collection<TypedBehaviourCallback<E, ?, ?>> callbacks(
		H behaviourHolder, Class<E> callbackHolder
	);
	
	<H, E, R, A extends BehaviourArgs> Optional<BehaviourCallback<E, R, A>> callback(
		H behaviourHolder, Class<E> callbackHolder, BehaviourType<? extends Behaviour<R, A>> type
	);
	
	<H> BehaviourRegistration<H> behaviour(Class<H> behaviourHolder);
	
	interface BehaviourRegistration<H> {
		
		/**
		 * Registers behaviour provider
		 */
		<B extends Behaviour<?, ?>> BehaviourRegistration<H> register(
			BehaviourType<B> type,
			Function<H, @Nullable B> behaviourProvider
		);
		
		<E> CallbackRegistration<H, E> callbacks(Class<E> callbackHolder);
	}
	
	interface CallbackRegistration<H, E> {
		
		/**
		 * Registers behaviour callback provider
		 */
		<R, A extends BehaviourArgs> CallbackRegistration<H, E> register(
			BehaviourType<? extends Behaviour<R, A>> type,
			Function<H, @Nullable BehaviourCallback<E, R, A>> callbackProvider
		);
		
		default <A extends BehaviourArgs> CallbackRegistration<H, E> registerAction(
			final BehaviourType<? extends Behaviour<Void, A>> type,
			final Function<H, @Nullable TriConsumer<E, Behaviour<Void, A>, A>> actionProvider
		) {
			return this.register(type, behaviourHolder -> {
				final @Nullable TriConsumer<E, Behaviour<Void, A>, A> action = actionProvider.apply(behaviourHolder);
				return action == null ? null : (callbackHolder, origin, args) -> {
					action.accept(callbackHolder, origin, args);
					return null;
				};
			});
		}
		
		default <R, A extends BehaviourArgs> CallbackRegistration<H, E> registerResult(
			final BehaviourType<? extends Behaviour<R, A>> type,
			final Function<H, R> resultProvider
		) {
			return this.register(type, behaviourHolder -> {
				final @Nullable R result = resultProvider.apply(behaviourHolder);
				return result == null ? null : (callbackHolder, origin, args) -> result;
			});
		}
	}
}
