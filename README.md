# Mob Tools

Mob Tools is a small spigot plugin with a couple utility functions to assist with spawn-proofing,
mob spawners, and finding wither cages.

## Spheres

`/mksphere <radius> [semisphere?] [force visible?]`

Summons a particle sphere at the player's location

- **radius**: `number`, sphere's radius
- **semisphere?**: `true` or `false`, only render the bottom half of the sphere
- **force visible?** `true` or `false`, force particle visibility at any range (by default particles are only visible from ~30 blocks)

This is useful for visualizing a spawn radius around an afk platform, simply run `/mksphere 128 true`.

Use `/cancelspheres` to despawn summoned spheres, and a server operator can use `/cancelspheres all` to remove every particle sphere.

![](screenshots/2020-12-10_19.13.42.png)
![](screenshots/2020-12-10_19.13.50.png)
![](screenshots/2020-12-10_19.13.57.png)
![](screenshots/2020-12-10_19.17.03.png)
![](screenshots/2020-12-10_19.17.12.png)
![](screenshots/2020-12-10_19.18.08.png)
![](screenshots/2020-12-10_23.06.00.png)
![](screenshots/2020-12-10_23.06.05.png)
![](screenshots/2020-12-10_23.15.59.png)
![](screenshots/2020-12-11_02.39.46.png)

## Spawn Visualization

`/showspawn` will visualize spawnable spaces around the player. Green particles represent safe blocks and white firework trails represent spawnable spaces.

Running `/showspawn` again will stop showing spawnable spaces.

![](screenshots/2020-12-15_17.47.48.png)
![](screenshots/2020-12-15_17.48.43.png)
![](screenshots/2020-12-15_17.57.12.png)
![](screenshots/2020-12-15_17.59.40.png)
![](screenshots/2020-12-20_16.59.27.png)
![](screenshots/2020-12-20_16.59.39.png)

## Wither Cage Finder

`/locatebedrockcage` will scan the area surrounding the player and locate a 3x3x3 bedrock cube in the nether. This formation is useful as a cage to trap and kill a wither.

![](screenshots/2020-12-20_16.40.36.png)
![](screenshots/2020-12-19_18.18.20.png)

![](screenshots/2020-12-19_17.55.39.png)
![](screenshots/2020-12-19_17.55.43.png)
![](screenshots/2020-12-20_16.40.26.png)
![](screenshots/2020-12-20_16.40.31.png)
![](screenshots/2020-12-20_16.40.45.png)
