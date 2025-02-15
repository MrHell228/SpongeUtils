package net.hellheim.spongeutils.manager;

import java.util.Optional;
import java.util.function.Consumer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.TaskUtil;

public class TaskManager extends Manager {
	
	public TaskManager(final PluginContainer plugin) {
		super(plugin);
	}
	
	public ScheduledTask server(final Runnable executor) {
		return TaskUtil.server(this.plugin(), executor);
	}
	
	public ScheduledTask server(final Consumer<ScheduledTask> executor) {
		return TaskUtil.server(this.plugin(), executor);
	}
	
	public ScheduledTask server(final Task.Builder builder) {
		return TaskUtil.server(this.plugin(), builder);
	}
	
	public ScheduledTask server(final Task task) {
		return TaskUtil.server(task);
	}
	
	public ScheduledTask client(final Runnable executor) {
		return TaskUtil.client(this.plugin(), executor);
	}
	
	public ScheduledTask client(final Consumer<ScheduledTask> executor) {
		return TaskUtil.client(this.plugin(), executor);
	}
	
	public ScheduledTask client(final Task.Builder builder) {
		return TaskUtil.client(this.plugin(), builder);
	}
	
	public ScheduledTask client(final Task task) {
		return TaskUtil.client(task);
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
	
	public ScheduledTask task(final Scheduler scheduler, final Runnable executor) {
		return TaskUtil.task(scheduler, this.plugin(), executor);
	}
	
	public ScheduledTask task(final Scheduler scheduler, final Consumer<ScheduledTask> executor) {
		return TaskUtil.task(scheduler, this.plugin(), executor);
	}
	
	public ScheduledTask task(final Scheduler scheduler, final Task.Builder builder) {
		return TaskUtil.task(scheduler, this.plugin(), builder);
	}
	
	public ScheduledTask task(final Scheduler scheduler, final Task task) {
		return TaskUtil.task(scheduler, task);
	}
	
	public ScheduledTask serverOrAsync(final boolean server, final Runnable executor) {
		return TaskUtil.serverOrAsync(this.plugin(), server, executor);
	}
	
	public ScheduledTask serverOrAsync(final boolean server, final Consumer<ScheduledTask> executor) {
		return TaskUtil.serverOrAsync(this.plugin(), server, executor);
	}
	
	public ScheduledTask serverOrAsync(final boolean server, final Task.Builder builder) {
		return TaskUtil.serverOrAsync(this.plugin(), server, builder);
	}
	
	public ScheduledTask serverOrAsync(final boolean server, final Task task) {
		return TaskUtil.serverOrAsync(server, task);
	}
	
	public ScheduledTask clientOrAsync(final boolean client, final Runnable executor) {
		return TaskUtil.clientOrAsync(this.plugin(), client, executor);
	}
	
	public ScheduledTask clientOrAsync(final boolean client, final Consumer<ScheduledTask> executor) {
		return TaskUtil.clientOrAsync(this.plugin(), client, executor);
	}
	
	public ScheduledTask clientOrAsync(final boolean client, final Task.Builder builder) {
		return TaskUtil.clientOrAsync(this.plugin(), client, builder);
	}
	
	public ScheduledTask clientOrAsync(final boolean client, final Task task) {
		return TaskUtil.clientOrAsync(client, task);
	}
	
	public Optional<ScheduledTask> optionalServer(final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optionalServer(this.plugin(), shouldCreate, executor);
	}
	
	public Optional<ScheduledTask> optionalClient(final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optionalClient(this.plugin(), shouldCreate, executor);
	}
	
	public Optional<ScheduledTask> optionalAsync(final boolean shouldCreate, final Runnable executor) {
		return TaskUtil.optionalAsync(this.plugin(), shouldCreate, executor);
	}
	
	public Optional<ScheduledTask> optional(final @Nullable Scheduler scheduler, final Runnable executor) {
		return TaskUtil.optional(this.plugin(), scheduler, executor);
	}
	
	public Optional<ScheduledTask> ensureServer(final Runnable executor) {
		return TaskUtil.ensureServer(this.plugin(), executor);
	}
	
	public Optional<ScheduledTask> ensureClient(final Runnable executor) {
		return TaskUtil.ensureClient(this.plugin(), executor);
	}
	
	public Optional<ScheduledTask> ensureAsync(final Runnable executor) {
		return TaskUtil.ensureAsync(this.plugin(), executor);
	}
	
	public Optional<ScheduledTask> ensureServerOrAsync(final boolean server, final Runnable executor) {
		return TaskUtil.ensureServerOrAsync(this.plugin(), server, executor);
	}
	
	public Optional<ScheduledTask> ensureClientOrAsync(final boolean client, final Runnable executor) {
		return TaskUtil.ensureClientOrAsync(this.plugin(), client, executor);
	}
	
	public ScheduledTask tickDelayedServer(final Runnable executor) {
		return TaskUtil.tickDelayedServer(this.plugin(), executor);
	}
	
	public void serverTasksFromAsync(final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.serverTasksFromAsync(this.plugin(), sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd);
	}
	
	public void asyncTasksFromAsync(final long sleepingTime, final int tasks, final int tasksPerThread,
			final Consumer<Integer> taskCons, final Runnable onStart, final Runnable onEnd) {
		TaskUtil.asyncTasksFromAsync(this.plugin(), sleepingTime, tasks, tasksPerThread, taskCons, onStart, onEnd);
	}
}
