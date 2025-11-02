package dev.khanhtimn.khuacraft.enchantment.effect;

import com.mojang.serialization.MapCodec;
import dev.khanhtimn.khuacraft.network.payload.StartPlayerSoundPayload;
import dev.khanhtimn.khuacraft.sound.sounds.PlayerSound;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public record InnerConscienceEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<InnerConscienceEnchantmentEffect> CODEC =
            MapCodec.unit(InnerConscienceEnchantmentEffect::new);

    @Override
    public void apply(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3
    ) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        StartPlayerSoundPayload packet = new StartPlayerSoundPayload(
                player.getUUID(),
                PlayerSound.SoundType.INNER_CONSCIENCE
        );
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}






