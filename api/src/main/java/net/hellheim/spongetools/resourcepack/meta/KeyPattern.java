package net.hellheim.spongetools.resourcepack.meta;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.spongepowered.api.registry.RegistryKey;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.kyori.adventure.key.Key;

public record KeyPattern(Optional<Pattern> namespace, Optional<Pattern> path) implements MetadataSectionLike.ServerSection {
	
	public static final Codec<KeyPattern> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ExtraCodecs.PATTERN.optionalFieldOf("namespace").forGetter(KeyPattern::namespace),
					ExtraCodecs.PATTERN.optionalFieldOf("path").forGetter(KeyPattern::path))
			.apply(instance, KeyPattern::new));
	
	public KeyPattern(final Optional<Pattern> namespace, final Optional<Pattern> path) {
		this.namespace = Objects.requireNonNull(namespace, "namespace");
		this.path = Objects.requireNonNull(path, "path");
	}
	
	public static KeyPattern of(final Pattern namespace, final Pattern value) {
		return new KeyPattern(Optional.of(namespace), Optional.of(value));
	}
	
	public static KeyPattern namespace(final Pattern namespace) {
		return new KeyPattern(Optional.of(namespace), Optional.empty());
	}
	
	public static KeyPattern path(final Pattern path) {
		return new KeyPattern(Optional.empty(), Optional.of(path));
	}
	
	public boolean test(final RegistryKey<?> key) {
		return this.test(key.location());
	}
	
	public boolean test(final Key key) {
		return this.testNamespace(key.namespace()) && this.testPath(key.value());
	}
	
	public boolean testNamespace(final String namespace) {
		return this.namespace.isEmpty() || this.namespace.get().matcher(namespace).find();
	}
	
	public boolean testPath(final String path) {
		return this.path.isEmpty() || this.path.get().matcher(path).find();
	}
	
	@Override
	public MetadataSection asSection() {
		return MetadataSection.filter(this);
	}
}
