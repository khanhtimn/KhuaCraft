package dev.khanhtimn.khuacraft.particle;

import dev.khanhtimn.khuacraft.KhuaCraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, KhuaCraft.MODID);
    public static final Supplier<SimpleParticleType> UNO_REVERSE_PARTICLE = PARTICLE_TYPES
            .register("uno_reverse_particle", () -> new SimpleParticleType(false));

}
