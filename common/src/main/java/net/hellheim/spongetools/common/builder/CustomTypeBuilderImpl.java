package net.hellheim.spongetools.common.builder;

import java.util.Objects;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.custom.type.CustomArchetype;
import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.object.ValueSetBuilder;

public abstract class CustomTypeBuilderImpl<T, I, A extends CustomArchetype<T, A>, B extends CustomTypeBuilder<T, I, A, B>>
		implements CustomTypeBuilder<T, I, A ,B>, TypedKeyMap.Operator.MutableProxy<B> {
	
	protected A archetype = this.baseArchetype();
	protected final TypedKeyMap.Impl.Mutable context = TypedKeyMap.create();
	
	@SuppressWarnings("unchecked")
	private B cast() {
		return (B) this;
	}
	
	@Override
	public Mutable context() {
		return this.context;
	}
	
	@Override
	public B archetype(final A archetype) {
		this.archetype = Objects.requireNonNull(archetype, "archetype");
		return this.cast();
	}
	
	@Override
	public B from(final T value) {
		Objects.requireNonNull(value, "value");
		this.reset();
		
		this.archetype = CustomArchetype.forType(this.archetypeRegistry().get(), this.baseArchetype(), value);
		
		this.archetype.cumulativeContextExtractor().accept(value, this.context);
		
		this.extractData(value);
		
		// TODO extract behaviour
		
		return this.cast();
	}
	
	@Override
	public B reset() {
		this.archetype = this.baseArchetype();
		this.context.clear();
		// TODO clear behaviour
		return this.cast();
	}
	
	@Override
	public T build() {
		final String missingKeys = this.archetype.cumulativeRequiredKeys()
				.filter(key -> !this.context.has(key))
				.map(TypedKey::key)
				.map(ResourceKey::asString)
				.collect(Collectors.joining(", "));
		
		if (!missingKeys.isEmpty()) {
			throw new IllegalStateException(String.format(
					"Archetype %s requires keys that are not present: %s",
					this.archetype.key(this.archetypeRegistry()), missingKeys));
		}
		
		return this.build0();
	}
	
	protected abstract A baseArchetype();
	
	protected abstract DefaultedRegistryType<A> archetypeRegistry();
	
	protected abstract void extractData(T value);
	
	protected abstract T build0();
	
	public static abstract class WithData<T, I, A extends CustomArchetype<T, A>, B extends CustomTypeBuilder.WithData<T, I, A, B>>
			extends CustomTypeBuilderImpl<T, I, A, B>
			implements CustomTypeBuilder.WithData<T, I, A, B>, DataOperator.Proxy<B> {
		
		protected final ValueSetBuilder data = new ValueSetBuilder();
		
		@Override
		public DataOperator<?> getAsData() {
			return this.data;
		}
		
		@Override
		public B reset() {
			this.data.reset();
			return super.reset();
		}
	}
}
