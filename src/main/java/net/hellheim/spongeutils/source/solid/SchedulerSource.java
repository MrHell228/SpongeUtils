package net.hellheim.spongeutils.source.solid;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scheduler.TaskExecutorService;
import org.spongepowered.plugin.PluginContainer;

@FunctionalInterface
public interface SchedulerSource extends Scheduler {
	
	Scheduler scheduler();
	
	@Override
	default Optional<ScheduledTask> findTask(final UUID id) {
		return this.scheduler().findTask(id);
	}
	
	@Override
	default Set<ScheduledTask> findTasks(final String pattern) {
		return this.scheduler().findTasks(pattern);
	}
	
	@Override
	default Set<ScheduledTask> tasks(final PluginContainer plugin) {
		return this.scheduler().tasks(plugin);
	}
	
	@Override
	default Set<ScheduledTask> tasks() {
		return this.scheduler().tasks();
	}
	
	@Override
	default TaskExecutorService executor(final PluginContainer plugin) {
		return this.scheduler().executor(plugin);
	}
	
	@Override
	default ScheduledTask submit(final Task task) {
		return this.scheduler().submit(task);
	}
	
	@Override
	default ScheduledTask submit(final Task task, final String name) {
		return this.scheduler().submit(task, name);
	}
}
