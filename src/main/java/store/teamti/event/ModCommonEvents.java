package store.teamti.event;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import store.teamti.KhuaCraft;
import store.teamti.enchantment.impl.UnoReverseEnchantmentEffect;
import store.teamti.network.impl.payload.ClientboundResetSoundPacket;
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

//    @SubscribeEvent
//    public static void onPlayerLogoutEvent(final PlayerEvent.PlayerLoggedOutEvent event) {
//        Player player = event.getEntity();
//        PacketDistributor.sendToAllPlayers(new ResetSoundPayload(player.getUUID()));
//    }
//
//
//    @SubscribeEvent
//    public static void onPlayerDimChangedEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
//        ServerPlayer player = (ServerPlayer) event.getEntity();
//        PacketDistributor.sendToAllPlayers(new ResetSoundPayload(player.getUUID()));
//    }

    @SubscribeEvent
    public static void respawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        PacketDistributor.sendToAllPlayers(new ClientboundResetSoundPacket(player.getUUID()));
    }
}
