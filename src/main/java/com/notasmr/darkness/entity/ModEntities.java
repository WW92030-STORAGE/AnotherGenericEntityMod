package com.notasmr.darkness.entity;

import com.notasmr.darkness.Main;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class ModEntities {
    public static void init() {
        createEntity(DarknessEntity.class, "DARKNESS", 0x000000, 0xFFFFFF, true);
        createEntity(DarknessXEntity.class, "DARKNESSX", 0x000000, 0xFFFFFF, false);
    }

    public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor, boolean natural){
        int randomId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
        EntityRegistry.registerModEntity(entityClass, entityName, randomId, Main.modInstance, 128, 1, true);

        createEgg(randomId, solidColor, spotColor);

        if (!natural) return;

        for (BiomeDictionary.Type value : BiomeDictionary.Type.values()) {
            EntityRegistry.addSpawn(entityClass, 30, 1, 4, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(value));
        }

    }

    private static void createEgg(int randomId, int solidColor, int spotColor){
        EntityList.entityEggs.put(Integer.valueOf(randomId), new EntityList.EntityEggInfo(randomId, solidColor, spotColor));

    }
}
