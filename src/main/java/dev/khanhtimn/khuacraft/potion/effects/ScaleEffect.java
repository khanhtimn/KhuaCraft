package dev.khanhtimn.khuacraft.potion.effects;

import dev.khanhtimn.khuacraft.data.ModDataAttachment;
import dev.khanhtimn.khuacraft.data.attachment.ScaleModifierAttachment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.HashMap;
import java.util.Map;

public class ScaleEffect extends MobEffect {
    private final Map<ResourceLocation, LevelBasedValue> scaleMultipliers;

    public ScaleEffect(MobEffectCategory category, int color, Map<ResourceLocation, LevelBasedValue> scaleModifiers) {
        super(category, color);
        this.scaleMultipliers = new HashMap<>(scaleModifiers);
    }

    public static void onMobEffectRemoved(MobEffectEvent.Remove event) {
        if (event.getEntity().level().isClientSide() || event.getEffectInstance() == null) {
            return;
        }

        MobEffect effect = event.getEffectInstance().getEffect().value();

        if (effect instanceof ScaleEffect scaleEffect) {
            scaleEffect.removeEffectModifiers(event.getEntity());
        }
    }

    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity().level().isClientSide() || event.getEffectInstance() == null) {
            return;
        }

        MobEffect effect = event.getEffectInstance().getEffect().value();

        if (effect instanceof ScaleEffect scaleEffect) {
            scaleEffect.removeEffectModifiers(event.getEntity());
        }
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        super.onEffectStarted(entity, amplifier);

        if (entity.level().isClientSide()) {
            return;
        }

        ScaleModifierAttachment modifiers = entity.getData(ModDataAttachment.SCALE_MODIFIERS);
        String modifierName = getModifierName();

        // Apply all scale modifiers once when effect starts
        for (Map.Entry<ResourceLocation, LevelBasedValue> entry : scaleMultipliers.entrySet()) {
            ResourceLocation scaleTypeId = entry.getKey();
            LevelBasedValue levelBasedValue = entry.getValue();

            float multiplier = levelBasedValue.calculate(amplifier + 1);

            modifiers.setNamedModifier(scaleTypeId, modifierName, multiplier);

            applyEffectiveScale(entity, scaleTypeId, modifiers);
        }
    }

    private void removeEffectModifiers(LivingEntity entity) {
        if (entity.level().isClientSide()) {
            return;
        }

        ScaleModifierAttachment modifiers = entity.getData(ModDataAttachment.SCALE_MODIFIERS);
        String modifierName = getModifierName();

        for (ResourceLocation scaleTypeId : scaleMultipliers.keySet()) {
            modifiers.removeNamedModifier(scaleTypeId, modifierName);

            applyEffectiveScale(entity, scaleTypeId, modifiers);
        }

    }


    private String getModifierName() {
        return "potion:" + this.getDescriptionId();
    }

    private void applyEffectiveScale(LivingEntity entity, ResourceLocation scaleTypeId,
                                     ScaleModifierAttachment modifiers) {
        ScaleType scaleType = ScaleRegistries.SCALE_TYPES.get(scaleTypeId);
        if (scaleType == null) return;

        ScaleData scaleData = scaleType.getScaleData(entity);

        if (!modifiers.baseScales().containsKey(scaleTypeId)) {
            modifiers.setBaseScale(scaleTypeId, 1.0F);
        }

        float effectiveScale = modifiers.calculateEffectiveScale(scaleTypeId);

        scaleData.setTargetScale(effectiveScale);
        scaleData.markForSync(true);
    }

    public static class Builder {
        private final Map<ResourceLocation, LevelBasedValue> modifiers = new HashMap<>();

        public Builder add(ResourceLocation scaleType, LevelBasedValue multiplier) {
            modifiers.put(scaleType, multiplier);
            return this;
        }

        public Map<ResourceLocation, LevelBasedValue> build() {
            return modifiers;
        }
    }
}
