package dev.rifkin.MobTools;

import org.bukkit.*;
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
	/*private boolean isGlass(Block b) {
		switch(b.getType()) {
			case BLACK_STAINED_GLASS:
			case BLUE_STAINED_GLASS:
			case BROWN_STAINED_GLASS:
			case CYAN_STAINED_GLASS:
			case GLASS:
			case GLOWSTONE:
			case GRAY_STAINED_GLASS:
			case GREEN_STAINED_GLASS:
			case LIGHT_BLUE_STAINED_GLASS:
			case LIGHT_GRAY_STAINED_GLASS:
			case LIME_STAINED_GLASS:
			case MAGENTA_STAINED_GLASS:
			case ORANGE_STAINED_GLASS:
			case PINK_STAINED_GLASS:
			case PURPLE_STAINED_GLASS:
			case REDSTONE_LAMP:
			case RED_STAINED_GLASS:
			case SEA_LANTERN:
			case WHITE_STAINED_GLASS:
			case YELLOW_STAINED_GLASS:
				return true;
		}
		return false;
	}*/
	@Override
	// 2.1e7 ns for normal  (1.95 best)       (2.47 new base)
	// 1.6e7 ns for memoization (1.49 best)   (1.70 new base) or 1.75?
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
		for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
			for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
				for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					//l.setX(x);
					//l.setY(y);
					//l.setZ(z);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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

		check_a();
		check_b();
		check_c();
		check_d();
		check_e();
		check_f();

		check_e();
		check_b();
		check_c();
		check_d();
		check_a();
		check_f();

		ticks_since_last += 10;
		if(ticks_since_last >= green_ticks) {
			ticks_since_last = 0;
		}
	}
	// x y z
	private void check_a() {
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
		for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
			for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
				for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
	// x z y
	private void check_b() {
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
		for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
			for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
				for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
	// y x z
	private void check_c() {
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
		for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
			for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
				for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
	// y z x
	private void check_d() {
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
		for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
			for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
				for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
	// z x y
	private void check_e() {
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
		for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
			for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
				for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
	// z y x
	private void check_f() {
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
		for(int z = location.getBlockZ() - 64; z < location.getBlockZ() + 64; z++) {
			for(int y = location.getBlockY() - 32; y < location.getBlockY() + 32; y++) {
				for(int x = location.getBlockX() - 64; x < location.getBlockX() + 64; x++) {
					l.setX(x + 0.5);
					l.setY(y + 0.5);
					l.setZ(z + 0.5);
					Block b = w.getBlockAt(l);
					// Can't spawn in a block
					if(SolidBlocks.lookup(b)) {
						continue;
					}
					l.add(0, -1, 0);
					Block bb = w.getBlockAt(l);
					l.add(0, 1, 0);
					if(SolidBlocks.lookup(bb)) {
						if(bb.getType().isOccluding() && bb.getType() != Material.BEDROCK && b.getType().isAir() && b.getLightLevel() <= light_level) {
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
	}
}
