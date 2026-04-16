package net.hellheim.spongetools.resourcepack.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.resourcepack.util.InclusiveRange;

record PackIntermediaryFormat(
		Optional<PackFormat> min, Optional<PackFormat> max,
		Optional<Integer> format, Optional<InclusiveRange<Integer>> supported) {
	
	public static final MapCodec<PackIntermediaryFormat> PACK_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					PackFormat.BOTTOM_CODEC.optionalFieldOf("min_format").forGetter(PackIntermediaryFormat::min),
					PackFormat.TOP_CODEC.optionalFieldOf("max_format").forGetter(PackIntermediaryFormat::max),
					Codec.INT.optionalFieldOf("pack_format").forGetter(PackIntermediaryFormat::format),
					InclusiveRange.codec(Codec.INT).optionalFieldOf("supported_formats").forGetter(PackIntermediaryFormat::supported))
			.apply(instance, PackIntermediaryFormat::new));
	
	public static final MapCodec<PackIntermediaryFormat> OVERLAY_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					PackFormat.BOTTOM_CODEC.optionalFieldOf("min_format").forGetter(PackIntermediaryFormat::min),
					PackFormat.TOP_CODEC.optionalFieldOf("max_format").forGetter(PackIntermediaryFormat::max),
					InclusiveRange.codec(Codec.INT).optionalFieldOf("formats").forGetter(PackIntermediaryFormat::supported))
			.apply(instance, (min, max, formats) -> new PackIntermediaryFormat(min, max, min.map(PackFormat::major), formats)));
	
	public PackIntermediaryFormat(
		final Optional<PackFormat> min, final Optional<PackFormat> max,
		final Optional<Integer> format, final Optional<InclusiveRange<Integer>> supported
	) {
		this.min = min;
		this.max = max;
		this.format = format;
		this.supported = supported;
	}
	
	public static PackIntermediaryFormat fromRange(final InclusiveRange<PackFormat> range, final int lastPreMinorVersion) {
		final InclusiveRange<Integer> majorRange = range.map(PackFormat::major);
		return new PackIntermediaryFormat(Optional.of(range.minInclusive()),
				Optional.of(range.maxInclusive()),
				majorRange.isValueInRange(lastPreMinorVersion)
						? Optional.of(majorRange.minInclusive())
						: Optional.empty(),
				majorRange.isValueInRange(lastPreMinorVersion)
						? Optional.of(InclusiveRange.of(majorRange.minInclusive(), majorRange.maxInclusive()))
						: Optional.empty());
	}
	
	public static <ResultType, HolderType extends Holder> DataResult<List<ResultType>> validateHolderList(
		final List<HolderType> list, final int lastPreMinorVersion,
		final BiFunction<HolderType, InclusiveRange<PackFormat>, ResultType> constructor
	) {
		final int minVersion = list.stream()
				.map(Holder::format)
				.mapToInt(PackIntermediaryFormat::effectiveMinMajorVersion)
				.min()
				.orElse(Integer.MAX_VALUE);
		final List<ResultType> result = new ArrayList<>(list.size());
		final Iterator<HolderType> iterator = list.iterator();
		while (iterator.hasNext()) {
			final HolderType entry = iterator.next();
			final PackIntermediaryFormat format = entry.format();
			if (format.min().isEmpty() && format.max().isEmpty() && format.supported().isEmpty()) {
				return DataResult.error(() -> String.format("Unknown or broken overlay entry %s", entry));
			} else {
				final DataResult<InclusiveRange<PackFormat>> entryResult = format.validate(
						lastPreMinorVersion,
						false,
						minVersion <= lastPreMinorVersion,
						"Overlay \"" + String.valueOf(entry) + "\"", "formats");
				if (!entryResult.isSuccess()) {
					return DataResult.error(() -> entryResult.error().get().message());
				}

				result.add(constructor.apply(entry, entryResult.getOrThrow()));
			}
		}

		return DataResult.success(List.copyOf(result));
	}
	
	public int effectiveMinMajorVersion() {
		if (this.min.isPresent()) {
			return this.supported.isPresent()
					? Math.min(this.min.get().major(), this.supported.get().minInclusive())
					: this.min.get().major();
		} else {
			return this.supported.isPresent()
					? this.supported.get().minInclusive()
					: Integer.MAX_VALUE;
		}
	}
	
	public DataResult<InclusiveRange<PackFormat>> validate(
		final int lastPreMinorVersion, final boolean hasPackFormatField,
		final boolean requireOldField, final String context, final String oldFieldName
	) {
		if (this.min.isPresent() != this.max.isPresent()) {
			return DataResult.error(() ->
					context + " missing field, must declare both min_format and max_format");
		} else if (requireOldField && this.supported.isEmpty()) {
			return DataResult.error(() ->
					context + " missing required field " + oldFieldName
						+ ", must be present in all overlays for any overlays to work across game versions");
		} else if (this.min.isPresent()) {
			return this.validateNewFormat(lastPreMinorVersion, hasPackFormatField, requireOldField, context, oldFieldName);
		} else if (this.supported.isPresent()) {
			return this.validateOldFormat(lastPreMinorVersion, hasPackFormatField, context, oldFieldName);
		} else if (hasPackFormatField && this.format.isPresent()) {
			final int mainFormat = this.format.get();
			return mainFormat > lastPreMinorVersion
					? DataResult.error(() ->
							context + " declares support for version newer than " + lastPreMinorVersion
									+ ", but is missing mandatory fields min_format and max_format")
					: DataResult.success(InclusiveRange.of(PackFormat.of(mainFormat)));
		} else {
			return DataResult.error(() ->
					context + " could not be parsed, missing format version information");
		}
	}
	
	public DataResult<InclusiveRange<PackFormat>> validateNewFormat(
		final int lastPreMinorVersion, final boolean hasPackFormatField,
		final boolean requireOldField, final String context, final String oldFieldName
	) {
		final int majorMin = this.min.get().major();
		final int majorMax = this.max.get().major();
		if (this.min.get().compareTo(this.max.get()) > 0) {
			return DataResult.error(() ->
					context + " min_format (" + String.valueOf(this.min.get()) + ") is greater than max_format ("
						+ String.valueOf(this.max.get()) + ")");
		} else {
			final String packFormatError;
			if (majorMin > lastPreMinorVersion && !requireOldField) {
				if (this.supported.isPresent()) {
					return DataResult.error(() -> 
							context + " key " + oldFieldName + " is deprecated starting from pack format "
								+ (lastPreMinorVersion + 1) + ". Remove " + oldFieldName
								+ " from your pack.mcmeta.");
				}
				
				if (hasPackFormatField && this.format.isPresent()) {
					packFormatError = this.validatePackFormatForRange(majorMin, majorMax);
					if (packFormatError != null) {
						return DataResult.error(() -> packFormatError);
					}
				}
			} else {
				if (!this.supported.isPresent()) {
					return DataResult.error(() -> 
							context + " declares support for format " + majorMin
								+ ", but game versions supporting formats 17 to " + lastPreMinorVersion
								+ " require a " + oldFieldName + " field. Add \"" + oldFieldName + "\": ["
								+ majorMin + ", " + lastPreMinorVersion
								+ "] or require a version greater or equal to " + (lastPreMinorVersion + 1) + ".0.");
				}
				
				final InclusiveRange<Integer> oldSupportedVersions = this.supported.get();
				if (oldSupportedVersions.minInclusive() != majorMin) {
					return DataResult.error(() -> 
							context + " version declaration mismatch between " + oldFieldName + " (from "
								+ String.valueOf(oldSupportedVersions.minInclusive()) + ") and min_format ("
								+ String.valueOf(this.min.get()) + ")");
				}
				
				if (oldSupportedVersions.maxInclusive() != majorMax
						&& oldSupportedVersions.maxInclusive() != lastPreMinorVersion) {
					return DataResult.error(() -> 
							context + " version declaration mismatch between " + oldFieldName + " (up to "
								+ String.valueOf(oldSupportedVersions.maxInclusive()) + ") and max_format ("
								+ String.valueOf(this.max.get()) + ")");
				}
				
				if (hasPackFormatField) {
					if (!this.format.isPresent()) {
						return DataResult.error(() -> 
								context + " declares support for formats up to " + lastPreMinorVersion
									+ ", but game versions supporting formats 17 to " + lastPreMinorVersion
									+ " require a pack_format field. Add \"pack_format\": " + majorMin
									+ " or require a version greater or equal to " + (lastPreMinorVersion + 1)
									+ ".0.");
					}
					
					packFormatError = this.validatePackFormatForRange(majorMin, majorMax);
					if (packFormatError != null) {
						return DataResult.error(() -> packFormatError);
					}
				}
			}
			
			return DataResult.success(InclusiveRange.of(this.min.get(), this.max.get()));
		}
	}
	
	private DataResult<InclusiveRange<PackFormat>> validateOldFormat(
		final int lastPreMinorVersion, final boolean hasPackFormatField,
		final String context, final String oldFieldName
	) {
		final InclusiveRange<Integer> oldSupportedVersions = this.supported.get();
		final int min = oldSupportedVersions.minInclusive();
		final int max = oldSupportedVersions.maxInclusive();
		if (max > lastPreMinorVersion) {
			return DataResult.error(() ->
					context + " declares support for version newer than " + lastPreMinorVersion
							+ ", but is missing mandatory fields min_format and max_format");
		} else {
			if (hasPackFormatField) {
				if (!this.format.isPresent()) {
					return DataResult.error(() -> {
						return context + " declares support for formats up to " + lastPreMinorVersion
								+ ", but game versions supporting formats 17 to " + lastPreMinorVersion
								+ " require a pack_format field. Add \"pack_format\": " + min
								+ " or require a version greater or equal to " + (lastPreMinorVersion + 1) + ".0.";
					});
				}
				
				final @Nullable String packFormatError = this.validatePackFormatForRange(min, max);
				if (packFormatError != null) {
					return DataResult.error(() -> packFormatError);
				}
			}
			
			return DataResult.success(InclusiveRange.of(min, max).map(PackFormat::of));
		}
	}
	
	public @Nullable String validatePackFormatForRange(final int min, final int max) {
		final int mainFormat = this.format.get();
		if (mainFormat >= min && mainFormat <= max) {
			return mainFormat < 15
					? "Multi-version packs cannot support minimum version of less than 15, since this will leave versions in range unable to load pack."
					: null;
		} else {
			return "Pack declared support for versions " + min + " to " + max + " but declared main format is " + mainFormat;
		}
	}
	
	public interface Holder {
		
		PackIntermediaryFormat format();
	}
}
