package net.hellheim.spongetools.custom.type.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.ModeledCustomType;
import net.hellheim.spongetools.resourcepack.Model;

public record ModeledEntity(
		EntityType<?> type,
		List<EntityDisplayType> display,
		Map<ResourceKey, Model> models
		) implements ModeledCustomType<EntityType<?>> {
	
	public ModeledEntity(
		final EntityType<?> type,
		final List<EntityDisplayType> display,
		final Map<ResourceKey, Model> models
	) {
		this.type = Objects.requireNonNull(type, "type");
		this.display = List.copyOf(display);
		this.models = Map.copyOf(models);
	}
	
	public static DefaultedRegistryType<ModeledEntity> registry() {
		return SpongeTools.Registries.MODELED_ENTITY;
	}
	
	public static Builder builder(final ResourceKey key) {
		return new Builder(key);
	}
	
	public static final class Builder
			extends ModeledCustomType.Builder<EntityType<?>, EntityTypeBuilder, ModeledEntity, Builder> {
		
		private final List<EntityDisplayType> display = new ArrayList<>();
		
		public Builder(final ResourceKey key) {
			super(key);
			this.reset();
		}
		
		public Builder display(final EntityDisplayType... displays) {
			for (final EntityDisplayType display : Objects.requireNonNull(displays, "displays")) {
				this.display.add(Objects.requireNonNull(display, "display"));
			}
			return this;
		}
		
		public Builder display(final Iterable<? extends EntityDisplayType> displays) {
			for (final EntityDisplayType display : Objects.requireNonNull(displays, "displays")) {
				this.display.add(Objects.requireNonNull(display, "display"));
			}
			return this;
		}
		
		@Override
		public Builder reset() {
			this.display.clear();
			return super.reset();
		}
		
		@Override
		public ModeledEntity build() {
			final EntityType<?> entity = this.buildType(EntityTypeBuilder.create());
			return new ModeledEntity(entity, this.display, this.models);
		}
	}
}
