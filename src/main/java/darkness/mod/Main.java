package darkness.mod;

import com.mojang.logging.LogUtils;
import darkness.mod.blocks.ModBlocks;
import darkness.mod.entities.ModEntityTypes;
import darkness.mod.items.ModItems;
import darkness.mod.util.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Main
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        for (int i = 0; i < 64; i++) System.out.println("MOD SETUP");
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModEntityTypes.registerSpawns(event);

        for (int i = 0; i < 64; i++) System.out.println("SETUP COMMON");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            for (int i = 0; i < 64; i++) System.out.println("SETUP CLIENT");
        }
    }
}
