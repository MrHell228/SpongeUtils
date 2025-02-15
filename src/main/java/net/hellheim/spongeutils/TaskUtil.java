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
	
	
	public static ScheduledTask server(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.server(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask server(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return TaskUtil.server(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask server(final PluginContainer plugin, final Task.Builder builder) {
		return TaskUtil.server(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask server(final Task task) {
		return TaskUtil.task(SERVER, task);
	}
	
	public static ScheduledTask client(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.client(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask client(final PluginContainer plugin, final Consumer<ScheduledTask> executor) {
		return TaskUtil.client(plugin, builder().execute(executor));
	}
	
	public static ScheduledTask client(final PluginContainer plugin, final Task.Builder builder) {
		return TaskUtil.client(builder.plugin(plugin).build());
	}
	
	public static ScheduledTask client(final Task task) {
		return TaskUtil.task(CLIENT, task);
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
	
	public static ScheduledTask serverOrAsync(final PluginContainer plugin, final boolean server, final Runnable executor) {
		return TaskUtil.serverOrAsync(plugin, server, builder().execute(executor));
	}
	
	public static ScheduledTask serverOrAsync(final PluginContainer plugin, final boolean server, final Consumer<ScheduledTask> executor) {
		return TaskUtil.serverOrAsync(plugin, server, builder().execute(executor));
	}
	
	public static ScheduledTask serverOrAsync(final PluginContainer plugin, final boolean server, final Task.Builder builder) {
		return TaskUtil.serverOrAsync(server, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask serverOrAsync(final boolean server, final Task task) {
		if (server) {
			return TaskUtil.server(task);
		} else {
			return TaskUtil.async(task);
		}
	}
	
	public static ScheduledTask clientOrAsync(final PluginContainer plugin, final boolean client, final Runnable executor) {
		return TaskUtil.clientOrAsync(plugin, client, builder().execute(executor));
	}
	
	public static ScheduledTask clientOrAsync(final PluginContainer plugin, final boolean client, final Consumer<ScheduledTask> executor) {
		return TaskUtil.clientOrAsync(plugin, client, builder().execute(executor));
	}
	
	public static ScheduledTask clientOrAsync(final PluginContainer plugin, final boolean client, final Task.Builder builder) {
		return TaskUtil.clientOrAsync(client, builder.plugin(plugin).build());
	}
	
	public static ScheduledTask clientOrAsync(final boolean client, final Task task) {
		if (client) {
			return TaskUtil.client(task);
		} else {
			return TaskUtil.async(task);
		}
	}
	
	public static Optional<ScheduledTask> optionalServer(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optional(plugin, shouldCreate ? SERVER : null, executor);
	}
	
	public static Optional<ScheduledTask> optionalClient(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optional(plugin, shouldCreate ? CLIENT : null, executor);
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
		return TaskUtil.optionalServer(plugin, !TaskUtil.isServer(), executor);
	}
	
	public static Optional<ScheduledTask> ensureClient(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.optionalClient(plugin, !TaskUtil.isClient(), executor);
	}
	
	public static Optional<ScheduledTask> ensureAsync(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.optionalAsync(plugin, !TaskUtil.isAsync(), executor);
	}
	
	public static Optional<ScheduledTask> ensureServerOrAsync(final PluginContainer plugin, final boolean server, final Runnable executor) {
		if (server) {
			return TaskUtil.ensureServer(plugin, executor);
		} else {
			return TaskUtil.ensureAsync(plugin, executor);
		}
	}
	
	public static Optional<ScheduledTask> ensureClientOrAsync(final PluginContainer plugin, final boolean client, final Runnable executor) {
		if (client) {
			return TaskUtil.ensureClient(plugin, executor);
		} else {
			return TaskUtil.ensureAsync(plugin, executor);
		}
	}
	
	/**
	 * Submits one tick delayed server task.
	 * 
	 * @param plugin The plugin
	 * @param executor Task executor
	 * @return Scheduled task
	 */
	public static ScheduledTask tickDelayedServer(final PluginContainer plugin, final Runnable executor) {
		return TaskUtil.server(plugin, builder().delay(TICK).execute(executor));
	}
	
	public static void serverTasksFromAsync(final PluginContainer plugin,
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
