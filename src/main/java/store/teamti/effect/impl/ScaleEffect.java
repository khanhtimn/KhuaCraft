package store.teamti.effect.impl;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;
import store.teamti.util.ModScales;
import virtuoel.pehkui.api.ScaleData;

import java.util.List;

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
        ScaleData baseData = ModScales.BASE.getScaleData(livingEntity);
        ScaleData movementData = ModScales.MOVEMENT.getScaleData(livingEntity);
        ScaleData attackData = ModScales.ATTACK.getScaleData(livingEntity);
        ScaleData attackSpeedData = ModScales.ATTACK_SPEED.getScaleData(livingEntity);

        baseData.setTargetScale(baseScale.calculate(amplifier));
        baseData.setScaleTickDelay(baseData.getScaleTickDelay());

        movementData.setTargetScale(movementScale.calculate(amplifier));
        movementData.setScaleTickDelay(movementData.getScaleTickDelay());

        attackData.setTargetScale(attackScale.calculate(amplifier));
        attackData.setScaleTickDelay(attackData.getScaleTickDelay());

        attackSpeedData.setTargetScale(attackSpeedScale.calculate(amplifier));
        attackSpeedData.setScaleTickDelay(attackSpeedData.getScaleTickDelay());
        super.onEffectStarted(livingEntity, amplifier);
    }

    public void onEffectRemoved(@NotNull LivingEntity livingEntity) {
        List.of(
                ModScales.BASE.getScaleData(livingEntity),
                ModScales.MOVEMENT.getScaleData(livingEntity),
                ModScales.ATTACK.getScaleData(livingEntity),
                ModScales.ATTACK_SPEED.getScaleData(livingEntity)
        ).forEach(scaleData -> {
            scaleData.setTargetScale(1.0F);
            scaleData.setScaleTickDelay(scaleData.getScaleTickDelay());
        });
    }
}
