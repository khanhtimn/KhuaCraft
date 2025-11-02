package dev.khanhtimn.khuacraft.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.khanhtimn.khuacraft.data.ModDataAttachment;
import dev.khanhtimn.khuacraft.data.attachment.ScaleModifierAttachment;
import dev.khanhtimn.khuacraft.enchantment.ModEnchantmentEffectComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ScaleEnchantmentEffect(
        ResourceLocation scaleType,
        float baseMultiplier,
        float perLevelMultiplier
) {
    public static final Codec<ScaleEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("scale_type").forGetter(ScaleEnchantmentEffect::scaleType),
                    Codec.FLOAT.fieldOf("base_multiplier").forGetter(ScaleEnchantmentEffect::baseMultiplier),
                    Codec.FLOAT.optionalFieldOf("per_level_multiplier", 0.0F).forGetter(ScaleEnchantmentEffect::perLevelMultiplier)
            ).apply(instance, ScaleEnchantmentEffect::new)
    );

    public float calculateMultiplier(int enchantmentLevel) {
        return baseMultiplier + (perLevelMultiplier * enchantmentLevel);
    }

    public static class Listener {

        public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level().isClientSide()) return;

            EquipmentSlot slot = event.getSlot();
            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();

            // Only handle armor slots
            if (!isArmorSlot(slot)) return;

            ScaleModifierAttachment modifiers = entity.getData(ModDataAttachment.SCALE_MODIFIERS);
            Set<ResourceLocation> affectedScaleTypes = new HashSet<>();

            // Remove modifiers from old equipment
            if (!from.isEmpty()) {
                processEquipmentEffects(from, slot, modifiers, affectedScaleTypes, true);
            }

            // Apply modifiers from new equipment
            if (!to.isEmpty()) {
                processEquipmentEffects(to, slot, modifiers, affectedScaleTypes, false);
            }

            // Recalculate all affected scale types
            for (ResourceLocation scaleType : affectedScaleTypes) {
                applyEffectiveScale(entity, scaleType, modifiers);
            }
        }

        private static void processEquipmentEffects(ItemStack stack, EquipmentSlot slot,
                                                    ScaleModifierAttachment modifiers,
                                                    Set<ResourceLocation> affectedScaleTypes,
                                                    boolean remove) {
            // Iterate through all enchantments on the item
            EnchantmentHelper.runIterationOnItem(stack, (enchantmentHolder, level) -> {
                Enchantment enchantment = enchantmentHolder.value();

                // Check if this enchantment has our custom EQUIPMENT_SCALE component
                List<ConditionalEffect<ScaleEnchantmentEffect>> effects = enchantment.effects().get(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get());

                if (effects != null && !effects.isEmpty()) {
                    for (ConditionalEffect<ScaleEnchantmentEffect> conditionalEffect : effects) {
                        // Unwrap the effect from ConditionalEffect
                        ScaleEnchantmentEffect effect = conditionalEffect.effect();

                        ResourceLocation scaleType = effect.scaleType();
                        affectedScaleTypes.add(scaleType);

                        if (remove) {
                            // Remove modifier for this slot
                            modifiers.removeEnchantmentModifier(scaleType, slot);
                        } else {
                            // Calculate and apply multiplier
                            float multiplier = effect.calculateMultiplier(level);
                            modifiers.setEnchantmentModifier(scaleType, slot, multiplier);
                        }
                    }
                }
            });
        }

        private static boolean isArmorSlot(EquipmentSlot slot) {
            return slot == EquipmentSlot.HEAD ||
                    slot == EquipmentSlot.CHEST ||
                    slot == EquipmentSlot.LEGS ||
                    slot == EquipmentSlot.FEET;
        }

        private static void applyEffectiveScale(LivingEntity entity, ResourceLocation scaleTypeId,
                                                ScaleModifierAttachment modifiers) {
            ScaleType scaleType = ScaleRegistries.SCALE_TYPES.get(scaleTypeId);
            if (scaleType == null) return;

            ScaleData scaleData = scaleType.getScaleData(entity);

            // Initialize base scale to 1.0 if not set
            if (!modifiers.baseScales().containsKey(scaleTypeId)) {
                modifiers.setBaseScale(scaleTypeId, 1.0F);
            }

            // Calculate effective scale: base × all modifiers
            float effectiveScale = modifiers.calculateEffectiveScale(scaleTypeId);

            scaleData.setTargetScale(effectiveScale);
            scaleData.markForSync(true);
        }
    }
}

