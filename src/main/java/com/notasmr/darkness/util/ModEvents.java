package com.notasmr.darkness.util;

import com.notasmr.darkness.entity.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        // for (int i = 0; i < 64; i++) System.out.println("BIOME LOADING EVENT " + event);
        addEntityToAllBiomes(event.getSpawns(), ModEntityTypes.DARKNESS.get(), 30, 1, 4);
    }

    private static void addEntityToAllBiomes(MobSpawnInfoBuilder spawns, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnInfo.Spawners> base = spawns.getSpawner(type.getCategory());
        base.add(new MobSpawnInfo.Spawners(type,weight, minCount, maxCount));
    }
}
