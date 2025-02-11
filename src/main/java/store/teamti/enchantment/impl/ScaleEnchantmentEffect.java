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
import store.teamti.util.ModScales;
import virtuoel.pehkui.api.ScaleData;

import java.util.List;

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
        ScaleData baseData = ModScales.BASE.getScaleData(entity);
        ScaleData movementData = ModScales.MOVEMENT.getScaleData(entity);
        ScaleData attackData = ModScales.ATTACK.getScaleData(entity);
        ScaleData attackSpeedData = ModScales.ATTACK_SPEED.getScaleData(entity);

        baseData.setTargetScale(baseScale.calculate(enchantmentLevel));
        baseData.setScaleTickDelay(baseData.getScaleTickDelay());

        movementData.setTargetScale(movementScale.calculate(enchantmentLevel));
        movementData.setScaleTickDelay(movementData.getScaleTickDelay());

        attackData.setTargetScale(attackScale.calculate(enchantmentLevel));
        attackData.setScaleTickDelay(attackData.getScaleTickDelay());

        attackSpeedData.setTargetScale(attackSpeedScale.calculate(enchantmentLevel));
        attackSpeedData.setScaleTickDelay(attackSpeedData.getScaleTickDelay());
    }

    @Override
    public void onDeactivated(@NotNull EnchantedItemInUse item, @NotNull Entity entity, @NotNull Vec3 pos, int enchantmentLevel) {
        List.of(
                ModScales.BASE.getScaleData(entity),
                ModScales.MOVEMENT.getScaleData(entity),
                ModScales.ATTACK.getScaleData(entity),
                ModScales.ATTACK_SPEED.getScaleData(entity)
        ).forEach(scaleData -> {
            scaleData.setTargetScale(1.0F);
            scaleData.setScaleTickDelay(scaleData.getScaleTickDelay());
        });
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentLocationBasedEffect> codec() {
        return CODEC;
    }

    public LevelBasedValue baseScale() {
        return this.baseScale;
    }

    public LevelBasedValue movementScale() {
        return this.movementScale;
    }

    public LevelBasedValue attackScale() {
        return this.attackScale;
    }

    public LevelBasedValue attackSpeedScale() {
        return this.attackSpeedScale;
    }
}
