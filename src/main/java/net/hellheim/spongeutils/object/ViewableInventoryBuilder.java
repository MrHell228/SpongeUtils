package net.hellheim.spongeutils.object;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.source.solid.PluginSource;
import net.hellheim.spongeutils.source.solid.item.IItemSource;

public interface ViewableInventoryBuilder extends InventoryOperator {
	
	static TypeStep of(final PluginSource pluginSource) {
		return of(pluginSource.plugin());
	}
	
	static TypeStep of(final PluginSource pluginSource, final @Nullable Carrier carrier) {
		return of(pluginSource.plugin(), carrier);
	}
	
	static TypeStep of(final PluginSource pluginSource, final @Nullable UUID uuid) {
		return of(pluginSource.plugin(), uuid);
	}
	
	static TypeStep of(final PluginSource pluginSource, final @Nullable UUID uuid, final @Nullable Carrier carrier) {
		return of(pluginSource.plugin(), uuid, carrier);
	}
	
	static TypeStep of(final PluginContainer plugin) {
		return of(plugin, null, null);
	}
	
	static TypeStep of(final PluginContainer plugin, final @Nullable Carrier carrier) {
		return of(plugin, null, carrier);
	}
	
	static TypeStep of(final PluginContainer plugin, final @Nullable UUID uuid) {
		return of(plugin, uuid, null);
	}
	
	static TypeStep of(final PluginContainer plugin, final @Nullable UUID uuid, final @Nullable Carrier carrier) {
		return new ViewableInventoryBuilderImpl(plugin, uuid, carrier);
	}
	
	interface TypeStep {
		
		default StructureStep type(final Supplier<ContainerType> type) {
			return this.type(type.get());
		}
		
		StructureStep type(ContainerType type);
	}
	
	interface StructureStep extends ViewableInventoryBuilder {
		
		@Override
		SizedStructureStep defineGrid(int height, int width);
		
		@Override
		SizedStructureStep defineGrid(Vector2i size);
		
		/**
		 * Adds dummy-slots to the inventory.
		 *
		 * @param count  The amount of slots to add
		 * @param offset The offset for adding the slots
		 *
		 * @return The dummy building step
		 */
		DummyStep dummySlots(int count, int offset);
		
		/**
		 * Adds dummy-slots to the inventory at given indizes.
		 *
		 * @param at The indizes
		 *
		 * @return The dummy building step
		 */
		DummyStep dummySlotsAtIndizes(List<Integer> at);
		
		/**
		 * Adds all undefined slots as dummy slots.
		 *
		 * @return The dummy building step.
		 */
		DummyStep fillDummy();
		
		/**
		 * Adds given slots to the inventory.
		 *
		 * @param source The source slots.
		 * @param offset The offset for adding the slots
		 *
		 * @return The structure step
		 */
		StructureStep slots(List<Slot> source, int offset);
		
		/**
		 * Adds given slot to the inventory at given index.
		 *
		 * @param source The slot
		 * @param at     The index
		 *
		 * @return The structure step
		 */
		StructureStep slot(Slot slot, int at);
		
		/**
		 * Adds given slots to the inventory at given indizes.
		 *
		 * @param source The source slots
		 * @param at     The indizes
		 *
		 * @return The structure step
		 */
		StructureStep slotsAtIndizes(List<Slot> source, List<Integer> at);
		
		/**
		 * Completes ths inventory structure and returns it.
		 * 
		 * <p>If no slots are defined this will create the structure mirroring the vanilla type.</p>
		 * <p>If some but not all slots are defined undefined slots will be defined using {@link #fillDummy()}</p>
		 * 
		 * @return The inventory
		 */
		ViewableInventory build();
		
		/**
		 * Returns {@link InventoryDecorator} for inventory obtained by {@link #build()}.
		 * 
		 * @return The inventory decorator
		 */
		InventoryDecorator<ViewableInventory> decorate();
	}
	
	interface SizedStructureStep extends StructureStep {
		
		@Override
		SizedDummyStep dummySlots(int count, int offset);
		
		@Override
		SizedDummyStep dummySlotsAtIndizes(List<Integer> at);
		
		@Override
		SizedDummyStep fillDummy();
		
		@Override
		SizedStructureStep slots(List<Slot> source, int offset);
		
		@Override
		SizedStructureStep slot(Slot slot, int at);
		
		@Override
		SizedStructureStep slotsAtIndizes(List<Slot> source, List<Integer> at);
		
		@Override
		InventoryDecorator.Sized<ViewableInventory> decorate();
		
		/**
		 * Adds dummy-slots to the inventory.
		 *
		 * @param count  The amount of slots to add
		 * @param offset The offset for adding the slots
		 *
		 * @return The dummy building step
		 */
		SizedDummyStep dummySlots(int count, Vector2i offset);
		
		/**
		 * Adds dummy-slots to the inventory at given positions.
		 *
		 * @param at The positions
		 *
		 * @return The dummy building step
		 */
		SizedDummyStep dummySlotsAtPositions(List<Vector2i> at);
		
		/**
		 * Adds a grid of dummy-slots to the inventory.
		 *
		 * @param size   The size of the grid
		 * @param offset The offset for adding the slots
		 *
		 * @return The dummy building step
		 */
		SizedDummyStep dummyGrid(Vector2i size, int offset);
		
		/**
		 * Adds a grid of dummy-slots to the inventory.
		 *
		 * @param size   The size of the grid
		 * @param offset The offset for adding the slots
		 *
		 * @return The dummy building step
		 */
		SizedDummyStep dummyGrid(Vector2i size, Vector2i offset);
		
		/**
		 * Adds given slots to the inventory.
		 *
		 * @param source The source slots.
		 * @param offset The offset for adding the slots
		 *
		 * @return The structure step
		 */
		SizedStructureStep slots(List<Slot> source, Vector2i offset);
		
		/**
		 * Adds given slots to the inventory in a grid.
		 *
		 * @param source The source slots.
		 * @param size   The size if the grid
		 * @param offset The offset for adding the slots.
		 *
		 * @return The structure step
		 */
		SizedStructureStep grid(List<Slot> source, Vector2i size, int offset);
		
		/**
		 * Adds given slots to the inventory in a grid.
		 *
		 * @param source The source slots.
		 * @param size   The size if the grid
		 * @param offset The offset for adding the slots.
		 *
		 * @return The structure step
		 */
		SizedStructureStep grid(List<Slot> source, Vector2i size, Vector2i offset);
		
		/**
		 * Adds given slot to the inventory at given position.
		 *
		 * @param source The slot
		 * @param at     The position
		 *
		 * @return The structure step
		 */
		SizedStructureStep slot(Slot slot, Vector2i at);
		
		/**
		 * Adds given slots to the inventory at given positions
		 *
		 * @param source The source slots
		 * @param at     The indizes
		 *
		 * @return The structure step
		 */
		SizedStructureStep slotsAtPositions(List<Slot> source, List<Vector2i> at);
	}
	
	interface DummyStep {
		
		/**
		 * Sets the default item for the dummy-slots.
		 *
		 * @param item The default item
		 *
		 * @return The structure step
		 */
		StructureStep item(IItemSource item);
		
		/**
		 * Sets the default item for the dummy-slots.
		 *
		 * @param item The default item
		 *
		 * @return The structure step
		 */
		StructureStep item(ItemStackLike item);
		
		/**
		 * Sets {@link ItemStackSnapshot#empty()} as default item for the dummy-slots.
		 *
		 * @return The structure step
		 */
		StructureStep empty();
	}
	
	interface SizedDummyStep extends DummyStep {
		
		@Override
		SizedStructureStep item(IItemSource item);
		
		@Override
		SizedStructureStep item(ItemStackLike item);
		
		@Override
		SizedStructureStep empty();
	}
}
