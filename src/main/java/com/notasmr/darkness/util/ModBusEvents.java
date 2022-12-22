package com.notasmr.darkness.util;

import com.notasmr.darkness.entity.DarknessEntity;
import com.notasmr.darkness.entity.DarknessXEntity;
import com.notasmr.darkness.entity.ModEntityTypes;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.DARKNESS.get(), DarknessEntity.registerAttributes().build());
        event.put(ModEntityTypes.DARKNESSX.get(), DarknessXEntity.registerAttributes().build());
    }

    @SubscribeEvent
    public static void registerEntitySpawns(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(ModEntityTypes.DARKNESS.get());
        EntitySpawnPlacementRegistry.register(ModEntityTypes.DARKNESS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
    }
}
