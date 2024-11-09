package net.hellheim.spongeutils;

import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.plugin.PluginContainer;

public final class TaskUtil {
	
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
		return Sponge.server().scheduler().submit(task);
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
		return Sponge.asyncScheduler().submit(task);
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
	
	public static Optional<ScheduledTask> optionalSync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		if (shouldCreate) {
			return Optional.of(sync(plugin, executor));
		} else {
			executor.run();
			return Optional.empty();
		}
	}
	
	public static Optional<ScheduledTask> optionalAsync(final PluginContainer plugin, final boolean shouldCreate, final Runnable executor) {
		if (shouldCreate) {
			return Optional.of(async(plugin, executor));
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
		tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, TaskUtil::sync);
	}
	
	public static void asyncTasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		tasksFromAsync(plugin, sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd, TaskUtil::async);
	}
	
	private static void tasksFromAsync(final PluginContainer plugin,
			final long sleepingTime, final int tasks, final int tasksPerThread, 
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd, final Consumer<Task> submitter) {
		Task.Builder builder = Task.builder().plugin(plugin);
		async(plugin, () -> {
			onStart.run();
			
			int fullThreads = tasks / tasksPerThread;
			MutableInt task = new MutableInt();
			MutableBoolean flag = new MutableBoolean(false);
			for (int thread = 0; thread < fullThreads; ++thread) {
				submitter.accept(builder.execute(() -> {
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
				submitter.accept(builder.execute(() -> {
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
