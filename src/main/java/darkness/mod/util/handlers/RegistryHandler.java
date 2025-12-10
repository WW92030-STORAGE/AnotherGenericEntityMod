package darkness.mod.util.handlers;

import darkness.mod.init.BlockInit;
import darkness.mod.init.EntityInit;
import darkness.mod.init.ItemInit;
import darkness.mod.util.IModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		for (Item i : ItemInit.ITEMS) {
			if (i instanceof IModel) {
				((IModel)i).registerModels();
			}
		}
		
		for (Block b : BlockInit.BLOCKS) {
			if (b instanceof IModel) {
				((IModel)b).registerModels();
			}
		}
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event) {
		System.out.println("!!PRE-INITIALIZATION!!");
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenders();
	}
	
	public static void InitRegistries(FMLInitializationEvent event) {
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenders();
	}
}
