package net.hellheim.spongeutils.manager;

import java.util.Optional;
import java.util.function.Consumer;

import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.TaskUtil;

public class TaskManager extends Manager {
	
	public TaskManager(final PluginContainer plugin) {
		super(plugin);
	}
	
	public ScheduledTask sync(final Runnable executor) {
		return TaskUtil.sync(this.plugin(), executor);
	}
	
	public ScheduledTask sync(final Consumer<ScheduledTask> executor) {
		return TaskUtil.sync(this.plugin(), executor);
	}
	
	public ScheduledTask sync(final Task.Builder builder) {
		return TaskUtil.sync(this.plugin(), builder);
	}
	
	public ScheduledTask sync(final Task task) {
		return TaskUtil.sync(task);
	}
	
	public ScheduledTask async(final Runnable executor) {
		return TaskUtil.async(this.plugin(), executor);
	}
	
	public ScheduledTask async(final Consumer<ScheduledTask> executor) {
		return TaskUtil.async(this.plugin(), executor);
	}
	
	public ScheduledTask async(final Task.Builder builder) {
		return TaskUtil.async(this.plugin(), builder);
	}
	
	public ScheduledTask async(final Task task) {
		return TaskUtil.async(task);
	}
	
	public ScheduledTask task(final boolean sync, final Runnable executor) {
		return TaskUtil.task(this.plugin(), sync, executor);
	}
	
	public ScheduledTask task(final boolean sync, final Consumer<ScheduledTask> executor) {
		return TaskUtil.task(this.plugin(), sync, executor);
	}
	
	public ScheduledTask task(final boolean sync, final Task.Builder builder) {
		return TaskUtil.task(this.plugin(), sync, builder);
	}
	
	public ScheduledTask task(final boolean sync, final Task task) {
		return TaskUtil.task(sync, task);
	}
	
	public Optional<ScheduledTask> optionalSync(final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optionalSync(this.plugin(), shouldCreate, executor);
	}
	
	public Optional<ScheduledTask> optionalAsync(final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optionalAsync(this.plugin(), shouldCreate, executor);
	}
	
	public ScheduledTask tickDelayedSync(final Runnable executor) {
		return TaskUtil.tickDelayedSync(this.plugin(), executor);
	}
	
	public void syncTasksFromAsync(final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.syncTasksFromAsync(this.plugin(), sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd);
	}
	
	public void asyncTasksFromAsync(final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.asyncTasksFromAsync(this.plugin(), sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd);
	}
}
