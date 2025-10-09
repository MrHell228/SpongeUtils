package net.hellheim.spongetools.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventContext;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.item.inventory.AffectItemStackEvent;
import org.spongepowered.api.event.item.inventory.AffectSlotEvent;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.collection.StringList;

public final class EventUtil {
	
	private static final Logger LOG = LogManager.getLogger("EventUtil");
	private static final int SHIFT = 3;
	private static final String DASH = " - ";
	
	public static void dump(final Event e) {
		LOG.info("");
		LOG.info("");
		list(e).forEach(LOG::info);
	}
	
	public static void dump(final CommandContext context) {
		LOG.info("");
		LOG.info("");
		list(context).forEach(LOG::info);
	}
	
	public static void dump(final CommandCause cause) {
		LOG.info("");
		LOG.info("");
		list(cause).forEach(LOG::info);
	}
	
	private static StringList list(final Event e) {
		final StringList result = new StringList();
		final String clazz = e.getClass().getSimpleName();
		
		if (e instanceof final ChangeBlockEvent.All eventWithTransactions) {
			result.addAll(listTransactions(
					"Block Transactions of " + clazz,
					eventWithTransactions.transactions(),
					bt -> {
						final StringList tr = new StringList();
						tr.add(DASH + "Operation: " + toString(bt.operation()));
						tr.add(DASH + "Original:  " + toString(bt.original()));
						if (bt.custom().isPresent()) {
							tr.add(DASH + "Default:   " + toString(bt.defaultReplacement()));
							tr.add(DASH + "Custom:    " + toString(bt.custom().get()));
						} else {
							tr.add(DASH + "Final:     " + toString(bt.finalReplacement()));
						}
						tr.add(DASH + "Valid:     " + bt.isValid());
						return tr;
					}));
		} else if (e instanceof final AffectSlotEvent eventWithTransactions) {
			result.addAll(listTransactions(
					"Slot Transactions of " + clazz,
					eventWithTransactions.transactions(),
					st -> {
						final StringList tr = new StringList();
						tr.add(DASH + "Slot:     " + toString(st.slot()));
						tr.add(DASH + "Original: " + toString(st.original()));
						if (st.custom().isPresent()) {
							tr.add(DASH + "Default:  " + toString(st.defaultReplacement()));
							tr.add(DASH + "Custom:   " + toString(st.custom().get()));
						} else {
							tr.add(DASH + "Final:    " + toString(st.finalReplacement()));
						}
						tr.add(DASH + "Valid:    " + st.isValid());
						return tr;
					}));
		} else if (e instanceof final AffectItemStackEvent eventWithTransactions) {
			result.addAll(listTransactions(
					"ItemStack Transactions of " + clazz,
					eventWithTransactions.transactions(),
					st -> {
						final StringList tr = new StringList();
						tr.add(DASH + "Original: " + toString(st.original()));
						if (st.custom().isPresent()) {
							tr.add(DASH + "Default:  " + toString(st.defaultReplacement()));
							tr.add(DASH + "Custom:   " + toString(st.custom().get()));
						} else {
							tr.add(DASH + "Final:    " + toString(st.finalReplacement()));
						}
						tr.add(DASH + "Valid:    " + st.isValid());
						return tr;
					}));
		}
		
		result.addAll(list(e.context(), clazz));
		result.addAll(list(e.cause(), clazz));
		
		result.shift(SHIFT);
		result.add(0, DASH + "Listing event " + clazz);
		return result;
	}
	
	private static StringList list(final CommandContext context) {
		final StringList result = new StringList();
		final String name = "CommandContext";
		
		context.executedCommand().ifPresent(command -> result.add(DASH + command));
		result.addAll(list(context.cause()));
		
		result.shift(SHIFT);
		result.add(0, DASH + "Listing " + name);
		return result;
	}
	
	private static StringList list(final CommandCause cause) {
		final StringList result = new StringList();
		final String name = "CommandCause";
		
		result.add(DASH + "Subject: " + cause.subject());
		result.add(DASH + "Audience: " + cause.audience());
		
		cause.location().ifPresent(location -> result.add(DASH + "Location: " + location));
		cause.rotation().ifPresent(rotation -> result.add(DASH + "Rotation:" + rotation));
		cause.targetBlock().ifPresent(target -> result.add(DASH + "TargetBlock: " + target));
		
		result.addAll(list(cause.context(), name));
		result.addAll(list(cause.cause(), name));
		
		result.shift(SHIFT);
		result.add(0, DASH + "Listing " + name);
		return result;
	}
	
	private static StringList list(final Cause cause, final String name) {
		final StringList result = new StringList();
		cause.forEach(o -> {
			if (o instanceof Event) {
				result.addAll(list((Event) o));
			} else {
				result.add(DASH + objectToString(o, () -> o.getClass().getSimpleName()));
			}
		});
		
		result.add(0, "Cause of " + name);
		result.shift(SHIFT);
		return result;
	}
	
	private static StringList list(final EventContext context, final String name) {
		final StringList result = new StringList();
		context.asMap().forEach((key, o) -> {
			result.add(DASH + key.key().asString() + DASH + objectToString(o, o::toString));
		});
		
		result.add(0, "Context of " + name);
		result.shift(SHIFT);
		return result;
	}
	
	private static <T extends Transaction<?>> StringList listTransactions(
		final String header, final Iterable<T> transactions, final Function<T, StringList> transactionMapper
	) {
		final StringList transactionList = new StringList();
		final MutableInt counter = new MutableInt(1);
		
		transactions.forEach(transaction -> {
			final StringList tr = new StringList();
			tr.addAll(transactionMapper.apply(transaction));
			tr.shift(SHIFT);
			tr.add(0, DASH + "Transaction №" + counter.getAndIncrement() + ":");
			transactionList.addAll(tr);
		});
		
		transactionList.add(0, header);
		transactionList.shift(SHIFT);
		return transactionList;
	}
	
	private static String objectToString(final Object object, final Supplier<String> defaultString) {
		return switch (object) {
			case BlockSnapshot bs -> toString(bs);
			case LocatableBlock lb -> toString(lb);
			case StringRepresentable sr -> toString(sr);
			case Enum<?> en -> toString(en);
			default -> defaultString.get();
		};
	}
	
	private static String toString(final StringRepresentable sr) {
		return sr.getClass().getSimpleName() + " " + sr.serializationString().toUpperCase();
	}
	
	private static String toString(final Enum<?> en) {
		return en.getClass().getSimpleName() + " " + en.name();
	}
	
	private static String toString(final Slot slot) {
		return toString(slot.peek());
	}
	
	private static String toString(final ItemStackLike stack) {
		if (stack.isEmpty()) {
			return "Empty ItemStack";
		}
		return "ItemStack{"
				+ "type=" + stack.type().toString() + "; "
				+ "quantity=" + stack.quantity()
				+ "}";
	}
	
	private static String toString(final BlockSnapshot bs) {
		return "BlockSnapshot{"
				+ toString(bs.world(), bs.position(), bs.state())
				+ "}";
	}
	
	private static String toString(final LocatableBlock lb) {
		return "LocatableBlock{"
				+ toString(lb.serverLocation().worldKey(), lb.blockPosition(), lb.blockState())
				+ "}";
	}
	
	private static String toString(final ResourceKey world, final Vector3i pos, final BlockState state) {
		return "world=" + world.asString() + "; " +
				"pos=" + pos.toString() + "; " +
				"state=" + state.asString();
	}
	
	private static final Map<Operation, String> OPERATIONS = new HashMap<>();
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
