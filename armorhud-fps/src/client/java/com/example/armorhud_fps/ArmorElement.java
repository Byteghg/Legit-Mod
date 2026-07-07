package com.example.armorhud_fps;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public final class ArmorElement {
    private static final int ITEM_SIZE = 18;
    private static final int PADDING = 2;
    private static final int BG = 0x66000000;

    private ArmorElement() {}

    public static void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Armor order: Helmet, Chestplate, Leggings, Boots
        ItemStack[] armor = {
            mc.player.getItemBySlot(EquipmentSlot.HEAD),
            mc.player.getItemBySlot(EquipmentSlot.CHEST),
            mc.player.getItemBySlot(EquipmentSlot.LEGS),
            mc.player.getItemBySlot(EquipmentSlot.FEET)
        };

        int screenW = graphics.guiWidth();
        int screenH = graphics.guiHeight();

        // Compute max row width
        int maxW = 0;
        for (ItemStack s : armor) {
            if (s.isEmpty()) continue;
            String dur = durText(s);
            int w = ITEM_SIZE + PADDING + mc.font.width(dur);
            if (w > maxW) maxW = w;
        }

        if (maxW == 0) return; // no armor

        int y = screenH - armor.length * (ITEM_SIZE + PADDING) - PADDING;
        for (ItemStack s : armor) {
            int x = screenW - maxW - PADDING;
            if (!s.isEmpty()) {
                graphics.fill(x, y, x + maxW, y + ITEM_SIZE, BG);
                graphics.item(s, x, y);
                graphics.itemDecorations(mc.font, s, x, y);

                String d = durText(s);
                int tx = x + ITEM_SIZE + PADDING;
                int ty = y + (ITEM_SIZE - mc.font.lineHeight) / 2;
                int col = durColor(s);
                graphics.text(mc.font, d, tx, ty, col, true);
            }
            y += ITEM_SIZE + PADDING;
        }
    }

    private static String durText(ItemStack s) {
        if (!s.isDamageableItem()) return "\u221E";
        return (s.getMaxDamage() - s.getDamageValue()) + "/" + s.getMaxDamage();
    }

    private static int durColor(ItemStack s) {
        if (!s.isDamageableItem()) return 0xFFAAAAAA;
        double r = (double)(s.getMaxDamage() - s.getDamageValue()) / s.getMaxDamage();
        if (r > 0.5) return 0xFF55FF55;
        if (r > 0.25) return 0xFFFFFF55;
        if (r > 0.1) return 0xFFFFAA00;
        return 0xFFFF5555;
    }
}
