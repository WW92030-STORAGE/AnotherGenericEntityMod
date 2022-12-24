package com.notasmr.darkness.entity;

import com.notasmr.darkness.util.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DarknessXRenderer extends RenderLiving {

    private static final ResourceLocation mobTextures = new ResourceLocation(Reference.MODID + ":textures/entities/darknessx.png");
    private static final String __OBFID = "CL_00000984";
    public DarknessXRenderer(ModelBase model, float x) {
        super(model, x);
    }
    protected ResourceLocation getEntityTexture(DarknessXEntity entity){
        return mobTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return mobTextures;
    }
}
