package dev.khanhtimn.khuacraft.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.Objects;
import java.util.Optional;

/**
 * Serializable snapshot of Pehkui ScaleData.
 * This captures key fields we can safely apply through the public API.
 *
 * @param baseScale Core fields; minimal set we can reapply via public API methods
 */
public record ScaleSnapshot(float baseScale, float targetScale, int totalScaleTicks, @Nullable Boolean persistent,
                            @Nullable ResourceLocation easingId) {
    public static final MapCodec<ScaleSnapshot> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.FLOAT.fieldOf("base").forGetter(ScaleSnapshot::baseScale),
            Codec.FLOAT.fieldOf("target").forGetter(ScaleSnapshot::targetScale),
            Codec.INT.fieldOf("total_ticks").forGetter(ScaleSnapshot::totalScaleTicks),
            Codec.BOOL.optionalFieldOf("persistent").forGetter(s -> Optional.ofNullable(s.persistent())),
            ResourceLocation.CODEC.optionalFieldOf("easing").forGetter(s -> Optional.ofNullable(s.easingId()))
    ).apply(inst, (base, target, totalTicks, persistentOpt, easingOpt) ->
            new ScaleSnapshot(base, target, totalTicks, persistentOpt.orElse(null), easingOpt.orElse(null))
    ));

    public static ScaleSnapshot from(ScaleData sd) {
        // We cannot read total tick progress precisely from public API, but we can read total delay
        int ticks = sd.getScaleTickDelay();
        Boolean persist = sd.getPersistence();
        // Easing id lookup if present
        Float2FloatFunction easing = sd.getEasing();
        ResourceLocation easingId = easing == null ? null : ScaleRegistries.getId(ScaleRegistries.SCALE_EASINGS, easing);
        return new ScaleSnapshot(sd.getBaseScale(), sd.getTargetScale(), ticks, persist, easingId);
    }

    public void applyTo(ScaleType type, ScaleData sd, boolean notify) {
        // Apply order: delay, persistence, easing, base/target
        sd.setScaleTickDelay(this.totalScaleTicks);
        if (this.persistent != null) {
            sd.setPersistence(this.persistent);
        }
        if (this.easingId != null) {
            Float2FloatFunction easing = ScaleRegistries.getEntry(ScaleRegistries.SCALE_EASINGS, this.easingId);
            sd.setEasing(easing);
        } else {
            sd.setEasing(null);
        }
        // Set base first for immediate visual correctness, then target for transitions
        sd.setBaseScale(this.baseScale);
        sd.setTargetScale(this.targetScale);
        if (notify) {
            sd.onUpdate();
        }
    }

    public boolean isDifferentFrom(ScaleData sd) {
        if (Float.floatToIntBits(sd.getBaseScale()) != Float.floatToIntBits(baseScale)) return true;
        if (Float.floatToIntBits(sd.getTargetScale()) != Float.floatToIntBits(targetScale)) return true;
        if (sd.getScaleTickDelay() != totalScaleTicks) return true;
        if (!Objects.equals(sd.getPersistence(), persistent)) return true;
        Float2FloatFunction easing = sd.getEasing();
        ResourceLocation id = easing == null ? null : ScaleRegistries.getId(ScaleRegistries.SCALE_EASINGS, easing);
        return !Objects.equals(id, easingId);
    }
}
