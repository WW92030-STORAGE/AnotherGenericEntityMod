package darkness.mod.init;

import java.util.ArrayList;
import java.util.List;

import darkness.mod.objects.blocks.BlockPortal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockInit {
public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block PORTAL = new BlockPortal("portal", Material.IRON, CreativeTabs.REDSTONE);
}
