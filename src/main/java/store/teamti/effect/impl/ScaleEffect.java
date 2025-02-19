package store.teamti.effect.impl;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;
import store.teamti.util.ModScales;

public class ScaleEffect extends MobEffect {

    private final LevelBasedValue baseScale;
    private final LevelBasedValue movementScale;
    private final LevelBasedValue attackScale;
    private final LevelBasedValue attackSpeedScale;

    public ScaleEffect(
            MobEffectCategory category,
            int color,
            LevelBasedValue baseScale,
            LevelBasedValue movementScale,
            LevelBasedValue attackScale,
            LevelBasedValue attackSpeedScale
    ) {
        super(category, color);
        this.baseScale = baseScale;
        this.movementScale = movementScale;
        this.attackScale = attackScale;
        this.attackSpeedScale = attackSpeedScale;
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity livingEntity, int amplifier) {
        ModScales.changeScale(amplifier, livingEntity, baseScale, movementScale, attackScale, attackSpeedScale);
        super.onEffectStarted(livingEntity, amplifier);
    }

    public void onEffectRemoved(@NotNull LivingEntity livingEntity) {
        ModScales.resetScale(livingEntity);
    }
}
