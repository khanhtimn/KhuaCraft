package dev.khanhtimn.khuacraft.enchantment;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.enchantment.effect.ScaleEquipmentEffect;
import dev.khanhtimn.khuacraft.item.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> BIG = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "big"));
    public static final ResourceKey<Enchantment> SMALL = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "small"));
    public static final ResourceKey<Enchantment> FAT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "fat"));
    public static final ResourceKey<Enchantment> THIN = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(KhuaCraft.MODID, "thin"));

    private static final ResourceLocation PEHKUI_BASE = ResourceLocation.fromNamespaceAndPath("pehkui", "base");
    private static final ResourceLocation PEHKUI_WIDTH = ResourceLocation.fromNamespaceAndPath("pehkui", "width");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        // BIG enchantment - increases base scale (for helmets)
        // base: 1.25, perLevel: 0.25 → levels: 1.5x, 1.75x, 2.0x
        register(context, BIG, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        3,  // weight
                        3,  // max level
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,  // anvil cost
                        EquipmentSlotGroup.HEAD))
                .exclusiveWith(enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE))
                .withEffect(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                        new ScaleEquipmentEffect(PEHKUI_BASE, 1.25F, 0.25F))
        );

        // SMALL enchantment - decreases base scale (for helmets)
        // base: 0.65, perLevel: -0.15 → levels: 0.5x, 0.35x, 0.2x
        register(context, SMALL, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        3,  // weight
                        3,  // max level
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,  // anvil cost
                        EquipmentSlotGroup.HEAD))
                .exclusiveWith(enchantments.getOrThrow(ModTags.Enchantments.HELMETS_EXCLUSIVE))
                .withEffect(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                        new ScaleEquipmentEffect(PEHKUI_BASE, 0.65F, -0.15F))
        );

        // FAT enchantment - increases width scale (for chestplates)
        // base: 1.25, perLevel: 0.25 → levels: 1.5x, 1.75x, 2.0x
        register(context, FAT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                        3,  // weight
                        3,  // max level
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,  // anvil cost
                        EquipmentSlotGroup.CHEST))
                .withEffect(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                        new ScaleEquipmentEffect(PEHKUI_WIDTH, 1.25F, 0.25F))
        );

        // THIN enchantment - decreases width scale (for chestplates)
        // base: 0.65, perLevel: -0.15 → levels: 0.5x, 0.35x, 0.2x
        register(context, THIN, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                        3,  // weight
                        3,  // max level
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(25, 10),
                        4,  // anvil cost
                        EquipmentSlotGroup.CHEST))
                .withEffect(ModEnchantmentEffectComponents.EQUIPMENT_SCALE.get(),
                        new ScaleEquipmentEffect(PEHKUI_WIDTH, 0.65F, -0.15F))
        );
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
