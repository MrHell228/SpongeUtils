package net.hellheim.spongeutils.manager;

import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.scheduler.ScheduledTaskFuture;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.TaskExecutorService;
import org.spongepowered.api.scheduler.TaskFuture;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.TaskUtil;
import net.hellheim.spongeutils.object.SpongeCompletableFuture;

/**
 * Manager for utilizing {@link SpongeCompletableFuture}
 * and scheduling {@link TaskFuture}s for Sponge-specific
 * {@link TaskExecutorService executor}s: Server, Client & Async.
 */
public class FutureTaskManager extends Manager implements AutoCloseable {
	
	private @MonotonicNonNull TaskExecutorService async;
	private @MonotonicNonNull TaskExecutorService server;
	private @MonotonicNonNull TaskExecutorService client;
	
	public FutureTaskManager(final PluginContainer plugin) {
		super(plugin);
	}
	
	private TaskExecutorService executor(Scheduler scheduler) {
		return scheduler.executor(this.plugin);
	}
	
	public TaskExecutorService asyncExecutor() {
		if (this.async == null) {
			this.async = this.executor(TaskUtil.ASYNC);
		}
		
		return this.async;
	}
	
	public TaskExecutorService serverExecutor() {
		if (this.server == null) {
			this.server = this.executor(TaskUtil.SERVER);
		}
		
		return this.server;
	}
	
	public TaskExecutorService clientExecutor() {
		if (this.client == null) {
			this.client = this.executor(TaskUtil.CLIENT);
		}
		
		return this.client;
	}
	
	@Override
	public void close() {
		if (this.async != null) {
			this.async.close();
		}
		
		if (this.server != null) {
			this.server.close();
		}
		
		if (this.client != null) {
			this.client.close();
		}
	}
	
	// CompletableFuture
	
	public <U> SpongeCompletableFuture<U> newFuture() {
		return SpongeCompletableFuture.newFuture(this);
	}
	
	public <U> SpongeCompletableFuture<U> wrapFuture(CompletableFuture<U> wrapped) {
		return SpongeCompletableFuture.wrapFuture(this, wrapped);
	}
	
	public SpongeCompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
		return SpongeCompletableFuture.allOf(this, cfs);
	}
	
	public SpongeCompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) {
		return SpongeCompletableFuture.anyOf(this, cfs);
	}
	
	public <U> SpongeCompletableFuture<U> completedFuture(U value) {
		return SpongeCompletableFuture.completedFuture(this, value);
	}
	
	public <U> SpongeCompletableFuture<U> failedFuture(Throwable ex) {
		return SpongeCompletableFuture.failedFuture(this, ex);
	}
	
	public SpongeCompletableFuture<Void> runAsync(Runnable runnable) {
		return SpongeCompletableFuture.runAsync(this, runnable);
	}
	
	public SpongeCompletableFuture<Void> runServer(Runnable runnable) {
		return SpongeCompletableFuture.runServer(this, runnable);
	}
	
	public SpongeCompletableFuture<Void> runClient(Runnable runnable) {
		return SpongeCompletableFuture.runClient(this, runnable);
	}
	
	public SpongeCompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
		return SpongeCompletableFuture.runAsync(this, runnable, executor);
	}
	
	public <U> SpongeCompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return SpongeCompletableFuture.supplyAsync(this, supplier);
	}
	
	public <U> SpongeCompletableFuture<U> supplyServer(Supplier<U> supplier) {
		return SpongeCompletableFuture.supplyServer(this, supplier);
	}
	
	public <U> SpongeCompletableFuture<U> supplyClient(Supplier<U> supplier) {
		return SpongeCompletableFuture.supplyClient(this, supplier);
	}
	
	public <U> SpongeCompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
		return SpongeCompletableFuture.supplyAsync(this, supplier, executor);
	}
	
	// TaskExecutorService
	
	public <T> TaskFuture<T> submitAsync(Callable<T> task) {
		return this.asyncExecutor().submit(task);
	}
	
	public <T> TaskFuture<T> submitServer(Callable<T> task) {
		return this.serverExecutor().submit(task);
	}
	
	public <T> TaskFuture<T> submitClient(Callable<T> task) {
		return this.clientExecutor().submit(task);
	}
	
	public TaskFuture<?> submitAsync(Runnable task) {
		return this.asyncExecutor().submit(task);
	}
	
	public TaskFuture<?> submitServer(Runnable task) {
		return this.serverExecutor().submit(task);
	}
	
	public TaskFuture<?> submitClient(Runnable task) {
		return this.clientExecutor().submit(task);
	}
	
	public ScheduledTaskFuture<?> scheduleAsync(Runnable command, long delay, TemporalUnit unit) {
		return this.asyncExecutor().schedule(command, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServer(Runnable command, long delay, TemporalUnit unit) {
		return this.serverExecutor().schedule(command, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClient(Runnable command, long delay, TemporalUnit unit) {
		return this.clientExecutor().schedule(command, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleAsync(Runnable command, long delay, TimeUnit unit) {
		return this.asyncExecutor().schedule(command, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServer(Runnable command, long delay, TimeUnit unit) {
		return this.serverExecutor().schedule(command, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClient(Runnable command, long delay, TimeUnit unit) {
		return this.clientExecutor().schedule(command, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleAsync(Callable<V> callable, long delay, TemporalUnit unit) {
		return this.asyncExecutor().schedule(callable, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleServer(Callable<V> callable, long delay, TemporalUnit unit) {
		return this.serverExecutor().schedule(callable, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleClient(Callable<V> callable, long delay, TemporalUnit unit) {
		return this.clientExecutor().schedule(callable, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleAsync(Callable<V> callable, long delay, TimeUnit unit) {
		return this.asyncExecutor().schedule(callable, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleServer(Callable<V> callable, long delay, TimeUnit unit) {
		return this.serverExecutor().schedule(callable, delay, unit);
	}
	
	public <V> ScheduledTaskFuture<V> scheduleClient(Callable<V> callable, long delay, TimeUnit unit) {
		return this.clientExecutor().schedule(callable, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleAsyncAtFixedRate(Runnable command, long initialDelay, long period, TemporalUnit unit) {
		return this.asyncExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServerAtFixedRate(Runnable command, long initialDelay, long period, TemporalUnit unit) {
		return this.serverExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClientAtFixedRate(Runnable command, long initialDelay, long period, TemporalUnit unit) {
		return this.clientExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleAsyncAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return this.asyncExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServerAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return this.serverExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClientAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return this.clientExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleAsyncWithFixedDelay(Runnable command, long initialDelay, long delay, TemporalUnit unit) {
		return this.asyncExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServerWithFixedDelay(Runnable command, long initialDelay, long delay, TemporalUnit unit) {
		return this.serverExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClientWithFixedDelay(Runnable command, long initialDelay, long delay, TemporalUnit unit) {
		return this.clientExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleAsyncWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return this.asyncExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleServerWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return this.serverExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public ScheduledTaskFuture<?> scheduleClientWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return this.clientExecutor().scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
}
