package dev.khanhtimn.khuacraft.item;

import dev.khanhtimn.khuacraft.KhuaCraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KhuaCraft.MODID);

    public static final DeferredItem<Item> UNO_REVERSE = ITEMS.registerSimpleItem("uno_reverse",
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).setNoRepair());

    public static final DeferredItem<Item> CREATIVE_TAB_ICON = ITEMS.registerSimpleItem("creative_tab_icon",
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).setNoRepair());
}
