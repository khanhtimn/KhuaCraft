package store.teamti.enchantment;

import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;
import net.minecraft.world.phys.Vec3;
import store.teamti.KhuaCraft;
import store.teamti.effect.ModEffects;
import store.teamti.enchantment.impl.InnerConscienceEnchantmentEffect;
import store.teamti.enchantment.impl.ApplyMobEffect;
import store.teamti.enchantment.impl.SwappinessEnchantmentEffect;
import store.teamti.sound.ModSounds;
import store.teamti.util.ModTags;

import java.util.Optional;
import java.util.function.Function;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> UNO_REVERSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "uno_reverse"));

    public static final ResourceKey<Enchantment> BIG = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "big"));

    public static final ResourceKey<Enchantment> SMALL = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "small"));

    public static final ResourceKey<Enchantment> SWAPPINESS_CURSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "swappiness_curse"));

    public static final ResourceKey<Enchantment> COMBUSTION_CURSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "combustion_curse"));

    public static final ResourceKey<Enchantment> INNER_CONSCIENCE_CURSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "inner_conscience_curse"));

    public static final ResourceKey<Enchantment> FAT_CURSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, "fat_curse"));


    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);
        var blocks = context.lookup(Registries.BLOCK);

        register(context, ModEnchantments.UNO_REVERSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModTags.Items.SHIELD_ENCHANTABLE), items.getOrThrow(ModTags.Items.SHIELDS),
                        4,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        2,
                        EquipmentSlotGroup.OFFHAND))
                // Maybe try         Registry.register(registry, "run_function", RunFunction.CODEC);
        );

        register(context, ModEnchantments.BIG, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        3,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,
                        EquipmentSlotGroup.HEAD))
                .exclusiveWith(enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE))
                .withEffect(
                        EnchantmentEffectComponents.LOCATION_CHANGED,
                        new ApplyMobEffect(ModEffects.BIG_EFFECT)
                )
        );

        register(context, ModEnchantments.SMALL, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        3,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,
                        EquipmentSlotGroup.HEAD))
                .exclusiveWith(enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE))
                .withEffect(
                        EnchantmentEffectComponents.LOCATION_CHANGED,
                        new ApplyMobEffect(ModEffects.SMALL_EFFECT)
                )
        );

        register(context, ModEnchantments.FAT_CURSE, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.CHEST))
                .withEffect(
                        EnchantmentEffectComponents.LOCATION_CHANGED,
                        new ApplyMobEffect(ModEffects.FAT_EFFECT)
                )
        );

        register(context, ModEnchantments.SWAPPINESS_CURSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.ANY))
                .withEffect(
                        EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,
                        AllOf.entityEffects(
                                new PlaySoundEffect(ModSounds.FART_REVERB, ConstantFloat.of(1.0F), ConstantFloat.of(1.0F)),
                                new SwappinessEnchantmentEffect()
                        ),
                        AllOfCondition.allOf(
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true)),
                                LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.15F)))
                        )
                )
        );

        register(context, ModEnchantments.COMBUSTION_CURSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.ANY))
                .withEffect(
                        EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,
                        new ExplodeEffect(
                                false,
                                Optional.empty(),
                                Optional.of(LevelBasedValue.perLevel(1.5F, 0.5F)),
                                blocks.get(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()),
                                Vec3.ZERO,
                                LevelBasedValue.perLevel(3.0F, 0.5F),
                                true,
                                Level.ExplosionInteraction.TRIGGER,
                                ParticleTypes.GUST_EMITTER_SMALL,
                                ParticleTypes.GUST_EMITTER_LARGE,
                                SoundEvents.WIND_CHARGE_BURST
                        ),
                        AllOfCondition.allOf(
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true)),
                                LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.1F)))
                        )
                )
        );

        register(context, ModEnchantments.INNER_CONSCIENCE_CURSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        1,
                        10,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.HEAD))
                .withEffect(
                        EnchantmentEffectComponents.TICK,
                        new InnerConscienceEnchantmentEffect(),
                        LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.001F)))
                )
        );


    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
