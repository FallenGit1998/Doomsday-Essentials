package net.fallen.doomsday_essentials.datagen;

import com.google.gson.JsonObject;
import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ModWorldPresetProvider implements DataProvider {
    private final Path targetPath;

    public ModWorldPresetProvider(PackOutput output) {
        this.targetPath = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(DoomsdayEssentials.MOD_ID)
                .resolve("worldgen/world_preset/doomsday.json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        JsonObject root = new JsonObject();
        JsonObject dimensions = new JsonObject();
        root.add("dimensions", dimensions);

        dimensions.add("minecraft:overworld", createFixedOverworld("biomesoplenty:wasteland"));
        dimensions.add("minecraft:the_nether", createNether());
        dimensions.add("minecraft:the_end", createEnd());
        dimensions.add("doomsday_essentials:wasteland_earth", createFixedOverworld("biomesoplenty:wasteland"));
        dimensions.add("doomsday_essentials:restored_earth", createRestoredEarth());

        return DataProvider.saveStable(cachedOutput, root, this.targetPath);
    }

    @Override
    public String getName() {
        return "World Preset: doomsday_essentials";
    }

    private static JsonObject createFixedOverworld(String biomeId) {
        JsonObject value = createDimensionRoot("minecraft:overworld", "minecraft:overworld");
        JsonObject biomeSource = value.getAsJsonObject("generator").getAsJsonObject("biome_source");
        biomeSource.addProperty("type", "minecraft:fixed");
        biomeSource.addProperty("biome", biomeId);
        return value;
    }

    private static JsonObject createRestoredEarth() {
        JsonObject value = createDimensionRoot("minecraft:overworld", "minecraft:overworld");
        JsonObject biomeSource = value.getAsJsonObject("generator").getAsJsonObject("biome_source");
        biomeSource.addProperty("type", "minecraft:multi_noise");
        biomeSource.addProperty("preset", "minecraft:overworld");
        return value;
    }

    private static JsonObject createNether() {
        JsonObject value = createDimensionRoot("minecraft:the_nether", "minecraft:nether");
        JsonObject biomeSource = value.getAsJsonObject("generator").getAsJsonObject("biome_source");
        biomeSource.addProperty("type", "minecraft:multi_noise");
        biomeSource.addProperty("preset", "minecraft:nether");
        return value;
    }

    private static JsonObject createEnd() {
        JsonObject value = createDimensionRoot("minecraft:the_end", "minecraft:end");
        JsonObject biomeSource = value.getAsJsonObject("generator").getAsJsonObject("biome_source");
        biomeSource.addProperty("type", "minecraft:the_end");
        return value;
    }

    private static JsonObject createDimensionRoot(String type, String settings) {
        JsonObject value = new JsonObject();
        value.addProperty("type", type);

        JsonObject generator = new JsonObject();
        generator.addProperty("type", "minecraft:noise");
        generator.add("biome_source", new JsonObject());
        generator.addProperty("settings", settings);
        value.add("generator", generator);
        return value;
    }
}
