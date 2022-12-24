package com.notasmr.darkness.blocks;

import com.notasmr.darkness.Main;
import com.notasmr.darkness.entity.DarknessXEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortalBlock extends Block {
    public PortalBlock(int id, Material mat) {
        super(mat);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setHarvestLevel("pickaxe", 1);
        this.setTickRandomly(true);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon("darkness:portal");
    }

    public void updateTick(World world, int a, int b, int c, Random random) {
        if (random.nextInt(this.tickRate(world)) > 1) return;
        List<Entity> list = new ArrayList<Entity>();

        DarknessXEntity spawn = new DarknessXEntity(world);
        spawn.setPosition(a + 0.5, b + 1, c + 0.5);

        list.add(spawn);
        world.addLoadedEntities(list);
    }
}
