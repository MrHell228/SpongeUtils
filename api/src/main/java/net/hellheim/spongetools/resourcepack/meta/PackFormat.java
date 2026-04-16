package net.hellheim.spongetools.resourcepack.meta;

import java.util.List;
import java.util.Locale;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.resource.pack.PackType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.resourcepack.util.InclusiveRange;

public record PackFormat(int major, int minor) implements Comparable<PackFormat> {
	
	private static final int MAX_MINOR = Integer.MAX_VALUE;
	public static final Codec<PackFormat> BOTTOM_CODEC = PackFormat.fullCodec(0);
	public static final Codec<PackFormat> TOP_CODEC = PackFormat.fullCodec(Integer.MAX_VALUE);
	
	public static PackFormat of(final int major, final int minor) {
		return new PackFormat(major, minor);
	}
	
	public static PackFormat of(final int major) {
		return new PackFormat(major, 0);
	}
	
	public static int lastPreMinorVersion(final PackType type) {
		return Sponge.game().factoryProvider().provide(Factory.class).lastPreMinorVersion(type);
	}
	
	private static Codec<PackFormat> fullCodec(final int defaultMinor) {
		return ExtraCodecs.compactList(ExtraCodecs.NON_NEGATIVE_INT, ExtraCodecs.NON_NEGATIVE_INT.listOf(1, 256)).xmap(
				list -> list.size() > 1
						? of(list.getFirst(), list.get(1))
						: of(list.getFirst(), defaultMinor),
				pf -> pf.minor != defaultMinor
					? List.of(pf.major(), pf.minor())
					: List.of(pf.major()));
	}
	
	protected static MapCodec<InclusiveRange<PackFormat>> packCodec(final PackType type) {
		final int lastPreMinorVersion = PackFormat.lastPreMinorVersion(type);
		return PackIntermediaryFormat.PACK_CODEC.flatXmap(
				intermediaryFormat -> intermediaryFormat.validate(lastPreMinorVersion, true, false, "Pack", "supported_formats"),
				range -> DataResult.success(PackIntermediaryFormat.fromRange(range, lastPreMinorVersion)));
	}
	
	public PackFormat minorRange() {
		return new PackFormat(this.major, PackFormat.MAX_MINOR);
	}
	
	@Override
	public int compareTo(final PackFormat other) {
		final int majorDiff = Integer.compare(this.major(), other.major());
		return majorDiff != 0
				? majorDiff
				: Integer.compare(this.minor(), other.minor());
	}
	
	@Override
	public String toString() {
		return this.minor == PackFormat.MAX_MINOR
				? String.format(Locale.ROOT, "%d.*", this.major())
				: String.format(Locale.ROOT, "%d.%d", this.major(), this.minor());
	}
	
	public static interface Factory {
		
		default int lastPreMinorVersion(final PackType type) {
			if (type == PackType.client()) {
				// TODO
				return 64;
			} else if (type == PackType.server()) {
				return 81;
			} else {
				throw new IllegalArgumentException("Unknown PackType: " + type);
			}
		}
	}
}
