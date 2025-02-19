package store.teamti.enchantment.impl;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import store.teamti.KhuaCraft;
import store.teamti.util.ModScales;

public record ScaleEnchantmentEffect(
        LevelBasedValue baseScale,
        LevelBasedValue movementScale,
        LevelBasedValue attackScale,
        LevelBasedValue attackSpeedScale
) implements EnchantmentLocationBasedEffect {

    public static final MapCodec<ScaleEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(
                    LevelBasedValue.CODEC.fieldOf("base_scale").forGetter(ScaleEnchantmentEffect::baseScale),
                    LevelBasedValue.CODEC.fieldOf("movement_scale").forGetter(ScaleEnchantmentEffect::movementScale),
                    LevelBasedValue.CODEC.fieldOf("attack_scale").forGetter(ScaleEnchantmentEffect::attackScale),
                    LevelBasedValue.CODEC.fieldOf("attack_speed_scale").forGetter(ScaleEnchantmentEffect::attackSpeedScale)
            ).apply(instance, ScaleEnchantmentEffect::new)
    );

    @Override
    public void onChangedBlock(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3,
            boolean b
    ) {
        KhuaCraft.LOGGER.info("changed scale");
        ModScales.changeScale(enchantmentLevel, entity, baseScale, movementScale, attackScale, attackSpeedScale);
    }

    @Override
    public void onDeactivated(@NotNull EnchantedItemInUse item, @NotNull Entity entity, @NotNull Vec3 pos, int enchantmentLevel) {
        KhuaCraft.LOGGER.info("resetted scale");
        ModScales.resetScale(entity);
    }

    @Override
    public @NotNull MapCodec<ScaleEnchantmentEffect> codec() {
        return CODEC;
    }
}
