package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.fluid.ModFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DoomsdayEssentials.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.IRIDIUM_BLOCK);
        blockWithItem(ModBlocks.PLATINUM_BLOCK);

        blockWithItem(ModBlocks.IRIDIUM_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_IRIDIUM_ORE);
        blockWithItem(ModBlocks.PLATINUM_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_PLATINUM_ORE);
        axisBlock((RotatedPillarBlock) ModBlocks.CHARRED_WOOD.get(),
                modLoc("block/charred_log"),
                modLoc("block/charred_log_top"));
        blockItem(ModBlocks.CHARRED_WOOD);

        simpleBlockWithItem(ModBlocks.WITHERED_LEAVES.get(), cubeAll(ModBlocks.WITHERED_LEAVES.get()));
        simpleBlockWithItem(ModBlocks.SALT_CRUSTED_DIRT.get(), cubeAll(ModBlocks.SALT_CRUSTED_DIRT.get()));
        getVariantBuilder(ModFluids.SALT_WATER_CAULDRON.get())
                .partialState().with(LayeredCauldronBlock.LEVEL, 1)
                .modelForState().modelFile(models().getExistingFile(mcLoc("block/water_cauldron_level1"))).addModel()
                .partialState().with(LayeredCauldronBlock.LEVEL, 2)
                .modelForState().modelFile(models().getExistingFile(mcLoc("block/water_cauldron_level2"))).addModel()
                .partialState().with(LayeredCauldronBlock.LEVEL, 3)
                .modelForState().modelFile(models().getExistingFile(mcLoc("block/water_cauldron_full"))).addModel();
    }

    private void blockWithItem(DeferredBlock<Block> d) {
        simpleBlockWithItem(d.get(), cubeAll(d.get()));
    }

    private void blockItem(DeferredBlock<Block> block) {
        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/" + block.getId().getPath())));
    }
}
