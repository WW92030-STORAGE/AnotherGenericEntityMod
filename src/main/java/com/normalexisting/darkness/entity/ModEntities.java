package com.normalexisting.darkness.entity;

import com.normalexisting.darkness.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);

    public static final RegistryObject<EntityType<DarknessEntity>> DARKNESS =
            ENTITY_TYPES.register("darkness", () -> EntityType.Builder.of(DarknessEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f).build("darkness"));

    public static final RegistryObject<EntityType<DarknessXEntity>> DARKNESSX =
            ENTITY_TYPES.register("darknessx", () -> EntityType.Builder.of(DarknessXEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f).build("darknessx"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}