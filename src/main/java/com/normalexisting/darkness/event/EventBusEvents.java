package com.normalexisting.darkness.event;

import com.normalexisting.darkness.Reference;
import com.normalexisting.darkness.entity.DarknessEntity;
import com.normalexisting.darkness.entity.DarknessXEntity;
import com.normalexisting.darkness.entity.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DARKNESS.get(), DarknessEntity.createAttributes().build());
        event.put(ModEntities.DARKNESSX.get(), DarknessXEntity.createAttributes().build());
    }
}