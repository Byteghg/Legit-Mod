package com.example.armorhud_fps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class ArmorHudFPSClient implements ClientModInitializer {
    public static final String MOD_ID = "armorhud_fps";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getLogger().info("[ArmorHUD+FPS] Initializing...");

        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "fps_display"),
            FPSElement::extractRenderState
        );

        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "armor_display"),
            ArmorElement::extractRenderState
        );

        FabricLoader.getInstance().getLogger().info("[ArmorHUD+FPS] Registered HUD elements successfully.");
    }
}
