package dev.khanhtimn.khuacraft.sound.sounds;

import dev.khanhtimn.khuacraft.enchantment.ModEnchantments;
import dev.khanhtimn.khuacraft.sound.ModSounds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InnerConscienceSound extends PlayerSound {

    public InnerConscienceSound(@NotNull Player player) {
        super(player, ModSounds.INNER_CONSCIENCE.value());
        setFade(0.05f, 0.05f);
    }

    @Override
    public boolean shouldPlaySound(@NotNull Player player) {
        ItemStack itemstack = player.getItemBySlot(EquipmentSlot.HEAD);
        int enchantmentLevel = itemstack.getEnchantmentLevel(player.registryAccess().holderOrThrow(ModEnchantments.INNER_CONSCIENCE));
        return enchantmentLevel > 0;
    }
}