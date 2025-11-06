package dev.khanhtimn.khuacraft.loot.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.khanhtimn.khuacraft.enchantment.ModEnchantments;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomnessLootModifier extends LootModifier {

    public static final MapCodec<RandomnessLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            instance -> codecStart(instance)
                    .and(instance.group(
                            NumberProviders.CODEC.fieldOf("chance").forGetter(m -> m.chance),
                            BuiltInRegistries.ITEM.holderByNameCodec().listOf()
                                    .optionalFieldOf("excluded_items", List.of())
                                    .forGetter(m -> m.excludedItems.stream().toList())
                    ))
                    .apply(instance, (conditions, chance, excludedList) ->
                            new RandomnessLootModifier(conditions, chance,
                                    excludedList.isEmpty() ? HolderSet.empty() : HolderSet.direct(excludedList)))
    );

    private final NumberProvider chance;
    private final HolderSet<Item> excludedItems;

    public RandomnessLootModifier(LootItemCondition[] conditionsIn, NumberProvider chance, HolderSet<Item> excludedItems) {
        super(conditionsIn);
        this.chance = chance;
        this.excludedItems = excludedItems;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

        if (tool == null || tool.isEmpty()) {
            return generatedLoot;
        }

        int enchantmentLevel = context.getLevel().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(ModEnchantments.RANDOMNESS)
                .map(tool::getEnchantmentLevel)
                .orElse(0);

        if (enchantmentLevel <= 0) {
            return generatedLoot;
        }

        float chancePerLevel = chance.getFloat(context);
        float triggerChance = chancePerLevel * enchantmentLevel;

        if (context.getRandom().nextFloat() >= triggerChance) {
            return generatedLoot;
        }

        List<Item> allItems = BuiltInRegistries.ITEM.stream().toList();

        if (!excludedItems.stream().toList().isEmpty()) {
            Set<Item> excludedSet = excludedItems.stream()
                    .map(Holder::value)
                    .collect(Collectors.toSet());

            allItems = allItems.stream()
                    .filter(item -> !excludedSet.contains(item))
                    .toList();
        }

        if (allItems.isEmpty()) {
            return generatedLoot;
        }

        List<Item> finalAllItems = allItems;
        return new ObjectArrayList<>(generatedLoot.stream()
                .map(stack -> {
                    Item randomItem = finalAllItems.get(context.getRandom().nextInt(finalAllItems.size()));
                    return new ItemStack(randomItem, stack.getCount());
                })
                .toList());
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

