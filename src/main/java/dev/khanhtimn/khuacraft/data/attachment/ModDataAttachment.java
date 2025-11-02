package dev.khanhtimn.khuacraft.data.attachment;

import dev.khanhtimn.khuacraft.KhuaCraft;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachment {

    public static final DeferredRegister<AttachmentType<?>> DATA_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, KhuaCraft.MODID);

    public static final Supplier<AttachmentType<ScaleModifierAttachment>> SCALE_MODIFIERS = DATA_ATTACHMENT_TYPES.register(
            "scale_modifiers",
            () -> AttachmentType.builder(ScaleModifierAttachment::new)
                    .serialize(ScaleModifierAttachment.CODEC.codec())
                    .build()
    );
}
