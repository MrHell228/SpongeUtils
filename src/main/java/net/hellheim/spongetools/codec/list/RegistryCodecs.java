package net.hellheim.spongetools.codec.list;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.advancement.Advancement;
import org.spongepowered.api.advancement.AdvancementType;
import org.spongepowered.api.advancement.criteria.trigger.Trigger;
import org.spongepowered.api.adventure.ChatType;
import org.spongepowered.api.adventure.ResolveOperation;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.entity.BlockEntityType;
import org.spongepowered.api.block.transaction.Operation;
import org.spongepowered.api.command.parameter.managed.ValueParameter;
import org.spongepowered.api.command.parameter.managed.clientcompletion.ClientCompletionType;
import org.spongepowered.api.command.parameter.managed.operator.Operator;
import org.spongepowered.api.command.registrar.CommandRegistrarType;
import org.spongepowered.api.command.registrar.tree.CommandCompletionProvider;
import org.spongepowered.api.command.registrar.tree.CommandTreeNodeType;
import org.spongepowered.api.command.selector.SelectorSortAlgorithm;
import org.spongepowered.api.command.selector.SelectorType;
import org.spongepowered.api.data.persistence.DataFormat;
import org.spongepowered.api.data.type.ArmorMaterial;
import org.spongepowered.api.data.type.ArtType;
import org.spongepowered.api.data.type.AttachmentSurface;
import org.spongepowered.api.data.type.AxolotlVariant;
import org.spongepowered.api.data.type.BambooLeavesType;
import org.spongepowered.api.data.type.BannerPatternShape;
import org.spongepowered.api.data.type.BellAttachmentType;
import org.spongepowered.api.data.type.BoatType;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.CatType;
import org.spongepowered.api.data.type.ChestAttachmentType;
import org.spongepowered.api.data.type.ComparatorMode;
import org.spongepowered.api.data.type.DoorHinge;
import org.spongepowered.api.data.type.DripstoneSegment;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.FoxType;
import org.spongepowered.api.data.type.FrogType;
import org.spongepowered.api.data.type.HandPreference;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.data.type.ItemTier;
import org.spongepowered.api.data.type.JigsawBlockOrientation;
import org.spongepowered.api.data.type.LlamaType;
import org.spongepowered.api.data.type.MatterType;
import org.spongepowered.api.data.type.MooshroomType;
import org.spongepowered.api.data.type.NotePitch;
import org.spongepowered.api.data.type.PandaGene;
import org.spongepowered.api.data.type.ParrotType;
import org.spongepowered.api.data.type.PhantomPhase;
import org.spongepowered.api.data.type.PickupRule;
import org.spongepowered.api.data.type.PistonType;
import org.spongepowered.api.data.type.PortionType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.data.type.RabbitType;
import org.spongepowered.api.data.type.RaidStatus;
import org.spongepowered.api.data.type.RailDirection;
import org.spongepowered.api.data.type.SalmonSize;
import org.spongepowered.api.data.type.SculkSensorState;
import org.spongepowered.api.data.type.SkinPart;
import org.spongepowered.api.data.type.SlabPortion;
import org.spongepowered.api.data.type.SpellType;
import org.spongepowered.api.data.type.StairShape;
import org.spongepowered.api.data.type.StructureMode;
import org.spongepowered.api.data.type.Tilt;
import org.spongepowered.api.data.type.TrialSpawnerState;
import org.spongepowered.api.data.type.TropicalFishShape;
import org.spongepowered.api.data.type.VaultState;
import org.spongepowered.api.data.type.VillagerType;
import org.spongepowered.api.data.type.WallConnectionState;
import org.spongepowered.api.data.type.WireAttachmentType;
import org.spongepowered.api.data.type.WolfVariant;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.music.MusicDisc;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityCategory;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.ai.goal.GoalExecutorType;
import org.spongepowered.api.entity.ai.goal.GoalType;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.api.entity.display.BillboardType;
import org.spongepowered.api.entity.display.ItemDisplayType;
import org.spongepowered.api.entity.display.TextAlignment;
import org.spongepowered.api.entity.living.monster.boss.dragon.phase.DragonPhaseType;
import org.spongepowered.api.entity.living.player.chat.ChatVisibility;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.event.cause.entity.DismountType;
import org.spongepowered.api.event.cause.entity.MovementType;
import org.spongepowered.api.event.cause.entity.SpawnType;
import org.spongepowered.api.event.cause.entity.damage.DamageEffect;
import org.spongepowered.api.event.cause.entity.damage.DamageScaling;
import org.spongepowered.api.event.cause.entity.damage.DamageStepType;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.item.FireworkShape;
import org.spongepowered.api.item.ItemRarity;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.equipment.EquipmentGroup;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.item.inventory.query.QueryType;
import org.spongepowered.api.item.potion.PotionType;
import org.spongepowered.api.item.recipe.Recipe;
import org.spongepowered.api.item.recipe.RecipeType;
import org.spongepowered.api.item.recipe.smithing.TrimMaterial;
import org.spongepowered.api.item.recipe.smithing.TrimPattern;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.map.color.MapShade;
import org.spongepowered.api.map.decoration.MapDecorationType;
import org.spongepowered.api.map.decoration.orientation.MapDecorationOrientation;
import org.spongepowered.api.placeholder.PlaceholderParser;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.scheduler.TaskPriority;
import org.spongepowered.api.scoreboard.CollisionRule;
import org.spongepowered.api.scoreboard.Visibility;
import org.spongepowered.api.scoreboard.criteria.Criterion;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayMode;
import org.spongepowered.api.service.ban.BanType;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.AccountDeletionResultType;
import org.spongepowered.api.service.economy.transaction.TransactionType;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.StatisticCategory;
import org.spongepowered.api.util.mirror.Mirror;
import org.spongepowered.api.util.orientation.Orientation;
import org.spongepowered.api.util.rotation.Rotation;
import org.spongepowered.api.world.ChunkRegenerateFlag;
import org.spongepowered.api.world.HeightType;
import org.spongepowered.api.world.LightType;
import org.spongepowered.api.world.WorldType;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.biome.climate.GrassColorModifier;
import org.spongepowered.api.world.biome.climate.Precipitation;
import org.spongepowered.api.world.biome.climate.TemperatureModifier;
import org.spongepowered.api.world.chunk.ChunkState;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.explosion.ExplosionBlockInteraction;
import org.spongepowered.api.world.gamerule.GameRule;
import org.spongepowered.api.world.generation.carver.Carver;
import org.spongepowered.api.world.generation.carver.CarverType;
import org.spongepowered.api.world.generation.config.flat.FlatGeneratorConfig;
import org.spongepowered.api.world.generation.config.noise.DensityFunction;
import org.spongepowered.api.world.generation.config.noise.Noise;
import org.spongepowered.api.world.generation.config.noise.NoiseConfig;
import org.spongepowered.api.world.generation.config.noise.NoiseGeneratorConfig;
import org.spongepowered.api.world.generation.feature.DecorationStep;
import org.spongepowered.api.world.generation.feature.Feature;
import org.spongepowered.api.world.generation.feature.FeatureType;
import org.spongepowered.api.world.generation.feature.PlacedFeature;
import org.spongepowered.api.world.generation.feature.PlacementModifierType;
import org.spongepowered.api.world.generation.structure.Structure;
import org.spongepowered.api.world.generation.structure.StructureSet;
import org.spongepowered.api.world.generation.structure.StructureType;
import org.spongepowered.api.world.generation.structure.jigsaw.JigsawPool;
import org.spongepowered.api.world.generation.structure.jigsaw.ProcessorList;
import org.spongepowered.api.world.generation.structure.jigsaw.ProcessorType;
import org.spongepowered.api.world.schematic.PaletteType;
import org.spongepowered.api.world.server.WorldArchetypeType;
import org.spongepowered.api.world.teleport.TeleportHelperFilter;
import org.spongepowered.api.world.weather.WeatherType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.item.CustomItemType;
import net.hellheim.spongetools.custom.item.EitherItemType;
import net.hellheim.spongetools.custom.item.data.CustomConsumeEffect;
import net.hellheim.spongetools.custom.model.item.Item;
import net.hellheim.spongetools.custom.model.item.ItemModel;

/**
 * Codecs for all {@link RegistryType}s provided by SpongeAPI.
 */
public final class RegistryCodecs {
	
	// SpongeToolsAPI
	
	public static final Codec<CustomItemType> CUSTOM_ITEM_TYPE = RegistryCodecs.register(CustomItemType.class, CustomItemType.registry());
	
	public static final Codec<EitherItemType> EITHER_ITEM_TYPE = RegistryCodecs.register(EitherItemType.class, EitherItemType.registry());
	
	public static final Codec<Item> ITEM = RegistryCodecs.register(Item.class, Item.registry());
	
	public static final Codec<ItemModel> ITEM_MODEL = RegistryCodecs.register(ItemModel.class, ItemModel.registry());
	
	public static final Codec<MapCodec<? extends CustomConsumeEffect>> CUSTOM_CONSUME_EFFECT_TYPE = RegistryCodecs.of(SpongeTools.Registries.CONSUME_EFFECT_TYPE);
	
	// SpongeAPI
	
	public static final Codec<Advancement> ADVANCEMENT = RegistryCodecs.register(Advancement.class, RegistryTypes.ADVANCEMENT);
	
	public static final Codec<ArtType> ART_TYPE = RegistryCodecs.register(ArtType.class, RegistryTypes.ART_TYPE);
	
	public static final Codec<ArmorMaterial> ARMOR_MATERIAL = RegistryCodecs.register(ArmorMaterial.class, RegistryTypes.ARMOR_MATERIAL);
	
	public static final Codec<AttributeType> ATTRIBUTE_TYPE = RegistryCodecs.register(AttributeType.class, RegistryTypes.ATTRIBUTE_TYPE);
	
	public static final Codec<AxolotlVariant> AXOLOTL_VARIANT = RegistryCodecs.register(AxolotlVariant.class, RegistryTypes.AXOLOTL_VARIANT);
	
	public static final Codec<Biome> BIOME = RegistryCodecs.register(Biome.class, RegistryTypes.BIOME);
	
	public static final Codec<BlockType> BLOCK_TYPE = RegistryCodecs.register(BlockType.class, RegistryTypes.BLOCK_TYPE);
	
	public static final Codec<BlockEntityType> BLOCK_ENTITY_TYPE = RegistryCodecs.register(BlockEntityType.class, RegistryTypes.BLOCK_ENTITY_TYPE);
	
	public static final Codec<CarverType> CARVER_TYPE = RegistryCodecs.register(CarverType.class, RegistryTypes.CARVER_TYPE);
	
	public static final Codec<Carver> CARVER = RegistryCodecs.register(Carver.class, RegistryTypes.CARVER);
	
	public static final Codec<ChatType> CHAT_TYPE = RegistryCodecs.register(ChatType.class, RegistryTypes.CHAT_TYPE);
	
	public static final Codec<ChunkState> CHUNK_STATE = RegistryCodecs.register(ChunkState.class, RegistryTypes.CHUNK_STATE);
	
	public static final Codec<ContainerType> CONTAINER_TYPE = RegistryCodecs.register(ContainerType.class, RegistryTypes.CONTAINER_TYPE);
	
	public static final Codec<DensityFunction> DENSITY_FUNCTION = RegistryCodecs.register(DensityFunction.class, RegistryTypes.DENSITY_FUNCTION);
	
	public static final Codec<EnchantmentType> ENCHANTMENT_TYPE = RegistryCodecs.register(EnchantmentType.class, RegistryTypes.ENCHANTMENT_TYPE);
	
	public static final Codec<EntityCategory> ENTITY_CATEGORY = RegistryCodecs.register(EntityCategory.class, RegistryTypes.ENTITY_CATEGORY);
	
	public static final Codec<EntityType<? extends Entity>> ENTITY_TYPE = RegistryCodecs.register(EntityType.class, RegistryTypes.ENTITY_TYPE);
	
	public static final Codec<Feature> FEATURE = RegistryCodecs.register(Feature.class, RegistryTypes.FEATURE);
	
	public static final Codec<FeatureType> FEATURE_TYPE = RegistryCodecs.register(FeatureType.class, RegistryTypes.FEATURE_TYPE);
	
	public static final Codec<FluidType> FLUID_TYPE = RegistryCodecs.register(FluidType.class, RegistryTypes.FLUID_TYPE);
	
	public static final Codec<ItemType> ITEM_TYPE = RegistryCodecs.register(ItemType.class, RegistryTypes.ITEM_TYPE);
	
	public static final Codec<JigsawPool> JIGSAW_POOL = RegistryCodecs.register(JigsawPool.class, RegistryTypes.JIGSAW_POOL);
	
	public static final Codec<MapDecorationType> MAP_DECORATION_TYPE = RegistryCodecs.register(MapDecorationType.class, RegistryTypes.MAP_DECORATION_TYPE);
	
	public static final Codec<NoiseGeneratorConfig> NOISE_GENERATOR_CONFIG = RegistryCodecs.register(NoiseGeneratorConfig.class, RegistryTypes.NOISE_GENERATOR_CONFIG);
	
	public static final Codec<Noise> NOISE = RegistryCodecs.register(Noise.class, RegistryTypes.NOISE);
	
	public static final Codec<ParticleType> PARTICLE_TYPE = RegistryCodecs.register(ParticleType.class, RegistryTypes.PARTICLE_TYPE);
	
	public static final Codec<PlacedFeature> PLACED_FEATURE = RegistryCodecs.register(PlacedFeature.class, RegistryTypes.PLACED_FEATURE);
	
	public static final Codec<PlacementModifierType> PLACEMENT_MODIFIER = RegistryCodecs.register(PlacementModifierType.class, RegistryTypes.PLACEMENT_MODIFIER);
	
	public static final Codec<PotionEffectType> POTION_EFFECT_TYPE = RegistryCodecs.register(PotionEffectType.class, RegistryTypes.POTION_EFFECT_TYPE);
	
	public static final Codec<ProcessorList> PROCESSOR_LIST = RegistryCodecs.register(ProcessorList.class, RegistryTypes.PROCESSOR_LIST);
	
	public static final Codec<ProcessorType> PROCESSOR_TYPE = RegistryCodecs.register(ProcessorType.class, RegistryTypes.PROCESSOR_TYPE);
	
	public static final Codec<PushReaction> PUSH_REACTION = RegistryCodecs.register(PushReaction.class, RegistryTypes.PUSH_REACTION);
	
	public static final Codec<PotionType> POTION_TYPE = RegistryCodecs.register(PotionType.class, RegistryTypes.POTION_TYPE);
	
	public static final Codec<RecipeType<? extends Recipe<?>>> RECIPE_TYPE = RegistryCodecs.register(RecipeType.class, RegistryTypes.RECIPE_TYPE);
	
	public static final Codec<SoundType> SOUND_TYPE = RegistryCodecs.register(SoundType.class, RegistryTypes.SOUND_TYPE);
	
	public static final Codec<Statistic> STATISTIC = RegistryCodecs.register(Statistic.class, RegistryTypes.STATISTIC);
	
	public static final Codec<StatisticCategory> STATISTIC_CATEGORY = RegistryCodecs.register(StatisticCategory.class, RegistryTypes.STATISTIC_CATEGORY);
	
	public static final Codec<Structure> STRUCTURE = RegistryCodecs.register(Structure.class, RegistryTypes.STRUCTURE);
	
	public static final Codec<StructureSet> STRUCTURE_SET = RegistryCodecs.register(StructureSet.class, RegistryTypes.STRUCTURE_SET);
	
	public static final Codec<StructureType> STRUCTURE_TYPE = RegistryCodecs.register(StructureType.class, RegistryTypes.STRUCTURE_TYPE);
	
	public static final Codec<Trigger<? extends Object>> TRIGGER = RegistryCodecs.register(Trigger.class, RegistryTypes.TRIGGER);
	
	public static final Codec<TrimMaterial> TRIM_MATERIAL = RegistryCodecs.register(TrimMaterial.class, RegistryTypes.TRIM_MATERIAL);
	
	public static final Codec<TrimPattern> TRIM_PATTERN = RegistryCodecs.register(TrimPattern.class, RegistryTypes.TRIM_PATTERN);
	
	public static final Codec<VillagerType> VILLAGER_TYPE = RegistryCodecs.register(VillagerType.class, RegistryTypes.VILLAGER_TYPE);
	
	public static final Codec<WorldType> WORLD_TYPE = RegistryCodecs.register(WorldType.class, RegistryTypes.WORLD_TYPE);
	
	// ----
	
	public static final Codec<AccountDeletionResultType> ACCOUNT_DELETION_RESULT_TYPE = RegistryCodecs.register(AccountDeletionResultType.class, RegistryTypes.ACCOUNT_DELETION_RESULT_TYPE);
	
	public static final Codec<AdvancementType> ADVANCEMENT_TYPE = RegistryCodecs.register(AdvancementType.class, RegistryTypes.ADVANCEMENT_TYPE);
	
	public static final Codec<AttachmentSurface> ATTACHMENT_SURFACE = RegistryCodecs.register(AttachmentSurface.class, RegistryTypes.ATTACHMENT_SURFACE);
	
	public static final Codec<AttributeOperation> ATTRIBUTE_OPERATION = RegistryCodecs.register(AttributeOperation.class, RegistryTypes.ATTRIBUTE_OPERATION);
	
	public static final Codec<BambooLeavesType> BAMBOO_LEAVES_TYPE = RegistryCodecs.register(BambooLeavesType.class, RegistryTypes.BAMBOO_LEAVES_TYPE);
	
	public static final Codec<BanType> BAN_TYPE = RegistryCodecs.register(BanType.class, RegistryTypes.BAN_TYPE);
	
	public static final Codec<BannerPatternShape> BANNER_PATTERN_SHAPE = RegistryCodecs.register(BannerPatternShape.class, RegistryTypes.BANNER_PATTERN_SHAPE);
	
	public static final Codec<BellAttachmentType> BELL_ATTACHMENT_TYPE = RegistryCodecs.register(BellAttachmentType.class, RegistryTypes.BELL_ATTACHMENT_TYPE);
	
	public static final Codec<BillboardType> BILLBOARD_TYPE = RegistryCodecs.register(BillboardType.class, RegistryTypes.BILLBOARD_TYPE);
	
	public static final Codec<BoatType> BOAT_TYPE = RegistryCodecs.register(BoatType.class, RegistryTypes.BOAT_TYPE);
	
	public static final Codec<BodyPart> BODY_PART = RegistryCodecs.register(BodyPart.class, RegistryTypes.BODY_PART);
	
	public static final Codec<CatType> CAT_TYPE = RegistryCodecs.register(CatType.class, RegistryTypes.CAT_TYPE);
	
	public static final Codec<ChatVisibility> CHAT_VISIBILITY = RegistryCodecs.register(ChatVisibility.class, RegistryTypes.CHAT_VISIBILITY);
	
	public static final Codec<ChestAttachmentType> CHEST_ATTACHMENT_TYPE = RegistryCodecs.register(ChestAttachmentType.class, RegistryTypes.CHEST_ATTACHMENT_TYPE);
	
	public static final Codec<ChunkRegenerateFlag> CHUNK_REGENERATE_FLAG = RegistryCodecs.register(ChunkRegenerateFlag.class, RegistryTypes.CHUNK_REGENERATE_FLAG);
	
	public static final Codec<ClickType<?>> CLICK_TYPE = RegistryCodecs.register(ClickType.class, RegistryTypes.CLICK_TYPE);
	
	public static final Codec<ClientCompletionType> CLIENT_COMPLETION_TYPE = RegistryCodecs.register(ClientCompletionType.class, RegistryTypes.CLIENT_COMPLETION_TYPE);
	
	public static final Codec<CollisionRule> COLLISION_RULE = RegistryCodecs.register(CollisionRule.class, RegistryTypes.COLLISION_RULE);
	
	public static final Codec<CommandCompletionProvider> COMMAND_COMPLETION_PROVIDER = RegistryCodecs.register(CommandCompletionProvider.class, RegistryTypes.COMMAND_COMPLETION_PROVIDER);
	
	public static final Codec<CommandRegistrarType<?>> COMMAND_REGISTRAR_TYPE = RegistryCodecs.register(CommandRegistrarType.class, RegistryTypes.COMMAND_REGISTRAR_TYPE);
	
	public static final Codec<CommandTreeNodeType<? extends Object>> COMMAND_TREE_NODE_TYPE = RegistryCodecs.register(CommandTreeNodeType.class, RegistryTypes.COMMAND_TREE_NODE_TYPE);
	
	public static final Codec<ComparatorMode> COMPARATOR_MODE = RegistryCodecs.register(ComparatorMode.class, RegistryTypes.COMPARATOR_MODE);
	
	public static final Codec<Criterion> CRITERION = RegistryCodecs.register(Criterion.class, RegistryTypes.CRITERION);
	
	public static final Codec<Currency> CURRENCY = RegistryCodecs.register(Currency.class, RegistryTypes.CURRENCY);
	
	public static final Codec<DamageStepType> DAMAGE_STEP_TYPE = RegistryCodecs.register(DamageStepType.class, RegistryTypes.DAMAGE_STEP_TYPE);
	
	public static final Codec<DamageType> DAMAGE_TYPE = RegistryCodecs.register(DamageType.class, RegistryTypes.DAMAGE_TYPE);
	
	public static final Codec<DamageScaling> DAMAGE_SCALING = RegistryCodecs.register(DamageScaling.class, RegistryTypes.DAMAGE_SCALING);
	
	public static final Codec<DamageEffect> DAMAGE_EFFECT = RegistryCodecs.register(DamageEffect.class, RegistryTypes.DAMAGE_EFFECT);
	
	public static final Codec<DataFormat> DATA_FORMAT = RegistryCodecs.register(DataFormat.class, RegistryTypes.DATA_FORMAT);
	
	public static final Codec<DecorationStep> DECORATION_STEP = RegistryCodecs.register(DecorationStep.class, RegistryTypes.DECORATION_STEP);
	
	public static final Codec<Difficulty> DIFFICULTY = RegistryCodecs.register(Difficulty.class, RegistryTypes.DIFFICULTY);
	
	public static final Codec<DismountType> DISMOUNT_TYPE = RegistryCodecs.register(DismountType.class, RegistryTypes.DISMOUNT_TYPE);
	
	public static final Codec<DisplaySlot> DISPLAT_SLOT = RegistryCodecs.register(DisplaySlot.class, RegistryTypes.DISPLAY_SLOT);
	
	public static final Codec<DoorHinge> DOOR_HINGE = RegistryCodecs.register(DoorHinge.class, RegistryTypes.DOOR_HINGE);
	
	public static final Codec<DragonPhaseType> DRAGON_PHASE_TYPE = RegistryCodecs.register(DragonPhaseType.class, RegistryTypes.DRAGON_PHASE_TYPE);
	
	public static final Codec<DripstoneSegment> DRIPSTONE_SEGMENT = RegistryCodecs.register(DripstoneSegment.class, RegistryTypes.DRIPSTONE_SEGMENT);
	
	public static final Codec<DyeColor> DYE_COLOR = RegistryCodecs.register(DyeColor.class, RegistryTypes.DYE_COLOR);
	
	public static final Codec<EquipmentGroup> EQUIPMENT_GROUP = RegistryCodecs.register(EquipmentGroup.class, RegistryTypes.EQUIPMENT_GROUP);
	
	public static final Codec<EquipmentType> EQUIPMENT_TYPE = RegistryCodecs.register(EquipmentType.class, RegistryTypes.EQUIPMENT_TYPE);
	
	// TODO Fix in API
	public static final Codec<ExplosionBlockInteraction> EXPLOSION_BLOCK_INTERACTION = RegistryCodecs.register(ExplosionBlockInteraction.class, RegistryTypes.EXPLOSION_BLOCK_INTERACTION.asDefaultedType(Sponge::game));
	
	public static final Codec<FireworkShape> FIREWORK_SHAPE = RegistryCodecs.register(FireworkShape.class, RegistryTypes.FIREWORK_SHAPE);
	
	public static final Codec<FlatGeneratorConfig> FLAT_GENERATOR_CONFIG = RegistryCodecs.register(FlatGeneratorConfig.class, RegistryTypes.FLAT_GENERATOR_CONFIG);
	
	public static final Codec<FoxType> FOX_TYPE = RegistryCodecs.register(FoxType.class, RegistryTypes.FOX_TYPE);
	
	public static final Codec<FrogType> FROG_TYPE = RegistryCodecs.register(FrogType.class, RegistryTypes.FROG_TYPE);
	
	public static final Codec<GameMode> GAME_MODE = RegistryCodecs.register(GameMode.class, RegistryTypes.GAME_MODE);
	
	public static final Codec<GameRule<?>> GAME_RULE = RegistryCodecs.register(GameRule.class, RegistryTypes.GAME_RULE);
	
	public static final Codec<GoalExecutorType> GOAL_EXECUTOR_TYPE = RegistryCodecs.register(GoalExecutorType.class, RegistryTypes.GOAL_EXECUTOR_TYPE);
	
	public static final Codec<GoalType> GOAL_TYPE = RegistryCodecs.register(GoalType.class, RegistryTypes.GOAL_TYPE);
	
	public static final Codec<GrassColorModifier> GRASS_COLOR_MODIFIER = RegistryCodecs.register(GrassColorModifier.class, RegistryTypes.GRASS_COLOR_MODIFIER);
	
	public static final Codec<HandPreference> HAND_PREFERENCE = RegistryCodecs.register(HandPreference.class, RegistryTypes.HAND_PREFERENCE);
	
	public static final Codec<HandType> HAND_TYPE = RegistryCodecs.register(HandType.class, RegistryTypes.HAND_TYPE);
	
	public static final Codec<HeightType> HEIGHT_TYPE = RegistryCodecs.register(HeightType.class, RegistryTypes.HEIGHT_TYPE);
	
	public static final Codec<HorseColor> HORSE_COLOR = RegistryCodecs.register(HorseColor.class, RegistryTypes.HORSE_COLOR);
	
	public static final Codec<HorseStyle> HORSE_STYLE = RegistryCodecs.register(HorseStyle.class, RegistryTypes.HORSE_STYLE);
	
	public static final Codec<InstrumentType> INSTRUMENT_TYPE = RegistryCodecs.register(InstrumentType.class, RegistryTypes.INSTRUMENT_TYPE);
	
	public static final Codec<ItemRarity> ITEM_RARITY = RegistryCodecs.register(ItemRarity.class, RegistryTypes.ITEM_RARITY);
	
	public static final Codec<ItemTier> ITEM_TIER = RegistryCodecs.register(ItemTier.class, RegistryTypes.ITEM_TIER);
	
	public static final Codec<ItemDisplayType> ITEM_DISPLAY_TYPE = RegistryCodecs.register(ItemDisplayType.class, RegistryTypes.ITEM_DISPLAY_TYPE);
	
	public static final Codec<JigsawBlockOrientation> JIGSAW_BLOCK_ORIENTATION = RegistryCodecs.register(JigsawBlockOrientation.class, RegistryTypes.JIGSAW_BLOCK_ORIENTATION);
	
	public static final Codec<LightType> LIGHT_TYPE = RegistryCodecs.register(LightType.class, RegistryTypes.LIGHT_TYPE);
	
	public static final Codec<LlamaType> LLAMA_TYPE = RegistryCodecs.register(LlamaType.class, RegistryTypes.LLAMA_TYPE);
	
	public static final Codec<MapColorType> MAP_COLOR_TYPE = RegistryCodecs.register(MapColorType.class, RegistryTypes.MAP_COLOR_TYPE);
	
	public static final Codec<MapDecorationOrientation> MAP_DECORATION_ORIENTATION = RegistryCodecs.register(MapDecorationOrientation.class, RegistryTypes.MAP_DECORATION_ORIENTATION);
	
	public static final Codec<MapShade> MAP_SHADE = RegistryCodecs.register(MapShade.class, RegistryTypes.MAP_SHADE);
	
	public static final Codec<MatterType> MATTER_TYPE = RegistryCodecs.register(MatterType.class, RegistryTypes.MATTER_TYPE);
	
	public static final Codec<Mirror> MIRROR = RegistryCodecs.register(Mirror.class, RegistryTypes.MIRROR);
	
	public static final Codec<MooshroomType> MOOSHROOM_TYPE = RegistryCodecs.register(MooshroomType.class, RegistryTypes.MOOSHROOM_TYPE);
	
	public static final Codec<MovementType> MOVEMENT_TYPE = RegistryCodecs.register(MovementType.class, RegistryTypes.MOVEMENT_TYPE);
	
	public static final Codec<MusicDisc> MUSIC_DISC = RegistryCodecs.register(MusicDisc.class, RegistryTypes.MUSIC_DISC);
	
	public static final Codec<NoiseConfig> NOISE_CONFIG = RegistryCodecs.register(NoiseConfig.class, RegistryTypes.NOISE_CONFIG);
	
	public static final Codec<NotePitch> NOTE_PITCH = RegistryCodecs.register(NotePitch.class, RegistryTypes.NOTE_PITCH);
	
	public static final Codec<ObjectiveDisplayMode> OBJECTIVE_DISPLAY_MODE = RegistryCodecs.register(ObjectiveDisplayMode.class, RegistryTypes.OBJECTIVE_DISPLAY_MODE);
	
	public static final Codec<Operator> OPERATOR = RegistryCodecs.register(Operator.class, RegistryTypes.OPERATOR);
	
	public static final Codec<Operation> OPERATION = RegistryCodecs.register(Operation.class, RegistryTypes.OPERATION);
	
	public static final Codec<Orientation> ORIENTATION = RegistryCodecs.register(Orientation.class, RegistryTypes.ORIENTATION);
	
	public static final Codec<PaletteType<?, ?>> PALETTE_TYPE = RegistryCodecs.register(PaletteType.class, RegistryTypes.PALETTE_TYPE);
	
	public static final Codec<PandaGene> PANDA_GENE = RegistryCodecs.register(PandaGene.class, RegistryTypes.PANDA_GENE);
	
	public static final Codec<ParrotType> PARROT_TYPE = RegistryCodecs.register(ParrotType.class, RegistryTypes.PARROT_TYPE);
	
	public static final Codec<ParticleOption<?>> PARTICLE_OPTION = RegistryCodecs.register(ParticleOption.class, RegistryTypes.PARTICLE_OPTION);
	
	public static final Codec<PhantomPhase> PHANTOM_PHASE = RegistryCodecs.register(PhantomPhase.class, RegistryTypes.PHANTOM_PHASE);
	
	public static final Codec<PickupRule> PICKUP_RULE = RegistryCodecs.register(PickupRule.class, RegistryTypes.PICKUP_RULE);
	
	public static final Codec<PistonType> PISTON_TYPE = RegistryCodecs.register(PistonType.class, RegistryTypes.PISTON_TYPE);
	
	public static final Codec<PlaceholderParser> PLACEHOLDER_PARSER = RegistryCodecs.register(PlaceholderParser.class, RegistryTypes.PLACEHOLDER_PARSER);
	
	public static final Codec<PortionType> PORTION_TYPE = RegistryCodecs.register(PortionType.class, RegistryTypes.PORTION_TYPE);
	
	public static final Codec<Precipitation> PRECIPITATION = RegistryCodecs.register(Precipitation.class, RegistryTypes.PRECIPITATION);
	
	public static final Codec<QueryType> QUERY_TYPE = RegistryCodecs.register(QueryType.class, RegistryTypes.QUERY_TYPE);
	
	public static final Codec<RabbitType> RABBIT_TYPE = RegistryCodecs.register(RabbitType.class, RegistryTypes.RABBIT_TYPE);
	
	public static final Codec<RaidStatus> RAID_STATUS = RegistryCodecs.register(RaidStatus.class, RegistryTypes.RAID_STATUS);
	
	public static final Codec<RailDirection> RAIL_DIRECTION = RegistryCodecs.register(RailDirection.class, RegistryTypes.RAIL_DIRECTION);
	
	public static final Codec<Recipe<?>> RECIPE = RegistryCodecs.register(Recipe.class, RegistryTypes.RECIPE);
	
	public static final Codec<ValueParameter<?>> REGISTRY_KEYED_VALUE_PARAMETER = RegistryCodecs.register(ValueParameter.class, RegistryTypes.REGISTRY_KEYED_VALUE_PARAMETER);
	
	public static final Codec<ResolveOperation> RESOLVE_OPERATION = RegistryCodecs.register(ResolveOperation.class, RegistryTypes.RESOLVE_OPERATION);
	
	public static final Codec<TemperatureModifier> TEMPERATURE_MODIFIER = RegistryCodecs.register(TemperatureModifier.class, RegistryTypes.TEMPERATURE_MODIFIER);
	
	public static final Codec<Rotation> ROTATION = RegistryCodecs.register(Rotation.class, RegistryTypes.ROTATION);
	
	public static final Codec<SalmonSize> SALMON_SIZE = RegistryCodecs.register(SalmonSize.class, RegistryTypes.SALMON_SIZE);
	
	public static final Codec<SculkSensorState> SCULK_SENSOR_STATE = RegistryCodecs.register(SculkSensorState.class, RegistryTypes.SCULK_SENSOR_STATE);
	
	public static final Codec<SelectorSortAlgorithm> SELECTOR_SORT_ALGORITHM = RegistryCodecs.register(SelectorSortAlgorithm.class, RegistryTypes.SELECTOR_SORT_ALGORITHM);
	
	public static final Codec<SelectorType> SELECTOR_TYPE = RegistryCodecs.register(SelectorType.class, RegistryTypes.SELECTOR_TYPE);
	
	public static final Codec<SkinPart> SKIN_PART = RegistryCodecs.register(SkinPart.class, RegistryTypes.SKIN_PART);
	
	public static final Codec<SlabPortion> SLAB_PORTION = RegistryCodecs.register(SlabPortion.class, RegistryTypes.SLAB_PORTION);
	
	public static final Codec<SpawnType> SPAWN_TYPE = RegistryCodecs.register(SpawnType.class, RegistryTypes.SPAWN_TYPE);
	
	public static final Codec<SpellType> SPELL_TYPE = RegistryCodecs.register(SpellType.class, RegistryTypes.SPELL_TYPE);
	
	public static final Codec<StairShape> STAIR_SHAPE = RegistryCodecs.register(StairShape.class, RegistryTypes.STAIR_SHAPE);
	
	public static final Codec<StructureMode> STRUCTURE_MODE = RegistryCodecs.register(StructureMode.class, RegistryTypes.STRUCTURE_MODE);
	
	public static final Codec<TaskPriority> TASK_PRIORITY = RegistryCodecs.register(TaskPriority.class, RegistryTypes.TASK_PRIORITY);
	
	public static final Codec<TeleportHelperFilter> TELEPORT_HELPER_FILTER = RegistryCodecs.register(TeleportHelperFilter.class, RegistryTypes.TELEPORT_HELPER_FILTER);
	
	public static final Codec<TextAlignment> TEXT_ALIGNMENT = RegistryCodecs.register(TextAlignment.class, RegistryTypes.TEXT_ALIGNMENT);
	
	public static final Codec<TransactionType> TRANSACTION_TYPE = RegistryCodecs.register(TransactionType.class, RegistryTypes.TRANSACTION_TYPE);
	
	public static final Codec<TrialSpawnerState> TRIAL_SPAWNER_STATE = RegistryCodecs.register(TrialSpawnerState.class, RegistryTypes.TRIAL_SPAWNER_STATE);
	
	public static final Codec<TropicalFishShape> TROPICAL_FISH_SHAPE = RegistryCodecs.register(TropicalFishShape.class, RegistryTypes.TROPICAL_FISH_SHAPE);
	
	public static final Codec<Tilt> TILT = RegistryCodecs.register(Tilt.class, RegistryTypes.TILT);
	
	// TODO Fix in API
	public static final Codec<VaultState> VAULT_STATE = RegistryCodecs.register(VaultState.class, RegistryTypes.VAULT_STATE.asDefaultedType(Sponge::game));
	
	public static final Codec<Visibility> VISIBILITY = RegistryCodecs.register(Visibility.class, RegistryTypes.VISIBILITY);
	
	public static final Codec<WallConnectionState> WALL_CONNECTION_STATE = RegistryCodecs.register(WallConnectionState.class, RegistryTypes.WALL_CONNECTION_STATE);
	
	public static final Codec<WeatherType> WEATHER_TYPE = RegistryCodecs.register(WeatherType.class, RegistryTypes.WEATHER_TYPE);
	
	public static final Codec<WolfVariant> WOLF_VARIANT = RegistryCodecs.register(WolfVariant.class, RegistryTypes.WOLF_VARIANT);
	
	public static final Codec<WorldArchetypeType> WORLD_ARCHETYPE_TYPE = RegistryCodecs.register(WorldArchetypeType.class, RegistryTypes.WORLD_ARCHETYPE_TYPE);
	
	public static final Codec<WireAttachmentType> WIRE_ATTACHMENT_TYPE = RegistryCodecs.register(WireAttachmentType.class, RegistryTypes.WIRE_ATTACHMENT_TYPE);
	
	public static <T> Codec<T> register(final Class<? super T> type, final DefaultedRegistryType<T> registry) {
		final Codec<T> codec = RegistryCodecs.of(registry);
		TypeCodecs.register(type, codec);
		return codec;
	}
	
	public static <T> Codec<T> of(final DefaultedRegistryType<T> registry) {
		return ExtraCodecs.idResolver(SpongeCodecs.RESOURCE_KEY,
				key -> registry.get().findValue(key).orElse(null),
				value -> registry.get().findValueKey(value).orElse(null)
				);
	}
	
	private RegistryCodecs() {
	}
}
