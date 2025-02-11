package store.teamti.effect.impl;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;
import store.teamti.util.ModScales;
import virtuoel.pehkui.api.ScaleData;

import java.util.List;

public class ThicknessEffect extends MobEffect {

    private final LevelBasedValue width;
    private final LevelBasedValue movementSpeed;


    public ThicknessEffect(
            MobEffectCategory category,
            int color,
            LevelBasedValue width,
            LevelBasedValue movementSpeed
    ) {
        super(category, color);
        this.width = width;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity livingEntity, int amplifier) {
        ScaleData thickness = ModScales.THICKNESS.getScaleData(livingEntity);
        thickness.setTargetScale(width.calculate(amplifier));
        thickness.setScaleTickDelay(thickness.getScaleTickDelay());

        ScaleData movement = ModScales.MOVEMENT.getScaleData(livingEntity);
        movement.setTargetScale(movementSpeed.calculate(amplifier));
        movement.setScaleTickDelay(movement.getScaleTickDelay());
        super.onEffectStarted(livingEntity, amplifier);
    }

    public void onEffectRemoved(@NotNull LivingEntity livingEntity) {
        List.of(
                ModScales.THICKNESS.getScaleData(livingEntity),
                ModScales.MOVEMENT.getScaleData(livingEntity)
        ).forEach(thicknessData -> {
            thicknessData.setTargetScale(1.0F);
            thicknessData.setScaleTickDelay(thicknessData.getScaleTickDelay());
        });
    }
}
