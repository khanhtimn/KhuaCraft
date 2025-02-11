package store.teamti.network.impl;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import store.teamti.KhuaCraft;

public record UnoReverseAnimationPayload(int entityId, ItemStack itemStack) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UnoReverseAnimationPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "uno_reverse_animation")
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, UnoReverseAnimationPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    UnoReverseAnimationPayload::entityId,
                    ItemStack.STREAM_CODEC,
                    UnoReverseAnimationPayload::itemStack,
                    UnoReverseAnimationPayload::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
