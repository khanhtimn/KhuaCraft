package dev.khanhtimn.khuacraft.datagen.provider;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.loot.modifier.RandomnessLootModifier;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, KhuaCraft.MODID);
    }

    @Override
    protected void start() {
        add("randomness", new RandomnessLootModifier(
                new LootItemCondition[0],
                UniformGenerator.between(0.01f, 0.05f),
                HolderSet.direct(
                        BuiltInRegistries.ITEM.wrapAsHolder(Items.BEDROCK),
                        BuiltInRegistries.ITEM.wrapAsHolder(Items.COMMAND_BLOCK),
                        BuiltInRegistries.ITEM.wrapAsHolder(Items.BARRIER),
                        BuiltInRegistries.ITEM.wrapAsHolder(Items.STRUCTURE_VOID)
                )
        ));
    }
}

