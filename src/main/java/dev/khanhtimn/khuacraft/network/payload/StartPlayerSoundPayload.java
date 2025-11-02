package dev.khanhtimn.khuacraft.network.payload;

import dev.khanhtimn.khuacraft.network.ClientBoundPacket;
import dev.khanhtimn.khuacraft.network.ModPackets;
import dev.khanhtimn.khuacraft.sound.ModSoundHandler;
import dev.khanhtimn.khuacraft.sound.sounds.PlayerSound;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Packet sent from server to client to start a PlayerSound
 */
public record StartPlayerSoundPayload(UUID playerUUID, PlayerSound.SoundType soundType) implements ClientBoundPacket {

    public static final Type<StartPlayerSoundPayload> TYPE = ModPackets.ModPacketPayload.createType("start_player_sound");

    public static final StreamCodec<RegistryFriendlyByteBuf, StartPlayerSoundPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.map(UUID::fromString, UUID::toString),
            StartPlayerSoundPayload::playerUUID,
            ByteBufCodecs.INT.map(i -> PlayerSound.SoundType.values()[i], Enum::ordinal),
            StartPlayerSoundPayload::soundType,
            StartPlayerSoundPayload::new
    );

    @Override
    public void handleOnClient(Player player) {
        if (player != null && player.getUUID().equals(playerUUID)) {
            ModSoundHandler.startSound(player.level(), playerUUID, soundType);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

