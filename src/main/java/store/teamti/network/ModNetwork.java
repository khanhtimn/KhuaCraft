package store.teamti.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import store.teamti.KhuaCraft;
import store.teamti.network.handler.ClientPayloadHandler;
import store.teamti.network.impl.UnoReverseAnimationPayload;

@EventBusSubscriber(modid = KhuaCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(KhuaCraft.MOD_ID);

        registrar.playToClient(
                UnoReverseAnimationPayload.TYPE,
                UnoReverseAnimationPayload.CODEC,
                ClientPayloadHandler.getInstance()::handleUnoReverseAnimation
        );

    }

}
