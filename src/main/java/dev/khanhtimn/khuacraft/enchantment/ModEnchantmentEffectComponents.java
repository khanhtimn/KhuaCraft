package dev.khanhtimn.khuacraft.enchantment;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.enchantment.effect.ScaleEquipmentEffect;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModEnchantmentEffectComponents {

    public static final DeferredRegister.DataComponents ENCHANTMENT_EFFECT_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, KhuaCraft.MODID);

    public static final Supplier<DataComponentType<List<ConditionalEffect<ScaleEquipmentEffect>>>> EQUIPMENT_SCALE =
            ENCHANTMENT_EFFECT_COMPONENTS.registerComponentType(
                    "equipment_scale",
                    builder -> builder.persistent(
                            ConditionalEffect.codec(ScaleEquipmentEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf()
                    )
            );
}


