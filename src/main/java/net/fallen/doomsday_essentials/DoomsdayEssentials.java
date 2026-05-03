package net.fallen.doomsday_essentials;

import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.fluid.BaseFluidType;
import net.fallen.doomsday_essentials.fluid.ModFluidTypes;
import net.fallen.doomsday_essentials.fluid.ModFluids;
import net.fallen.doomsday_essentials.item.ModCreativeModeTab;
import net.fallen.doomsday_essentials.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.fallen.doomsday_essentials.DoomsdayEssentials.MOD_ID;

@Mod(MOD_ID)
public class DoomsdayEssentials {
    public static final String MOD_ID = "doomsday_essentials";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String SALT_PROCESS_TICKS_TAG = "doomsday_salt_process_ticks";
    private static final String SALT_PROCESS_DELAY_TAG = "doomsday_salt_process_delay";
    private static final int SALT_PROCESS_DELAY_MIN_TICKS = 10; // 0.5s
    private static final int SALT_PROCESS_DELAY_MAX_TICKS = 30; // 1.5s

    public DoomsdayEssentials(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        event.enqueueWork(ModFluids::registerCauldronInteractions);

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));


    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity) || itemEntity.level().isClientSide) {
            return;
        }

        ItemStack stack = itemEntity.getItem();
        if (!stack.is(ModBlocks.SALT_CRUSTED_DIRT.asItem())) {
            return;
        }

        net.minecraft.core.BlockPos cauldronPos = itemEntity.blockPosition();
        BlockState cauldronState = itemEntity.level().getBlockState(cauldronPos);
        if (!cauldronState.is(Blocks.WATER_CAULDRON)) {
            CompoundTag data = itemEntity.getPersistentData();
            data.remove(SALT_PROCESS_TICKS_TAG);
            data.remove(SALT_PROCESS_DELAY_TAG);
            return;
        }

        CompoundTag data = itemEntity.getPersistentData();
        int delay = data.getInt(SALT_PROCESS_DELAY_TAG);
        if (delay <= 0) {
            delay = SALT_PROCESS_DELAY_MIN_TICKS + itemEntity.level().random.nextInt(SALT_PROCESS_DELAY_MAX_TICKS - SALT_PROCESS_DELAY_MIN_TICKS + 1);
            data.putInt(SALT_PROCESS_DELAY_TAG, delay);
            data.putInt(SALT_PROCESS_TICKS_TAG, 0);
            return;
        }

        int processTicks = data.getInt(SALT_PROCESS_TICKS_TAG) + 1;
        data.putInt(SALT_PROCESS_TICKS_TAG, processTicks);
        if (processTicks < delay) {
            return;
        }

        int level = cauldronState.getValue(LayeredCauldronBlock.LEVEL);
        itemEntity.level().setBlockAndUpdate(cauldronPos,
                ModFluids.SALT_WATER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, level));
        data.remove(SALT_PROCESS_TICKS_TAG);
        data.remove(SALT_PROCESS_DELAY_TAG);

        stack.shrink(1);
        if (stack.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(stack);
        }

        Block.popResource(itemEntity.level(), cauldronPos, new ItemStack(Blocks.DIRT));
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_SALT_WATER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_SALT_WATER.get(), RenderType.translucent());
            });
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidTypes.SALT_WATER_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.SALT_WATER_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register((state, level, pos, tintIndex) -> tintIndex == 0 ? 0x326B53 : -1,
                    ModFluids.SALT_WATER_CAULDRON.get());
        }
    }
}


