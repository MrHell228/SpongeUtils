package net.hellheim.spongetools.codec;

import java.util.List;
import java.util.function.Function;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.util.weighted.RandomObjectTable;
import org.spongepowered.api.util.weighted.TableEntry;
import org.spongepowered.api.util.weighted.VariableAmount;
import org.spongepowered.api.util.weighted.WeightedTable;

import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.hellheim.spongetools.codec.dispatched.TableEntryCodecs;
import net.hellheim.spongetools.codec.dispatched.VariableAmountCodecs;

/**
 * Codecs for SpongeAPI types
 */
public final class SpongeCodecs {
	
	public static final Codec<ResourceKey> RESOURCE_KEY = SpongeCodecs.resourceKey(ResourceKey.MINECRAFT_NAMESPACE);
	
	public static final Codec<ResourceKey> SPONGE_RESOURCE_KEY = SpongeCodecs.resourceKey(ResourceKey.SPONGE_NAMESPACE);
	
	public static final Codec<VariableAmount> VARIABLE_AMOUNT = VariableAmountCodecs.CODEC;
	
	public static final Codec<Direction> DIRECTION = StringRepresentableCodec.fromValues(Direction::values);
	
	public static final Codec<Direction> CARDINAL_DIRECTION = SpongeCodecs.DIRECTION.validate(dir -> dir.isCardinal()
					? DataResult.success(dir)
					: DataResult.error(() -> "Direction must be cardinal: " + dir)
					);
	
	public static final Codec<Ticks> TICKS = Codec.LONG.xmap(
			i -> i < 0 ? Ticks.infinite() : Ticks.of(i),
			t -> t.isInfinite() ? -1 : t.ticks());
	
	// Plain codec builders
	
	public static <T> Codec<ResourceKey> resourceKey(final String defaultNamespace) {
		return AdventureCodecs.key(defaultNamespace)
				.xmap(key -> ResourceKey.of(key.namespace(), key.value()), Function.identity());
	}
	
	public static <T> Codec<RegistryKey<T>> registryKey(final String defaultNamespace, final RegistryType<T> registry) {
		return SpongeCodecs.resourceKey(defaultNamespace).xmap(key -> RegistryKey.of(registry, key), RegistryKey::location);
	}
	
	public static <T> Codec<RegistryKey<T>> registryKey(final RegistryType<T> registry) {
		return SpongeCodecs.registryKey(ResourceKey.MINECRAFT_NAMESPACE, registry);
	}
	
	public static <T extends ResourceKeyed> Codec<T> keyedResolver(
		final Codec<ResourceKey> keyCodec, final Function<? super ResourceKey, ? extends T> keyToValue
	) {
		return ExtraCodecs.idResolver(keyCodec, keyToValue, ResourceKeyed::key);
	}
	
	public static <T extends ResourceKeyed> Codec<T> keyedResolver(
		final Function<? super ResourceKey, ? extends T> keyToValue
	) {
		return SpongeCodecs.keyedResolver(SpongeCodecs.RESOURCE_KEY, keyToValue);
	}
	
	// Mapped codec builders
	
	public static <T> Codec<RandomObjectTable<T>> randomObjectTable(final Codec<T> elementCodec) {
		return ExtraCodecs.casted(SpongeCodecs.weigthedTable(elementCodec));
	}
	
	public static <T> Codec<WeightedTable<T>> weigthedTable(final Codec<T> elementCodec) {
		final Codec<List<TableEntry<T>>> entries = SpongeCodecs.tableEntry(elementCodec).listOf();
		final Codec<WeightedTable<T>> alternative = entries.xmap(SpongeCodecs::tableOf, WeightedTable::entries);
		
		return Codec.withAlternative(alternative, RecordCodecBuilder.create(
				instance -> SpongeCodecs
						.randomObjectTableBuilder(instance, entries)
						.apply(instance, SpongeCodecs::tableOf)
				));
	}
	
	public static <T> Codec<TableEntry<T>> tableEntry(final Codec<T> elementCodec) {
		return TableEntryCodecs.codec(elementCodec);
	}
	
	// Impl
	
	private static <T, R extends RandomObjectTable<T>> P2<Mu<R>, VariableAmount, List<TableEntry<T>>> randomObjectTableBuilder(
		final Instance<R> instance, final Codec<List<TableEntry<T>>> entriesCodec
	) {
		return instance.group(
				SpongeCodecs.VARIABLE_AMOUNT.optionalFieldOf("rolls", VariableAmount.fixed(1)).forGetter(RandomObjectTable::rolls),
				entriesCodec.optionalFieldOf("entries", List.of()).forGetter(RandomObjectTable::entries)
				);
	}
	
	private static <T> WeightedTable<T> tableOf(final List<TableEntry<T>> entries) {
		return SpongeCodecs.tableOf(VariableAmount.fixed(1), entries);
	}
	
	private static <T> WeightedTable<T> tableOf(final VariableAmount rolls, final List<TableEntry<T>> entries) {
		final WeightedTable<T> table = new WeightedTable<>(rolls);
		table.addAll(entries);
		return table;
	}
	
	private SpongeCodecs() {
	}
}
