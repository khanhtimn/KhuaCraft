package store.teamti.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;

import java.util.function.Supplier;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, KhuaCraft.MOD_ID);

    public static final Supplier<SimpleParticleType> UNO_REVERSE_PARTICLE = PARTICLE_TYPES
            .register("uno_reverse_particle", () -> new SimpleParticleType(false));
}
