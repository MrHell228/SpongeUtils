package net.hellheim.spongetools.common.builder;

import java.util.Objects;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.type.CustomTypeArchetype;
import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.object.ValueSetBuilder;

public abstract class CustomTypeBuilderImpl<T, I, A extends CustomTypeArchetype<T, I, A>, B extends CustomTypeBuilder<T, I, A, B>>
		implements CustomTypeBuilder<T, I, A, B>, TypedKeyMap.Operator.MutableProxy<B>, BehaviourCallbackHolderProxy.Mutable<I, B> {
	
	protected A archetype = this.baseArchetype();
	protected final TypedKeyMap.Impl.Mutable context = TypedKeyMap.create();
	protected final BehaviourCallbackHolderLogic.Mutable<I> behaviour = BehaviourCallbackHolderLogic.mutable();
	
	@SuppressWarnings("unchecked")
	protected B cast() {
		return (B) this;
	}
	
	@Override
	public TypedKeyMap.Mutable context() {
		return this.context;
	}
	
	@Override
	public BehaviourCallbackHolder.Mutable<I, ?> getAsBehaviourCallbackHolder() {
		return this.behaviour;
	}
	
	@Override
	public B archetype(final A archetype) {
		this.archetype = Objects.requireNonNull(archetype, "archetype");
		return this.cast();
	}
	
	@Override
	public B reset() {
		this.archetype = this.baseArchetype();
		this.context.clear();
		this.behaviour.clear();
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
	
	protected abstract T build0();
	
	public static abstract class WithDataImpl<T, I, A extends CustomTypeArchetype<T, I, A>, B extends WithData<T, I, A, B>>
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
	
	public static abstract class TypeBasedWithData<T, I, A extends CustomTypeArchetype.TypeBased<T, I, A>, B extends TypeBased<T, I, A, B> & WithData<T, I, A, B>>
			extends WithDataImpl<T, I, A, B>
			implements CustomTypeBuilder.TypeBased<T, I, A, B> {
		
		@Override
		public B from(final T value) {
			Objects.requireNonNull(value, "value");
			this.reset();
			
			this.archetype = this.extractArchetype(value);
			this.archetype.cumulativeContextExtractor().accept(value, this.context);
			
			this.extractData(value);
			
			// TODO extract behaviour
			
			return this.cast();
		}
		
		protected abstract A extractArchetype(T value);
		
		protected abstract void extractData(T value);
	}
}
