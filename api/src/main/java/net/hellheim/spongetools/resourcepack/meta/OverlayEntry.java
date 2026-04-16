package net.hellheim.spongetools.resourcepack.meta;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.spongepowered.api.resource.pack.PackType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.resourcepack.util.InclusiveRange;

public record OverlayEntry(String overlay, InclusiveRange<PackFormat> format) {
	
	public OverlayEntry(final String overlay, final InclusiveRange<PackFormat> format) {
		this.overlay = Objects.requireNonNull(overlay, "overlay");
		this.format = Objects.requireNonNull(format, "format");
	}
	
	public static OverlayEntry of(final String overlay, final PackFormat format) {
		return OverlayEntry.of(overlay, InclusiveRange.of(format));
	}
	
	public static OverlayEntry of(final String overlay, final PackFormat minFormat, PackFormat maxFormat) {
		return OverlayEntry.of(overlay, InclusiveRange.of(minFormat, maxFormat));
	}
	
	public static OverlayEntry of(final String overlay, final InclusiveRange<PackFormat> format) {
		return new OverlayEntry(overlay, format);
	}
	
	protected static Codec<List<OverlayEntry>> codec(final PackType packType) {
		final int lastPreMinorVersion = PackFormat.lastPreMinorVersion(packType);
		return IntermediateEntry.CODEC.listOf().flatXmap(
				(list) -> PackIntermediaryFormat.validateHolderList(list, lastPreMinorVersion,
						(entry, formats) -> new OverlayEntry(entry.overlay(), formats)),
				(list) -> DataResult.success(list.stream().map(
						(entry) -> new IntermediateEntry(PackIntermediaryFormat.fromRange(entry.format(), lastPreMinorVersion), entry.overlay())).toList()));
	}
	
	public boolean isApplicable(final PackFormat formatToTest) {
		return this.format.isValueInRange(formatToTest);
	}
	
	private static record IntermediateEntry(PackIntermediaryFormat format, String overlay)
			implements PackIntermediaryFormat.Holder {
		
		private static final Pattern DIR_VALIDATOR = Pattern.compile("[-_a-zA-Z0-9.]+");
		
		private static final Codec<IntermediateEntry> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						PackIntermediaryFormat.OVERLAY_CODEC.forGetter(IntermediateEntry::format),
						Codec.STRING.validate(IntermediateEntry::validate).fieldOf("directory").forGetter(IntermediateEntry::overlay))
				.apply(instance, IntermediateEntry::new));
		
		private static DataResult<String> validate(final String path) {
			return IntermediateEntry.DIR_VALIDATOR.matcher(path).matches()
					? DataResult.success(path)
					: DataResult.error(() -> path + " is not accepted directory name");
		}
		
		@Override
		public String toString() {
			return this.overlay;
		}
	}
}
