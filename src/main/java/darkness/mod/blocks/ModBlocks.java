package darkness.mod.blocks;

import darkness.mod.items.ModItems;
import darkness.mod.util.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    Block importthing = Blocks.REDSTONE_BLOCK;
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    public static final RegistryObject<Block> PORTAL = registerBlock("portal",
            () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().randomTicks().strength(6f).requiresCorrectToolForDrops()),
            CreativeModeTab.TAB_REDSTONE);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> sup, CreativeModeTab tab) {
        RegistryObject<T> retval = BLOCKS.register(name, sup);
        registerBlockItem(name, retval, tab);
        return retval;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus ieb) {
        BLOCKS.register(ieb);
    }
}
