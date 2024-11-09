package net.hellheim.spongeutils.menu;

import java.util.function.Consumer;

import net.hellheim.spongeutils.source.solid.ColorSource;
import net.kyori.adventure.text.ComponentLike;

@FunctionalInterface
public interface MenuAction<M extends Menu<M>> extends Consumer<M> {
	
	public static <M extends Menu<M>> MenuAction<M> empty() {
		return (menu) -> {};
	}
	
	public static <M extends Menu<M>> MenuAction<M> close() {
		return Menu::close;
	}
	
	public static <M extends Menu<M>> MenuAction<M> back() {
		return Menu::back;
	}
	
	public static <M extends Menu<M>> MenuAction<M> backOrClose() {
		return Menu::backOrClose;
	}
	
	public static <M extends Menu<M>> MenuAction<M> nextTo(final IMenuType<M> type) {
		return (menu) -> menu.nextTo(type);
	}
	
	public static <M extends Menu<M>> MenuAction<M> backTo(final IMenuType<M> type) {
		return (menu) -> menu.backTo(type);
	}
	
	public static <M extends Menu<M>> MenuAction<M> backToOrClose(final IMenuType<M> type) {
		return (menu) -> menu.backToOrClose(type);
	}
	
	public static <M extends Menu<M>> MenuAction<M> backBy(final int amount) {
		return (menu) -> menu.backBy(amount);
	}
	
	public static <M extends Menu<M>> MenuAction<M> send(final ComponentLike message) {
		return (menu) -> menu.player().sendMessage(message);
	}
	
	public static <M extends Menu<M>> MenuAction<M> send(final ColorSource color, final String message) {
		return MenuAction.send(color.text(message));
	}
	
	
	
	@Override
	void accept(M menu);
	
	default MenuAction<M> andThen(final MenuAction<M> other) {
		return (menu) -> {
			this.accept(menu);
			other.accept(menu);
		};
	}
	
	default MenuAction<M> andThen(final Runnable other) {
		return this.andThen((menu) -> other.run());
	}
	
	default MenuAction<M> andSend(final ComponentLike message) {
		return this.andThen(MenuAction.send(message));
	}
	
	default MenuAction<M> andSend(final ColorSource color, final String message) {
		return this.andThen(MenuAction.send(color, message));
	}
}
