package store.teamti.sound.impl;

import io.netty.buffer.ByteBuf;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.function.IntFunction;

/**
 * @author mekanism
 * */
public abstract class PlayerSound extends AbstractTickableSoundInstance {

    @NotNull
    private final WeakReference<Player> playerReference;
    private final int subtitleFrequency;
    private float lastX;
    private float lastY;
    private float lastZ;

    private float fadeUpStep = 0.1f;
    private float fadeDownStep = 0.1f;
    private int consecutiveTicks;

    public PlayerSound(@NotNull Player player, @NotNull SoundEvent sound) {
        this(player, sound, 3 * SharedConstants.TICKS_PER_SECOND);
        //Set it to repeat the subtitle every 3 seconds the sound is constantly playing
    }

    public PlayerSound(@NotNull Player player, @NotNull SoundEvent sound, int subtitleFrequency) {
        super(sound, SoundSource.PLAYERS, player.getRandom());
        this.playerReference = new WeakReference<>(player);
        this.subtitleFrequency = subtitleFrequency;
        this.lastX = (float) player.getX();
        this.lastY = (float) player.getY();
        this.lastZ = (float) player.getZ();
        this.delay = 0;

        // N.B. the volume must be > 0 on first time it's processed by sound system or else it will not
        // get registered for tick events.
        this.volume = 0.1F;
    }

    @Nullable
    private Player getPlayer() {
        return playerReference.get();
    }

    protected void setFade(float fadeUpStep, float fadeDownStep) {
        this.fadeUpStep = fadeUpStep;
        this.fadeDownStep = fadeDownStep;
    }

    @Override
    public double getX() {
        //Gracefully handle the player becoming null if this object is kept around after update marks us as donePlaying
        Player player = getPlayer();
        if (player != null) {
            this.lastX = (float) player.getX();
        }
        return this.lastX;
    }

    @Override
    public double getY() {
        //Gracefully handle the player becoming null if this object is kept around after update marks us as donePlaying
        Player player = getPlayer();
        if (player != null) {
            this.lastY = (float) player.getY();
        }
        return this.lastY;
    }

    @Override
    public double getZ() {
        //Gracefully handle the player becoming null if this object is kept around after update marks us as donePlaying
        Player player = getPlayer();
        if (player != null) {
            this.lastZ = (float) player.getZ();
        }
        return this.lastZ;
    }

    @Override
    public void tick() {
        Player player = getPlayer();
        if (player == null || !player.isAlive()) {
            stop();
            volume = 0.0F;
            consecutiveTicks = 0;
            return;
        }

        if (shouldPlaySound(player) && player.level().tickRateManager().runsNormally()) {
            if (volume < 1.0F) {
                // If we weren't max volume, start fading up
                volume = Math.min(1.0F, volume + fadeUpStep);
            }
            if (consecutiveTicks % subtitleFrequency == 0) {
                SoundManager soundHandler = Minecraft.getInstance().getSoundManager();
                if (!soundHandler.soundEngine.listeners.isEmpty()) {
                    float scaledVolume = Math.max(volume, 1.0F) * (float) sound.getAttenuationDistance();
                    float range = !relative && attenuation != SoundInstance.Attenuation.NONE ? scaledVolume : Float.POSITIVE_INFINITY;
                    for (SoundEventListener soundEventListener : soundHandler.soundEngine.listeners) {
                        WeighedSoundEvents soundEventAccessor = resolve(soundHandler);
                        soundEventListener.onPlaySound(this, soundEventAccessor, range);
                    }
                }
                consecutiveTicks = 1;
            } else {
                consecutiveTicks++;
            }
        } else if (volume > 0.0F) {
            consecutiveTicks = 0;
            // Not yet fully muted, fade down
            volume = Math.max(0.0F, volume - fadeDownStep);
        }
    }

    public abstract boolean shouldPlaySound(@NotNull Player player);

    @Override
    public float getVolume() {
        return super.getVolume();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        Player player = getPlayer();
        if (player == null) {
            return super.canPlaySound();
        }
        return !player.isSilent();
    }

    public enum SoundType {
        INNER_CONSCIENCE(0);

        private final int id;

        SoundType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static final IntFunction<SoundType> BY_ID = ByIdMap.continuous(SoundType::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        public static final StreamCodec<ByteBuf, SoundType> CODEC = ByteBufCodecs.idMapper(BY_ID, SoundType::getId);
    }
}
