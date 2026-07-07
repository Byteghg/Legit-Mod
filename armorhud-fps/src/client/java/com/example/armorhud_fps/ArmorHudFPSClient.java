package com.example.armorhud_fps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

/**
 * Client initializer for ArmorHUD+FPS mod.
 * Registers custom HUD elements via Fabric's HUD API.
 * <p>
 * In 26.2, the HUD rendering uses a layered element system where each
 * element's {@code extractRenderState(GuiGraphicsExtractor, DeltaTracker)}
 * is called in a fixed order. We replace the vanilla held-item tooltip
 * with our armor display and insert the FPS counter near the end.
 */
public class ArmorHudFPSClient implements ClientModInitializer {
    public static final String MOD_ID = "armorhud_fps";

    @Override
    public void onInitializeClient() {
        // ── FPS counter ────────────────────────────────────────────
        // Attach it as the very last element so it renders on top.
        HudElementRegistry.addLast(
            Identifier.fromNamespaceAndPath(MOD_ID, "fps_display"),
            FPSElement::extractRenderState
        );

        // ── Armor display (replaces held-item tooltip) ─────────────
        // Replace the vanilla held-item tooltip element so our armor
        // display takes that slot in the render order.
        HudElementRegistry.replaceElement(
            VanillaHudElements.HELD_ITEM_TOOLTIP,
            old -> ArmorElement::extractRenderState
        );
    }
}
