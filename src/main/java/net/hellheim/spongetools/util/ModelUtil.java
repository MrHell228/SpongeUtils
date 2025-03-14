package net.hellheim.spongetools.util;

import java.util.Objects;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

public final class ModelUtil {
	
	public static ResourceKey withPrefix(final ResourceKey key, final String prefix) {
		return ResourceKey.of(key.namespace(), Objects.requireNonNull(prefix, "prefix") + key.value());
	}
	
	public static ResourceKey withSuffix(final ResourceKey key, final String suffix) {
		return ResourceKey.of(key.namespace(), key.value() + Objects.requireNonNull(suffix, "suffix"));
	}
	
	public static ResourceKey withPath(final ResourceKey key, final UnaryOperator<String> pathModifier) {
		return ResourceKey.of(key.namespace(), pathModifier.apply(key.value()));
	}
	
	public static ResourceKey withPrefix(final String key, final String prefix) {
		return ModelUtil.withPrefix(ResourceKey.resolve(key), prefix);
	}
	
	public static ResourceKey withSuffix(final String key, final String prefix) {
		return ModelUtil.withSuffix(ResourceKey.resolve(key), prefix);
	}
	
	public static ResourceKey withItemPrefix(final ResourceKey key) {
		return ModelUtil.withPrefix(key, "item/");
	}
	
	public static ResourceKey withBlockPrefix(final ResourceKey key) {
		return ModelUtil.withPrefix(key, "block/");
	}
	
	public static ResourceKey withItemPrefix(final String key) {
		return ModelUtil.withItemPrefix(ResourceKey.resolve(key));
	}
	
	public static ResourceKey withBlockPrefix(final String key) {
		return ModelUtil.withBlockPrefix(ResourceKey.resolve(key));
	}
	
	private ModelUtil() {
	}
}
