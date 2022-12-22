package com.notasmr.darkness.entity;

import com.notasmr.darkness.util.Reference;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final float RANGE = 128;
    public static final float SPEED = 0.375F;
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MODID);

    public static final RegistryObject<EntityType<DarknessEntity>> DARKNESS =
            ENTITY_TYPES.register("darkness",
                    () -> EntityType.Builder.of(DarknessEntity::new, EntityClassification.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(Reference.MODID, "darkness").toString()));

    public static final RegistryObject<EntityType<DarknessXEntity>> DARKNESSX =
            ENTITY_TYPES.register("darknessx",
                    () -> EntityType.Builder.of(DarknessXEntity::new, EntityClassification.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(Reference.MODID, "darknessx").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
