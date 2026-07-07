package com.example.armorhud_fps;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 * Renders the player's equipped armor items with durability values
 * stacked vertically in the bottom-right corner of the screen.
 * <p>
 * The items are rendered from top to bottom in this order:
 * Helmet, Chestplate, Leggings, Boots.
 * Each row shows the item icon and the current/max durability.
 */
public final class ArmorElement {

    private static final int ITEM_SIZE = 18;
    private static final int PADDING = 2;
    private static final int BACKGROUND_COLOR = 0x66000000;

    private ArmorElement() {
    }

    public static void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player == null) {
            return;
        }

        // Get armor items in order: Helmet → Chestplate → Leggings → Boots
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        ItemStack[] armorSlots = { helmet, chestplate, leggings, boots };
        int visibleRows = 0;
        for (ItemStack stack : armorSlots) {
            if (!stack.isEmpty()) {
                visibleRows++;
            }
        }

        // If no armor is equipped, show nothing
        if (visibleRows == 0) {
            return;
        }

        // Calculate screen dimensions from the graphics context
        int screenWidth = graphics.guiWidth();
        int screenHeight = graphics.guiHeight();

        // Find the widest row for consistent background width
        int maxRowWidth = 0;
        for (ItemStack stack : armorSlots) {
            if (!stack.isEmpty()) {
                String durabilityText = getDurabilityText(stack);
                int rowWidth = ITEM_SIZE + PADDING + minecraft.font.width(durabilityText);
                if (rowWidth > maxRowWidth) {
                    maxRowWidth = rowWidth;
                }
            }
        }

        // Starting Y: bottom of screen, going upward
        int startY = screenHeight - (armorSlots.length * (ITEM_SIZE + PADDING)) - PADDING;

        // Render each armor row from top to bottom
        for (ItemStack stack : armorSlots) {
            int x = screenWidth - maxRowWidth - PADDING;
            int y = startY;
            startY += ITEM_SIZE + PADDING;

            if (stack.isEmpty()) {
                continue;
            }

            // Background rectangle for this row
            graphics.fill(x, y, x + maxRowWidth, y + ITEM_SIZE, BACKGROUND_COLOR);

            // Render the item icon
            graphics.item(stack, x, y);
            graphics.itemDecorations(minecraft.font, stack, x, y);

            // Render durability text
            String durabilityText = getDurabilityText(stack);
            int textX = x + ITEM_SIZE + PADDING;
            int textY = y + (ITEM_SIZE - minecraft.font.lineHeight) / 2;
            int color = getDurabilityColor(stack);

            graphics.text(minecraft.font, durabilityText, textX, textY, color, true);
        }
    }

    /**
     * Returns a formatted durability string for the given item stack.
     * e.g. "450/500" or "∞" for unbreakable items.
     */
    private static String getDurabilityText(ItemStack stack) {
        if (!stack.isDamageableItem()) {
            return "\u221E"; // infinity symbol
        }
        int current = stack.getMaxDamage() - stack.getDamageValue();
        int max = stack.getMaxDamage();
        return current + "/" + max;
    }

    /**
     * Returns a colour for the durability text based on how damaged the item is.
     * Green ≥ 50%, yellow ≥ 25%, orange ≥ 10%, red < 10%.
     */
    private static int getDurabilityColor(ItemStack stack) {
        if (!stack.isDamageableItem()) {
            return 0xFFAAAAAA; // grey for unbreakable
        }
        double ratio = (double) (stack.getMaxDamage() - stack.getDamageValue()) / stack.getMaxDamage();
        if (ratio > 0.5) {
            return 0xFF55FF55; // green
        } else if (ratio > 0.25) {
            return 0xFFFFFF55; // yellow
        } else if (ratio > 0.1) {
            return 0xFFFFAA00; // orange
        } else {
            return 0xFFFF5555; // red
        }
    }
}
