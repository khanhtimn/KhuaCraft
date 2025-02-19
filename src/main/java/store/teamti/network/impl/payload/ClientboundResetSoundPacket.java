package store.teamti.network.impl.payload;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import store.teamti.network.ModNetwork;
import store.teamti.network.impl.ClientboundPacket;
import store.teamti.sound.ModSoundHandler;

import java.util.UUID;

public record ClientboundResetSoundPacket(UUID uuid) implements ClientboundPacket {

    public static final Type<ClientboundResetSoundPacket> TYPE = ModNetwork.ModPacketPayload.createType("reset_sound");

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundResetSoundPacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundResetSoundPacket::uuid,
            ClientboundResetSoundPacket::new
    );

    @Override
    public @NotNull Type<ClientboundResetSoundPacket> type() {
        return TYPE;
    }

    @Override
    public void handleOnClient(Player player) {
        ModSoundHandler.clearPlayerSounds(player.getUUID());
    }
}
