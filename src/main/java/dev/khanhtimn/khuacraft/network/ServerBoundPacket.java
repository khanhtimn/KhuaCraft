package dev.khanhtimn.khuacraft.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ServerBoundPacket extends ModPackets.ModPacketPayload {
    default void handleOnServer(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                handleOnServer(serverPlayer);
            }
        });
    }

    void handleOnServer(ServerPlayer player);
}
