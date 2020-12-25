package dev.rifkin.MobTools;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class SolidBlocks {
	// Bukkit defines a solid block as something that can be built upon. This includes just about everything from stone
	// to doors, fences, bells, pressure plates, anvils, etc. This definition is kind of useless and inconsistent with
	// the colloquial definition. This file creates a definition of solid more useful for the purpose of deciding when
	// it's important to show if a block is spawnable or not.
	private static HashMap<Material, Boolean> solid_blocks;
	private static boolean did_setup = false;
	public static void setup() {
		if(did_setup)
			throw new Error("setup called more than once");
		solid_blocks = new HashMap<>(200, 0.5f);
		did_setup = true;
	}
	public static boolean lookup(Block b) {
		Material m = b.getType();
		if(solid_blocks.containsKey(m)) {
			return solid_blocks.get(m);
		} else {
			boolean solid = b.getBoundingBox().getVolume() == 1;
			solid_blocks.put(m, solid);
			return solid;
		}
	}
}
