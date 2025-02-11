package store.teamti.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;
import store.teamti.enchantment.impl.ScaleEnchantmentEffect;
import store.teamti.enchantment.impl.SwappinessEnchantmentEffect;

import java.util.function.Supplier;

public class ModEnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, KhuaCraft.MOD_ID);

    public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LOCATION_BASED_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, KhuaCraft.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SWAPPINESS =
            ENTITY_ENCHANTMENT_EFFECTS.register("swappiness", () -> SwappinessEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentLocationBasedEffect>> SCALE =
            LOCATION_BASED_ENCHANTMENT_EFFECTS.register("scale", () -> ScaleEnchantmentEffect.CODEC);

}
