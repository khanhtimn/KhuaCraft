package dev.khanhtimn.khuacraft.network.payload;

import dev.khanhtimn.khuacraft.item.ModItems;
import dev.khanhtimn.khuacraft.network.ClientBoundPacket;
import dev.khanhtimn.khuacraft.network.ModPackets;
import dev.khanhtimn.khuacraft.particle.ModParticles;
import dev.khanhtimn.khuacraft.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public record UnoReversePayload() implements ClientBoundPacket {

    public static final Type<UnoReversePayload> TYPE = ModPackets.ModPacketPayload.createType("uno_reverse_animation");

    public static final StreamCodec<RegistryFriendlyByteBuf, UnoReversePayload> CODEC = StreamCodec.unit(new UnoReversePayload());

    @Override
    public @NotNull Type<UnoReversePayload> type() {
        return TYPE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleOnClient(Player player) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != null) {
            mc.particleEngine.createTrackingEmitter(player, ModParticles.UNO_REVERSE_PARTICLE.get(), 30);
            level.playLocalSound(
                    player.getX(), player.getY(), player.getZ(),
                    ModSounds.UNO_REVERSE.value(),
                    player.getSoundSource(),
                    1.0F, 1.0F, false
            );
            mc.gameRenderer.displayItemActivation(ModItems.UNO_REVERSE.toStack());
        }
    }

}
