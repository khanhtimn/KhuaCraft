package dev.khanhtimn.khuacraft.network;

import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ClientBoundPacket extends ModPackets.ModPacketPayload {

    default void handleOnClient(IPayloadContext context) {
        if (FMLEnvironment.dist.isClient()) {
            context.enqueueWork(() -> handleOnClient(context.player()));
        }
    }

    default void handleOnClient(Player player) {
        throw new AbstractMethodError("Unimplemented method on " + getClass());
    }
}
