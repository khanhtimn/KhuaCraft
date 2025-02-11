package store.teamti.item;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;
import store.teamti.potion.ModPotions;

import java.util.List;
import java.util.stream.IntStream;

public class ModItemGroups {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, KhuaCraft.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL = CREATIVE_MODE_TABS.register("general",
            () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1)
                    .icon(ModItems.CREATIVE_TAB_ICON.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup." + KhuaCraft.MOD_ID + ".general"))
                    .displayItems((context, entries) -> {

                        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = context.holders().lookupOrThrow(Registries.ENCHANTMENT);

                        List<ItemStack> modItems = ModItems.ITEMS.getEntries()
                                .stream()
                                .filter(reg -> reg.get() != ModItems.CREATIVE_TAB_ICON.get().asItem())
                                .map(reg -> new ItemStack(reg.get()))
                                .toList();

                        entries.acceptAll(modItems);

                        List<ItemStack> modEnchantedBooks = enchantmentLookup
                                .listElements()
                                .filter(enchantment -> enchantment.key().location().getNamespace().equals(KhuaCraft.MOD_ID))
                                .flatMap(
                                        enchantment -> IntStream.rangeClosed(enchantment.value().getMinLevel(), enchantment.value().getMaxLevel())
                                                .mapToObj(level -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)))
                                ).toList();

                        entries.acceptAll(modEnchantedBooks);

                        List<ItemStack> modPotions = ModPotions.POTIONS.getEntries()
                                .stream()
                                .map(reg -> PotionContents.createItemStack(Items.POTION, reg.getDelegate()))
                                .toList();

                        entries.acceptAll(modPotions);

                        List<ItemStack> modSplashPotions = ModPotions.POTIONS.getEntries()
                                .stream()
                                .map(reg -> PotionContents.createItemStack(Items.SPLASH_POTION, reg.getDelegate()))
                                .toList();

                        entries.acceptAll(modSplashPotions);

                        List<ItemStack> modTippedArrows = ModPotions.POTIONS.getEntries()
                                .stream()
                                .map(reg -> PotionContents.createItemStack(Items.TIPPED_ARROW, reg.getDelegate()))
                                .toList();

                        entries.acceptAll(modTippedArrows);


                    })
                    .build());
}
