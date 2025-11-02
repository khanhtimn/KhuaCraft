package dev.khanhtimn.khuacraft.potion;


import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.potion.effects.ScaleEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, KhuaCraft.MODID);

    private static final ResourceLocation PEHKUI_BASE = ResourceLocation.fromNamespaceAndPath("pehkui", "base");
    private static final ResourceLocation PEHKUI_WIDTH = ResourceLocation.fromNamespaceAndPath("pehkui", "width");
    private static final ResourceLocation PEHKUI_MOTION = ResourceLocation.fromNamespaceAndPath("pehkui", "motion");
    public static final Holder<MobEffect> FAT_EFFECT = MOB_EFFECTS.register("fat",
            () -> new ScaleEffect(
                    MobEffectCategory.HARMFUL,
                    0xba232c,
                    new ScaleEffect.Builder()
                            .add(PEHKUI_WIDTH, LevelBasedValue.perLevel(1.5F, 0.25F))
                            .add(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(0.8F, 0.6F, 0.5F), LevelBasedValue.perLevel(0.8F, -0.2F)))
                            .build()
            )
    );
    public static final Holder<MobEffect> THIN_EFFECT = MOB_EFFECTS.register("thin",
            () -> new ScaleEffect(
                    MobEffectCategory.HARMFUL,
                    0x36ebab,
                    new ScaleEffect.Builder()
                            .add(PEHKUI_WIDTH, LevelBasedValue.perLevel(0.7F, -0.1F))
                            .add(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(1.0F, 1.0F, 1.1F), LevelBasedValue.perLevel(1.0F, 0.05F)))
                            .build()
            )
    );
    private static final ResourceLocation PEHKUI_ATTACK = ResourceLocation.fromNamespaceAndPath("pehkui", "attack");
    private static final ResourceLocation PEHKUI_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("pehkui", "attack_speed");
    public static final Holder<MobEffect> BIG_EFFECT = MOB_EFFECTS.register("big",
            () -> new ScaleEffect(
                    MobEffectCategory.NEUTRAL,
                    0x2d2920,
                    new ScaleEffect.Builder()
                            .add(PEHKUI_BASE, LevelBasedValue.perLevel(1.5F, 0.25F))
                            .add(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(0.625F, 0.5F, 0.45F), LevelBasedValue.perLevel(0.6F, -0.1F)))
                            .add(PEHKUI_ATTACK, LevelBasedValue.lookup(List.of(1.1F, 1.25F, 1.5F), LevelBasedValue.perLevel(1.0F, 0.25F)))
                            .add(PEHKUI_ATTACK_SPEED, LevelBasedValue.perLevel(0.7F, -0.1F))
                            .build()
            )
    );

    public static final Holder<MobEffect> SMALL_EFFECT = MOB_EFFECTS.register("small",
            () -> new ScaleEffect(
                    MobEffectCategory.NEUTRAL,
                    0x88ffd9,
                    new ScaleEffect.Builder()
                            .add(PEHKUI_BASE, LevelBasedValue.lookup(List.of(0.5F, 0.35F, 0.2F), LevelBasedValue.perLevel(0.6F, -0.2F)))
                            .add(PEHKUI_MOTION, LevelBasedValue.constant(1.0F))
                            .add(PEHKUI_ATTACK, LevelBasedValue.perLevel(0.8F, -0.1F))
                            .add(PEHKUI_ATTACK_SPEED, LevelBasedValue.perLevel(1.0F, 0.1F))
                            .build()
            )
    );

}
