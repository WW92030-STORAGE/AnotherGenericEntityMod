package darkness.mod.init;

import java.util.ArrayList;
import java.util.Iterator;

import darkness.mod.Main;
import darkness.mod.entities.EntityDarkness;
import darkness.mod.entities.EntityDarknessX;
import darkness.mod.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
	public static final int RANGE = 128;
	public static final double SPEED = 0.375;
	public static final boolean PERSISTENCE = false; // TODO add configs
	public static void registerEntities() {
		registerEntity("DARKNESS", EntityDarkness.class, Reference.ENTITY_DARKNESS, RANGE, true);
		registerEntity("DARKNESSX", EntityDarknessX.class, Reference.ENTITY_DARKNESSX, RANGE, false);
	}
	
	private static Biome[] biomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
		Iterator<Biome> iter = in.iterator();
		ArrayList<Biome> list = new ArrayList<Biome>();
		while (iter.hasNext())
			list.add(iter.next());
		return list.toArray(new Biome[list.size()]);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> c, int id, int range, boolean natural) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), c, name, id, Main.instance, range, 1, true);
		
		try {
			Biome[] biomes = biomes(Biome.REGISTRY);
			if (natural) EntityRegistry.addSpawn((Class<? extends EntityLiving>) c, 40, 3, 3, EnumCreatureType.MONSTER, biomes);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("REGISTERED ENTITY " + name);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> c, int id, int range, int prim, int seco, boolean natural) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), c, name, id, Main.instance, range, 1, true, prim, seco);
		
		try {
			Biome[] biomes = biomes(Biome.REGISTRY);
			if (natural) EntityRegistry.addSpawn((Class<? extends EntityLiving>) c, 40, 3, 3, EnumCreatureType.MONSTER, biomes);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("REGISTERED ENTITY " + name);
	}
}
