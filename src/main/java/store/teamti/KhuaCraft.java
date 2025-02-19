package store.teamti;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import store.teamti.effect.ModEffects;
import store.teamti.enchantment.ModEnchantmentEffects;
import store.teamti.item.ModItemGroups;
import store.teamti.item.ModItems;
import store.teamti.network.ModNetwork;
import store.teamti.particle.ModParticles;
import store.teamti.potion.ModPotions;
import store.teamti.sound.ModSounds;
import store.teamti.util.ModScales;

@Mod(KhuaCraft.MOD_ID)
public class KhuaCraft
{
    public static final String MOD_ID = "khuacraft";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ISSUE_LINK = "https://github.com/khanhtimn/KhuaCraft/issues";
    public static boolean displayPreviewWarning = true;

    public KhuaCraft(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModItemGroups.CREATIVE_MODE_TABS.register(modEventBus);

        ModParticles.PARTICLE_TYPES.register(modEventBus);

        ModSounds.SOUND_EVENTS.register(modEventBus);

        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);

        ModEnchantmentEffects.ENTITY_ENCHANTMENT_EFFECTS.register(modEventBus);
        ModEnchantmentEffects.LOCATION_BASED_ENCHANTMENT_EFFECTS.register(modEventBus);

        modEventBus.addListener(ModNetwork::init);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModScales.init();
    }
}
