# ArmorHUD+FPS — Minecraft 26.2 Fabric Mod

A lightweight client-side Fabric mod that displays:

- **FPS counter** in the top-left corner
- **Equipped armor** (helmet, chestplate, leggings, boots) with **durability values** stacked vertically in the bottom-right corner
- Replaces the vanilla held-item tooltip to keep the HUD clean

## Requirements

- Minecraft **26.2** (stable)
- Fabric Loader **≥0.19.3**
- Fabric API **≥0.152.0+26.2**
- Java **21**

## Build

```bash
cd armorhud-fps
./gradlew build
```

The built `.jar` will be in `build/libs/armorhud-fps-1.0.0.jar`.

## Install

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 26.2
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for 26.2 and place it in your `mods/` folder
3. Copy `armorhud-fps-1.0.0.jar` into the same `mods/` folder
4. Launch Minecraft with the Fabric profile

## Features

| Feature | Location |
|---|---|
| **FPS** | Top-left corner (white text, dark background) |
| **Armor items** | Bottom-right corner, stacked top→bottom: Helmet, Chestplate, Leggings, Boots |
| **Durability** | Coloured number next to each item (green ≥50%, yellow ≥25%, orange ≥10%, red <10%) |
| **Held-item tooltip** | Disabled — replaced by the armor display |
| **Performance** | Minimal overhead; only renders when a world is active |

## How It Works

The mod uses Fabric's `HudElementRegistry` API (introduced in Fabric API 0.150+) to attach custom HUD elements. The FPS display uses `Minecraft.fps`, the armor display reads equipment slots via `Player.getItemBySlot()`, and a Mixin disables the vanilla held-item tooltip to reduce visual clutter.
