package com.example.armorhud_fps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class ArmorHudFPSClient implements ClientModInitializer {
    public static final String MOD_ID = "armorhud_fps";

    @Override
    public void onInitializeClient() {
        // FPS counter — attach before chat
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "fps_display"),
            FPSElement::extractRenderState
        );

        // Armor display — also before chat
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "armor_display"),
            ArmorElement::extractRenderState
        );
    }
}
