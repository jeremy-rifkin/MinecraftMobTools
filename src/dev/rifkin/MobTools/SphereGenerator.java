package dev.rifkin.MobTools;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SphereGenerator extends BukkitRunnable {
	// Pre-computing particle coordinates yields a 230% increase in performance
	// Reducing branch prediction yields another 100% increase in performance
	// Total performance increase over the most trivial method is 570%
	// https://spark.lucko.me/#ltVfEkTCjo
	//  a() - loop over points with if(force_vis) inside the loop
	//  b() - if(force_vis) outside the loop
	//  c() - pre-calculated points no branch
	//  d() - calculates points every time
	private boolean force_vis;
	private ArrayList<Location> points;
	private World world;

	private double radius;
	private boolean semisphere;
	private Location location;

	SphereGenerator(Location location, double radius, boolean semisphere, boolean force_vis) {
		this.force_vis = force_vis;
		world = location.getWorld();
		points = new ArrayList<>();
		// Outer loop: vertical arc
		// Inner loop: horizontal slices
		// The interval of particles is proportional to the circumference. This provides fairly even spacing.
		double circumference = 2 * Math.PI * radius;
		for(double phi = (semisphere ? Math.PI * 0.5 : 0); phi <= Math.PI; phi += Math.PI / (circumference / 2 / 4)) { // divide by two since the vertical arc only goes halfway around the sphere
			double level_radius = radius * Math.sin(phi);
			double level_circumference = 2 * Math.PI * level_radius;
			for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / (level_circumference / 4)) {
				double x = radius * Math.cos(theta) * Math.sin(phi);
				double y = radius * Math.cos(phi);
				double z = radius * Math.sin(theta) * Math.sin(phi);
				location.add(x, y, z);
				points.add(location.clone());
				location.subtract(x, y, z);
			}
		}

		this.radius = radius;
		this.semisphere = semisphere;
		this.location = location;
	}
	@Override
	public void run() {
		a();
		b();
		c();
		d();
	}
	private void a() {
		// Branch prediction should make this efficient?
		for(Location l : points) {
			if(force_vis) {
				world.spawnParticle(Particle.VILLAGER_HAPPY, l, 1, 0, 0, 0, 0, null, true);
			} else {
				world.spawnParticle(Particle.VILLAGER_HAPPY, l, 1);
			}
		}
	}
	private void b() {
		// Branch prediction should make this efficient?
		if(force_vis) {
			for(Location l : points) {
				world.spawnParticle(Particle.VILLAGER_HAPPY, l, 1, 0, 0, 0, 0, null, true);
			}
		} else {
			for(Location l : points) {
				world.spawnParticle(Particle.VILLAGER_HAPPY, l, 1);
			}
		}
	}
	private void c() {
		// Branch prediction should make this efficient?
		for(Location l : points) {
			world.spawnParticle(Particle.VILLAGER_HAPPY, l, 1, 0, 0, 0, 0, null, force_vis);
		}
	}
	private void d() {
		double circumference = 2 * Math.PI * radius;
		for(double phi = (semisphere ? Math.PI * 0.5 : 0); phi <= Math.PI; phi += Math.PI / (circumference / 2 / 4)) { // divide by two since the vertical arc only goes halfway around the sphere
			double level_radius = radius * Math.sin(phi);
			double level_circumference = 2 * Math.PI * level_radius;
			for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / (level_circumference / 4)) {
				double x = radius * Math.cos(theta) * Math.sin(phi);
				double y = radius * Math.cos(phi);
				double z = radius * Math.sin(theta) * Math.sin(phi);
				location.add(x, y, z);
				world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1, 0, 0, 0, 0, null, force_vis);
				//world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1);
				location.subtract(x, y, z);
			}
		}
	}
}
