package dev.khanhtimn.khuacraft.data.attachment;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.HashMap;
import java.util.Map;

public class ScaleSyncHandler implements AttachmentSyncHandler<ScaleAttachment> {
    @Override
    public void write(RegistryFriendlyByteBuf buf, ScaleAttachment attachment, boolean initialSync) {
        Map<ResourceLocation, ScaleSnapshot> map = attachment.scales();
        buf.writeVarInt(map.size());
        for (Map.Entry<ResourceLocation, ScaleSnapshot> e : map.entrySet()) {
            buf.writeResourceLocation(e.getKey());
            ScaleSnapshot s = e.getValue();
            buf.writeFloat(s.baseScale());
            buf.writeFloat(s.targetScale());
            buf.writeVarInt(s.totalScaleTicks());
            // persistent (nullable)
            Boolean p = s.persistent();
            buf.writeBoolean(p != null);
            if (p != null) buf.writeBoolean(p);
            // easing (optional)
            ResourceLocation easing = s.easingId();
            buf.writeBoolean(easing != null);
            if (easing != null) buf.writeResourceLocation(easing);
        }
    }

    @Override
    public ScaleAttachment read(@NotNull IAttachmentHolder holder, RegistryFriendlyByteBuf buf, ScaleAttachment previousValue) {
        int size = buf.readVarInt();
        Map<ResourceLocation, ScaleSnapshot> map = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            ResourceLocation id = buf.readResourceLocation();
            float base = buf.readFloat();
            float target = buf.readFloat();
            int totalTicks = buf.readVarInt();
            Boolean persistent = null;
            if (buf.readBoolean()) {
                persistent = buf.readBoolean();
            }
            ResourceLocation easing = null;
            if (buf.readBoolean()) {
                easing = buf.readResourceLocation();
            }
            map.put(id, new ScaleSnapshot(base, target, totalTicks, persistent, easing));
        }
        ScaleAttachment updated = new ScaleAttachment(map);

        // If client and the holder is an entity, apply snapshots to client-side Pehkui to update visuals
        if (holder instanceof Entity entity && entity.level().isClientSide) {
            for (Map.Entry<ResourceLocation, ScaleSnapshot> e : map.entrySet()) {
                ResourceLocation typeId = e.getKey();
                ScaleType type = ScaleRegistries.SCALE_TYPES.get(typeId);
                if (type == null) continue;
                ScaleData sd = type.getScaleData(entity);
                e.getValue().applyTo(type, sd, true);
            }
        }

        return updated;
    }

    @Override
    public boolean sendToPlayer(@NotNull IAttachmentHolder holder, @NotNull ServerPlayer to) {
        // Allow NeoForge to send to all tracking players by default for the holder type
        return true;
    }
}
