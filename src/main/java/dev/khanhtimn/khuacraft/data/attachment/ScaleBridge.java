package dev.khanhtimn.khuacraft.data.attachment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility methods to bridge between our NeoForge data attachment and Pehkui's runtime ScaleData.
 */
public final class ScaleBridge {
    // Reentrancy/loop-prevention during two-way sync
    private static final Set<UUID> APPLYING = ConcurrentHashMap.newKeySet();

    private ScaleBridge() {
    }

    public static boolean isApplying(UUID id) {
        return APPLYING.contains(id);
    }

    public static void applyAll(Entity entity) {
        UUID id = entity.getUUID();
        if (!APPLYING.add(id)) return;
        try {
            ScaleAttachment att = entity.getData(ModDataAttachment.SCALES);
            for (Map.Entry<ResourceLocation, ScaleSnapshot> e : att.scales().entrySet()) {
                ResourceLocation typeId = e.getKey();
                ScaleType type = ScaleRegistries.SCALE_TYPES.get(typeId);
                if (type == null) continue;
                ScaleData sd = type.getScaleData(entity);
                e.getValue().applyTo(type, sd, true);
            }
        } finally {
            APPLYING.remove(id);
        }
    }

    public static void setScale(Entity entity, ResourceLocation typeId, float value, Integer totalTicks, ResourceLocation easingId) {
        UUID id = entity.getUUID();
        if (!APPLYING.add(id)) return;
        try {
            ScaleType type = ScaleRegistries.SCALE_TYPES.get(typeId);
            if (type == null) return;

            // Update Pehkui first
            ScaleData sd = type.getScaleData(entity);
            if (totalTicks != null) sd.setScaleTickDelay(totalTicks);
            if (easingId != null) sd.setEasing(ScaleRegistries.getEntry(ScaleRegistries.SCALE_EASINGS, easingId));
            sd.setBaseScale(value);
            sd.setTargetScale(value);
            sd.onUpdate();

            // Mirror into attachment and sync
            ScaleAttachment att = entity.getData(ModDataAttachment.SCALES);
            int ticks = totalTicks != null ? totalTicks : sd.getScaleTickDelay();
            ScaleSnapshot snapshot = new ScaleSnapshot(value, value, ticks, sd.getPersistence(), easingId);
            att.putSnapshot(typeId, snapshot);
            entity.syncData(ModDataAttachment.SCALES.get());
        } finally {
            APPLYING.remove(id);
        }
    }

    public static void mirrorFromPehkui(Entity entity, ResourceLocation typeId) {
        UUID id = entity.getUUID();
        if (isApplying(id)) return; // skip echo
        ScaleType type = ScaleRegistries.SCALE_TYPES.get(typeId);
        if (type == null) return;
        ScaleData sd = type.getScaleData(entity);
        ScaleSnapshot snap = ScaleSnapshot.from(sd);
        ScaleAttachment att = entity.getData(ModDataAttachment.SCALES);
        att.putSnapshot(typeId, snap);
        entity.syncData(ModDataAttachment.SCALES.get());
    }
}
