# Mob Tools

This is a Minecraft plugin with some utilities to help with making mob farms and spawn-proofing the surrounding area. This plugin adds a command to draw a sphere around a player (or spawn radius around an afk platform) and a utility to display spawnable blocks around the player. It also adds a command to search the nether roof for a 3x3x3 bedrock "cage" for fighting the wither.

## Spheres

`/mksphere <radius> [semisphere?] [force visible?]`

Summons a particle sphere at the player's location

- **radius**: `number`, sphere's radius
- **semisphere?**: `true` or `false`, only render the bottom half of the sphere
- **force visible?** `true` or `false`, force particle visibility at any range (by default particles are only visible from ~30 blocks)

This is useful for visualizing a spawn radius around an afk platform, simply run `/mksphere 128 true`.

Use `/cancelspheres` to despawn summoned spheres, and a server operator can use `/cancelspheres all` to remove every particle sphere.

![](screenshots/2020-12-10_19.13.57.png)
Sphere showing the spawn radius around an afk platform. This mob farm is high off the ground so no mob proofing needed to be done.

![](screenshots/2020-12-10_23.06.05.png)
Spawn radius sphere being used to spawn-proof part of the nether.

![](screenshots/2020-12-10_23.15.59.png)
Spawn radius sphere identifying part of a nether roof cave in spawn range.


## Spawn Visualization

`/showspawn` will visualize spawnable spaces around the player. Green particles represent safe blocks and white firework trails represent spawnable spaces.

Running `/showspawn` again will stop showing spawnable spaces.

![](screenshots/2020-12-20_16.59.39.png)
Particles showing where mobs can spawn in a base.

![](screenshots/2020-12-20_16.59.27.png)
Low-light spawnable spaces between torches.

![](screenshots/2020-12-15_17.47.48.png)
Spawnable spaces on a warped fungus.

![](screenshots/2020-12-15_17.57.12.png)
Particles showing missed blocks in a previously spawn-proofed area.

## Wither Cage Finder

`/locatebedrockcage` will scan the area surrounding the player and locate a 3x3x3 bedrock cube in the nether. This formation is useful as a cage to trap and kill a wither.

![](screenshots/2020-12-20_16.40.36.png)
Wither trapped in a wither cage.

![](screenshots/2020-12-19_18.18.20.png)
Wither spawning machine used to spawn dozens of withers in a wither cage.
