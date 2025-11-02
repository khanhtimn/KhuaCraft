package dev.khanhtimn.khuacraft;

import com.mojang.logging.LogUtils;
import dev.khanhtimn.khuacraft.data.ModDataAttachment;
import dev.khanhtimn.khuacraft.datagen.ModDataGeneration;
import dev.khanhtimn.khuacraft.enchantment.ModEnchantmentEffectComponents;
import dev.khanhtimn.khuacraft.enchantment.ModEnchantmentEffects;
import dev.khanhtimn.khuacraft.enchantment.effect.ScaleEnchantmentEffect;
import dev.khanhtimn.khuacraft.item.ModItemGroups;
import dev.khanhtimn.khuacraft.item.ModItems;
import dev.khanhtimn.khuacraft.network.ModPackets;
import dev.khanhtimn.khuacraft.particle.ModParticles;
import dev.khanhtimn.khuacraft.sound.ModSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(KhuaCraft.MODID)
public class KhuaCraft {
    public static final String MODID = "khuacraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public KhuaCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModPackets::init);
        modEventBus.addListener(ModDataGeneration::gatherData);

        ModSounds.SOUND_EVENTS.register(modEventBus);

        ModParticles.PARTICLE_TYPES.register(modEventBus);

        ModItems.ITEMS.register(modEventBus);
        ModItemGroups.CREATIVE_MODE_TABS.register(modEventBus);

        ModDataAttachment.DATA_ATTACHMENT_TYPES.register(modEventBus);
        ModEnchantmentEffectComponents.ENCHANTMENT_EFFECT_COMPONENTS.register(modEventBus);
        ModEnchantmentEffects.ENTITY_ENCHANTMENT_EFFECTS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");


        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));

        // Enchantments
        NeoForge.EVENT_BUS.addListener(ScaleEnchantmentEffect.Listener::onEquipmentChange);
    }
}
