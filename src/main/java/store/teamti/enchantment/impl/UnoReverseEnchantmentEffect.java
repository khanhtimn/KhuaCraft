package store.teamti.enchantment.impl;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import store.teamti.enchantment.ModEnchantments;
import store.teamti.network.impl.payload.ClientboundUnoReverseAnimationPacket;

public class UnoReverseEnchantmentEffect {
    public static void handleUnoReverse(final LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        LivingEntity defender = event.getEntity();
        ItemStack activeItem = defender.getUseItem();
        int enchantmentLevel = activeItem.getEnchantmentLevel(
                defender.registryAccess().holderOrThrow(ModEnchantments.UNO_REVERSE));
        if (enchantmentLevel <= 0) {
            return;
        }
        if (activeItem.getItem() instanceof ShieldItem && defender.isBlocking()) {
            double chance = enchantmentLevel * 0.1;
            if (defender.getRandom().nextDouble() < chance) {
                event.setCanceled(true);
                reflectDamageAndKnockback(defender, attacker, event.getAmount());
            }
        }
    }

    private static void reflectDamageAndKnockback(LivingEntity defender, LivingEntity attacker, float damage) {
        DamageSources damageSources = defender.damageSources();
        attacker.hurt(damageSources.generic(), damage);

        double knockbackStrength = 0.5;
        Vec3 knockbackVector = attacker.position().subtract(defender.position()).normalize().scale(knockbackStrength);
        attacker.push(knockbackVector.x, 0.1, knockbackVector.z);
        if (attacker instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ClientboundUnoReverseAnimationPacket());
        }
    }
}
