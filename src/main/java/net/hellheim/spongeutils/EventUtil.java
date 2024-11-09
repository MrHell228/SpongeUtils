package net.hellheim.spongeutils;

import java.util.HashMap;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.BlockTransactionReceipt;
import org.spongepowered.api.block.transaction.Operation;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongeutils.collection.StringList;

public final class EventUtil {
	
	private static final Logger LOG = LogManager.getLogger("EventUtil");
	
	public static void dump(final Event e) {
		LOG.info("");
		LOG.info("");
		list(e).forEach(s -> LOG.info(s));
	}
	
	private static StringList list(final Event e) {
		final StringList result = new StringList();
		final String clazz = e.getClass().getSimpleName();
		final int shift = 3;
		final String dash = " - ";
		
		if (e instanceof ChangeBlockEvent.All) {
			final StringList transactions = new StringList();
			final MutableInt counter = new MutableInt(1);
			((ChangeBlockEvent.All) e).transactions().forEach(bt -> {
				final StringList tr = new StringList();
				tr.add(dash + "Operation: " + toString(bt.operation()));
				tr.add(dash + "Original:  " + toString(bt.original()));
				if (bt.custom().isPresent()) {
					tr.add(dash + "Default:   " + toString(bt.defaultReplacement()));
					tr.add(dash + "Custom:    " + toString(bt.custom().get()));
				}
				tr.add(dash + "Final:     " + toString(bt.finalReplacement()));
				
				tr.shift(shift);
				tr.add(0, dash + "Transaction №" + counter.getAndIncrement() + ":");
				transactions.addAll(tr);
			});
			
			transactions.add(0, "BlockTransactions of " + clazz);
			transactions.shift(shift);
			result.addAll(transactions);
		}
		
		final StringList context = new StringList();
		e.context().asMap().forEach((key, o) -> context.add(dash + key.key().asString() + " - " + o.toString()));
		context.add(0, "Context of " + clazz);
		context.shift(shift);
		result.addAll(context);
		
		final StringList cause = new StringList();
		e.cause().forEach(o -> {
			if (o instanceof Event) {
				cause.addAll(list((Event) o));
			} else if (o instanceof LocatableBlock) {
				cause.add(dash + "LocatableBlock: " + toString((LocatableBlock) o));
			} else {
				cause.add(dash + o.getClass().getSimpleName());
			}
		});
		cause.add(0, "Cause of " + clazz);
		cause.shift(shift);
		result.addAll(cause);
		
		result.shift(shift);
		result.add(0, dash + "Listing event " + clazz);
		return result;
	}
	
	private static String toString(final BlockSnapshot bs) {
		return toString(bs.world(), bs.position(), bs.state());
	}
	private static String toString(final LocatableBlock lb) {
		return toString(lb.serverLocation().worldKey(), lb.blockPosition(), lb.blockState());
	}
	private static String toString(final ResourceKey world, final Vector3i pos, final BlockState state) {
		return "World=" + world.asString() + "; " +
				"Loc=" + pos.toString() + "; " +
				"State=" + state.asString() + ";";
	}
	
	private static final HashMap<Operation, String> OPERATIONS = new HashMap<>();
	static {
		OPERATIONS.put(Operations.BREAK.get(), "BREAK");
		OPERATIONS.put(Operations.PLACE.get(), "PLACE");
		OPERATIONS.put(Operations.MODIFY.get(), "MODIFY");
		OPERATIONS.put(Operations.GROWTH.get(), "GROWTH");
		OPERATIONS.put(Operations.DECAY.get(), "DECAY");
		OPERATIONS.put(Operations.LIQUID_DECAY.get(), "LIQUID_DECAY");
		OPERATIONS.put(Operations.LIQUID_SPREAD.get(), "LIQUID_SPREAD");
	}
	private static String toString(final Operation op) {
		return OPERATIONS.getOrDefault(op, "Unable to find operation ID");
	}
	
	
	/*
	@Exclude(value = {
			MoveEntityEvent.class, CriterionEvent.class,
			ChunkEvent.class, CollideBlockEvent.class,
			SpawnEntityEvent.class, ConstructEntityEvent.class,
			RotateEntityEvent.class, BreedingEvent.class,
			AffectItemStackEvent.class, TickBlockEvent.class
			})
	@Listener
	public void event(Event e) {
		EventUtil.dump(e);
	}
	*/
	
	
	public static boolean is(final BlockTransactionReceipt bt1, final BlockTransactionReceipt bt2) {
		return is(bt1.operation(), bt2.operation());
	}
	public static boolean is(final BlockTransactionReceipt bt1, final BlockTransaction bt2) {
		return is(bt1.operation(), bt2.operation());
	}
	public static boolean is(final BlockTransactionReceipt bt, final Supplier<Operation> o) {
		return is(bt.operation(), o.get());
	}
	public static boolean is(final BlockTransactionReceipt bt, final Operation o) {
		return is(bt.operation(), o);
	}
	public static boolean is(final BlockTransaction bt1, final BlockTransactionReceipt bt2) {
		return is(bt1.operation(), bt2.operation());
	}
	public static boolean is(final BlockTransaction bt1, final BlockTransaction bt2) {
		return is(bt1.operation(), bt2.operation());
	}
	public static boolean is(final BlockTransaction bt, final Supplier<Operation> o) {
		return is(bt.operation(), o.get());
	}
	public static boolean is(final BlockTransaction bt, final Operation o) {
		return is(bt.operation(), o);
	}
	public static boolean is(final Supplier<Operation> o1, final BlockTransactionReceipt bt) {
		return is(o1.get(), bt.operation());
	}
	public static boolean is(final Supplier<Operation> o1, final BlockTransaction bt) {
		return is(o1.get(), bt.operation());
	}
	public static boolean is(final Supplier<Operation> o1, final Supplier<Operation> o2) {
		return is(o1.get(), o2.get());
	}
	public static boolean is(final Supplier<Operation> o1, final Operation o2) {
		return is(o1.get(), o2);
	}
	public static boolean is(final Operation o1, final BlockTransactionReceipt bt) {
		return is(o1, bt.operation());
	}
	public static boolean is(final Operation o1, final BlockTransaction bt) {
		return is(o1, bt.operation());
	}
	public static boolean is(final Operation o1, final Supplier<Operation> o2) {
		return is(o1, o2.get());
	}
	public static boolean is(final Operation o1, final Operation o2) {
		return o1 == o2;
	}
	
	
	public static boolean is(final Supplier<? extends ClickType<?>> t1, final ClickType<?> t2) {
		return is(t1.get(), t2);
	}
	public static boolean is(final ClickType<?> t1, final Supplier<? extends ClickType<?>> t2) {
		return is(t1, t2.get());
	}
	public static boolean is(final ClickType<?> t1, final ClickType<?> t2) {
		return t1 == t2;
	}
	
	
	public static boolean contains(final Event e, final Class<?> clazz) {
		return contains(e.cause(), clazz);
	}
	public static boolean contains(final Cause cause, final Class<?> clazz) {
		return cause.first(clazz).isPresent();
	}
	
	private EventUtil() {
	}
}
