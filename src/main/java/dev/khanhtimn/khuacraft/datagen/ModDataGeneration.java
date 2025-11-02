package dev.khanhtimn.khuacraft.datagen;

import dev.khanhtimn.khuacraft.datagen.provider.ModBlockTagProvider;
import dev.khanhtimn.khuacraft.datagen.provider.ModDatapackProvider;
import dev.khanhtimn.khuacraft.datagen.provider.ModEnchantmentTagsProvider;
import dev.khanhtimn.khuacraft.datagen.provider.ModItemTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGeneration {

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        DatapackBuiltinEntriesProvider modDatapackProvider = new ModDatapackProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> modLookupProvider = modDatapackProvider.getRegistryProvider();
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(output, modLookupProvider, existingFileHelper);

        generator.addProvider(event.includeServer(), modDatapackProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(output, modLookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(output, modLookupProvider, existingFileHelper));
    }
}

