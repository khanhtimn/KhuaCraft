package dev.khanhtimn.khuacraft.data.attachment;

import dev.khanhtimn.khuacraft.KhuaCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.Map;

@EventBusSubscriber(modid = KhuaCraft.MODID)
public class ScaleAttachmentEvents {

    /**
     * Register Pehkui scale change callbacks → mirror to attachment
     */
    public static void registerScaleCallbacks() {
        // Force-load built-in ScaleTypes so registry is populated
        // no-op; triggers class init

        for (Map.Entry<ResourceLocation, ScaleType> entry : ScaleRegistries.SCALE_TYPES.entrySet()) {
            final ResourceLocation typeId = entry.getKey();
            final ScaleType type = entry.getValue();
            type.getScaleChangedEvent().add(ScaleAttachmentEvents::onPehkuiScaleChanged);
        }
    }

    private static void onPehkuiScaleChanged(ScaleData sd) {
        final Entity e = sd.getEntity();
        if (e == null || e.level().isClientSide) return;
        if (!(e.level() instanceof ServerLevel)) return;
        if (ScaleBridge.isApplying(e.getUUID())) return; // prevent echo

        final ScaleType type = sd.getScaleType();
        final ResourceLocation typeId = ScaleRegistries.getId(ScaleRegistries.SCALE_TYPES, type);
        if (typeId == null) return;

        // Mirror to attachment and broadcast
        ScaleBridge.mirrorFromPehkui(e, typeId);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        seedFromPehkui(player);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        // Attachment was copied due to copyOnDeath; re-apply runtime state in Pehkui
        ScaleBridge.applyAll(player);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) return;
        if (event.getEntity() instanceof ServerPlayer player) {
            seedFromPehkui(player);
        }
    }

    private static void seedFromPehkui(Entity entity) {
        // Fill attachment from current Pehkui values and sync once
        ScaleAttachment att = entity.getData(ModDataAttachment.SCALES);
        // Ensure types loaded
        for (Map.Entry<ResourceLocation, ScaleType> entry : ScaleRegistries.SCALE_TYPES.entrySet()) {
            ScaleType type = entry.getValue();
            ScaleData sd = type.getScaleData(entity);
            ScaleSnapshot snap = ScaleSnapshot.from(sd);
            att.putSnapshot(entry.getKey(), snap);
        }
    }
}
