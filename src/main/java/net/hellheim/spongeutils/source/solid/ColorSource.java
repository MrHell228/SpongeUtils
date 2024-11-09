package net.hellheim.spongeutils.source.solid;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;

public interface ColorSource {
	
	TextColor getAsColor();
	
	default Component text(Object o) {
		return Component.text(o.toString(), this.getAsColor());
	}
	
	default Component textNonItalic(Object o) {
		return this.text(o).decoration(TextDecoration.ITALIC, State.FALSE);
	}
	
	default String textMini(Object o) {
		return "<color:" + this.getAsColor().asHexString() + ">" + o.toString() + "</color>";
	}
}
