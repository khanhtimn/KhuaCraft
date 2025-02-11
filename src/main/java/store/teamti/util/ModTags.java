package store.teamti.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import store.teamti.KhuaCraft;

public class ModTags {

    public static class Blocks {
        private static TagKey<Block> bind(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SHIELDS = bindNeoForge("shields");
        public static final TagKey<Item> SHIELD_ENCHANTABLE = bindNeoForge("enchantable/shield");

        private static TagKey<Item> bindNeoForge(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
        private static TagKey<Item> bindVanilla(String name) {
            return ItemTags.create(ResourceLocation.withDefaultNamespace(name));
        }
        private static TagKey<Item> bindMod(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, name));
        }
    }

    public static class Enchantments {

        public static final TagKey<Enchantment> HELMETS_EXCLUSIVE = bindNeoForge("exclusive_set/helmets");

        private static TagKey<Enchantment> bindNeoForge(String name) {
            return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("c", name));
        }
        private static TagKey<Enchantment> bindVanilla(String name) {
            return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace(name));
        }
        private static TagKey<Enchantment> bindMod(String name) {
            return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(KhuaCraft.MOD_ID, name));
        }
    }
}
