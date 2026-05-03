package net.fallen.doomsday_essentials.worldgen;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensions {

    public static final ResourceKey<LevelStem> WASTELAND_EARTH = ResourceKey.create(
            Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(DoomsdayEssentials.MOD_ID, "wasteland_earth")
    );
    public static final ResourceKey<Level> WASTELAND_EARTH_LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(DoomsdayEssentials.MOD_ID, "wasteland_earth")
    );
    public static final ResourceKey<Level> RESTORED_EARTH_LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(DoomsdayEssentials.MOD_ID, "restored_earth")
    );
    public static final ResourceKey<LevelStem> RESTORED_EARTH = ResourceKey.create(
            Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(DoomsdayEssentials.MOD_ID, "restored_earth")
    );

    public static final ResourceKey<Biome> BOP_WASTELAND = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath("biomesoplenty", "wasteland")
    );

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var biomes = context.lookup(Registries.BIOME);
        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        var multiNoisePresets = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        var overworldType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
        var wastelandBiome = biomes.getOrThrow(BOP_WASTELAND);
        var overworldNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        var overworldPreset = multiNoisePresets.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);

        LevelStem wastelandStem = new LevelStem(
                overworldType,
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(wastelandBiome),
                        overworldNoise
                )
        );
        LevelStem restoredStem = new LevelStem(
                overworldType,
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromPreset(overworldPreset),
                        overworldNoise
                )
        );

        context.register(WASTELAND_EARTH, wastelandStem);
        context.register(RESTORED_EARTH, restoredStem);
    }
}
