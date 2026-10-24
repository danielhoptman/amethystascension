# Amethyst Ascension — Fabric 26.3

A purple-crystal themed Fabric mod project for Minecraft 26.3. Built for the real Fabric Loader/API (not placeholder stubs).

## Included in this expanded build
- Four registered blocks: Amethyst Ore, Deepslate Amethyst Ore, Polished Amethyst Block, and glowing Crystal Altar.
- Four custom items/relics: Amethyst Blade, Crystal Wand, Ascension Core, and Crystal Apple.
- Pixel textures, blockstates, block/item models, loot tables, and crafting recipes.
- Built-in title-logo texture override that brands the Minecraft title logo as AMETHYST ASCENSION.
- Join title/subtitle welcome sequence and purple welcome messages.
- Built-in ranks with scoreboard team prefixes that appear in player nametags: Owner, Admin, Mod, Ascendant, Member.
- Powers: bless, pulse, dash, heal, shield, levitate, night vision, shard grants, and custom-item grants.
- Operator-only rank command: `/ascend rank set <player> <rank>`.

## Commands
- `/ascend help`
- `/ascend bless`, `/ascend pulse`, `/ascend dash`, `/ascend heal`, `/ascend shield`, `/ascend levitate`, `/ascend night`
- `/ascend shards <1-64>`
- `/ascend give <ore|deepore|block|blade|wand|core|apple>`
- `/ascend rank set <player> <owner|admin|mod|ascendant|member>` (operator only)

## Build in GitHub Actions
1. Upload/commit the contents of this project to the repository root, including `.github/workflows/build.yml`.
2. Open Actions and run **Build Amethyst Ascension**.
3. Download the `amethyst-ascension-mod` artifact from a successful run.
4. Put the resulting JAR in `.minecraft/mods` and launch Fabric 26.3 with Fabric API installed.

## Important status notes
This source has not been compiled in the current authoring environment. The Minecraft 26.3 API has recently changed; use GitHub Actions to compile it and share any compiler output if it identifies a signature that changed. The custom ore blocks are registered and obtainable through `/ascend give ore` and `/ascend give deepore`; **natural underground ore generation is not yet wired in**. The title logo uses a vanilla texture override, so it replaces the standard Minecraft title logo while this mod is installed. The custom relics are currently registered items with durability/food properties; command powers are the active abilities in this iteration.
