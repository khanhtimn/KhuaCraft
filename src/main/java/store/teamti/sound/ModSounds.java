package store.teamti.sound;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import store.teamti.KhuaCraft;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, KhuaCraft.MOD_ID);

    public static final Holder<SoundEvent> UNO_REVERSE = SOUND_EVENTS.register("uno_reverse", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> FART_REVERB = SOUND_EVENTS.register("fart_reverb", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> HUY_NGOC_DO = SOUND_EVENTS.register("huy_ngoc_do", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> SOI_ECH_EVERYNIGHT = SOUND_EVENTS.register("soi_ech_everynight", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> FREE_FIRE = SOUND_EVENTS.register("free_fire", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> HET_SUONG_CAN_GIO = SOUND_EVENTS.register("het_suong_can_gio", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> HET_XANG_CAN_SO = SOUND_EVENTS.register("het_xang_can_so", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> AI_CUNG_DU_THUA = SOUND_EVENTS.register("ai_cung_du_thua", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> VINFLOW = SOUND_EVENTS.register("vinflow", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> DANG_LONG_BIEN = SOUND_EVENTS.register("dang_long_bien", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> QUAN_AO_2HAND = SOUND_EVENTS.register("quan_ao_2hand", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> VIT_QUAY_LANG_SON = SOUND_EVENTS.register("vit_quay_lang_son", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> TSMT = SOUND_EVENTS.register("tsmt", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> PHO_TAI = SOUND_EVENTS.register("pho_tai", SoundEvent::createVariableRangeEvent);

}
