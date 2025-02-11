package store.teamti.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;
import store.teamti.effect.impl.ScaleEffect;
import store.teamti.effect.impl.ThicknessEffect;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, KhuaCraft.MOD_ID);

    public static final Holder<MobEffect> BIG_EFFECT = MOB_EFFECTS.register("big",
            () -> new ScaleEffect(
                    MobEffectCategory.NEUTRAL,
                    0x2d2920,
                    LevelBasedValue.perLevel(1.5F, 0.25F),
                    LevelBasedValue.perLevel(0.6F, -0.1F),
                    LevelBasedValue.perLevel(1.0F, 0.25F),
                    LevelBasedValue.perLevel(0.7F, -0.1F)
            )
    );

    public static final Holder<MobEffect> SMALL_EFFECT = MOB_EFFECTS.register("small",
            () -> new ScaleEffect(
                    MobEffectCategory.NEUTRAL,
                    0x88ffd9,
                    LevelBasedValue.perLevel(0.6F, -0.2F),
                    LevelBasedValue.constant(1.0F),
                    LevelBasedValue.perLevel(0.8F, -0.1F),
                    LevelBasedValue.perLevel(1.0F, 0.1F)
            )
    );

    public static final Holder<MobEffect> FAT_EFFECT = MOB_EFFECTS.register("fat",
            () -> new ThicknessEffect(
                    MobEffectCategory.NEUTRAL,
                    0xba232c,
                    LevelBasedValue.perLevel(1.5F, 0.25F),
                    LevelBasedValue.perLevel(0.6F, -0.1F)
            )
    );

    public static final Holder<MobEffect> THIN_EFFECT = MOB_EFFECTS.register("thin",
            () -> new ThicknessEffect(
                    MobEffectCategory.NEUTRAL,
                    0x36ebab,
                    LevelBasedValue.perLevel(0.6F, -0.2F),
                    LevelBasedValue.constant(1.0F)
            )
    );

}
