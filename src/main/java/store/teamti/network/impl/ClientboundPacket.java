package store.teamti.network.impl;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import store.teamti.network.ModNetwork;

public interface ClientboundPacket extends ModNetwork.ModPacketPayload {

    default void handleOnClient(IPayloadContext context) {
        context.enqueueWork(() -> handleOnClient(context.player()));
    }

    default void handleOnClient(Player player) {
        throw new AbstractMethodError("Unimplemented method on " + getClass());
    }
}
