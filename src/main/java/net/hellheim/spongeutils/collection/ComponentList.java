package net.hellheim.spongeutils.collection;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;

@SuppressWarnings("serial")
public class ComponentList extends ArrayList<Component> {
	
	public static final List<Component> EMPTY = List.of();
	
	public void shift(final int spaces) {
		this.appendLeft(Component.text(" ".repeat(spaces)));
	}
	
	public void appendLeft(final Component prefix) {
		this.replaceAll(c -> prefix.append(c));
	}
	
	public void appendRight(final Component suffix) {
		this.replaceAll(c -> c.append(suffix));
	}
	
	public void addEmptyLine() {
		this.add(Component.empty());
	}
}
