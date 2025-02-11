package store.teamti.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import store.teamti.KhuaCraft;
import store.teamti.enchantment.impl.UnoReverseEnchantmentEffect;
import store.teamti.potion.ModPotions;

@EventBusSubscriber(modid = KhuaCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModCommonEvents {

    @SubscribeEvent
    public static void onLivingIncomingDamageEvent(final LivingIncomingDamageEvent event) {
        UnoReverseEnchantmentEffect.handleUnoReverse(event);
    }

    @SubscribeEvent
    public static void registerBrewingRecipes(final RegisterBrewingRecipesEvent event) {
        ModPotions.registerBrewingRecipes(event);
    }
}
