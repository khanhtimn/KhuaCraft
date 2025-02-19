package store.teamti.enchantment.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import store.teamti.network.impl.payload.ClientboundPlaySoundPacket;
import store.teamti.sound.impl.PlayerSound;

public record InnerConscienceEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<InnerConscienceEnchantmentEffect> CODEC = MapCodec.unit(InnerConscienceEnchantmentEffect::new);

    @Override
    public void apply(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3
    ) {
        if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ClientboundPlaySoundPacket(PlayerSound.SoundType.INNER_CONSCIENCE));
        }
    }

    @Override
    public @NotNull MapCodec<InnerConscienceEnchantmentEffect> codec() {
        return CODEC;
    }
}
