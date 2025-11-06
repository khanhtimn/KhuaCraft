package dev.khanhtimn.khuacraft.loot;

import com.mojang.serialization.MapCodec;
import dev.khanhtimn.khuacraft.KhuaCraft;
import dev.khanhtimn.khuacraft.loot.modifier.RandomnessLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, KhuaCraft.MODID);

    public static final Supplier<MapCodec<RandomnessLootModifier>> RANDOMNESS =
            GLOBAL_LOOT_MODIFIERS.register("randomness", () -> RandomnessLootModifier.CODEC);
}

