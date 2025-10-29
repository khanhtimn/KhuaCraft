package dev.khanhtimn.khuacraft.data.attachment;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ScaleAttachment(Map<ResourceLocation, ScaleSnapshot> scales) {
    public static final MapCodec<ScaleAttachment> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.either(
                                    Codec.unboundedMap(ResourceLocation.CODEC, ScaleSnapshot.CODEC.codec()),
                                    Codec.unboundedMap(ResourceLocation.CODEC, Codec.FLOAT)
                                            .xmap(
                                                    legacy -> {
                                                        Map<ResourceLocation, ScaleSnapshot> out = new HashMap<>();
                                                        legacy.forEach((k, v) -> out.put(k, new ScaleSnapshot(v, v, 20, null, null)));
                                                        return out;
                                                    },
                                                    snaps -> {
                                                        Map<ResourceLocation, Float> back = new HashMap<>();
                                                        snaps.forEach((k, v) -> back.put(k, v.baseScale()));
                                                        return back;
                                                    }
                                            )
                            )
                            .xmap(
                                    either -> either.map(m -> m, m -> m),
                                    Either::left
                            )
                            .optionalFieldOf("scales", Map.of())
                            .forGetter(ScaleAttachment::scales)
            ).apply(instance, ScaleAttachment::new)
    );

    public ScaleAttachment() {
        this(new HashMap<>());
    }

    public ScaleAttachment(Map<ResourceLocation, ScaleSnapshot> scales) {
        this.scales = new HashMap<>(scales);
    }

    /**
     * Convenience: return base scale from snapshot, or default if none.
     */
    public float getOrDefault(ResourceLocation scaleId, float defaultValue) {
        ScaleSnapshot s = scales.get(scaleId);
        return s != null ? s.baseScale() : defaultValue;
    }

    /**
     * Convenience: set scale instantly using a basic snapshot.
     * Note: for transitions/easing, prefer putting a full ScaleSnapshot.
     */
    public void set(ResourceLocation scaleId, float value) {
        scales.put(scaleId, new ScaleSnapshot(value, value, 20, null, null));
    }

    public void putSnapshot(ResourceLocation scaleId, ScaleSnapshot snapshot) {
        scales.put(scaleId, snapshot);
    }

    public ScaleSnapshot getSnapshot(ResourceLocation scaleId) {
        return scales.get(scaleId);
    }
}
