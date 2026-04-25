package net.hellheim.spongetools.util;

import java.util.Objects;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

public final class ModelUtil {
	
	public static ResourceKey prefix(final ResourceKey key, final String prefix) {
		return ResourceKey.of(key.namespace(), Objects.requireNonNull(prefix, "prefix") + key.value());
	}
	
	public static ResourceKey suffix(final ResourceKey key, final String suffix) {
		return ResourceKey.of(key.namespace(), key.value() + Objects.requireNonNull(suffix, "suffix"));
	}
	
	public static ResourceKey path(final ResourceKey key, final UnaryOperator<String> pathModifier) {
		return ResourceKey.of(key.namespace(), pathModifier.apply(key.value()));
	}
	
	public static ResourceKey prefix(final String key, final String prefix) {
		return ModelUtil.prefix(ResourceKey.resolve(key), prefix);
	}
	
	public static ResourceKey suffix(final String key, final String prefix) {
		return ModelUtil.suffix(ResourceKey.resolve(key), prefix);
	}
	
	public static ResourceKey itemPrefix(final ResourceKey key) {
		return ModelUtil.prefix(key, "item/");
	}
	
	public static ResourceKey blockPrefix(final ResourceKey key) {
		return ModelUtil.prefix(key, "block/");
	}
	
	public static ResourceKey itemPrefix(final String key) {
		return ModelUtil.itemPrefix(ResourceKey.resolve(key));
	}
	
	public static ResourceKey blockPrefix(final String key) {
		return ModelUtil.blockPrefix(ResourceKey.resolve(key));
	}
	
	private ModelUtil() {
	}
}
