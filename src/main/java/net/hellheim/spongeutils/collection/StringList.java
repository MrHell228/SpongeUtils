package net.hellheim.spongeutils.collection;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class StringList extends ArrayList<String> {
	
	public static final List<String> EMPTY = List.of();
	
	public void shift(final int spaces) {
		this.appendLeft(" ".repeat(spaces));
	}
	
	public void appendLeft(final String prefix) {
		this.replaceAll(s -> prefix + s);
	}
	
	public void appendRight(final String suffix) {
		this.replaceAll(s -> s + suffix);
	}
	
	public void addEmptyLine() {
		this.add("");
	}
}
