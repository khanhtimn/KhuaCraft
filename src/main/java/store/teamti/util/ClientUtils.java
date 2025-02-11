package store.teamti.util;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import store.teamti.KhuaCraft;

public class ClientUtils {
    public static MutableComponent getLinkText(String link) {
        return Component.literal(link).withStyle(
                style -> style.withClickEvent(new ClickEvent(
                        ClickEvent.Action.OPEN_URL, link
                )).withUnderlined(true)
        );
    }

    public static void showWarningOnJoin(PlayerTickEvent.Pre event) {
        if (KhuaCraft.displayPreviewWarning) {
            KhuaCraft.displayPreviewWarning = false;
            event.getEntity().sendSystemMessage(
                    Component.translatable(KhuaCraft.MOD_ID + ".preview").append(
                            getLinkText(KhuaCraft.ISSUE_LINK)
                    )
            );
        }
    }
}
