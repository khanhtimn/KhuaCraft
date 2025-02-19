package store.teamti.sound.impl;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import store.teamti.enchantment.ModEnchantments;
import store.teamti.sound.ModSounds;

public class InnerConscienceSound extends PlayerSound {

    public InnerConscienceSound(@NotNull Player player) {
        super(player, ModSounds.INNER_CONSCIENCE.value());
    }

    @Override
    public boolean shouldPlaySound(@NotNull Player player) {
        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
        int enchantmentLevel = itemstack.getEnchantmentLevel(player.registryAccess().holderOrThrow(ModEnchantments.INNER_CONSCIENCE_CURSE));
        return enchantmentLevel > 0;
    }
}
