package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> IRIDIUM_SMELTABLES = List.of(ModItems.RAW_IRIDIUM,
                ModBlocks.IRIDIUM_ORE, ModBlocks.DEEPSLATE_IRIDIUM_ORE);
        List<ItemLike> PLATINUM_SMELTABLES = List.of(ModItems.RAW_PLATINUM,
                ModBlocks.PLATINUM_ORE, ModBlocks.DEEPSLATE_PLATINUM_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.IRIDIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.IRIDIUM_INGOT.get())
                .unlockedBy("has_ingot_iridium", has(ModItems.IRIDIUM_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PLATINUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.PLATINUM_INGOT.get())
                .unlockedBy("has_ingot_platinum", has(ModItems.PLATINUM_INGOT.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRIDIUM_INGOT.get(), 9)
                .requires(ModBlocks.IRIDIUM_BLOCK.get())
                .unlockedBy("has_iridium_block", has(ModBlocks.IRIDIUM_BLOCK.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), 9)
                .requires(ModBlocks.PLATINUM_BLOCK.get())
                .unlockedBy("has_platinum_block", has(ModBlocks.PLATINUM_BLOCK.get())).save(pRecipeOutput);

        oreSmelting(pRecipeOutput, IRIDIUM_SMELTABLES, RecipeCategory.MISC, ModItems.IRIDIUM_INGOT.get(), 0.25f, 200, "iridium");
        oreBlasting(pRecipeOutput, IRIDIUM_SMELTABLES, RecipeCategory.MISC, ModItems.IRIDIUM_INGOT.get(), 0.25f, 200, "iridium");

        oreSmelting(pRecipeOutput, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), 0.25f, 200, "platinum");
        oreBlasting(pRecipeOutput, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), 0.25f, 200, "platinum");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CREATIVE_TERRAFORMER.get())
                .pattern("IPI")
                .pattern("PEP")
                .pattern("IPI")
                .define('I', ModItems.IRIDIUM_INGOT.get())
                .define('P', ModItems.PLATINUM_INGOT.get())
                .define('E', ModItems.ENRICHED_IRIDIUM.get())
                .unlockedBy("has_enriched_iridium", has(ModItems.ENRICHED_IRIDIUM.get()))
                .save(pRecipeOutput);

    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }
}
