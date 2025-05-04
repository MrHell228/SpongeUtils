package net.hellheim.spongetools.custom.behaviour.tag;

import java.util.stream.Stream;

import org.spongepowered.api.tag.Tag;
import org.spongepowered.api.tag.Taggable;

// TODO
public interface TaggableInstance<T extends Taggable<T>> {
	
	T taggedType();
	
	Stream<Tag<T>> tags();
	
	boolean is(Tag<T> tag);
	
	interface Defaulted<T extends Taggable<T>> extends TaggableInstance<T> {
		
		@Override
		default Stream<Tag<T>> tags() {
			return this.taggedType().tags().stream();
		}
		
		@Override
		default boolean is(final Tag<T> tag) {
			return this.taggedType().is(tag);
		}
	}
}
