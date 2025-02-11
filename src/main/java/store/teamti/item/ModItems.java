package store.teamti.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KhuaCraft.MOD_ID);

    public static final DeferredItem<Item> UNO_REVERSE = ITEMS.registerSimpleItem("uno_reverse",
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).setNoRepair());

    public static final DeferredItem<Item> CREATIVE_TAB_ICON = ITEMS.registerSimpleItem("creative_tab_icon",
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).setNoRepair());
}