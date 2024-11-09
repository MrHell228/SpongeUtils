package net.hellheim.spongeutils.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.menu.handler.ClickHandler;
import org.spongepowered.api.item.inventory.menu.handler.CloseHandler;
import org.spongepowered.api.item.inventory.menu.handler.KeySwapHandler;
import org.spongepowered.api.item.inventory.menu.handler.SlotChangeHandler;
import org.spongepowered.api.item.inventory.menu.handler.SlotClickHandler;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.util.Tuple;

import net.hellheim.spongeutils.CompUtil;
import net.hellheim.spongeutils.function.BooleanBinaryOperator;
import net.hellheim.spongeutils.menu.handler.MenuClickHandler;
import net.hellheim.spongeutils.menu.handler.MenuCloseHandler;
import net.hellheim.spongeutils.menu.handler.MenuInventoryCallbackHandler;
import net.hellheim.spongeutils.menu.handler.MenuKeySwapHandler;
import net.hellheim.spongeutils.menu.handler.MenuSlotChangeHandler;
import net.hellheim.spongeutils.menu.handler.MenuSlotClickHandler;
import net.hellheim.spongeutils.menu.pagination.MultiplePagination;
import net.hellheim.spongeutils.menu.pagination.PageContentProvider;
import net.hellheim.spongeutils.menu.pagination.Pagination;
import net.hellheim.spongeutils.menu.pagination.PaginationConfig;
import net.hellheim.spongeutils.menu.pagination.PaginationInitStage;
import net.hellheim.spongeutils.menu.pagination.SinglePagination;
import net.kyori.adventure.text.Component;

/**
 * Properties of {@link IDefaultedMenuType}. <br>
 * <br>
 * For constructing properties, see {@link MenuTypeProperties.Mutable}.
 * 
 * @param <M> The {@link Menu}
 */
public class MenuTypeProperties<M extends Menu<M>> {
	
	protected @Nullable IMenuType<M> defaultPrevious = null;
	protected List<Tuple<Predicate<M>, MenuAction<M>>> tests;
	protected @Nullable ContainerType inventoryType = null;
	protected Function<M, ViewableInventory> inventoryProvider;
	protected Predicate<M> readOnlyProvider = (menu) -> true;
	protected Function<M, Component> titleProvider = (menu) -> Component.empty();
	protected BiConsumer<M, Container> finalizer = (menu, container) -> {};
	protected List<MenuInventoryCallbackHandler<M>> handlers;
	protected PaginationInitStage paginationStage = PaginationInitStage.NONE;
	protected BiFunction<M, Inventory, Pagination> paginationProvider = null;
	
	protected @Nullable MenuClickHandler<M> onClick = null;
	protected @Nullable MenuSlotClickHandler<M> onSlotClick = null;
	protected @Nullable MenuKeySwapHandler<M> onKeySwap = null;
	protected @Nullable MenuSlotChangeHandler<M> onSlotChange = null;
	protected @Nullable MenuCloseHandler<M> onClose = null;
	
	public MenuTypeProperties() {
		this.tests = List.of();
		this.handlers = List.of();
	}
	
	/**
	 * Copies all known data from provided properties to this properties.
	 * 
	 * @param from The properties to copy data from
	 */
	protected void copyDataFrom(final MenuTypeProperties<M> from) {
		this.defaultPrevious = from.defaultPrevious;
		this.inventoryType = from.inventoryType;
		this.inventoryProvider = from.inventoryProvider;
		this.readOnlyProvider = from.readOnlyProvider;
		this.titleProvider = from.titleProvider;
		this.finalizer = from.finalizer;
		
		this.tests = this instanceof Mutable ? new ArrayList<>(from.tests) : List.copyOf(from.tests);
		this.handlers = this instanceof Mutable ? new ArrayList<>(from.handlers) : List.copyOf(from.handlers);
		
		this.onClick = from.onClick;
		this.onSlotClick = from.onSlotClick;
		this.onKeySwap = from.onKeySwap;
		this.onSlotChange = from.onSlotChange;
		this.onClose = from.onClose;
		
		this.paginationStage = from.paginationStage;
		this.paginationProvider = from.paginationProvider;
	}
	
	/**
	 * Returns mutable form of this {@link MenuTypeProperties}. <br>
	 * If this is already mutable, this would simply return itself. Otherwise, new mutable
	 * {@link MenuTypeProperties} created with all the data copied from this properties.
	 * 
	 * @return Mutable menu type properties
	 */
	public Mutable<M, ?> asMutable() {
		return this.asMutableCopy();
	}
	
	/**
	 * Returns copy of this {@link MenuTypeProperties} in a mutable form. <br>
	 * All the data is copied from this properties.
	 * 
	 * @return Mutable menu type properties
	 */
	public Mutable<M, ?> asMutableCopy() {
		final Mutable<M, ?> props = new Mutable<>();
		props.copyDataFrom(this);
		return props;
	}
	
	/**
	 * Returns immutable form of this {@link MenuTypeProperties}. <br>
	 * If this is already immutable, this would simply return itself. Otherwise, new immutable
	 * {@link MenuTypeProperties} created with all the data copied from this properties.
	 * 
	 * @return Immutable menu type properties
	 */
	public MenuTypeProperties<M> asImmutable() {
		return this;
	}
	
	/**
	 * See {@link IDefaultedMenuType#defaultPrevious()}.
	 */
	public @Nullable IMenuType<M> defaultPrevious() {
		return this.defaultPrevious;
	}
	
	/**
	 * See {@link IDefaultedMenuType#test(Menu)}.
	 */
	public List<Tuple<Predicate<M>, MenuAction<M>>> tests() {
		return this.tests;
	}
	
	/**
	 * See {@link IDefaultedMenuType#inventoryType()}.
	 */
	public @Nullable ContainerType inventoryType() {
		return this.inventoryType;
	}
	
	/**
	 * See {@link IDefaultedMenuType#inventory(Menu)}.
	 */
	public Function<M, ViewableInventory> inventoryProvider() {
		return this.inventoryProvider;
	}
	
	/**
	 * See {@link IDefaultedMenuType#isReadOnly(Menu)}.
	 */
	public Predicate<M> readOnlyProvider() {
		return this.readOnlyProvider;
	}
	
	/**
	 * See {@link IDefaultedMenuType#title(Menu)}.
	 */
	public Function<M, Component> titleProvider() {
		return this.titleProvider;
	}
	
	/**
	 * See {@link IDefaultedMenuType#finalize(Menu, Container)}.
	 */
	public BiConsumer<M, Container> finalizer() {
		return this.finalizer;
	}
	
	/**
	 * See {@link IDefaultedMenuType#handlers()}.
	 */
	public List<MenuInventoryCallbackHandler<M>> handlers() {
		return this.handlers;
	}
	
	/**
	 * See {@link IDefaultedMenuType#paginationStage()}.
	 */
	public PaginationInitStage paginationStage() {
		return this.paginationStage;
	}
	
	/**
	 * See {@link IDefaultedMenuType#pagination(Menu)}.
	 */
	public BiFunction<M, Inventory, Pagination> paginationProvider() {
		return this.paginationProvider;
	}
	
	/**
	 * Mutable form of {@link MenuTypeProperties} used to configure it.
	 * 
	 * @param <M> The {@link Menu}
	 * @param <P> The {@link MenuTypeProperties.Mutable} to return from methods for chaining
	 */
	public static class Mutable<M extends Menu<M>, P extends MenuTypeProperties.Mutable<M, P>> extends MenuTypeProperties<M> {
		
		public Mutable() {
			this.tests = new ArrayList<>();
			this.handlers = new ArrayList<>();
		}
		
		@SuppressWarnings("unchecked")
		private P cast() {
			return (P) this;
		}
		
		public @This P copyOf(final IDefaultedMenuType<M> type) {
			return this.copyOf(type.properties());
		}
		
		public @This P copyOf(final MenuTypeProperties<M> properties) {
			return this
					.previousOf(properties)
					.testAllOf(properties)
					.inventoryTypeOf(properties)
					.inventoryOf(properties)
					.readOnlyOf(properties)
					.handlersOf(properties)
					.titleOf(properties)
					.finalizerOf(properties)
					.paginationOf(properties);
		}
		
		public @This P previousWithTests(final IDefaultedMenuType<M> type) {
			return this.previous(type).testAllOf(type);
		}
		
		public @This P previousOf(final IDefaultedMenuType<M> type) {
			return this.previousOf(type.properties());
		}
		
		public @This P previousOf(final MenuTypeProperties<M> properties) {
			return this.previous(properties.defaultPrevious);
		}
		
		public @This P previous(final IMenuType<M> type) {
			this.defaultPrevious = type;
			return this.cast();
		}
		
		public @This P testAllOf(final IDefaultedMenuType<M> type) {
			return this.testAllOf(type.properties());
		}
		
		public @This P testAllOf(final MenuTypeProperties<M> properties) {
			return this.testAll(properties.tests);
		}
		
		public @This P testAll(final Iterable<Tuple<Predicate<M>, MenuAction<M>>> tests) {
			tests.forEach(test -> this.test(test));
			return this.cast();
		}
		
		public @This P testClose(final Predicate<M> predicate) {
			return this.test(predicate, MenuAction.close());
		}
		
		public @This P testBack(final Predicate<M> predicate) {
			return this.test(predicate, MenuAction.back());
		}
		
		public @This P testBackOrClose(final Predicate<M> predicate) {
			return this.test(predicate, MenuAction.backOrClose());
		}
		
		public @This P testEmpty(final Predicate<M> predicate) {
			return this.test(predicate, MenuAction.empty());
		}
		
		public @This P test(final Predicate<M> predicate, final MenuAction<M> action) {
			return this.test(Tuple.of(predicate, action));
		}
		
		public @This P test(final Tuple<Predicate<M>, MenuAction<M>> test) {
			this.tests.add(test);
			return this.cast();
		}
		
		public @This P inventoryTypeOf(final IDefaultedMenuType<M> type) {
			return this.inventoryTypeOf(type.properties());
		}
		
		public @This P inventoryTypeOf(final MenuTypeProperties<M> properties) {
			return this.inventoryType(properties.inventoryType);
		}
		
		public @This P inventoryType(final Supplier<ContainerType> type) {
			return this.inventoryType(type.get());
		}
		
		public @This P inventoryType(final ContainerType type) {
			this.inventoryType = type;
			return this.cast();
		}
		
		public @This P inventoryOf(final IDefaultedMenuType<M> type) {
			return this.inventoryOf(type.properties());
		}
		
		public @This P inventoryOf(final MenuTypeProperties<M> properties) {
			return this.inventory(properties.inventoryProvider);
		}
		
		public @This P inventory(final Supplier<ViewableInventory> inventory) {
			return this.inventory((menu) -> inventory.get());
		}
		
		public @This P inventory(final Function<M, ViewableInventory> inventory) {
			this.inventoryProvider = inventory;
			return this.cast();
		}
		
		public @This P readOnlyOf(final IDefaultedMenuType<M> type) {
			return this.readOnlyOf(type.properties());
		}
		
		public @This P readOnlyOf(final MenuTypeProperties<M> properties) {
			return this.readOnly(properties.readOnlyProvider);
		}
		
		public @This P readOnly(final boolean readOnly) {
			return this.readOnly((menu) -> readOnly);
		}
		
		public @This P readOnly(final Predicate<M> readOnly) {
			this.readOnlyProvider = readOnly;
			return this.cast();
		}
		
		public @This P handlersOf(final IDefaultedMenuType<M> type) {
			return this.handlersOf(type.properties());
		}
		
		public @This P handlersOf(final MenuTypeProperties<M> properties) {
			return this
					.onClickOf(properties)
					.onSlotClickOf(properties)
					.onKeySwapOf(properties)
					.onSlotChangeOf(properties)
					.onCloseOf(properties);
		}
		
		public @This P onClickOf(final IDefaultedMenuType<M> type) {
			return this.onClickOf(type.properties());
		}
		
		public @This P onClickOf(final MenuTypeProperties<M> properties) {
			return this.onClick(properties.onClick);
		}
		
		public @This P addOnClick(final ClickHandler handler, final BooleanBinaryOperator combiner) {
			return this.addOnClick(MenuClickHandler.asCustomHandler(handler), combiner);
		}
		
		public @This P addOnClick(final MenuClickHandler<M> handler, final BooleanBinaryOperator combiner) {
			return this.onClick(this.onClick == null ? handler : this.onClick.andThen(handler, combiner));
		}
		
		public @This P onClick(final ClickHandler handler) {
			return this.onClick(MenuClickHandler.asCustomHandler(handler));
		}
		
		public @This P onClick(final @Nullable MenuClickHandler<M> handler) {
			this.handlers.remove(this.onClick);
			this.onClick = handler;
			if (this.onClick != null) {
				this.handlers.add(this.onClick);
			}
			return this.cast();
		}
		
		public @This P onPaginationButtonClick() {
			return this.onSlotClick((menu, cause, container, slot, slotIndex, clickType) -> {
				menu.pagination().ifPresent(p -> p.handleButtonClick(slot.peek()));
				return false;
			});
		}
		
		public @This P onSlotClickOf(final IDefaultedMenuType<M> type) {
			return this.onSlotClickOf(type.properties());
		}
		
		public @This P onSlotClickOf(final MenuTypeProperties<M> properties) {
			return this.onSlotClick(properties.onSlotClick);
		}
		
		public @This P addOnSlotClick(final SlotClickHandler handler, final BooleanBinaryOperator combiner) {
			return this.addOnSlotClick(MenuSlotClickHandler.asCustomHandler(handler), combiner);
		}
		
		public @This P addOnSlotClick(final MenuSlotClickHandler<M> handler, final BooleanBinaryOperator combiner) {
			return this.onSlotClick(this.onSlotClick == null ? handler : this.onSlotClick.andThen(handler, combiner));
		}
		
		public @This P onSlotClick(final SlotClickHandler handler) {
			return this.onSlotClick(MenuSlotClickHandler.asCustomHandler(handler));
		}
		
		public @This P onSlotClick(final @Nullable MenuSlotClickHandler<M> handler) {
			this.handlers.remove(this.onSlotClick);
			this.onSlotClick = handler;
			if (this.onSlotClick != null) {
				this.handlers.add(this.onSlotClick);
			}
			return this.cast();
		}
		
		public @This P onKeySwapOf(final IDefaultedMenuType<M> type) {
			return this.onKeySwapOf(type.properties());
		}
		
		public @This P onKeySwapOf(final MenuTypeProperties<M> properties) {
			return this.onKeySwap(properties.onKeySwap);
		}
		
		public @This P addOnKeySwap(final KeySwapHandler handler, final BooleanBinaryOperator combiner) {
			return this.addOnKeySwap(MenuKeySwapHandler.asCustomHandler(handler), combiner);
		}
		
		public @This P addOnKeySwap(final MenuKeySwapHandler<M> handler, final BooleanBinaryOperator combiner) {
			return this.onKeySwap(this.onKeySwap == null ? handler : this.onKeySwap.andThen(handler, combiner));
		}
		
		public @This P onKeySwap(final KeySwapHandler handler) {
			return this.onKeySwap(MenuKeySwapHandler.asCustomHandler(handler));
		}
		
		public @This P onKeySwap(final @Nullable MenuKeySwapHandler<M> handler) {
			this.handlers.remove(this.onKeySwap);
			this.onKeySwap = handler;
			if (this.onKeySwap != null) {
				this.handlers.add(this.onKeySwap);
			}
			return this.cast();
		}
		
		public @This P onSlotChangeOf(final IDefaultedMenuType<M> type) {
			return this.onSlotChangeOf(type.properties());
		}
		
		public @This P onSlotChangeOf(final MenuTypeProperties<M> properties) {
			return this.onSlotChange(properties.onSlotChange);
		}
		
		public @This P addOnSlotChange(final SlotChangeHandler handler, final BooleanBinaryOperator combiner) {
			return this.addOnSlotChange(MenuSlotChangeHandler.asCustomHandler(handler), combiner);
		}
		
		public @This P addOnSlotChange(final MenuSlotChangeHandler<M> handler, final BooleanBinaryOperator combiner) {
			return this.onSlotChange(this.onSlotChange == null ? handler : this.onSlotChange.andThen(handler, combiner));
		}
		
		public @This P onSlotChange(final SlotChangeHandler handler) {
			return this.onSlotChange(MenuSlotChangeHandler.asCustomHandler(handler));
		}
		
		public @This P onSlotChange(final @Nullable MenuSlotChangeHandler<M> handler) {
			this.handlers.remove(this.onSlotChange);
			this.onSlotChange = handler;
			if (this.onSlotChange != null) {
				this.handlers.add(this.onSlotChange);
			}
			return this.cast();
		}
		
		public @This P onCloseOf(final IDefaultedMenuType<M> type) {
			return this.onCloseOf(type.properties());
		}
		
		public @This P onCloseOf(final MenuTypeProperties<M> properties) {
			return this.onClose(properties.onClose);
		}
		
		public @This P addOnClose(final CloseHandler handler) {
			return this.addOnClose(MenuCloseHandler.asCustomHandler(handler));
		}
		
		public @This P addOnClose(final MenuCloseHandler<M> handler) {
			return this.onClose(this.onClose == null ? handler : this.onClose.andThen(handler));
		}
		
		public @This P onClose(final CloseHandler handler) {
			return this.onClose(MenuCloseHandler.asCustomHandler(handler));
		}
		
		public @This P onClose(final @Nullable MenuCloseHandler<M> handler) {
			this.handlers.remove(this.onClose);
			this.onClose = handler;
			if (this.onClose != null) {
				this.handlers.add(this.onClose);
			}
			return this.cast();
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param type The menu type to copy title provider from
		 * @return This properties
		 */
		public @This P titleOf(final IDefaultedMenuType<M> type) {
			return this.titleOf(type.properties());
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param properties The properties to copy title provider from
		 * @return This properties
		 */
		public @This P titleOf(final MenuTypeProperties<M> properties) {
			return this.title(properties.titleProvider);
		}
		
		/**
		 * Sets empty title provider for this properties.
		 * 
		 * @return This properties
		 */
		public @This P titleEmpty() {
			return this.title(Component.empty());
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The plain text title
		 * @return This properties
		 */
		public @This P titlePlain(final String title) {
			return this.title(CompUtil.fromPlain(title));
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The mini message title
		 * @return This properties
		 */
		public @This P titleMini(final String title) {
			return this.title(CompUtil.fromMini(title));
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The title
		 * @return This properties
		 */
		public @This P title(final Component title) {
			return this.title((menu) -> title);
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The plain text title provider
		 * @return This properties
		 */
		public @This P titlePlain(final Function<M, String> title) {
			return this.title((menu) -> CompUtil.fromPlain(title.apply(menu)));
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The mini message title provider
		 * @return This properties
		 */
		public @This P titleMini(final Function<M, String> title) {
			return this.title((menu) -> CompUtil.fromMini(title.apply(menu)));
		}
		
		/**
		 * Sets title provider for this properties.
		 * 
		 * @param title The title provider
		 * @return This properties
		 */
		public @This P title(final Function<M, Component> title) {
			this.titleProvider = title;
			return this.cast();
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param type The menu type to copy finalizer from
		 * @return This properties
		 */
		public @This P finalizerOf(final IDefaultedMenuType<M> type) {
			return this.finalizerOf(type.properties());
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param properties The properties to copy finalizer from
		 * @return This properties
		 */
		public @This P finalizerOf(final MenuTypeProperties<M> properties) {
			return this.finalizer(properties.finalizer);
		}
		
		/**
		 * Sets empty finalizer for this properties.
		 * 
		 * @return This properties
		 */
		public @This P finalizerEmpty() {
			return this.finalizer((menu, container) -> {});
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param finalizer The finalizer to append to current finalizer
		 * @return This properties
		 */
		public @This P addFinalizer(final Consumer<Container> finalizer) {
			return this.addFinalizer((menu, container) -> finalizer.accept(container));
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param finalizer The finalizer to append to current finalizer
		 * @return This properties
		 */
		public @This P addFinalizer(final BiConsumer<M, Container> finalizer) {
			return this.finalizer(this.finalizer.andThen(finalizer));
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param finalizer The finalizer
		 * @return This properties
		 */
		public @This P finalizer(final Consumer<Container> finalizer) {
			return this.finalizer((menu, container) -> finalizer.accept(container));
		}
		
		/**
		 * Sets finalizer for this properties.
		 * 
		 * @param finalizer The finalizer
		 * @return This properties
		 */
		public @This P finalizer(final BiConsumer<M, Container> finalizer) {
			this.finalizer = finalizer;
			return this.cast();
		}
		
		/**
		 * Sets pagination stage and provider for this properties.
		 * 
		 * @param type The menu type to copy pagination stage and provider from
		 * @return This properties
		 */
		public @This P paginationOf(final IDefaultedMenuType<M> type) {
			return this.paginationOf(type.properties());
		}
		
		/**
		 * Sets pagination stage and provider for this properties.
		 * 
		 * @param properties The properties to copy pagination stage and provider from
		 * @return This properties
		 */
		public @This P paginationOf(final MenuTypeProperties<M> properties) {
			return this.pagination(properties.paginationStage, properties.paginationProvider);
		}
		
		/**
		 * Sets pagination stage and {@link MultiplePagination} provider with the given
		 * {@link PaginationConfig config} providers and {@link PageContentProvider content} providers.
		 * 
		 * @param stage The pagination stage
		 * @param size The amount of paginations
		 * @param cfg The config provider
		 * @param content The content provider
		 * @return This properties
		 */
		public @This P pagination(final PaginationInitStage stage, final int size,
			final IntFunction<PaginationConfig> cfg, final IntFunction<PageContentProvider<M, ?>> content
		) {
			return this.pagination(stage, (menu, inventory) -> Pagination.multiple(menu, inventory, size, cfg, content));
		}
		
		/**
		 * Sets pagination stage and {@link MultiplePagination} provider with the given
		 * {@link PaginationConfig config}s and {@link PageContentProvider content} providerss.
		 * 
		 * @param stage The pagination stage
		 * @param cfgs The configs
		 * @param contents The content providers
		 * @return This properties
		 * @throws IllegalArgumentException If sizes of configs and contents are not equal
		 */
		public @This P pagination(final PaginationInitStage stage,
			final List<PaginationConfig> cfgs, final List<? extends PageContentProvider<M, ?>> contents
		) {
			return this.pagination(stage, (menu, inventory) -> Pagination.multiple(menu, inventory, cfgs, contents));
		}
		
		/**
		 * Sets pagination stage and {@link SinglePagination} provider with the given
		 * {@link PaginationConfig config} and {@link PageContentProvider content}.
		 * 
		 * @param stage The pagination stage
		 * @param cfg The config
		 * @param content The content
		 * @return
		 */
		public @This P pagination(final PaginationInitStage stage,
			final PaginationConfig cfg, final PageContentProvider<M, ?> content
		) {
			return this.pagination(stage, (menu, inventory) -> Pagination.single(menu, inventory, cfg, content));
		}
		
		/**
		 * Sets pagination stage and provider for this properties.
		 * 
		 * @param stage The pagination stage
		 * @param provider The pagination provider
		 * @return This properties
		 */
		public @This P pagination(final PaginationInitStage stage,
			final BiFunction<M, Inventory, Pagination> provider
		) {
			this.paginationStage = stage;
			this.paginationProvider = provider;
			return this.cast();
		}
		
		@Override
		public @This P asMutable() {
			return this.cast();
		}
		
		@Override
		public MenuTypeProperties<M> asImmutable() {
			final MenuTypeProperties<M> props = new MenuTypeProperties<>();
			props.copyDataFrom(this);
			return props;
		}
	}
}
