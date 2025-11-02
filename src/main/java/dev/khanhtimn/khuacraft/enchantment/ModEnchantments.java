package dev.khanhtimn.khuacraft.enchantment;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.enchantment.effect.InnerConscienceEnchantmentEffect;
import dev.khanhtimn.khuacraft.enchantment.effect.ScaleEnchantmentEffect;
import dev.khanhtimn.khuacraft.enchantment.effect.SwappinessEnchantmentEffect;
import dev.khanhtimn.khuacraft.enchantment.effect.UnoReverseEnchantmentEffect;
import dev.khanhtimn.khuacraft.sound.ModSounds;
import dev.khanhtimn.khuacraft.tag.ModTags;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.ExplodeEffect;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> BIG = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "big"));
    public static final ResourceKey<Enchantment> SMALL = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "small"));
    public static final ResourceKey<Enchantment> FAT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "fat"));
    public static final ResourceKey<Enchantment> THIN = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "thin"));
    public static final ResourceKey<Enchantment> SWAPPINESS = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "swappiness"));
    public static final ResourceKey<Enchantment> COMBUSTION = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "combustion"));
    public static final ResourceKey<Enchantment> INNER_CONSCIENCE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "inner_conscience"));
    public static final ResourceKey<Enchantment> UNO_REVERSE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "uno_reverse"));

    private static final ResourceLocation PEHKUI_BASE = ResourceLocation.fromNamespaceAndPath("pehkui", "base");
    private static final ResourceLocation PEHKUI_WIDTH = ResourceLocation.fromNamespaceAndPath("pehkui", "width");
    private static final ResourceLocation PEHKUI_MOTION = ResourceLocation.fromNamespaceAndPath("pehkui", "motion");
    private static final ResourceLocation PEHKUI_ATTACK = ResourceLocation.fromNamespaceAndPath("pehkui", "attack");
    private static final ResourceLocation PEHKUI_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("pehkui", "attack_speed");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        context.register(
                BIG,
                new Enchantment(
                        Component.translatable("enchantment.khuacraft.big"),
                        new Enchantment.EnchantmentDefinition(
                                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                Optional.empty(),
                                3,
                                3,
                                Enchantment.dynamicCost(10, 10),
                                Enchantment.dynamicCost(25, 10),
                                4,
                                List.of(EquipmentSlotGroup.HEAD)
                        ),
                        enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE),
                        DataComponentMap.builder()
                                .set(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                                        List.of(
                                                new ScaleEnchantmentEffect(PEHKUI_BASE, LevelBasedValue.perLevel(1.5F, 0.25F)),
                                                new ScaleEnchantmentEffect(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(0.625F, 0.5F, 0.45F), LevelBasedValue.perLevel(0.6F, -0.1F))),
                                                new ScaleEnchantmentEffect(PEHKUI_ATTACK, LevelBasedValue.lookup(List.of(1.1F, 1.25F, 1.5F), LevelBasedValue.perLevel(1.0F, 0.25F))),
                                                new ScaleEnchantmentEffect(PEHKUI_ATTACK_SPEED, LevelBasedValue.perLevel(0.7F, -0.1F))
                                        )
                                )
                                .build()
                )
        );

        context.register(
                SMALL,
                new Enchantment(
                        Component.translatable("enchantment.khuacraft.small"),
                        new Enchantment.EnchantmentDefinition(
                                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                Optional.empty(),
                                3,
                                3,
                                Enchantment.dynamicCost(10, 10),
                                Enchantment.dynamicCost(25, 10),
                                4,
                                List.of(EquipmentSlotGroup.HEAD)
                        ),
                        enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE),
                        DataComponentMap.builder()
                                .set(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                                        List.of(
                                                new ScaleEnchantmentEffect(PEHKUI_BASE, LevelBasedValue.lookup(List.of(0.5F, 0.35F, 0.2F), LevelBasedValue.perLevel(0.6F, -0.2F))),
                                                new ScaleEnchantmentEffect(PEHKUI_MOTION, LevelBasedValue.constant(1.0F)),
                                                new ScaleEnchantmentEffect(PEHKUI_ATTACK, LevelBasedValue.perLevel(0.8F, -0.1F)),
                                                new ScaleEnchantmentEffect(PEHKUI_ATTACK_SPEED, LevelBasedValue.perLevel(1.0F, 0.1F))
                                        )
                                )
                                .build()
                )
        );

        context.register(
                FAT,
                new Enchantment(
                        Component.translatable("enchantment.khuacraft.fat"),
                        new Enchantment.EnchantmentDefinition(
                                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                Optional.empty(),
                                3,
                                3,
                                Enchantment.dynamicCost(10, 10),
                                Enchantment.dynamicCost(25, 10),
                                4,
                                List.of(EquipmentSlotGroup.CHEST)
                        ),
                        HolderSet.empty(),
                        DataComponentMap.builder()
                                .set(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                                        List.of(
                                                new ScaleEnchantmentEffect(PEHKUI_WIDTH, LevelBasedValue.perLevel(1.5F, 0.25F)),
                                                new ScaleEnchantmentEffect(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(0.8F, 0.6F, 0.5F), LevelBasedValue.perLevel(0.8F, -0.2F)))
                                        )
                                )
                                .build()
                )
        );

        context.register(
                THIN,
                new Enchantment(
                        Component.translatable("enchantment.khuacraft.thin"),
                        new Enchantment.EnchantmentDefinition(
                                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                Optional.empty(),
                                3,
                                3,
                                Enchantment.dynamicCost(10, 10),
                                Enchantment.dynamicCost(25, 10),
                                4,
                                List.of(EquipmentSlotGroup.CHEST)
                        ),
                        HolderSet.empty(),
                        DataComponentMap.builder()
                                .set(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                                        List.of(
                                                new ScaleEnchantmentEffect(PEHKUI_WIDTH, LevelBasedValue.perLevel(0.7F, -0.1F)),
                                                new ScaleEnchantmentEffect(PEHKUI_MOTION, LevelBasedValue.lookup(List.of(1.0F, 1.0F, 1.1F), LevelBasedValue.perLevel(1.0F, +0.05F)))
                                        )
                                )
                                .build()
                )
        );

        register(context, ModEnchantments.SWAPPINESS, Enchantment.enchantment(Enchantment.definition(
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


        register(context, ModEnchantments.COMBUSTION, Enchantment.enchantment(Enchantment.definition(
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


        register(context, ModEnchantments.INNER_CONSCIENCE, Enchantment.enchantment(Enchantment.definition(
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
                        LootItemRandomChanceCondition.randomChance(
                                EnchantmentLevelProvider.forEnchantmentLevel(
                                        LevelBasedValue.perLevel(0.001F)
                                )
                        )
                )
        );


        register(context, ModEnchantments.UNO_REVERSE, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.SHIELD_ENCHANTABLE), items.getOrThrow(ModTags.Items.SHIELDS),
                        4,
                        3,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        2,
                        EquipmentSlotGroup.OFFHAND))
                .withEffect(
                        EnchantmentEffectComponents.TICK,
                        new UnoReverseEnchantmentEffect(),
                        LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.1F)))
                )
        );

    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
