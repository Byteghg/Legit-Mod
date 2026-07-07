package com.example.armorhud_fps;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Renders the current FPS count in the top-left corner of the screen.
 */
public final class FPSElement {

    private FPSElement() {
    }

    public static void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        // Don't render if there's no world loaded or if the debug screen is active
        if (minecraft.level == null || minecraft.getDebugOverlay().showDebugScreen()) {
            return;
        }

        int fps = minecraft.getFps();
        String fpsText = fps + " FPS";

        int x = 2;
        int y = 2;

        // Semi-transparent background for readability
        int textWidth = minecraft.font.width(fpsText);
        graphics.fill(x - 1, y - 1, x + textWidth + 1, y + minecraft.font.lineHeight + 1, 0x66000000);

        // Draw the FPS text in white, with shadow
        graphics.text(minecraft.font, fpsText, x, y, 0xFFFFFFFF, true);
    }
}
