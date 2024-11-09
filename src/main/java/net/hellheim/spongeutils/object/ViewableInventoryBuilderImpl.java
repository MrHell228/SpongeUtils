package net.hellheim.spongeutils.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.item.inventory.type.ViewableInventory.Builder.BuildingStep;
import org.spongepowered.api.item.inventory.type.ViewableInventory.Builder.EndStep;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.object.InventoryDecorator.Sized;
import net.hellheim.spongeutils.object.ViewableInventoryBuilder.*;
import net.hellheim.spongeutils.source.solid.item.IItemSource;

public class ViewableInventoryBuilderImpl extends InventoryOperatorImpl implements ViewableInventoryBuilder, TypeStep, SizedStructureStep, SizedDummyStep {
	
	private final PluginContainer plugin;
	private final @Nullable UUID uuid;
	private final @Nullable Carrier carrier;
	
	private final ViewableInventory.Builder builder;
	private @MonotonicNonNull ViewableInventory inventory;
	
	private @Nullable List<Integer> dummySlotsIndizes = null;
	
	protected ViewableInventoryBuilderImpl(final PluginContainer plugin, final @Nullable UUID uuid, final @Nullable Carrier carrier) {
		this.plugin = plugin;
		this.uuid = uuid;
		this.carrier = carrier;
		
		this.builder = ViewableInventory.builder();
	}
	
	// InventoryOperator
	
	@Override
	public ViewableInventoryBuilderImpl defineGrid(final int height, final int width) {
		super.defineGrid(height, width);
		return this;
	}
	
	@Override
	public ViewableInventoryBuilderImpl defineGrid(final Vector2i size) {
		super.defineGrid(size);
		return this;
	}
	
	// TypeStep
	
	@Override
	public StructureStep type(final ContainerType type) {
		this.builder.type(type);
		return this;
	}
	
	// StructureStep
	
	@Override
	public SizedDummyStep dummySlots(final int count, final int offset) {
		this.dummySlotsIndizes = null;
		((BuildingStep) this.builder).dummySlots(count, offset);
		return this;
	}
	
	@Override
	public SizedDummyStep dummySlotsAtIndizes(final List<Integer> at) {
		this.dummySlotsIndizes = at;
		return this;
	}
	
	@Override
	public SizedDummyStep fillDummy() {
		this.dummySlotsIndizes = null;
		((BuildingStep) this.builder).fillDummy();
		return this;
	}
	
	@Override
	public SizedStructureStep slots(final List<Slot> source, final int offset) {
		((BuildingStep) this.builder).slots(source, offset);
		return this;
	}
	
	@Override
	public SizedStructureStep slot(final Slot slot, final int at) {
		return this.slotsAtIndizes(List.of(slot), List.of(at));
	}
	
	@Override
	public SizedStructureStep slotsAtIndizes(final List<Slot> source, final List<Integer> at) {
		((BuildingStep) this.builder).slotsAtIndizes(source, at);
		return this;
	}
	
	// SizedStructureStep
	
	@Override
	public SizedDummyStep dummySlots(final int count, final Vector2i offset) {
		return this.dummySlots(count, this.posToIndex(offset));
	}
	
	@Override
	public SizedDummyStep dummySlotsAtPositions(final List<Vector2i> at) {
		return this.dummySlotsAtIndizes(at.stream().map(this::posToIndex).collect(Collectors.toList()));
	}
	
	@Override
	public SizedDummyStep dummyGrid(final Vector2i size, final int offset) {
		return this.dummyGrid(size, this.indexToPos(offset));
	}
	
	@Override
	public SizedDummyStep dummyGrid(final Vector2i size, final Vector2i offset) {
		final int xMin = offset.x();
		final int yMin = offset.y();
		final int xMax = xMin + size.x() - 1;
		final int yMax = yMin + size.y() - 1;

		final List<Integer> indizes = new ArrayList<>();
		for (int y = yMin; y <= yMax; y++) {
			for (int x = xMin; x <= xMax; x++) {
				indizes.add(this.posToIndex(x, y));
			}
		}
		return this.dummySlotsAtIndizes(indizes);
	}
	
	@Override
	public SizedStructureStep slots(final List<Slot> source, final Vector2i offset) {
		return this.slots(source, this.posToIndex(offset));
	}
	
	@Override
	public SizedStructureStep grid(final List<Slot> source, final Vector2i size, final int offset) {
		return this.grid(source, size, this.indexToPos(offset));
	}
	
	@Override
	public SizedStructureStep grid(final List<Slot> source, final Vector2i size, final Vector2i offset) {
		final int xMin = offset.x();
		final int yMin = offset.y();
		final int xMax = xMin + size.x() - 1;
		final int yMax = yMin + size.y() - 1;

		final List<Integer> indizes = new ArrayList<>();
		for (int y = yMin; y <= yMax; y++) {
			for (int x = xMin; x <= xMax; x++) {
				indizes.add(this.posToIndex(x, y));
			}
		}
		return this.slotsAtIndizes(source, indizes);
	}
	
	@Override
	public SizedStructureStep slot(final Slot slot, final Vector2i at) {
		return this.slot(slot, this.posToIndex(at));
	}
	
	@Override
	public SizedStructureStep slotsAtPositions(final List<Slot> source, final List<Vector2i> at) {
		return this.slotsAtIndizes(source, at.stream().map(this::posToIndex).collect(Collectors.toList()));
	}
	
	// DummyStep
	
	@Override
	public SizedStructureStep item(final IItemSource item) {
		return this.item(item.getAsItemStackSnapshot());
	}
	
	@Override
	public SizedStructureStep item(final ItemStackSnapshot item) {
		if (this.dummySlotsIndizes == null) {
			((DummyStep) this.builder).item(item);
		} else {
			if (!this.dummySlotsIndizes.isEmpty()) {
				final Iterator<Integer> indizes = new TreeSet<>(this.dummySlotsIndizes).iterator();
				int lastIndex = indizes.next();
				int consecutiveIndizes = 1;
				int offset = lastIndex;
				while (indizes.hasNext()) {
					int index = indizes.next();
					if (index == lastIndex + 1) {
						++consecutiveIndizes;
					} else {
						this.dummySlots(consecutiveIndizes, offset);
						consecutiveIndizes = 1;
						offset = index;
					}
					lastIndex = index;
				}
				this.dummySlots(consecutiveIndizes, offset);
			}
			this.dummySlotsIndizes = null;
		}
		return this;
	}
	
	@Override
	public SizedStructureStep item(final ItemStack item) {
		return this.item(item.createSnapshot());
	}
	
	@Override
	public SizedStructureStep empty() {
		return this.item(ItemStackSnapshot.empty());
	}
	
	// Structural methods
	
	@Override
	public ViewableInventory build() {
		final EndStep endStep = ((BuildingStep) this.builder)
				.completeStructure()
				.plugin(this.plugin);
		
		if (this.uuid != null) {
			endStep.identity(this.uuid);
		}
		
		if (this.carrier != null) {
			endStep.carrier(this.carrier);
		}
		
		return endStep.build();
	}
	
	@Override
	public Sized<ViewableInventory> decorate() {
		final InventoryDecoratorImpl<ViewableInventory> decorator = new InventoryDecoratorImpl<>(this.build());
		return this.isSizeSet() ? decorator.defineGrid(this.height, this.width) : decorator;
	}
}
