package store.teamti.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import store.teamti.effect.impl.ScaleEffect;
import store.teamti.effect.impl.ThicknessEffect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "onEffectRemoved", at = @At("HEAD"))
    public void onEffectRemovedTriggerer(MobEffectInstance effectInstance, CallbackInfo ci) {
        if (effectInstance.getEffect().value() instanceof ScaleEffect scaleEffect) {
            scaleEffect.onEffectRemoved((LivingEntity) (Object) this);
        }
        if (effectInstance.getEffect().value() instanceof ThicknessEffect thicknessEffect) {
            thicknessEffect.onEffectRemoved((LivingEntity) (Object) this);
        }
    }
}
