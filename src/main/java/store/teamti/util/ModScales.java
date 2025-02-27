package store.teamti.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;
import store.teamti.KhuaCraft;
import virtuoel.pehkui.api.*;

import java.util.List;

public class ModScales {

    public static final ScaleModifier BASE_MODIFIER = registerModifier("base_modifier", new TypedScaleModifier(() -> ModScales.BASE));
    public static final ScaleModifier MOVEMENT_MODIFIER = registerModifier("movement_modifier", new TypedScaleModifier(() -> ModScales.MOVEMENT));
    public static final ScaleModifier ATTACK_MODIFIER = registerModifier("attack_modifier", new TypedScaleModifier(() -> ModScales.ATTACK));
    public static final ScaleModifier ATTACK_SPEED_MODIFIER = registerModifier("attack_speed_modifier", new TypedScaleModifier(() -> ModScales.ATTACK_SPEED));

    public static final ScaleModifier THICKNESS_MODIFIER = registerModifier("thickness_modifier", new TypedScaleModifier(() -> ModScales.THICKNESS));

    public static final ScaleType BASE = registerScale("base", ModScales.BASE_MODIFIER);
    public static final ScaleType MOVEMENT = registerScale("movement", ModScales.MOVEMENT_MODIFIER);
    public static final ScaleType ATTACK = registerScale("attack", ModScales.ATTACK_MODIFIER);
    public static final ScaleType ATTACK_SPEED = registerScale("attack_speed", ModScales.ATTACK_SPEED_MODIFIER);

    public static final ScaleType THICKNESS = registerScale("thickness", ModScales.THICKNESS_MODIFIER);

    private static ScaleType registerScale(String id, ScaleModifier dependantModifier) {
        final ScaleType.Builder builder = ScaleType.Builder.create().affectsDimensions();

        builder.addDependentModifier(ScaleModifiers.REACH_MULTIPLIER);
        builder.addDependentModifier(dependantModifier);

        return ScaleRegistries.register(ScaleRegistries.SCALE_TYPES,
                ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, id),
                builder.build());
    }

    public static ScaleModifier registerModifier(String id, ScaleModifier modifier) {
        return ScaleRegistries.register(ScaleRegistries.SCALE_MODIFIERS, ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, id), modifier);
    }

    public static void init() {
        ScaleTypes.BASE.getDefaultBaseValueModifiers().add(BASE_MODIFIER);

        ScaleTypes.MOTION.getDefaultBaseValueModifiers().add(MOVEMENT_MODIFIER);
        ScaleTypes.JUMP_HEIGHT.getDefaultBaseValueModifiers().add(MOVEMENT_MODIFIER);

        ScaleTypes.ATTACK.getDefaultBaseValueModifiers().add(ATTACK_MODIFIER);
        ScaleTypes.ATTACK_SPEED.getDefaultBaseValueModifiers().add(ATTACK_SPEED_MODIFIER);

        ScaleTypes.WIDTH.getDefaultBaseValueModifiers().add(THICKNESS_MODIFIER);
    }

    public static void changeScale(
            int enchantmentLevel, @NotNull Entity entity,
            LevelBasedValue baseScale,
            LevelBasedValue movementScale,
            LevelBasedValue attackScale,
            LevelBasedValue attackSpeedScale
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

    public static void resetScale(@NotNull Entity entity) {
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
}
