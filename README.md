# Amethyst Ascension — Fabric 26.3

A Minecraft Fabric mod source project for Minecraft 26.3, with a custom purple amethyst icon and gameplay commands:

- `/ascend` — show the mod command overview
- `/ascend help` — show command help
- `/ascend bless` — apply short Night Vision, Speed, and Resistance effects
- `/ascend pulse` — emit a ring of portal particles
- `/ascend shards <1-16>` — receive amethyst shards
- A welcome message when joining a world/server

## Easiest build method (no Gradle installation on your PC)

This project includes a GitHub Actions workflow that builds the JAR on GitHub's computer using Java 25 and Gradle 9.6.0.

1. Sign in to GitHub and create a **new repository**. A public repository is fine; do not upload passwords or private keys.
2. Unzip this project on your PC.
3. Upload the **contents** of the unzipped `Amethyst-Ascension-Fabric-26.3` folder to the repository, including the hidden `.github` folder. If you use GitHub's website, ensure `.github/workflows/build.yml` is uploaded too.
4. Open the repository's **Actions** tab and select **Build Amethyst Ascension**.
5. Click **Run workflow** if it has not already run automatically after the upload.
6. Open the completed green workflow run. Under **Artifacts**, download `amethyst-ascension-mod` and unzip it. The built mod JAR will be inside.

If the workflow fails, open the failed run and copy the first red error section; it will show what needs fixing. This cloud build has not been executed or verified from this environment.

## Install the JAR

1. Make sure the Minecraft launcher profile uses **Fabric for Minecraft 26.3**.
2. Install the matching Fabric API version if the project requires it.
3. Press `Win + R`, enter `%appdata%\\.minecraft\\mods`, and press Enter. Create the `mods` folder if needed.
4. Put the built Amethyst Ascension JAR in that folder.
5. Launch the Fabric 26.3 profile. In a world with cheats enabled (or as an operator on a server), try `/ascend help`.

## Versions

- Minecraft: `26.3`
- Fabric Loader: `0.19.5`
- Fabric API: `0.164.0+26.3`
- Loom: `1.18-SNAPSHOT`
- Java toolchain: `25`
