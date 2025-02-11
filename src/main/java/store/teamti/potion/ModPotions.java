package store.teamti.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;
import store.teamti.effect.ModEffects;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, KhuaCraft.MOD_ID);

    public static final Holder<Potion> BIG_POTION = POTIONS.register("big_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BIG_EFFECT, 3600))
    );

    public static final Holder<Potion> STRONG_BIG_POTION = POTIONS.register("strong_big_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BIG_EFFECT, 1800, 3))
    );

    public static final Holder<Potion> LONG_BIG_POTION = POTIONS.register("long_big_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BIG_EFFECT, 9600))
    );

    public static final Holder<Potion> STRONG_LONG_BIG_POTION = POTIONS.register("strong_long_big_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BIG_EFFECT, 9600,3))
    );


    public static final Holder<Potion> SMALL_POTION = POTIONS.register("small_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SMALL_EFFECT, 3600))
    );

    public static final Holder<Potion> STRONG_SMALL_POTION = POTIONS.register("strong_small_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SMALL_EFFECT, 1800, 3))
    );

    public static final Holder<Potion> LONG_SMALL_POTION = POTIONS.register("long_small_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SMALL_EFFECT, 9600))
    );

    public static final Holder<Potion> STRONG_LONG_SMALL_POTION = POTIONS.register("strong_long_small_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SMALL_EFFECT, 9600, 3))
    );

    public static final Holder<Potion> FAT_POTION = POTIONS.register("fat_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FAT_EFFECT, 3600))
    );

    public static final Holder<Potion> STRONG_FAT_POTION = POTIONS.register("strong_fat_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FAT_EFFECT, 1800, 3))
    );

    public static final Holder<Potion> LONG_FAT_POTION = POTIONS.register("long_fat_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FAT_EFFECT, 9600))
    );

    public static final Holder<Potion> STRONG_LONG_FAT_POTION = POTIONS.register("strong_long_fat_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.FAT_EFFECT, 9600, 3))
    );

    public static final Holder<Potion> THIN_POTION = POTIONS.register("thin_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THIN_EFFECT, 3600))
    );

    public static final Holder<Potion> STRONG_THIN_POTION = POTIONS.register("strong_thin_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THIN_EFFECT, 1800, 3))
    );

    public static final Holder<Potion> LONG_THIN_POTION = POTIONS.register("long_thin_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THIN_EFFECT, 9600))
    );

    public static final Holder<Potion> STRONG_LONG_THIN_POTION = POTIONS.register("strong_long_thin_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.THIN_EFFECT, 9600, 3))
    );


    public static void registerBrewingRecipes(final RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(
                Potions.AWKWARD,
                Items.BRICK,
                ModPotions.BIG_POTION
        );

        builder.addMix(
                ModPotions.BIG_POTION,
                Items.REDSTONE,
                ModPotions.LONG_BIG_POTION
        );

        builder.addMix(
                ModPotions.BIG_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_BIG_POTION
        );

        builder.addMix(
                ModPotions.LONG_BIG_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_LONG_BIG_POTION
        );

        builder.addMix(
                ModPotions.STRONG_BIG_POTION,
                Items.REDSTONE,
                ModPotions.STRONG_LONG_BIG_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.BRICK,
                ModPotions.SMALL_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.FEATHER,
                ModPotions.STRONG_SMALL_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.PORKCHOP,
                ModPotions.FAT_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.BONE,
                ModPotions.STRONG_FAT_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.APPLE,
                ModPotions.THIN_POTION
        );

        builder.addMix(
                Potions.AWKWARD,
                Items.SUGAR,
                ModPotions.STRONG_THIN_POTION
        );

        builder.addMix(
                ModPotions.SMALL_POTION,
                Items.REDSTONE,
                ModPotions.LONG_SMALL_POTION
        );

        builder.addMix(
                ModPotions.SMALL_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_SMALL_POTION
        );

        builder.addMix(
                ModPotions.LONG_SMALL_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_LONG_SMALL_POTION
        );

        builder.addMix(
                ModPotions.STRONG_SMALL_POTION,
                Items.REDSTONE,
                ModPotions.STRONG_LONG_SMALL_POTION
        );

        builder.addMix(
                ModPotions.FAT_POTION,
                Items.REDSTONE,
                ModPotions.LONG_FAT_POTION
        );

        builder.addMix(
                ModPotions.FAT_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_FAT_POTION
        );

        builder.addMix(
                ModPotions.LONG_FAT_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_LONG_FAT_POTION
        );

        builder.addMix(
                ModPotions.STRONG_FAT_POTION,
                Items.REDSTONE,
                ModPotions.STRONG_LONG_FAT_POTION
        );

        builder.addMix(
                ModPotions.THIN_POTION,
                Items.REDSTONE,
                ModPotions.LONG_THIN_POTION
        );

        builder.addMix(
                ModPotions.THIN_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_THIN_POTION
        );

        builder.addMix(
                ModPotions.LONG_THIN_POTION,
                Items.GLOWSTONE_DUST,
                ModPotions.STRONG_LONG_THIN_POTION
        );

        builder.addMix(
                ModPotions.STRONG_THIN_POTION,
                Items.REDSTONE,
                ModPotions.STRONG_LONG_THIN_POTION
        );
    }
}
