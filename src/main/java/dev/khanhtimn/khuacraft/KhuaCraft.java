package dev.khanhtimn.khuacraft;

import com.mojang.logging.LogUtils;
import dev.khanhtimn.khuacraft.data.attachment.ModDataAttachment;
import dev.khanhtimn.khuacraft.data.attachment.ScaleAttachmentEvents;
import dev.khanhtimn.khuacraft.item.ModItemGroups;
import dev.khanhtimn.khuacraft.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(KhuaCraft.MODID)
public class KhuaCraft {
    public static final String MODID = "khuacraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public KhuaCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModItemGroups.CREATIVE_MODE_TABS.register(modEventBus);

        ModDataAttachment.DATA_ATTACHMENT_TYPES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        // Register Pehkui scale change callbacks → mirror into our attachment
        ScaleAttachmentEvents.registerScaleCallbacks();

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }
}
