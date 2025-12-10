package darkness.mod.objects.blocks;

import java.util.Random;

import darkness.mod.Main;
import darkness.mod.entities.EntityDarknessX;
import darkness.mod.init.BlockInit;
import darkness.mod.init.ItemInit;
import darkness.mod.util.IModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortal extends Block implements IModel{
	public BlockPortal(String name, Material mat, CreativeTabs tab) {
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setHarvestLevel("pickaxe", 1);
		setHardness(1F);
		setResistance(10F);
		setLightLevel(0F);
		setLightOpacity(0);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		super.updateTick(world, pos, state, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if ((Math.random() < 0.005)) {
			if (!world.isRemote) {
				Entity entityToSpawn = new EntityDarknessX(world);
				if (entityToSpawn != null) {
					entityToSpawn.setLocationAndAngles((x + 0.5), (y + 1), (z + 0.5), world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
			}
		}
		world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
	}
}
