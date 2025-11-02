package dev.khanhtimn.khuacraft.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.HashMap;
import java.util.Map;

/**
 * Formula: effectiveScale = baseScale × Π(enchantments) × Π(namedModifiers)
 * The effective scale is calculated and applied directly to Pehkui.
 */
public record ScaleModifierAttachment(
        Map<ResourceLocation, Float> baseScales,
        Map<ResourceLocation, Map<EquipmentSlot, Float>> enchantmentModifiers,
        Map<ResourceLocation, Map<String, Float>> namedModifiers
) {

    public static final MapCodec<ScaleModifierAttachment> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.unboundedMap(ResourceLocation.CODEC, Codec.FLOAT)
                            .optionalFieldOf("base_scales", Map.of())
                            .forGetter(ScaleModifierAttachment::baseScales),
                    Codec.unboundedMap(
                                    ResourceLocation.CODEC,
                                    Codec.unboundedMap(
                                            Codec.STRING.xmap(EquipmentSlot::byName, EquipmentSlot::getName),
                                            Codec.FLOAT
                                    )
                            ).optionalFieldOf("enchantment_modifiers", Map.of())
                            .forGetter(ScaleModifierAttachment::enchantmentModifiers),
                    Codec.unboundedMap(
                                    ResourceLocation.CODEC,
                                    Codec.unboundedMap(Codec.STRING, Codec.FLOAT)
                            ).optionalFieldOf("named_modifiers", Map.of())
                            .forGetter(ScaleModifierAttachment::namedModifiers)
            ).apply(instance, ScaleModifierAttachment::new)
    );

    public ScaleModifierAttachment() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public ScaleModifierAttachment(Map<ResourceLocation, Float> baseScales,
                                   Map<ResourceLocation, Map<EquipmentSlot, Float>> enchantmentModifiers,
                                   Map<ResourceLocation, Map<String, Float>> namedModifiers) {
        this.baseScales = new HashMap<>(baseScales);
        this.enchantmentModifiers = new HashMap<>();
        enchantmentModifiers.forEach((scaleType, slotMap) ->
                this.enchantmentModifiers.put(scaleType, new HashMap<>(slotMap))
        );
        this.namedModifiers = new HashMap<>();
        namedModifiers.forEach((scaleType, nameMap) ->
                this.namedModifiers.put(scaleType, new HashMap<>(nameMap))
        );
    }

    // ==================== BASE SCALE MANAGEMENT ====================

    /**
     * Set the base scale (unmodified by any effects).
     * This is the "true" scale that all modifiers multiply against.
     */
    public void setBaseScale(ResourceLocation scaleType, float scale) {
        baseScales.put(scaleType, scale);
    }

    /**
     * Get the base scale (default 1.0 if not set).
     */
    public float getBaseScale(ResourceLocation scaleType) {
        return baseScales.getOrDefault(scaleType, 1.0F);
    }

    // ==================== ENCHANTMENT MODIFIERS ====================

    /**
     * Set an enchantment modifier for a specific equipment slot.
     */
    public void setEnchantmentModifier(ResourceLocation scaleType, EquipmentSlot slot, float multiplier) {
        enchantmentModifiers.computeIfAbsent(scaleType, k -> new HashMap<>())
                .put(slot, multiplier);
    }

    /**
     * Remove an enchantment modifier for a specific slot.
     */
    public void removeEnchantmentModifier(ResourceLocation scaleType, EquipmentSlot slot) {
        Map<EquipmentSlot, Float> slotMap = enchantmentModifiers.get(scaleType);
        if (slotMap != null) {
            slotMap.remove(slot);
            if (slotMap.isEmpty()) {
                enchantmentModifiers.remove(scaleType);
            }
        }
    }

    /**
     * Get all enchantment modifiers for a scale type.
     */
    public Map<EquipmentSlot, Float> getEnchantmentModifiers(ResourceLocation scaleType) {
        Map<EquipmentSlot, Float> slotMap = enchantmentModifiers.get(scaleType);
        return slotMap != null ? new HashMap<>(slotMap) : new HashMap<>();
    }

    // ==================== NAMED MODIFIERS (Extensible) ====================

    /**
     * Set a named modifier (for potions, custom effects, etc.).
     * Use unique names like "potion:strength" or "custom:my_effect"
     */
    public void setNamedModifier(ResourceLocation scaleType, String modifierName, float multiplier) {
        namedModifiers.computeIfAbsent(scaleType, k -> new HashMap<>())
                .put(modifierName, multiplier);
    }

    /**
     * Remove a named modifier.
     */
    public void removeNamedModifier(ResourceLocation scaleType, String modifierName) {
        Map<String, Float> nameMap = namedModifiers.get(scaleType);
        if (nameMap != null) {
            nameMap.remove(modifierName);
            if (nameMap.isEmpty()) {
                namedModifiers.remove(scaleType);
            }
        }
    }

    /**
     * Get all named modifiers for a scale type.
     */
    public Map<String, Float> getNamedModifiers(ResourceLocation scaleType) {
        Map<String, Float> nameMap = namedModifiers.get(scaleType);
        return nameMap != null ? new HashMap<>(nameMap) : new HashMap<>();
    }

    // ==================== EFFECTIVE SCALE CALCULATION ====================

    /**
     * Calculate the final effective scale by composing base scale with all modifiers.
     * Formula: effectiveScale = baseScale × Π(enchantmentModifiers) × Π(namedModifiers)
     */
    public float calculateEffectiveScale(ResourceLocation scaleType) {
        float base = getBaseScale(scaleType);

        // Multiply by all enchantment modifiers
        for (float modifier : getEnchantmentModifiers(scaleType).values()) {
            base *= modifier;
        }

        // Multiply by all named modifiers
        for (float modifier : getNamedModifiers(scaleType).values()) {
            base *= modifier;
        }

        return base;
    }

    /**
     * Check if there are any active modifiers for this scale type.
     */
    public boolean hasModifiers(ResourceLocation scaleType) {
        return enchantmentModifiers.containsKey(scaleType) ||
                namedModifiers.containsKey(scaleType);
    }
}

