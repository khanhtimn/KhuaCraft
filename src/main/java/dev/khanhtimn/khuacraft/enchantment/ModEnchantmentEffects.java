package dev.khanhtimn.khuacraft.enchantment;

import com.mojang.serialization.MapCodec;
import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.enchantment.effect.InnerConscienceEnchantmentEffect;
import dev.khanhtimn.khuacraft.enchantment.effect.SwappinessEnchantmentEffect;
import dev.khanhtimn.khuacraft.enchantment.effect.UnoReverseEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, KhuaCraft.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SWAPPINESS =
            registerEnchantmentEffect("swappiness", SwappinessEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> INNER_CONSCIENCE =
            registerEnchantmentEffect("inner_conscience", InnerConscienceEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> UNO_REVERSE =
            registerEnchantmentEffect("uno_reverse", UnoReverseEnchantmentEffect.CODEC);

    private static Supplier<MapCodec<? extends EnchantmentEntityEffect>> registerEnchantmentEffect(String name,
                                                                                                   MapCodec<? extends EnchantmentEntityEffect> codec) {
        return ENTITY_ENCHANTMENT_EFFECTS.register(name, () -> codec);
    }
}
