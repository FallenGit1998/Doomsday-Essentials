package net.fallen.doomsday_essentials.fluid;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.item.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, DoomsdayEssentials.MOD_ID);

    public static final Supplier<FlowingFluid> SOURCE_SALT_WATER = FLUIDS.register("source_salt_water",
            () -> new BaseFlowingFluid.Source(ModFluids.SALT_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_SALT_WATER = FLUIDS.register("flowing_salt_water",
            () -> new BaseFlowingFluid.Flowing(ModFluids.SALT_WATER_PROPERTIES));

    public static final DeferredBlock<LiquidBlock> SALT_WATER_BLOCK = ModBlocks.BLOCKS.register("salt_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_SALT_WATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> SALT_WATER_BUCKET = ModItems.ITEMS.registerItem("salt_water_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_SALT_WATER.get(), properties.stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final CauldronInteraction.InteractionMap SALT_WATER_CAULDRON_INTERACTIONS = CauldronInteraction.newInteractionMap("salt_water");
    public static final DeferredBlock<LayeredCauldronBlock> SALT_WATER_CAULDRON = ModBlocks.BLOCKS.register("salt_water_cauldron",
            () -> new LayeredCauldronBlock(Biome.Precipitation.RAIN, SALT_WATER_CAULDRON_INTERACTIONS,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER_CAULDRON)));

    public static final BaseFlowingFluid.Properties SALT_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.SALT_WATER_FLUID_TYPE, SOURCE_SALT_WATER, FLOWING_SALT_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.SALT_WATER_BLOCK).bucket(ModFluids.SALT_WATER_BUCKET);

    public static void registerCauldronInteractions() {
        CauldronInteraction.addDefaultInteractions(SALT_WATER_CAULDRON_INTERACTIONS.map());

        CauldronInteraction.EMPTY.map().put(ModFluids.SALT_WATER_BUCKET.get(),
                (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(
                        level, pos, player, hand, stack,
                        ModFluids.SALT_WATER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));

        SALT_WATER_CAULDRON_INTERACTIONS.map().put(Items.BUCKET,
                (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(
                        state, level, pos, player, hand, stack,
                        ModFluids.SALT_WATER_BUCKET.toStack(),
                        cauldronState -> cauldronState.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL));
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
