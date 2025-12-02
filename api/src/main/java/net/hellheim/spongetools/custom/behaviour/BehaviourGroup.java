package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;
import java.util.function.Function;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;

public interface BehaviourGroup<H> {
	
	static <I, T> Simple<I, T> of(
		final ResourceKey key,
		final Class<? extends T> baseClass,
		final Function<I, T> behaviourBaseExtractor
	) {
		return new Simple<>(key, baseClass, behaviourBaseExtractor);
	}
	
	Class<H> behaviourHolderClass();
	
	Class<?> behaviourBaseClass();
	
	Object extractBehaviourBase(H holder);
	
	record Simple<I, T>(ResourceKey key, Class<T> baseClass, Function<I, T> behaviourBaseExtractor)
			implements BehaviourGroup<I>, ResourceKeyed {
		
		public Simple(final ResourceKey key, final Class<? extends T> baseClass, final Function<I, T> behaviourBaseExtractor) {
			this.key = Objects.requireNonNull(key, "key");
			this.baseClass = Objects.requireNonNull(baseClass, "baseClass");
			this.behaviourBaseExtractor = Objects.requireNonNull(behaviourBaseExtractor, "behaviourBaseExtractor");
		}
		
		@Override
		public Object extractBehaviourBase(final I holder) {
			return this.behaviourBaseExtractor.apply(holder);
		}
	}
}
