package dev.khanhtimn.khuacraft.network;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.network.payload.StartPlayerSoundPayload;
import dev.khanhtimn.khuacraft.network.payload.UnoReversePayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPackets {

    public static void init(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(KhuaCraft.MODID);

        clientBound(registrar, StartPlayerSoundPayload.TYPE, StartPlayerSoundPayload.CODEC);
        clientBound(registrar, UnoReversePayload.TYPE, UnoReversePayload.CODEC);
    }

    private static <T extends ClientBoundPacket> void clientBound(PayloadRegistrar registrar,
                                                                  CustomPacketPayload.Type<T> type,
                                                                  StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToClient(type, codec, ClientBoundPacket::handleOnClient);
    }

    private static <T extends ServerBoundPacket> void serverBound(PayloadRegistrar registrar,
                                                                  CustomPacketPayload.Type<T> type,
                                                                  StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToServer(type, codec, ServerBoundPacket::handleOnServer);
    }

    public interface ModPacketPayload extends CustomPacketPayload {
        static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> createType(String name) {
            return new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, name));
        }
    }
}

