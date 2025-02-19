//package store.teamti.network.impl.payload;
//
//import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.sounds.SimpleSoundInstance;
//import net.minecraft.client.resources.sounds.SoundInstance;
//import net.minecraft.core.Holder;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import org.jetbrains.annotations.NotNull;
//import store.teamti.network.impl.ClientboundPacket;
//import store.teamti.network.impl.ModPacketPayload;
//
//import java.util.Map;
//
//public record PlaySoundPayload(Holder<SoundEvent> soundEvent, float pitch, float volume) implements ClientboundPacket {
//
//    private static final Map<Holder<SoundEvent>, SoundInstance> activeClientSounds = new Object2ObjectOpenHashMap<>();
//
//    public static final Type<PlaySoundPayload> TYPE = ModPacketPayload.createType("play_sound");
//
//    public static final StreamCodec<RegistryFriendlyByteBuf, PlaySoundPayload> CODEC =
//            StreamCodec.composite(
//                    ByteBufCodecs.holder(Registries.SOUND_EVENT, SoundEvent.DIRECT_STREAM_CODEC), PlaySoundPayload::soundEvent,
//                    ByteBufCodecs.FLOAT, PlaySoundPayload::pitch,
//                    ByteBufCodecs.FLOAT, PlaySoundPayload::volume,
//                    PlaySoundPayload::new
//            );
//
//    @Override
//    public @NotNull Type<PlaySoundPayload> type() {
//        return TYPE;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void handleOnClient(Player player) {
//        SoundInstance currentSound = activeClientSounds.get(soundEvent);
//
//        if (currentSound != null && Minecraft.getInstance().getSoundManager().isActive(currentSound)) {
//            return;
//        }
//
//        SoundInstance instance = SimpleSoundInstance.forUI(soundEvent.value(), pitch, volume);
//
//        activeClientSounds.put(soundEvent, instance);
//
//        Minecraft.getInstance().getSoundManager().play(instance);
//    }
//}

package store.teamti.network.impl.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import store.teamti.network.ModNetwork;
import store.teamti.network.impl.ClientboundPacket;
import store.teamti.sound.ModSoundHandler;
import store.teamti.sound.impl.PlayerSound;

public record ClientboundPlaySoundPacket(PlayerSound.SoundType soundType) implements ClientboundPacket {

    public static final Type<ClientboundPlaySoundPacket> TYPE = ModNetwork.ModPacketPayload.createType("to_play");

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundPlaySoundPacket> CODEC =
            StreamCodec.composite(
                    PlayerSound.SoundType.CODEC, ClientboundPlaySoundPacket::soundType,
                    ClientboundPlaySoundPacket::new
            );

    @Override
    public @NotNull Type<ClientboundPlaySoundPacket> type() {
        return TYPE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleOnClient(Player player) {
        ModSoundHandler.startSound(player.level(), player.getUUID(), soundType);
    }
}
