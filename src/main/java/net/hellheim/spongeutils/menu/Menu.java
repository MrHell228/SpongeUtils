package net.hellheim.spongeutils.menu;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.util.Identifiable;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.TaskUtil;
import net.hellheim.spongeutils.collection.UnmodifiableDeque;
import net.hellheim.spongeutils.manager.IMenuManager;
import net.hellheim.spongeutils.menu.pagination.Pagination;
import net.hellheim.spongeutils.menu.pagination.PaginationInitStage;
import net.hellheim.spongeutils.menu.pagination.PaginationType;
import net.hellheim.spongeutils.source.solid.PluginSource;
import net.kyori.adventure.text.Component;

/**
 * {@link Menu} is a wrapper for {@link InventoryMenu} with some useful additions: <br>
 * - History of visited {@link MenuType}s is stored to surf through them back and forth <br>
 * - Data can be stored via {@link MenuKey}s <br>
 * - {@link Pagination} support <br>
 * <br>
 * Since menu creation requires {@link PluginContainer}, it's recommended to use {@link IMenuManager}. <br>
 * <br>
 * If you want to add more customization to this {@link Menu}, extend it with your own class. <br>
 * If you don't need anything more than this {@link Menu} already has, use {@link BasicMenu}.
 *
 * @param <M> The menu itself
 */
public abstract class Menu<M extends Menu<M>> implements PluginSource, Identifiable {
	
	private final PluginContainer plugin;
	private final UUID uniqueId;
	
	private final ServerPlayer player;
	
	private final Deque<IMenuType<M>> history = new ArrayDeque<>();
	private final UnmodifiableDeque<IMenuType<M>> historyView = new UnmodifiableDeque<>(this.history);
	private final Map<MenuKey<?>, Object> data = new HashMap<>();
	private @Nullable Pagination pagination = null;
	
	private IMenuType<M> type;
	private InventoryMenu menu;
	private Component currentTitle;
	
	public Menu(final PluginContainer plugin, final ServerPlayer player, final IMenuType<M> type, final MenuAction<M> dataSetter) {
		this.plugin = plugin;
		this.uniqueId = UUID.randomUUID();
		
		this.player = player;
		this.type = type;
		
		final M $this = this.cast();
		dataSetter.accept($this);
		
		if (!this.type.test($this)) {
			return;
		}
		
		this.menu = this.createInventory().asMenu();
		this.menu.setReadOnly(this.type.isReadOnly($this));
		this.currentTitle = this.type.title($this);
		this.menu.setTitle(this.currentTitle);
		this.registerHandlers();
	}
	
	@SuppressWarnings("unchecked")
	private M cast() {
		return (M) this;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T castValue(final MenuKey<T> key, final Object value) {
		return (T) value;
	}
	
	private void registerHandlers() {
		this.type.handlers().forEach(handler -> this.menu.registerHandler(handler.asDefaultHandler(this.cast())));
	}
	
	private ViewableInventory createInventory() {
		final M $this = this.cast();
		final ViewableInventory inventory = this.type.inventory($this);
		if (this.type.paginationStage() == PaginationInitStage.NONE) {
			this.setPagination(null);
		} else if (this.type.paginationStage() == PaginationInitStage.BUILD) {
			this.setPagination(this.type.pagination($this, inventory));
		}
		return inventory;
	}
	
	private void setPagination(@Nullable Pagination pagination) {
		this.pagination = pagination;
		if (this.pagination != null) {
			this.pagination.update();
		}
	}
	
	
	
	@Override
	public PluginContainer plugin() {
		return this.plugin;
	}
	
	@Override
	public UUID uniqueId() {
		return this.uniqueId;
	}
	
	/**
	 * Returns unmodifiable view of the current {@link IMenuType} history of this {@link Menu}.
	 * 
	 * @return The history
	 */
	public UnmodifiableDeque<IMenuType<M>> history() {
		return this.historyView;
	}
	
	/**
	 * Returns the {@link ServerPlayer} which observes this {@link Menu}.
	 * 
	 * @return The {@link ServerPlayer player}
	 */
	public ServerPlayer player() {
		return this.player;
	}
	
	/**
	 * Returns current {@link IMenuType} of this {@link Menu}.
	 * 
	 * @return The current menu type
	 */
	public IMenuType<M> type() {
		return this.type;
	}
	
	/**
	 * Returns current {@link ViewableInventory} of this {@link Menu}.
	 * 
	 * @return The current inventory
	 */
	public ViewableInventory inventory() {
		return this.menu.inventory();
	}
	
	
	
	/**
	 * Sets value of unknown type for the specified {@link MenuKey} for this {@link Menu}. <br>
	 * Useful when deleloper is sure about complex type compatability but IDE complains about it. <br>
	 * <br>
	 * <b>It's strongly advised to not use this method whenever possible.</b>
	 * 
	 * @param key The {@link MenuKey key}
	 * @param value The value
	 */
	public void setUnsafe(final MenuKey<?> key, final @Nullable Object value) {
		this.data.put(key, value);
	}
	
	/**
	 * Sets value for the specified {@link MenuKey} for this {@link Menu}. <br>
	 * <code>Null</code> value is supported.
	 * 
	 * @param <T> The type of key and value
	 * @param key The {@link MenuKey key}
	 * @param value The value
	 */
	public <T> void set(final MenuKey<T> key, final @Nullable T value) {
		this.data.put(key, value);
	}
	
	/**
	 * 
	 * Sets value for the specified {@link MenuKey} for this {@link Menu} only if provided {@link Optional} is present.
	 * 
	 * @param <T> The type of key and value
	 * @param key The key
	 * @param optionalValue The optional value
	 */
	public <T> void setIfPresent(final MenuKey<T> key, final Optional<T> optionalValue) {
		optionalValue.ifPresent(value -> this.set(key, value));
	}
	
	/**
	 * Removes provided {@link MenuKey} for this {@link Menu}. <br>
	 * <br>
	 * Returns the previous value assosiated with key, or null if there was no value for key.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The previous value assosiated with key, or null if there was no value for key.
	 */
	public <T> @Nullable T remove(final MenuKey<T> key) {
		return this.castValue(key, this.data.remove(key));
	}
	
	/**
	 * Returns whether this {@link Menu} contains value for provided {@link MenuKey}.
	 * 
	 * @param key The key
	 * @return True if this menu contains value for provided key.
	 */
	public boolean has(final MenuKey<?> key) {
		return this.data.containsKey(key);
	}
	
	/**
	 * Returns the value assosiated with provided {@link MenuKey} for this {@link Menu},
	 * or {@link Optional#empty()} if there is no value for key.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The value, if available
	 */
	public <T> Optional<T> get(final MenuKey<T> key) {
		return this.data.containsKey(key) ? Optional.of(this.castValue(key, this.data.get(key))) : Optional.empty();
	}
	
	/**
	 * Returns the value assosiated with provided {@link MenuKey} for this {@link Menu},
	 * or <code>defaultValue</code> if there is no value for key.
	 * 
	 * @param <T> The type of key and default value
	 * @param key The key
	 * @param defaultValue The default value
	 * @return The value, or default if not set
	 */
	public <T> @Nullable T getOrElse(final MenuKey<T> key, T defaultValue) {
		return this.castValue(key, this.data.getOrDefault(key, defaultValue));
	}
	
	/**
	 * Returns the value assosiated with provided {@link MenuKey} for this {@link Menu},
	 * or <code>null</code> if there is no value for key.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The value, or null if not set
	 */
	public <T> @Nullable T getOrNull(final MenuKey<T> key) {
		return this.castValue(key, this.data.get(key));
	}
	
	/**
	 * Returns the value assosiated with provided {@link MenuKey} for this {@link Menu}. <br>
	 * <br>
	 * If there is no value for key, {@link NoSuchElementException} will be thrown.
	 * 
	 * @param <T> The type of key
	 * @param key The {@link MenuKey key}
	 * @return The value
	 * @throws NoSuchElementException If there is no value for provided key
	 */
	public <T> @Nullable T require(final MenuKey<T> key) {
		if (!this.data.containsKey(key)) {
			throw new NoSuchElementException("No value found for the specified menu key");
		}
		
		return this.castValue(key, this.data.get(key));
	}
	
	
	
	/**
	 * Returns the {@link Pagination} currently present on this {@link Menu}, or
	 * {@link Optional#empty()} if there is no pagination.
	 * 
	 * @return The pagination, if available
	 */
	public Optional<Pagination> pagination() {
		return Optional.ofNullable(this.pagination);
	}
	
	/**
	 * Returns the {@link Pagination} of the given {@link PaginationType} currently
	 * present on this {@link Menu}, or {@link Optional#empty()} if there is no
	 * pagination or if it cannot be casted to the given type.
	 * 
	 * @param <P>  The type of pagination to return
	 * @param type The pagination type
	 * @return The pagination, if available
	 */
	public <P extends Pagination> Optional<P> pagination(final PaginationType<P> type) {
		try {
			@SuppressWarnings("unchecked")
			final P castedPagination = (P) this.pagination;
			return Optional.ofNullable(castedPagination);
		} catch (ClassCastException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Returns the {@link Pagination} currently present on this {@link Menu}, or
	 * throws an exception if there is no pagination.
	 * 
	 * @return The pagination
	 * @throws IllegalStateException If no pagination is present on this menu
	 */
	public Pagination requirePagination() {
		if (this.pagination == null) {
			throw new IllegalStateException("No pagination is present on menu");
		}
		
		return this.pagination;
	}
	
	/**
	 * Returns the {@link Pagination} of the given {@link PaginationType} currently
	 * present on this {@link Menu}, or throws an exception if there is no
	 * pagination or if it cannot be casted to the given type.
	 * 
	 * @param <P>  The type of pagination to return
	 * @param type The pagination type
	 * @return The pagination
	 * @throws IllegalStateException If no pagination is present on this menu
	 * @throws ClassCastException    If pagination on this menu cannot be casted to the given type
	 */
	public <P extends Pagination> P requirePagination(final PaginationType<P> type) {
		@SuppressWarnings("unchecked")
		final P castedPagination = (P) this.requirePagination();
		return castedPagination;
	}
	
	
	
	/**
	 * Switches this {@link Menu} to provided <code>next</code> {@link IMenuType}. <br>
	 * Adds current {@link IMenuType} to history.
	 * 
	 * @param next The menu type that should be opened
	 */
	public void nextTo(final IMenuType<M> next) {
		this.history.addLast(this.type);
		this.setType(next);
	}
	
	/**
	 * Switches this {@link Menu} to provided <code>next</code> {@link IMenuType}. <br>
	 * Adds current and provided <code>skipped</code> {@link IMenuType}s to history.
	 * 
	 * @param next The menu type that should be opened
	 * @param skippedType The first skipped menu type
	 * @param skippedTypes Other skipped menu types
	 */
	@SuppressWarnings("unchecked")
	public void nextTo(final IMenuType<M> next, final IMenuType<M> skippedType, final IMenuType<M>... skippedTypes) {
		this.history.addLast(this.type);
		this.history.addLast(skippedType);
		for (final IMenuType<M> skipped : skippedTypes) {
			this.history.addLast(skipped);
		}
		this.setType(next);
	}
	
	/**
	 * Switches this {@link Menu} to provided <code>next</code> {@link IMenuType}. <br>
	 * Adds current and provided <code>skipped</code> {@link IMenuType}s to history.
	 * 
	 * @param next The menu type that should be opened
	 * @param skippedTypes Skipped menu types
	 */
	public void nextTo(final IMenuType<M> next, final Collection<? extends IMenuType<M>> skippedTypes) {
		this.history.addLast(this.type);
		this.history.addAll(skippedTypes);
		this.setType(next);
	}
	
	/**
	 * Switches this {@link Menu} to last {@link IMenuType} in history. <br>
	 * 
	 * If history is empty, {@link #close() closes} the menu.
	 */
	public void backOrClose() {
		this.back(false);
	}
	
	/**
	 * Switches this {@link Menu} to last {@link IMenuType} in history. <br>
	 * 
	 * If history is empty, attempts to switch to {@link IMenuType#defaultPrevious()
	 * default previous} of {@link #type() current} type. <br>
	 * 
	 * If {@link IMenuType#defaultPrevious() default previous} of {@link #type()
	 * current} type is null, {@link #close() closes} the menu.
	 */
	public void back() {
		this.back(true);
	}
	
	private void back(final boolean shouldUseDefaultPrevious) {
		@Nullable IMenuType<M> previous = null;
		if (this.history.isEmpty()) {
			if (shouldUseDefaultPrevious) {
				previous = this.type.defaultPrevious();
			}
		} else {
			previous = this.history.pollLast();
		}
		
		if (previous == null) {
			this.close();
		} else {
			this.setType(previous);
		}
	}
	
	/**
	 * Switches this {@link Menu} back to provided {@link IMenuType},
	 * removing all intermediate types from history. <br>
	 * 
	 * If history does not contain provided {@link IMenuType}, {@link #close()
	 * closes} the menu.
	 * 
	 * @param type The menu type that should be opened
	 */
	public void backToOrClose(final IMenuType<M> type) {
		this.backTo(type, true);
	}
	
	/**
	 * Switches this {@link Menu} back to provided {@link IMenuType},
	 * removing all intermediate types from history. <br>
	 * 
	 * @param type The menu type that should be opened
	 */
	public void backTo(final IMenuType<M> type) {
		this.backTo(type, false);
	}
	
	private void backTo(final IMenuType<M> type, final boolean shouldCloseIfNotFound) {
		@Nullable IMenuType<M> removed;
		do {
			removed = this.history.pollLast();
		} while (removed != type && removed != null);
		
		if (removed == null && shouldCloseIfNotFound) {
			this.close();
		} else {
			this.setType(type);
		}
	}
	
	/**
	 * Switches this {@link Menu} back by provided amount of types,
	 * removing all intermediate types from history.
	 * 
	 * If history is less than provided amount, {@link #close() closes} the menu.
	 * 
	 * @param amount The amount to switch back by
	 * @throws IllegalArgumentException if amount is not positive
	 */
	public void backBy(final int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		
		@Nullable IMenuType<M> removed = null;
		for (int i = 0; i < amount; ++i) {
			removed = this.history.pollLast();
		}
		
		if (removed == null) {
			this.close();
		} else {
			this.setType(removed);
		}
	}
	
	
	
	private void setType(final IMenuType<M> type) {
		final IMenuType<M> oldType = this.type;
		this.type = type;
		final M $this = this.cast();
		
		if (!this.type.test($this)) {
			return;
		}
		
		if (Objects.equals(this.currentTitle, this.currentTitle = this.type.title($this))
				&& this.type.inventoryType() == oldType.inventoryType()) {
			this.update();
			if (this.type != oldType) {
				this.menu.setReadOnly(this.type.isReadOnly($this));
				this.menu.unregisterAll();
				this.registerHandlers();
			}
		} else {
			TaskUtil.tickDelayedSync(this.plugin, () -> {
				this.closeToViewers();
				this.menu.setCurrentInventory(this.createInventory());
				this.menu.setReadOnly(this.type.isReadOnly($this));
				this.menu.setTitle(this.currentTitle);
				this.menu.unregisterAll();
				this.registerHandlers();
				this.openToViewers();
			});
		}
	}
	
	/**
	 * Updates title and inventory of this {@link Menu}.
	 */
	public void updateAll() {
		this.setType(this.type);
	}
	
	/**
	 * Updates inventory of this {@link Menu}.
	 */
	public void update() {
		this.menu.setCurrentInventory(this.createInventory());
	}
	
	/**
	 * Closes this {@link Menu} with one tick delay.
	 */
	public void close() {
		this.doTickDelayedTask(() -> {
			this.closeToViewers();
		});
	}
	
	/**
	 * Opens this {@link Menu} with one tick delay.
	 */
	public void open() {
		if (this.menu == null) {
			return;
		}
		
		this.doTickDelayedTask(() -> {
			this.closeToViewers();
			this.openToViewers();
		});
	}
	
	/**
	 * See {@link TaskUtil#tickDelayedSync}.
	 * 
	 * @param executor Task executor
	 */
	public void doTickDelayedTask(final Runnable executor) {
		TaskUtil.tickDelayedSync(this.plugin, executor);
	}
	
	private void closeToViewers() {
		this.player.closeInventory();
	}
	
	private void openToViewers() {
		this.menu.open(this.player).ifPresent(container -> {
			final M $this = this.cast();
			this.type.finalize($this, container);
			if (this.type.paginationStage() == PaginationInitStage.OPEN) {
				this.setPagination(this.type.pagination($this, container));
			}
		});
	}
	
}
