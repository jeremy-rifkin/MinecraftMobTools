package dev.rifkin.MobTools;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnGenerator extends BukkitRunnable {
	private Player player;
	private final static Particle.DustOptions red_dust = new Particle.DustOptions(Color.RED, 1);
	private final static Particle.DustOptions green_dust = new Particle.DustOptions(Color.GREEN, 1);
	private int ticks_since_last;
	private final int green_ticks;
	SpawnGenerator(Player player) {
		this.player = player;
		ticks_since_last = 0;
		green_ticks = 20;
	}
	// Performance for checking blocks each iteration vs using memoization:
	// 2.1e7 ns for normal       (1.95 best)   (2.47 new base)
	// 1.6e7 ns for memoization  (1.49 best)   (1.70 new base) or 1.75?
	// 23.6% better performance with memoization, so that's used here
	@Override
	public void run() {
		Location location = player.getLocation();
		World w = player.getWorld();
		Location l = location.clone();
		int light_level = 15;
		switch(w.getEnvironment()) {
			case NORMAL:
			case NETHER:
				light_level = 7;
				break;
			case THE_END:
				light_level = 11;
				break;
		}
		// as experimentally determined, the most cache-friendly loop orders are
		// x z y
		// z x y
		// https://spark.lucko.me/#5Mx0WacxmW
		// https://spark.lucko.me/#gI9ywZcNoj
		// https://spark.lucko.me/#uq3UFrj3ku
		// very close performance between the two, just as long as y is in the most inner loop
		// this iteration method yields ~11.5% better performance
		// I think blocks are stored internally in chunks and further performance could be squeezed out by blocking
		// the region into chunks and then iterating those chunks
		for(int z = location.getBlockZ() - 32; z < location.getBlockZ() + 32; z++) {
			for(int x = location.getBlockX() - 32; x < location.getBlockX() + 32; x++) {
				for(int y = location.getBlockY() - 20; y < location.getBlockY() + 20; y++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					// Get block below
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					// If we're on a full block we should make an indicator
					if(SolidBlocks.lookup(bb)) {
						// Get block above
						l.add(0, 1, 0);
						Block ba = w.getBlockAt(l);
						l.add(0, -1, 0);
						// Check for valid spawning space
						if(
								// Block below needs be opaque and not be bedrock
								bb.getType().isOccluding() &&
								bb.getType() != Material.BEDROCK &&
								// Current block needs to be air and be below the light threshold
								b.getType().isAir() &&
								b.getLightFromBlocks() <= light_level &&
								// Block above needs to be opaque, free of liquid, and not obstruct a mob's bounding box
								!ba.getType().isOccluding() &&
								!ba.isLiquid() &&
								!SolidBlocks.lookup(ba)
							) {
							//w.spawnParticle(Particle.SPELL_WITCH, l, 1, 0, 0, 0, 0);
							//w.spawnParticle(Particle.REDSTONE, l, 1, 0, 0, 0, 0, red_dust);
							w.spawnParticle(Particle.FIREWORKS_SPARK, l, 1, 0, 0, 0, 0);
						} else {
							if(ticks_since_last == 0) {
								w.spawnParticle(Particle.VILLAGER_HAPPY, l, 1, 0, 0, 0, 0);
							}
							//w.spawnParticle(Particle.REDSTONE, l, 1, 0, 0, 0, 0, green_dust);
						}
					}
				}
			}
		}
		ticks_since_last += 10;
		if(ticks_since_last >= green_ticks) {
			ticks_since_last = 0;
		}
	}
}
