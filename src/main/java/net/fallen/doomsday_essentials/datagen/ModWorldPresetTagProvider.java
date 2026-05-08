package net.fallen.doomsday_essentials.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ModWorldPresetTagProvider implements DataProvider {
    private final Path normalTagPath;

    public ModWorldPresetTagProvider(PackOutput output) {
        this.normalTagPath = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve("minecraft")
                .resolve("tags/worldgen/world_preset/normal.json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);

        JsonArray values = new JsonArray();
        values.add("doomsday_essentials:doomsday");
        root.add("values", values);

        return DataProvider.saveStable(cachedOutput, root, this.normalTagPath);
    }

    @Override
    public String getName() {
        return "World Preset Tags: doomsday_essentials";
    }
}
