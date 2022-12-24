package com.notasmr.darkness.util;

import com.notasmr.darkness.Main;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Recipes {
    public static void recipe() {
        ItemStack portalStack = new ItemStack(Main.PORTAL);
        GameRegistry.addShapedRecipe(portalStack,
                "IDI", "IXI", "IDI", 'I', Items.iron_ingot, 'D', Items.diamond,
                'X', Item.getItemFromBlock(Blocks.end_stone)
                );
    }
}
