package net.fallen.doomsday_essentials.datagen;

import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.fluid.ModFluids;
import net.fallen.doomsday_essentials.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.IRIDIUM_BLOCK.get());
        dropSelf(ModBlocks.PLATINUM_BLOCK.get());

        this.add(ModBlocks.IRIDIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.IRIDIUM_ORE.get(), ModItems.RAW_IRIDIUM.get()));
        this.add(ModBlocks.DEEPSLATE_IRIDIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_IRIDIUM_ORE.get(), ModItems.RAW_IRIDIUM.get()));
        this.add(ModBlocks.PLATINUM_ORE.get(),
                block -> createOreDrop(ModBlocks.PLATINUM_ORE.get(), ModItems.RAW_PLATINUM.get()));
        this.add(ModBlocks.DEEPSLATE_PLATINUM_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_PLATINUM_ORE.get(), ModItems.RAW_PLATINUM.get()));
        this.add(ModBlocks.CHARRED_WOOD.get(),
                block -> createSilkTouchDispatchTable(
                        ModBlocks.CHARRED_WOOD.get(),
                        applyExplosionDecay(ModBlocks.CHARRED_WOOD.get(),
                                LootItem.lootTableItem(ModItems.ASH.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        )
                ));
        this.add(ModBlocks.WITHERED_LEAVES.get(),
                block -> LootTable.lootTable().withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionCondition(
                                        ModBlocks.WITHERED_LEAVES.get(),
                                        LootItem.lootTableItem(ModBlocks.WITHERED_LEAVES.get())
                                                .when(AnyOfCondition.anyOf(HAS_SHEARS, hasSilkTouch()))
                                ))
                ));
        dropSelf(ModBlocks.SALT_CRUSTED_DIRT.get());
        this.add(ModFluids.SALT_WATER_CAULDRON.get(), noDrop());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
