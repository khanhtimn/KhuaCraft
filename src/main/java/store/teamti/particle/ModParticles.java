package store.teamti.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;
import store.teamti.particle.impl.UnoReverseParticle;

import java.util.function.Supplier;

@EventBusSubscriber(modid = KhuaCraft.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, KhuaCraft.MOD_ID);

    public static final Supplier<SimpleParticleType> UNO_REVERSE_PARTICLE = PARTICLE_TYPES
            .register("uno_reverse_particle", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UNO_REVERSE_PARTICLE.get(), UnoReverseParticle.Provider::new);
    }
}
