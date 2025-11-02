package dev.khanhtimn.khuacraft.enchantment.effect;

import com.mojang.serialization.MapCodec;
import dev.khanhtimn.khuacraft.network.payload.UnoReversePayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public record UnoReverseEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<UnoReverseEnchantmentEffect> CODEC = MapCodec.unit(UnoReverseEnchantmentEffect::new);

    @Override
    public void apply(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3
    ) {
        LivingEntity defender = enchantedItemInUse.owner();
        if (defender == null ||
                !(enchantedItemInUse.itemStack().getItem() instanceof ShieldItem) ||
                !defender.isBlocking() ||
                defender.getLastDamageSource() == null) {
            return;
        }

        DamageSource damageSource = defender.getLastDamageSource();
        Entity attacker = damageSource.getDirectEntity();

        if (!(attacker instanceof LivingEntity livingAttacker)) {
            return;
        }

        livingAttacker.hurt(defender.damageSources().generic(), defender.lastHurt);

        double knockbackStrength = 0.5;
        Vec3 knockbackVector = attacker.position()
                .subtract(defender.position())
                .normalize()
                .scale(knockbackStrength);
        attacker.push(knockbackVector.x, 0.1, knockbackVector.z);

        if (attacker instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new UnoReversePayload()
            );
        }
    }

    @Override
    public @NotNull MapCodec<UnoReverseEnchantmentEffect> codec() {
        return CODEC;
    }
}
