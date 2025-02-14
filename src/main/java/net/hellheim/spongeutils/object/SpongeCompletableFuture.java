package net.hellheim.spongeutils.object;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.scheduler.TaskExecutorService;

import net.hellheim.spongeutils.function.QuadFunction;
import net.hellheim.spongeutils.function.TriFunction;
import net.hellheim.spongeutils.manager.FutureTaskManager;

/**
 * {@link CompletableFuture} with additional methods for Sponge-specific
 * {@link TaskExecutorService executor}s: Server, Client & Async.
 * <br><br>
 * New instances should be retrieved through {@link FutureTaskManager}.
 * 
 * @param <T> The result type returned by this future's {@code join}
 * and {@code get} methods
 */
@SuppressWarnings("synthetic-access")
public class SpongeCompletableFuture<T> extends CompletableFuture<T> {
	
	private final FutureTaskManager manager;
	private final @Nullable CompletableFuture<T> wrapped;
	
	private SpongeCompletableFuture(FutureTaskManager manager) {
		this(manager, null);
	}
	
	private SpongeCompletableFuture(FutureTaskManager manager, CompletableFuture<T> wrapped) {
		this.manager = manager;
		this.wrapped = wrapped;
	}
	
	
	
	public static <U> SpongeCompletableFuture<U> newFuture(
			FutureTaskManager manager) {
		return new SpongeCompletableFuture<>(
				Objects.requireNonNull(manager, "manager")
				);
	}
	
	public static <U> SpongeCompletableFuture<U> wrapFuture(
			FutureTaskManager manager, CompletableFuture<U> wrapped) {
		return new SpongeCompletableFuture<>(
				Objects.requireNonNull(manager, "manager"),
				Objects.requireNonNull(wrapped, "wrapped")
				);
	}
	
	public static SpongeCompletableFuture<Void> allOf(
			FutureTaskManager manager, CompletableFuture<?>... cfs) {
		return wrapFuture(manager, CompletableFuture.allOf(cfs));
	}
	
	public static SpongeCompletableFuture<Object> anyOf(
			FutureTaskManager manager, CompletableFuture<?>... cfs) {
		return wrapFuture(manager, CompletableFuture.anyOf(cfs));
	}
	
	public static <U> SpongeCompletableFuture<U> completedFuture(
			FutureTaskManager manager, U value) {
		return wrapFuture(manager, CompletableFuture.completedFuture(value));
	}
	
	public static <U> SpongeCompletableFuture<U> failedFuture(
			FutureTaskManager manager, Throwable ex) {
		return wrapFuture(manager, CompletableFuture.failedFuture(ex));
	}
	
	public static SpongeCompletableFuture<Void> runAsync(
			FutureTaskManager manager, Runnable runnable) {
		return runAsync(manager, runnable, manager.asyncExecutor());
	}
	
	public static SpongeCompletableFuture<Void> runServer(
			FutureTaskManager manager, Runnable runnable) {
		return runAsync(manager, runnable, manager.serverExecutor());
	}
	
	public static SpongeCompletableFuture<Void> runClient(
			FutureTaskManager manager, Runnable runnable) {
		return runAsync(manager, runnable, manager.clientExecutor());
	}
	
	public static SpongeCompletableFuture<Void> runAsync(
			FutureTaskManager manager, Runnable runnable, Executor executor) {
		return wrapFuture(manager, CompletableFuture.runAsync(runnable, executor));
	}
	
	public static <U> SpongeCompletableFuture<U> supplyAsync(
			FutureTaskManager manager, Supplier<U> supplier) {
		return supplyAsync(manager, supplier, manager.asyncExecutor());
	}
	
	public static <U> SpongeCompletableFuture<U> supplyServer(
			FutureTaskManager manager, Supplier<U> supplier) {
		return supplyAsync(manager, supplier, manager.serverExecutor());
	}
	
	public static <U> SpongeCompletableFuture<U> supplyClient(
			FutureTaskManager manager, Supplier<U> supplier) {
		return supplyAsync(manager, supplier, manager.clientExecutor());
	}
	
	public static <U> SpongeCompletableFuture<U> supplyAsync(
			FutureTaskManager manager, Supplier<U> supplier, Executor executor) {
		return wrapFuture(manager, CompletableFuture.supplyAsync(supplier, executor));
	}
	
	
	
	private <U> SpongeCompletableFuture<U> transform(
		Supplier<CompletableFuture<U>> superCaller,
		Function<CompletableFuture<T>, CompletableFuture<U>> wrapperCaller
	) {
		if (this.wrapped == null) {
			return (SpongeCompletableFuture<U>) superCaller.get();
		} else {
			CompletableFuture<U> newWrapped = wrapperCaller.apply(this.wrapped);
			if (newWrapped == this.wrapped) {
				@SuppressWarnings("unchecked")
				SpongeCompletableFuture<U> newFuture = (SpongeCompletableFuture<U>) this;
				return newFuture;
			} else {
				return new SpongeCompletableFuture<>(this.manager, newWrapped);
			}
		}
	}
	
	private <A, U> SpongeCompletableFuture<U> transform(
		Function<A, CompletableFuture<U>> superCaller,
		BiFunction<CompletableFuture<T>, A, CompletableFuture<U>> wrapperCaller,
		A arg
	) {
		return this.transform(() -> superCaller.apply(arg), f -> wrapperCaller.apply(f, arg));
	}
	
	private <A, B, U> SpongeCompletableFuture<U> transform(
		BiFunction<A, B, CompletableFuture<U>> superCaller,
		TriFunction<CompletableFuture<T>, A, B, CompletableFuture<U>> wrapperCaller,
		A arg1, B arg2
	) {
		return this.transform(() -> superCaller.apply(arg1, arg2), f -> wrapperCaller.apply(f, arg1, arg2));
	}
	
	private <A, B, C, U> SpongeCompletableFuture<U> transform(
		TriFunction<A, B, C, CompletableFuture<U>> superCaller,
		QuadFunction<CompletableFuture<T>, A, B, C, CompletableFuture<U>> wrapperCaller,
		A arg1, B arg2, C arg3
	) {
		return this.transform(() -> superCaller.apply(arg1, arg2, arg3), f -> wrapperCaller.apply(f, arg1, arg2, arg3));
	}
	
	
	
	public FutureTaskManager manager() {
		return this.manager;
	}
	
	@Override
	public Executor defaultExecutor() {
		return this.manager.asyncExecutor();
	}
	
	public Executor serverExecutor() {
		return this.manager.serverExecutor();
	}
	
	public Executor clientExecutor() {
		return this.manager.clientExecutor();
	}
	
	@Override
	public SpongeCompletableFuture<T> toCompletableFuture() {
		return this;
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> newIncompleteFuture() {
		return new SpongeCompletableFuture<>(this.manager);
	}
	
	@Override
	public SpongeCompletableFuture<T> copy() {
		return this.transform(() -> super.copy(), f -> f.copy());
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenApply(
			Function<? super T, ? extends U> fn) {
		return this.transform(() -> super.thenApply(fn), f -> f.thenApply(fn));
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenApplyAsync(
			Function<? super T, ? extends U> fn) {
		return this.thenApplyAsync(fn, this.defaultExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> thenApplyServer(
			Function<? super T, ? extends U> fn) {
		return this.thenApplyAsync(fn, this.serverExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> thenApplyClient(
			Function<? super T, ? extends U> fn) {
		return this.thenApplyAsync(fn, this.clientExecutor());
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenApplyAsync(
			Function<? super T, ? extends U> fn, Executor executor) {
		return this.transform(super::thenApplyAsync, CompletableFuture::thenApplyAsync, fn, executor);
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenAccept(
			Consumer<? super T> action) {
		return this.transform(super::thenAccept, CompletableFuture::thenAccept, action);
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenAcceptAsync(
			Consumer<? super T> action) {
		return this.thenAcceptAsync(action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<Void> thenAcceptServer(
			Consumer<? super T> action) {
		return this.thenAcceptAsync(action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<Void> thenAcceptClient(
			Consumer<? super T> action) {
		return this.thenAcceptAsync(action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenAcceptAsync(
			Consumer<? super T> action, Executor executor) {
		return this.transform(super::thenAcceptAsync, CompletableFuture::thenAcceptAsync, action, executor);
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenRun(
			Runnable action) {
		return this.transform(super::thenRun, CompletableFuture::thenRun, action);
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenRunAsync(
			Runnable action) {
		return this.thenRunAsync(action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<Void> thenRunServer(
			Runnable action) {
		return this.thenRunAsync(action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<Void> thenRunClient(
			Runnable action) {
		return this.thenRunAsync(action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<Void> thenRunAsync(
			Runnable action, Executor executor) {
		return this.transform(super::thenRunAsync, CompletableFuture::thenRunAsync, action, executor);
	}
	
	@Override
	public <U, V> SpongeCompletableFuture<V> thenCombine(
			CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
		return this.transform(super::thenCombine, CompletableFuture::thenCombine, other, fn);
	}
	
	@Override
	public <U, V> SpongeCompletableFuture<V> thenCombineAsync(
			CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
		return this.thenCombineAsync(other, fn, this.defaultExecutor());
	}
	
	public <U, V> SpongeCompletableFuture<V> thenCombineServer(
			CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
		return this.thenCombineAsync(other, fn, this.serverExecutor());
	}
	
	public <U, V> SpongeCompletableFuture<V> thenCombineClient(
			CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
		return this.thenCombineAsync(other, fn, this.clientExecutor());
	}
	
	@Override
	public <U, V> SpongeCompletableFuture<V> thenCombineAsync(
			CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
		return this.transform(super::thenCombineAsync, CompletableFuture::thenCombineAsync, other, fn, executor);
	}
	
	@Override
	public <U> SpongeCompletableFuture<Void> thenAcceptBoth(
			CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
		return this.transform(super::thenAcceptBoth, CompletableFuture::thenAcceptBoth, other, action);
	}
	
	@Override
	public <U> SpongeCompletableFuture<Void> thenAcceptBothAsync(
			CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
		return this.thenAcceptBothAsync(other, action, this.defaultExecutor());
	}
	
	public <U> SpongeCompletableFuture<Void> thenAcceptBothServer(
			CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
		return this.thenAcceptBothAsync(other, action, this.serverExecutor());
	}
	
	public <U> SpongeCompletableFuture<Void> thenAcceptBothClient(
			CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
		return this.thenAcceptBothAsync(other, action, this.clientExecutor());
	}
	
	@Override
	public <U> SpongeCompletableFuture<Void> thenAcceptBothAsync(
			CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
		return this.transform(super::thenAcceptBothAsync, CompletableFuture::thenAcceptBothAsync, other, action, executor);
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterBoth(
			CompletionStage<?> other, Runnable action) {
		return this.transform(super::runAfterBoth, CompletableFuture::runAfterBoth, other, action);
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterBothAsync(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterBothAsync(other, action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<Void> runAfterBothServer(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterBothAsync(other, action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<Void> runAfterBothClient(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterBothAsync(other, action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterBothAsync(
			CompletionStage<?> other, Runnable action, Executor executor) {
		return this.transform(super::runAfterBothAsync, CompletableFuture::runAfterBothAsync, other, action, executor);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> applyToEither(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return this.transform(super::applyToEither, CompletableFuture::applyToEither, other, fn);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> applyToEitherAsync(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return this.applyToEitherAsync(other, fn, this.defaultExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> applyToEitherServer(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return this.applyToEitherAsync(other, fn, this.serverExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> applyToEitherClient(
			CompletionStage<? extends T> other, Function<? super T, U> fn) {
		return this.applyToEitherAsync(other, fn, this.clientExecutor());
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> applyToEitherAsync(
			CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
		return this.transform(super::applyToEitherAsync, CompletableFuture::applyToEitherAsync, other, fn, executor);
	}
	
	@Override
	public SpongeCompletableFuture<Void> acceptEither(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		return this.transform(super::acceptEither, CompletableFuture::acceptEither, other, action);
	}
	
	@Override
	public SpongeCompletableFuture<Void> acceptEitherAsync(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		return this.acceptEitherAsync(other, action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<Void> acceptEitherServer(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		return this.acceptEitherAsync(other, action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<Void> acceptEitherClient(
			CompletionStage<? extends T> other, Consumer<? super T> action) {
		return this.acceptEitherAsync(other, action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<Void> acceptEitherAsync(
			CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
		return this.transform(super::acceptEitherAsync, CompletableFuture::acceptEitherAsync, other, action, executor);
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterEither(
			CompletionStage<?> other, Runnable action) {
		return this.transform(super::runAfterEither, CompletableFuture::runAfterEither, other, action);
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterEitherAsync(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterEitherAsync(other, action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<Void> runAfterEitherServer(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterEitherAsync(other, action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<Void> runAfterEitherClient(
			CompletionStage<?> other, Runnable action) {
		return this.runAfterEitherAsync(other, action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<Void> runAfterEitherAsync(
			CompletionStage<?> other, Runnable action, Executor executor) {
		return this.transform(super::runAfterEitherAsync, CompletableFuture::runAfterEitherAsync, other, action, executor);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenCompose(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		return this.transform(super::thenCompose, CompletableFuture::thenCompose, fn);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenComposeAsync(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		return this.thenComposeAsync(fn, this.defaultExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> thenComposeServer(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		return this.thenComposeAsync(fn, this.serverExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> thenComposeClient(
			Function<? super T, ? extends CompletionStage<U>> fn) {
		return this.thenComposeAsync(fn, this.clientExecutor());
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> thenComposeAsync(
			Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
		return this.transform(super::thenComposeAsync, CompletableFuture::thenComposeAsync, fn, executor);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> handle(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		return this.transform(super::handle, CompletableFuture::handle, fn);
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> handleAsync(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		return this.handleAsync(fn, this.defaultExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> handleServer(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		return this.handleAsync(fn, this.serverExecutor());
	}
	
	public <U> SpongeCompletableFuture<U> handleClient(
			BiFunction<? super T, Throwable, ? extends U> fn) {
		return this.handleAsync(fn, this.clientExecutor());
	}
	
	@Override
	public <U> SpongeCompletableFuture<U> handleAsync(
			BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
		return this.transform(super::handleAsync, CompletableFuture::handleAsync, fn, executor);
	}
	
	@Override
	public SpongeCompletableFuture<T> whenComplete(
			BiConsumer<? super T, ? super Throwable> action) {
		return this.transform(super::whenComplete, CompletableFuture::whenComplete, action);
	}
	
	@Override
	public SpongeCompletableFuture<T> whenCompleteAsync(
			BiConsumer<? super T, ? super Throwable> action) {
		return this.whenCompleteAsync(action, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<T> whenCompleteServer(
			BiConsumer<? super T, ? super Throwable> action) {
		return this.whenCompleteAsync(action, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<T> whenCompleteClient(
			BiConsumer<? super T, ? super Throwable> action) {
		return this.whenCompleteAsync(action, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<T> whenCompleteAsync(
			BiConsumer<? super T, ? super Throwable> action, Executor executor) {
		return this.transform(super::whenCompleteAsync, CompletableFuture::whenCompleteAsync, action, executor);
	}
	
	@Override
	public SpongeCompletableFuture<T> completeAsync(
			Supplier<? extends T> supplier) {
		return this.completeAsync(supplier, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<T> completeServer(
			Supplier<? extends T> supplier) {
		return this.completeAsync(supplier, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<T> completeClient(
			Supplier<? extends T> supplier) {
		return this.completeAsync(supplier, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<T> completeAsync(
			Supplier<? extends T> supplier, Executor executor) {
		return this.transform(super::completeAsync, CompletableFuture::completeAsync, supplier, executor);
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionally(
			Function<Throwable, ? extends T> fn) {
		return this.transform(super::exceptionally, CompletableFuture::exceptionally, fn);
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionallyAsync(
			Function<Throwable, ? extends T> fn) {
		return this.exceptionallyAsync(fn, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<T> exceptionallyServer(
			Function<Throwable, ? extends T> fn) {
		return this.exceptionallyAsync(fn, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<T> exceptionallyClient(
			Function<Throwable, ? extends T> fn) {
		return this.exceptionallyAsync(fn, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionallyAsync(
			Function<Throwable, ? extends T> fn, Executor executor) {
		return this.transform(super::exceptionallyAsync, CompletableFuture::exceptionallyAsync, fn, executor);
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionallyCompose(
			Function<Throwable, ? extends CompletionStage<T>> fn) {
		return this.transform(super::exceptionallyCompose, CompletableFuture::exceptionallyCompose, fn);
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionallyComposeAsync(
			Function<Throwable, ? extends CompletionStage<T>> fn) {
		return this.exceptionallyComposeAsync(fn, this.defaultExecutor());
	}
	
	public SpongeCompletableFuture<T> exceptionallyComposeServer(
			Function<Throwable, ? extends CompletionStage<T>> fn) {
		return this.exceptionallyComposeAsync(fn, this.serverExecutor());
	}
	
	public SpongeCompletableFuture<T> exceptionallyComposeClient(
			Function<Throwable, ? extends CompletionStage<T>> fn) {
		return this.exceptionallyComposeAsync(fn, this.clientExecutor());
	}
	
	@Override
	public SpongeCompletableFuture<T> exceptionallyComposeAsync(
			Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor) {
		return this.transform(super::exceptionallyComposeAsync, CompletableFuture::exceptionallyComposeAsync, fn, executor);
	}
	
	@Override
	public SpongeCompletableFuture<T> orTimeout(long timeout, TimeUnit unit) {
		return this.transform(super::orTimeout, CompletableFuture::orTimeout, timeout, unit);
	}
	
	@Override
	public SpongeCompletableFuture<T> completeOnTimeout(T value, long timeout, TimeUnit unit) {
		return this.transform(super::completeOnTimeout, CompletableFuture::completeOnTimeout, value, timeout, unit);
	}
	
	// Non future return types
	
	private <A> void run(
		Consumer<A> superCaller,
		BiConsumer<CompletableFuture<T>, A> wrapperCaller,
		A arg
	) {
		if (this.wrapped == null) {
			superCaller.accept(arg);
		} else {
			wrapperCaller.accept(this.wrapped, arg);
		}
	}
	
	private <R> R calculate(
		Supplier<R> superCaller,
		Function<CompletableFuture<T>, R> wrapperCaller
	) {
		return this.wrapped == null
				? superCaller.get()
				: wrapperCaller.apply(this.wrapped);
	}
	
	private <A, R> R calculate(
		Function<A, R> superCaller,
		BiFunction<CompletableFuture<T>, A, R> wrapperCaller,
		A arg
	) {
		return this.wrapped == null
				? superCaller.apply(arg)
				: wrapperCaller.apply(this.wrapped, arg);
	}
	
	@Override
	public boolean isDone() {
		return this.calculate(super::isDone, CompletableFuture::isDone);
	}
	
	@Override
	public T get() throws InterruptedException, ExecutionException {
		return this.wrapped == null
				? super.get()
				: this.wrapped.get();
	}
	
	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.wrapped == null
				? super.get(timeout, unit)
				: this.wrapped.get(timeout, unit);
	}
	
	@Override
	public T join() {
		return this.calculate(super::join, CompletableFuture::join);
	}
	
	@Override
	public T getNow(T valueIfAbsent) {
		return this.calculate(super::getNow, CompletableFuture::getNow, valueIfAbsent);
	}
	
	@Override
	public T resultNow() {
		return this.calculate(super::resultNow, CompletableFuture::resultNow);
	}
	
	@Override
	public Throwable exceptionNow() {
		return this.calculate(super::exceptionNow, CompletableFuture::exceptionNow);
	}
	
	@Override
	public boolean complete(T value) {
		return this.calculate(super::complete, CompletableFuture::complete, value);
	}
	
	@Override
	public boolean completeExceptionally(Throwable ex) {
		return this.calculate(super::completeExceptionally, CompletableFuture::completeExceptionally, ex);
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return this.calculate(super::cancel, CompletableFuture::cancel, mayInterruptIfRunning);
	}
	
	@Override
	public boolean isCancelled() {
		return this.calculate(super::isCancelled, CompletableFuture::isCancelled);
	}
	
	@Override
	public boolean isCompletedExceptionally() {
		return this.calculate(super::isCompletedExceptionally, CompletableFuture::isCompletedExceptionally);
	}
	
	@Override
	public State state() {
		return this.calculate(super::state, CompletableFuture::state);
	}
	
	@Override
	public void obtrudeValue(T value) {
		this.run(super::obtrudeValue, CompletableFuture::obtrudeValue, value);
	}
	
	@Override
	public void obtrudeException(Throwable ex) {
		this.run(super::obtrudeException, CompletableFuture::obtrudeException, ex);
	}
	
	@Override
	public int getNumberOfDependents() {
		return this.calculate(super::getNumberOfDependents, CompletableFuture::getNumberOfDependents);
	}
	
	@Override
	public String toString() {
		return this.calculate(super::toString, CompletableFuture::toString);
	}
	
	@Override
	public CompletionStage<T> minimalCompletionStage() {
		return this.calculate(super::minimalCompletionStage, CompletableFuture::minimalCompletionStage);
	}
}
