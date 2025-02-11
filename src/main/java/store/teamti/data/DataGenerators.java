package store.teamti.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import store.teamti.KhuaCraft;
import store.teamti.data.impl.ModBlockTagProvider;
import store.teamti.data.impl.ModDatapackProvider;
import store.teamti.data.impl.ModEnchantmentTagsProvider;
import store.teamti.data.impl.ModItemTagProvider;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = KhuaCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        DatapackBuiltinEntriesProvider modDatapackProvider = new ModDatapackProvider(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> modLookupProvider = modDatapackProvider.getRegistryProvider();
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, modLookupProvider, existingFileHelper);

        generator.addProvider(event.includeServer(), modDatapackProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, modLookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(packOutput, modLookupProvider, existingFileHelper));
    }
}
