package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.fluid.ModFluids;
import net.fallen.doomsday_essentials.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DoomsdayEssentials.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.IRIDIUM_INGOT.get());
        basicItem(ModItems.RAW_IRIDIUM.get());
        basicItem(ModItems.ENRICHED_IRIDIUM.get());

        basicItem(ModItems.PLATINUM_INGOT.get());
        basicItem(ModItems.IRIDIUM_PLATINUM.get());
        basicItem(ModItems.RAW_PLATINUM.get());

        basicItem(ModFluids.SALT_WATER_BUCKET.get());

        withExistingParent(ModItems.CREATIVE_TERRAFORMER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", mcLoc("item/ender_eye"));
        withExistingParent(ModItems.ASH.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", mcLoc("item/gunpowder"));
    }
}
