package store.teamti.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import store.teamti.KhuaCraft;
import store.teamti.network.impl.ClientboundPacket;
import store.teamti.network.impl.ServerboundPacket;
import store.teamti.network.impl.payload.ClientboundPlaySoundPacket;
import store.teamti.network.impl.payload.ClientboundResetSoundPacket;
import store.teamti.network.impl.payload.ClientboundUnoReverseAnimationPacket;

public class ModNetwork {

    public static void init(final RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar(KhuaCraft.MOD_ID);

        clientbound(registrar, ClientboundUnoReverseAnimationPacket.TYPE, ClientboundUnoReverseAnimationPacket.CODEC);
        clientbound(registrar, ClientboundPlaySoundPacket.TYPE, ClientboundPlaySoundPacket.CODEC);
        clientbound(registrar, ClientboundResetSoundPacket.TYPE, ClientboundResetSoundPacket.CODEC);
    }

    private static <T extends ClientboundPacket> void clientbound(PayloadRegistrar registrar,
                                                                                    CustomPacketPayload.Type<T> type,
                                                                                    StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToClient(type, codec, ClientboundPacket::handleOnClient);
    }

    private static <T extends ServerboundPacket> void serverbound(PayloadRegistrar registrar,
                                                                  CustomPacketPayload.Type<T> type,
                                                                  StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToServer(type, codec, ServerboundPacket::handleOnServer);
    }

    public interface ModPacketPayload extends CustomPacketPayload {
        static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> createType(String name) {
            return new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, name));
        }
    }
}
