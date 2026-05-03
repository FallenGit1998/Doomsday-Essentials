package net.fallen.doomsday_essentials.item;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.block.ModBlocks;
import net.fallen.doomsday_essentials.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DoomsdayEssentials.MOD_ID);

    public static final Supplier<CreativeModeTab> DOOMSDAY_ESSENTIALS_TAB =
            CREATIVE_MODE_TABS.register("doomsday_essentials_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.doomsday_essentials.doomsday_essentials_tab"))
                    .icon(() -> new ItemStack(ModItems.IRIDIUM_INGOT.get()))
                    .displayItems((itemDisplayParameters, output) ->  {
                        output.accept(ModItems.IRIDIUM_INGOT);
                        output.accept(ModBlocks.IRIDIUM_ORE);
                        output.accept(ModBlocks.DEEPSLATE_IRIDIUM_ORE);
                        output.accept(ModItems.RAW_IRIDIUM);
                        output.accept(ModBlocks.IRIDIUM_BLOCK);
                        output.accept(ModItems.PLATINUM_INGOT);
                        output.accept(ModBlocks.PLATINUM_ORE);
                        output.accept(ModBlocks.DEEPSLATE_PLATINUM_ORE);
                        output.accept(ModBlocks.CHARRED_WOOD);
                        output.accept(ModBlocks.WITHERED_LEAVES);
                        output.accept(ModBlocks.SALT_CRUSTED_DIRT);
                        output.accept(ModItems.RAW_PLATINUM);
                        output.accept(ModBlocks.PLATINUM_BLOCK);
                        output.accept(ModItems.ENRICHED_IRIDIUM);
                        output.accept(ModItems.IRIDIUM_PLATINUM);
                        output.accept(ModItems.ASH);
                        output.accept(ModItems.CREATIVE_TERRAFORMER);
                        output.accept(ModFluids.SALT_WATER_BUCKET);

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
