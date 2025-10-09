package net.hellheim.spongetools.custom.behaviour.tag;

import java.util.stream.Stream;

import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.tag.DefaultedTag;
import org.spongepowered.api.tag.Tag;
import org.spongepowered.api.tag.Taggable;

// TODO
public interface TaggableInstance<T extends Taggable<T>> {
	
	T taggedType();
	
	DefaultedRegistryType<T> taggedRegistry();
	
	Stream<Tag<T>> tags();
	
	boolean is(DefaultedTag<T> tag);
	
	interface Defaulted<T extends Taggable<T>> extends TaggableInstance<T> {
		
		@Override
		default Stream<Tag<T>> tags() {
			return this.taggedType().tags(this.taggedRegistry());
		}
		
		@Override
		default boolean is(final DefaultedTag<T> tag) {
			return this.taggedType().is(tag);
		}
	}
}
