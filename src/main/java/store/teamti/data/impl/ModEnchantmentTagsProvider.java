package store.teamti.data.impl;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import store.teamti.KhuaCraft;
import store.teamti.enchantment.ModEnchantments;
import store.teamti.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KhuaCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(EnchantmentTags.NON_TREASURE).add(ModEnchantments.UNO_REVERSE);

        this.tag(EnchantmentTags.TREASURE).add(ModEnchantments.BIG, ModEnchantments.SMALL);

        this.tag(EnchantmentTags.CURSE).add(
                ModEnchantments.FAT_CURSE,
                ModEnchantments.SWAPPINESS_CURSE,
                ModEnchantments.COMBUSTION_CURSE,
                ModEnchantments.INNER_CONSCIENCE_CURSE
        );

        this.tag(EnchantmentTags.ON_RANDOM_LOOT).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.UNO_REVERSE,
                ModEnchantments.FAT_CURSE,
                ModEnchantments.SWAPPINESS_CURSE,
                ModEnchantments.COMBUSTION_CURSE,
                ModEnchantments.INNER_CONSCIENCE_CURSE
        );

        this.tag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.UNO_REVERSE,
                ModEnchantments.FAT_CURSE,
                ModEnchantments.SWAPPINESS_CURSE,
                ModEnchantments.COMBUSTION_CURSE
        );

        this.tag(ModTags.Enchantments.HELMETS_EXCLUSIVE).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL
        );

    }
}