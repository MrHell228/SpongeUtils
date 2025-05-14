package net.hellheim.spongetools.custom.type.block;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryTypes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.custom.type.EitherType;
import net.hellheim.spongetools.proxy.solid.block.BlockStateProxy;
import net.hellheim.spongetools.proxy.solid.block.BlockTypeProxy;

public sealed abstract class EitherBlockType
		implements EitherType<BlockType, CustomBlockType, EitherBlockType>, BlockTypeProxy
		permits EitherBlockType.Common, EitherBlockType.Custom {
	
	private static final Map<BlockType, EitherBlockType> COMMON_MAP = new IdentityHashMap<>();
	private static final Map<CustomBlockType, EitherBlockType> CUSTOM_MAP = new IdentityHashMap<>();
	
	public static final Codec<EitherBlockType> CODEC = Codec
			.either(RegistryCodecs.BLOCK_TYPE, RegistryCodecs.CUSTOM_BLOCK_TYPE)
			.xmap(EitherBlockType::of, EitherBlockType::either);
	
	public static final DefaultedRegistryType<EitherBlockType> registry() {
		return SpongeTools.Registries.EITHER_BLOCK_TYPE;
	}
	
	public static Codec<EitherBlockType> registryCodec() {
		return RegistryCodecs.EITHER_BLOCK_TYPE;
	}
	
	public static EitherBlockType common(final Supplier<? extends BlockType> type) {
		return EitherBlockType.common(type.get());
	}
	
	public static EitherBlockType common(final BlockType type) {
		return EitherBlockType.COMMON_MAP.computeIfAbsent(type, EitherBlockType.Common::new);
	}
	
	public static EitherBlockType custom(final Supplier<? extends CustomBlockType> type) {
		return EitherBlockType.custom(type.get());
	}
	
	public static EitherBlockType custom(final CustomBlockType type) {
		return EitherBlockType.CUSTOM_MAP.computeIfAbsent(type, EitherBlockType.Custom::new);
	}
	
	public static EitherBlockType of(final Either<BlockType, CustomBlockType> either) {
		return either.map(EitherBlockType::common, EitherBlockType::custom);
	}
	
	public static Optional<EitherBlockType> resolve(final ResourceKey key) {
		return RegistryTypes.BLOCK_TYPE
				.get()
				.findValue(key)
				.map(EitherBlockType::common)
				.or(() -> CustomBlockType.resolve(key)
						.map(EitherBlockType::custom));
	}
	
	protected static final class Common extends EitherBlockType implements
			EitherType.Common<BlockType, CustomBlockType, EitherBlockType> {
		
		private final BlockType type;
		
		protected Common(final BlockType type) {
			this.type = Objects.requireNonNull(type, "type");
		}
		
		@Override
		public BlockType get() {
			return this.get();
		}
		
		@Override
		public BlockType getAsBlockType() {
			return this.type;
		}
		
		@Override
		public EitherBlockType map(
			final Function<? super BlockType, ? extends BlockType> commonMapper,
			final Function<? super CustomBlockType, ? extends CustomBlockType> customMapper
		) {
			final BlockType newType = commonMapper.apply(this.type);
			return newType == this.type ? this : EitherBlockType.common(newType);
		}
		
		@Override
		public int hashCode() {
			return this.type.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			
			return this.type.equals(((EitherBlockType.Common) obj).type);
		}
	}
	
	protected static final class Custom extends EitherBlockType implements
			EitherType.Custom<BlockType, CustomBlockType, EitherBlockType>,
			BlockStateProxy {
		
		private final CustomBlockType type;
		
		protected Custom(final CustomBlockType type) {
			this.type = Objects.requireNonNull(type, "type");
		}
		
		@Override
		public CustomBlockType get() {
			return this.type;
		}
		
		@Override
		public BlockState getAsBlockState() {
			return this.type.getAsBlockState();
		}
		
		@Override
		public EitherBlockType map(
			final Function<? super BlockType, ? extends BlockType> commonMapper,
			final Function<? super CustomBlockType, ? extends CustomBlockType> customMapper
		) {
			final CustomBlockType newType = customMapper.apply(this.type);
			return newType == this.type ? this : EitherBlockType.custom(newType);
		}
		
		@Override
		public int hashCode() {
			return this.type.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			
			return this.type.equals(((EitherBlockType.Custom) obj).type);
		}
	}
}
