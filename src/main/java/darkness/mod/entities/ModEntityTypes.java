package darkness.mod.entities;

import darkness.mod.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final float RANGE = 128;
    public static final float SPEED = 0.375F;

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);

    public static final RegistryObject<EntityType<DarknessEntity>> DARKNESS =
            ENTITY_TYPES.register("darkness",
                    () -> EntityType.Builder.of(DarknessEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(Reference.MODID, "darkness").toString()));

    public static final RegistryObject<EntityType<DarknessXEntity>> DARKNESSX =
            ENTITY_TYPES.register("darknessx",
                    () -> EntityType.Builder.of(DarknessXEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(Reference.MODID, "darknessx").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DARKNESS.get(), DarknessEntity.registerAttributes().build());
        event.put(DARKNESSX.get(), DarknessXEntity.registerAttributes().build());
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DARKNESS.get(), m -> new DarknessRenderer(m));
        event.registerEntityRenderer(DARKNESSX.get(), m -> new DarknessXRenderer(m));
    }

    public static void registerSpawns(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntityTypes.DARKNESS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        });
    }
}
