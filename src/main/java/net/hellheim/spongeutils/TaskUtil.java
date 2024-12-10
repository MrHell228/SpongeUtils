package net.hellheim.spongeutils;

import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.source.solid.SchedulerSource;

public final class TaskUtil {
	
	public static final SchedulerSource ASYNC = () -> Sponge.asyncScheduler();
	public static final SchedulerSource SERVER = () -> Sponge.server().scheduler();
	public static final SchedulerSource CLIENT = () -> Sponge.client().scheduler();
	
	private static final Ticks TICK = Ticks.of(1);
	
	private static Task.Builder builder() {
		return Task.builder();
	}
	
	private static void sleep(final long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static ScheduledTask sync(final PluginContainer plugin, final Runnable executor) {
		return sync(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask sync(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return sync(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask sync(final PluginContainer plugin, final Task.Builder builder) {
		return sync(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask sync(final Task task) {
		return task(SERVER, task);
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Runnable executor) {
		return async(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return async(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Task.Builder builder) {
		return async(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask async(final Task task) {
		return task(ASYNC, task);
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Runnable executor) {
		return task(plugin, sync, builder().execute(executor));
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Consumer<ScheduledTask> executor) {
		return task(plugin, sync, builder().execute(executor));
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Task.Builder builder) {
		return task(sync, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask task(final boolean sync, final Task task) {
		if (sync) {
			return sync(task);
		} else {
			return async(task);
		}
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Runnable executor) {
		return task(scheduler, plugin, builder().execute(executor));
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return task(scheduler, plugin, builder().execute(executor));
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Task.Builder builder) {
		return task(scheduler, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final Task task) {
		return scheduler.submit(task);
	}
	
	public static Optional<ScheduledTask> optionalSync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return optional(plugin, shouldCreate ? SERVER : null, executor);
	}
	
	public static Optional<ScheduledTask> optionalAsync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return optional(plugin, shouldCreate ? ASYNC : null, executor);
	}
	
	public static Optional<ScheduledTask> optional(final PluginContainer plugin, final @Nullable Scheduler scheduler, final Runnable executor) {
		if (scheduler != null) {
			return Optional.of(task(scheduler, plugin, executor));
		} else {
			executor.run();
			return Optional.empty();
		}
	}
	
	/**
	 * Submits one tick delayed sync task.
	 * 
	 * @param plugin The plugin
	 * @param executor Task executor
	 * @return Scheduled task
	 */
	public static ScheduledTask tickDelayedSync(final PluginContainer plugin, final Runnable executor) {
		return sync(plugin, builder().delay(TICK).execute(executor));
	}
	
	public static void syncTasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, SERVER);
	}
	
	public static void asyncTasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, ASYNC);
	}
	
	private static void tasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread, 
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd, final Scheduler scheduler) {
		Task.Builder builder = Task.builder().plugin(plugin);
		async(plugin, () -> {
			onStart.run();
			
			int fullThreads = tasks / tasksPerThread;
			MutableInt task = new MutableInt();
			MutableBoolean flag = new MutableBoolean(false);
			for (int thread = 0; thread < fullThreads; ++thread) {
				scheduler.submit(builder.execute(() -> {
					for (int i = 0; i < tasksPerThread; ++i) {
						taskCons.accept(task.getAndIncrement());
					}
					
					flag.setTrue();
				}).build());
				
				while (flag.isFalse()) {
					sleep(sleepingTime);
				}
				
				sleep(sleepingTime);
				flag.setFalse();
			}
			
			int remainingTasks = tasks % tasksPerThread;
			if (remainingTasks > 0) {
				scheduler.submit(builder.execute(() -> {
					for (int i = 0; i < remainingTasks; ++i) {
						taskCons.accept(task.getAndIncrement());
					}
					
					flag.setTrue();
				}).build());
				
				while (flag.isFalse()) {
					sleep(sleepingTime);
				}
				
				flag.setFalse();
			}
			
			onEnd.run();
		});
	}
	
	private TaskUtil() {
	}
}
