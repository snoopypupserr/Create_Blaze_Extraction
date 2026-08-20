# Blaze Extraction

Adds an output inventory slot to Blaze Spawners, enabling full Create automation of Blaze Burner filling.

---

## What it does

Vanilla Blaze Spawners have no inventory, making it impossible to automate Blaze Burner filling with Create. This mod fixes that by:

- Adding an output slot to every Blaze Spawner
- Making that slot accessible to Funnels, Belts, and other Create logistics blocks
- Supporting Create's Deployer as the filling mechanism

---

## Usage

### How to automate it using Create:

1. Aim a **Deployer** at a Blaze Spawner and supply it with **Empty Blaze Burners**
2. The Deployer "right-clicks" the spawner, fills a burner, and deposits it into the spawner's output slot
3. Attach a **Funnel** or **Belt with funnel** to the spawner to extract the filled burners automatically

> Only works on actual Blaze Spawners. The mod checks the spawner's entity type and ignores all others.

---

## Installation

1. Install [NeoForge 21.1.235+](https://neoforged.net/) for Minecraft 1.21.1
2. Download the latest `blaze_extraction-x.x.x.jar` from the [Releases](../../releases) page or directly trough Curseforge/Modrinth
3. Place the `.jar` file into your `mods/` folder
4. Launch the game

[Create 6.0.0+](https://www.curseforge.com/minecraft/mc-mods/create) is required for automation.

---

## Requirements

| Dependency | Version  | 
|------------|----------|
| Minecraft  | 1.21.1   | 
| NeoForge   | 21.1.235+ | 
| Create     | 6.0.0+ |  

---

## License

MIT
