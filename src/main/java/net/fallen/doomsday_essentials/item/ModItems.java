package net.fallen.doomsday_essentials.item;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.fallen.doomsday_essentials.item.custom.AshItem;
import net.fallen.doomsday_essentials.item.custom.CreativeTerraformerItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DoomsdayEssentials.MOD_ID);

    public static final DeferredItem<Item> RAW_IRIDIUM = ITEMS.registerItem("raw_iridium", Item::new, new Item.Properties());
    public static final DeferredItem<Item> IRIDIUM_INGOT = ITEMS.registerItem("iridium_ingot", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ENRICHED_IRIDIUM = ITEMS.registerItem("enriched_iridium", Item::new, new Item.Properties());

    public static final DeferredItem<Item> RAW_PLATINUM = ITEMS.registerItem("raw_platinum", Item::new, new Item.Properties());
    public static final DeferredItem<Item> PLATINUM_INGOT = ITEMS.registerItem("platinum_ingot", Item::new, new Item.Properties());
    public static final DeferredItem<Item> IRIDIUM_PLATINUM = ITEMS.registerItem("iridium_platinum", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ASH = ITEMS.registerItem("ash", AshItem::new, new Item.Properties());
    public static final DeferredItem<Item> CREATIVE_TERRAFORMER = ITEMS.registerItem("creative_terraformer", CreativeTerraformerItem::new, new Item.Properties().stacksTo(1));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
