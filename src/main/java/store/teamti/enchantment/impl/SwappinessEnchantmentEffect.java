package store.teamti.enchantment.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public record SwappinessEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<SwappinessEnchantmentEffect> CODEC = MapCodec.unit(SwappinessEnchantmentEffect::new);

    @Override
    public void apply(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3
    ) {
        if (!(entity instanceof LivingEntity attacker) || !(enchantedItemInUse.owner() instanceof LivingEntity defender)) {
            return;
        }

        ItemStack defenderItem = defender.getMainHandItem();
        EquipmentSlot slot = EquipmentSlot.MAINHAND;

        defender.setItemSlot(slot, attacker.getMainHandItem().copy());
        if (defender instanceof Mob mob) {
            mob.setDropChance(slot, 1.0F);
        }
        if (!defenderItem.isEmpty()) {
            attacker.setItemSlot(slot, defenderItem.copy());
        } else {
            attacker.getMainHandItem().setCount(0);
        }
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
