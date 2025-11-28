package net.hellheim.spongetools.custom.type.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.registry.RegistryKey;

public record EntityDefaultAttributes(Map<ResourceKey, OptionalDouble> attributes) {
	
	public EntityDefaultAttributes(final Map<ResourceKey, OptionalDouble> attributes) {
		this.attributes = Map.copyOf(attributes);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder implements org.spongepowered.api.util.Builder<EntityDefaultAttributes, Builder> {
		
		private final Map<ResourceKey, OptionalDouble> attributes = new HashMap<>();
		
		public Builder add(final RegistryKey<AttributeType> attribute, final double base) {
			return this.add(attribute.location(), base);
		}
		
		public Builder add(final RegistryKey<AttributeType> attribute) {
			return this.add(attribute.location());
		}
		
		public Builder add(final ResourceKey attribute, final double base) {
			return this.add(attribute, OptionalDouble.of(base));
		}
		
		public Builder add(final ResourceKey attribute) {
			return this.add(attribute, OptionalDouble.empty());
		}
		
		public Builder add(final ResourceKey attribute, final OptionalDouble base) {
			this.attributes.put(attribute, base);
			return this;
		}
		
		@Override
		public Builder reset() {
			this.attributes.clear();
			return this;
		}
		
		@Override
		public EntityDefaultAttributes build() {
			return new EntityDefaultAttributes(this.attributes);
		}
	}
}
