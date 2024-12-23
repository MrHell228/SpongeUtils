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
	
	
	
	public static boolean isServer() {
		return Sponge.isServerAvailable() && Sponge.server().onMainThread();
	}
	
	public static boolean isClient() {
		return Sponge.isClientAvailable() && Sponge.client().onMainThread();
	}
	
	public static boolean isAsync() {
		return !TaskUtil.isServer() && !TaskUtil.isClient();
	}
	
	
	public static ScheduledTask sync(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.sync(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask sync(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return TaskUtil.sync(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask sync(final PluginContainer plugin, final Task.Builder builder) {
		return TaskUtil.sync(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask sync(final Task task) {
		return TaskUtil.task(SERVER, task);
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.async(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return TaskUtil.async(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask async(final PluginContainer plugin, final Task.Builder builder) {
		return TaskUtil.async(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask async(final Task task) {
		return TaskUtil.task(ASYNC, task);
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Runnable executor) {
		return TaskUtil.task(plugin, sync, builder().execute(executor));
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Consumer<ScheduledTask> executor) {
		return TaskUtil.task(plugin, sync, builder().execute(executor));
	}
	
	public static ScheduledTask task(final PluginContainer plugin, final boolean sync, final Task.Builder builder) {
		return TaskUtil.task(sync, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask task(final boolean sync, final Task task) {
		if (sync) {
			return TaskUtil.sync(task);
		} else {
			return TaskUtil.async(task);
		}
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.task(scheduler, plugin, builder().execute(executor));
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return TaskUtil.task(scheduler, plugin, builder().execute(executor));
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final PluginContainer plugin, final Task.Builder builder) {
		return TaskUtil.task(scheduler, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask task(final Scheduler scheduler, final Task task) {
		return scheduler.submit(task);
	}
	
	public static Optional<ScheduledTask> optionalSync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optional(plugin, shouldCreate ? SERVER : null, executor);
	}
	
	public static Optional<ScheduledTask> optionalAsync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optional(plugin, shouldCreate ? ASYNC : null, executor);
	}
	
	public static Optional<ScheduledTask> optional(final PluginContainer plugin, final @Nullable Scheduler scheduler, final Runnable executor) {
		if (scheduler != null) {
			return Optional.of(TaskUtil.task(scheduler, plugin, executor));
		} else {
			executor.run();
			return Optional.empty();
		}
	}
	
	public static Optional<ScheduledTask> ensureServer(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.optionalSync(plugin, !TaskUtil.isServer(), executor);
	}
	
	public static Optional<ScheduledTask> ensureAsync(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.optionalAsync(plugin, !TaskUtil.isAsync(), executor);
	}
	
	/**
	 * Submits one tick delayed sync task.
	 * 
	 * @param plugin The plugin
	 * @param executor Task executor
	 * @return Scheduled task
	 */
	public static ScheduledTask tickDelayedSync(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.sync(plugin, builder().delay(TICK).execute(executor));
	}
	
	public static void syncTasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, SERVER);
	}
	
	public static void asyncTasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, ASYNC);
	}
	
	private static void tasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread, 
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd, final Scheduler scheduler) {
		Task.Builder builder = Task.builder().plugin(plugin);
		TaskUtil.async(plugin, () -> {
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
