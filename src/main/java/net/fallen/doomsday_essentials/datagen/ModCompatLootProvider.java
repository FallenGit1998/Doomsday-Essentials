package net.fallen.doomsday_essentials.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModCompatLootProvider implements DataProvider {
    private final Path outputRoot;

    public ModCompatLootProvider(PackOutput output) {
        this.outputRoot = output.getOutputFolder(PackOutput.Target.DATA_PACK);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = List.of(
                saveDeadWoodTable(cachedOutput, "dead_wood"),
                saveDeadWoodTable(cachedOutput, "dead_log")
        );
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Compat Loot Tables: Biomes O' Plenty";
    }

    private CompletableFuture<?> saveDeadWoodTable(CachedOutput cache, String blockId) {
        Path target = outputRoot.resolve("biomesoplenty/loot_table/blocks/" + blockId + ".json");

        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:block");
        root.addProperty("random_sequence", "biomesoplenty:blocks/" + blockId);

        JsonArray pools = new JsonArray();
        JsonObject pool = new JsonObject();
        pool.addProperty("rolls", 1.0F);
        pool.addProperty("bonus_rolls", 0.0F);

        JsonArray entries = new JsonArray();
        JsonObject alternatives = new JsonObject();
        alternatives.addProperty("type", "minecraft:alternatives");

        JsonArray children = new JsonArray();
        children.add(createSilkTouchDrop("biomesoplenty:" + blockId));
        children.add(createAshDrop());
        alternatives.add("children", children);

        entries.add(alternatives);
        pool.add("entries", entries);
        pools.add(pool);
        root.add("pools", pools);

        return DataProvider.saveStable(cache, root, target);
    }

    private static JsonObject createSilkTouchDrop(String blockId) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", blockId);

        JsonArray conditions = new JsonArray();
        JsonObject matchTool = new JsonObject();
        matchTool.addProperty("condition", "minecraft:match_tool");
        JsonObject predicate = new JsonObject();
        JsonObject predicates = new JsonObject();
        JsonArray enchantments = new JsonArray();
        JsonObject enchantment = new JsonObject();
        enchantment.addProperty("enchantments", "minecraft:silk_touch");
        JsonObject levels = new JsonObject();
        levels.addProperty("min", 1);
        enchantment.add("levels", levels);
        enchantments.add(enchantment);
        predicates.add("minecraft:enchantments", enchantments);
        predicate.add("predicates", predicates);
        matchTool.add("predicate", predicate);
        conditions.add(matchTool);
        entry.add("conditions", conditions);
        return entry;
    }

    private static JsonObject createAshDrop() {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", "doomsday_essentials:ash");

        JsonArray functions = new JsonArray();

        JsonObject setCount = new JsonObject();
        setCount.addProperty("function", "minecraft:set_count");
        setCount.addProperty("add", false);
        JsonObject count = new JsonObject();
        count.addProperty("type", "minecraft:uniform");
        count.addProperty("min", 2.0F);
        count.addProperty("max", 3.0F);
        setCount.add("count", count);
        functions.add(setCount);

        JsonObject explosionDecay = new JsonObject();
        explosionDecay.addProperty("function", "minecraft:explosion_decay");
        functions.add(explosionDecay);

        entry.add("functions", functions);
        return entry;
    }
}
