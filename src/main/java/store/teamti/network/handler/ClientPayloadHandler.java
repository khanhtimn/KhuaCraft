package store.teamti.network.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import store.teamti.KhuaCraft;
import store.teamti.network.impl.UnoReverseAnimationPayload;
import store.teamti.particle.ModParticles;
import store.teamti.sound.ModSounds;

public class ClientPayloadHandler {

    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleUnoReverseAnimation(final UnoReverseAnimationPayload packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel level = mc.level;

                if (level != null) {
                    Entity entity = mc.level.getEntity(packet.entityId());

                    if (entity != null) {
                        mc.particleEngine.createTrackingEmitter(entity, ModParticles.UNO_REVERSE_PARTICLE.get(), 30);
                        entity.level().playLocalSound(
                                entity.getX(), entity.getY(), entity.getZ(),
                                ModSounds.UNO_REVERSE.value(),
                                entity.getSoundSource(),
                                1.0F, 1.0F, false
                        );

                        if (entity == mc.player) {
                            mc.gameRenderer.displayItemActivation(packet.itemStack());
                        }
                    }
                }
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable(KhuaCraft.MOD_ID + ".networking.uno_reverse_animation.failed", e.getMessage()));
            return null;
        });
    }
}
