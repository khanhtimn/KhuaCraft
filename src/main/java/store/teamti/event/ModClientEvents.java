package store.teamti.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import store.teamti.KhuaCraft;
import store.teamti.particle.ModParticles;
import store.teamti.particle.impl.UnoReverseParticle;
import store.teamti.sound.ModSoundHandler;
import store.teamti.util.ClientUtils;

@EventBusSubscriber(modid = KhuaCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    public static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void registerParticle(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.UNO_REVERSE_PARTICLE.get(), UnoReverseParticle.Provider::new);
    }

    public static void onCloneRespawn(final ClientPlayerNetworkEvent.Clone event) {
        if (event.getOldPlayer().level() != event.getNewPlayer().level()) {
            ModSoundHandler.clearPlayerSounds();
        }
    }

    public static void onLogout(final ClientPlayerNetworkEvent.LoggingOut event) {
        ModSoundHandler.clearPlayerSounds();
    }

    public static void onTick(final ClientTickEvent.Pre event) {
        if (mc.level != null && mc.player != null) {
            ModSoundHandler.restartSounds();
        }
    }

    public static void showWarningOnJoin(final PlayerTickEvent.Pre event) {
        if (KhuaCraft.displayPreviewWarning) {
            KhuaCraft.displayPreviewWarning = false;
            event.getEntity().sendSystemMessage(
                    Component.translatable(KhuaCraft.MOD_ID + ".preview").append(
                            ClientUtils.getLinkText(KhuaCraft.ISSUE_LINK)
                    )
            );
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(ModClientEvents::showWarningOnJoin);
        NeoForge.EVENT_BUS.addListener(ModClientEvents::onTick);
        NeoForge.EVENT_BUS.addListener(ModClientEvents::onLogout);
        NeoForge.EVENT_BUS.addListener(ModClientEvents::onCloneRespawn);
    }

}
