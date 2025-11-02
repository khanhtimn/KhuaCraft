package dev.khanhtimn.khuacraft.datagen.provider;

import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.enchantment.ModEnchantments;
import dev.khanhtimn.khuacraft.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KhuaCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(EnchantmentTags.TRADEABLE).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.INNER_CONSCIENCE,
                ModEnchantments.UNO_REVERSE
        );

        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.INNER_CONSCIENCE,
                ModEnchantments.UNO_REVERSE
        );

        this.tag(EnchantmentTags.NON_TREASURE).add(ModEnchantments.UNO_REVERSE);

        this.tag(EnchantmentTags.TREASURE).add(ModEnchantments.BIG, ModEnchantments.SMALL);

        this.tag(EnchantmentTags.CURSE).add(
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.INNER_CONSCIENCE
        );

        this.tag(EnchantmentTags.ON_RANDOM_LOOT).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.INNER_CONSCIENCE,
                ModEnchantments.UNO_REVERSE
        );

        this.tag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.UNO_REVERSE
        );

        this.tag(EnchantmentTags.ON_TRADED_EQUIPMENT).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL,
                ModEnchantments.FAT,
                ModEnchantments.THIN,
                ModEnchantments.SWAPPINESS,
                ModEnchantments.COMBUSTION,
                ModEnchantments.UNO_REVERSE
        );

        this.tag(ModTags.Enchantments.HELMETS_EXCLUSIVE).add(
                ModEnchantments.BIG,
                ModEnchantments.SMALL
        );

    }
}
