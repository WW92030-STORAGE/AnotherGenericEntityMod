package com.notasmr.darkness;

import com.notasmr.darkness.blocks.PortalBlock;
import com.notasmr.darkness.entity.ModEntities;
import com.notasmr.darkness.util.Recipes;
import com.notasmr.darkness.util.Reference;
import com.notasmr.darkness.util.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class Main
{
    @SidedProxy(clientSide = "com.notasmr.darkness.util.ClientProxy", serverSide = "com.notasmr.darkness.util.ServerProxy")
    public static ServerProxy proxy;

    @Mod.Metadata
    public static ModMetadata meta;

    @Mod.Instance(Reference.MODID)
    public static Main modInstance;
    public static final int PORTAL_ID = 8192;
    public static final Block PORTAL = new PortalBlock(PORTAL_ID, Material.iron).setHardness(5f).setBlockName("portal");

    @EventHandler
    public void PreLoad(FMLPreInitializationEvent event) {
        GameRegistry.registerBlock(PORTAL, "portal");
        ModEntities.init();
        proxy.render();
        Recipes.recipe();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+ Blocks.dirt.getUnlocalizedName());

        // GameRegistry.registerBlock(PORTAL, "portal");
    }
}
