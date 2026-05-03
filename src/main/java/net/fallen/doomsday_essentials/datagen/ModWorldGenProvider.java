package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.worldgen.ModBiomeModifiers;
import net.fallen.doomsday_essentials.worldgen.ModConfiguredFeatures;
import net.fallen.doomsday_essentials.worldgen.ModDimensions;
import net.fallen.doomsday_essentials.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DoomsdayEssentials.MOD_ID));
    }
}
