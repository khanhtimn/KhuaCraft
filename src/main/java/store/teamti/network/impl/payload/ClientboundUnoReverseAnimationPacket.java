package store.teamti.network.impl.payload;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import store.teamti.item.ModItems;
import store.teamti.network.ModNetwork;
import store.teamti.network.impl.ClientboundPacket;
import store.teamti.particle.ModParticles;
import store.teamti.sound.ModSounds;

public record ClientboundUnoReverseAnimationPacket() implements ClientboundPacket {

    public static final Type<ClientboundUnoReverseAnimationPacket> TYPE = ModNetwork.ModPacketPayload.createType("uno_reverse_animation");

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUnoReverseAnimationPacket> CODEC = StreamCodec.unit(new ClientboundUnoReverseAnimationPacket());

    @Override
    public @NotNull Type<ClientboundUnoReverseAnimationPacket> type() {
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
