package com.example.armorhud_fps;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class FPSElement {
    private FPSElement() {}

    public static void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        // Only show FPS when in-game
        if (minecraft.level == null) return;

        int fps = minecraft.getFps();
        String fpsText = fps + " FPS";

        int x = 2;
        int y = 2;

        int textWidth = minecraft.font.width(fpsText);
        graphics.fill(x - 1, y - 1, x + textWidth + 1, y + minecraft.font.lineHeight + 1, 0x66000000);
        graphics.text(minecraft.font, fpsText, x, y, 0xFFFFFFFF, true);
    }
}
