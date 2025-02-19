package store.teamti.enchantment.impl;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public record ApplyMobEffect(
        Holder<MobEffect> toApply
) implements EnchantmentLocationBasedEffect {

    public static final MapCodec<ApplyMobEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MobEffect.CODEC.fieldOf("to_apply").forGetter(ApplyMobEffect::toApply)
            ).apply(instance, ApplyMobEffect::new)
    );

    @Override
    public void onChangedBlock(
            @NotNull ServerLevel serverLevel,
            int enchantmentLevel,
            @NotNull EnchantedItemInUse enchantedItemInUse,
            @NotNull Entity entity,
            @NotNull Vec3 vec3,
            boolean b
    ) {
        if (entity instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(toApply, -1, enchantmentLevel - 1, true, false, false));
        }
    }

    @Override
    public void onDeactivated(
            @NotNull EnchantedItemInUse item,
            @NotNull Entity entity,
            @NotNull Vec3 pos,
            int enchantmentLevel
    ) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.removeEffect(toApply);
        }
    }

    @Override
    public @NotNull MapCodec<ApplyMobEffect> codec() {
        return CODEC;
    }
}
