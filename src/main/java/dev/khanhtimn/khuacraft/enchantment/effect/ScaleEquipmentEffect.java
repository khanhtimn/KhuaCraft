package dev.khanhtimn.khuacraft.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record ScaleEquipmentEffect(
        ResourceLocation scaleType,
        float baseMultiplier,
        float perLevelMultiplier
) {
    public static final Codec<ScaleEquipmentEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("scale_type").forGetter(ScaleEquipmentEffect::scaleType),
                    Codec.FLOAT.fieldOf("base_multiplier").forGetter(ScaleEquipmentEffect::baseMultiplier),
                    Codec.FLOAT.optionalFieldOf("per_level_multiplier", 0.0F).forGetter(ScaleEquipmentEffect::perLevelMultiplier)
            ).apply(instance, ScaleEquipmentEffect::new)
    );

    public float calculateMultiplier(int enchantmentLevel) {
        return baseMultiplier + (perLevelMultiplier * enchantmentLevel);
    }
}

