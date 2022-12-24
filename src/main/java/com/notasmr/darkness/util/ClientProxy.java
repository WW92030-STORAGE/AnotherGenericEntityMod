package com.notasmr.darkness.util;

import com.notasmr.darkness.entity.DarknessEntity;
import com.notasmr.darkness.entity.DarknessRenderer;
import com.notasmr.darkness.entity.DarknessXEntity;
import com.notasmr.darkness.entity.DarknessXRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;

public class ClientProxy extends ServerProxy {
    public void render() {
        RenderingRegistry.registerEntityRenderingHandler(DarknessEntity.class, new DarknessRenderer(new ModelBiped(), 0));
        RenderingRegistry.registerEntityRenderingHandler(DarknessXEntity.class, new DarknessXRenderer(new ModelBiped(), 0));
    }
}
