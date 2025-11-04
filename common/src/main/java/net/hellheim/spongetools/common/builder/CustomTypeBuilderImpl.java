package net.hellheim.spongetools.common.builder;

import java.util.Objects;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.custom.type.CustomArchetype;
import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

public abstract class CustomTypeBuilderImpl<T, A extends CustomArchetype<T, A>, B extends CustomTypeBuilder<T, A, B>> implements
		CustomTypeBuilder<T, A ,B>,
		TypedKeyMap.Operator.MutableProxy<B> {
	
	protected A archetype = this.baseArchetype();
	protected final TypedKeyMap.Impl.Mutable context = TypedKeyMap.create();
	
	public CustomTypeBuilderImpl() {
		this.reset();
	}
	
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
		
		return this.cast();
	}
	
	@Override
	public B reset() {
		this.archetype = this.baseArchetype();
		this.context.clear();
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
}
