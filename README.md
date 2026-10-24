# Amethyst Ascension

A Fabric mod project for Minecraft 26.3, with a purple amethyst theme.

## Features

- `/ascend` — show the command overview
- `/ascend help` — list the available abilities
- `/ascend bless` — grants night vision, speed, and resistance temporarily
- `/ascend pulse` — releases a ring of portal particles
- `/ascend shards <1-16>` — gives amethyst shards
- A welcome message when players join
- Custom 128×128 pixel-art icon

## Build in GitHub Actions (no local Gradle installation)

1. Upload the contents of this project to a GitHub repository, including `.github/workflows/build.yml`.
2. Open **Actions** and run **Build Amethyst Ascension** (or push a commit).
3. Download the `amethyst-ascension-mod` artifact from a successful run.
4. Extract the artifact and put the JAR in your Minecraft `mods` folder.

The workflow uses Java 25 and Gradle 9.7.0. The project targets Fabric Loader 0.19.5, Fabric API 0.164.0+26.3, and Minecraft 26.3.

## Notes

This source has been updated to address the Minecraft 26.3 API errors reported by the first cloud build. It still needs a successful GitHub Actions build and an in-game test before it can be described as verified. Install Fabric API for Minecraft 26.3 alongside the mod.
