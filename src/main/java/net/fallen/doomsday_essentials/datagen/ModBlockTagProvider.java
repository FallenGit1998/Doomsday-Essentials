package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DoomsdayEssentials.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.IRIDIUM_BLOCK.get())
                .add(ModBlocks.PLATINUM_BLOCK.get())
                .add(ModBlocks.IRIDIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_IRIDIUM_ORE.get())
                .add(ModBlocks.PLATINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_PLATINUM_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.CHARRED_WOOD.get())
                .add(ModBlocks.WITHERED_LEAVES.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.SALT_CRUSTED_DIRT.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.IRIDIUM_BLOCK.get())
                .add(ModBlocks.IRIDIUM_BLOCK.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.IRIDIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_IRIDIUM_ORE.get())
                .add(ModBlocks.PLATINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_PLATINUM_ORE.get());

        this.tag(BlockTags.LOGS)
                .add(ModBlocks.CHARRED_WOOD.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHARRED_WOOD.get());
        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.WITHERED_LEAVES.get());
        this.tag(BlockTags.DIRT)
                .add(ModBlocks.SALT_CRUSTED_DIRT.get());



    }
}
