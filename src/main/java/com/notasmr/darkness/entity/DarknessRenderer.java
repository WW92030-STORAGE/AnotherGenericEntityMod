package com.notasmr.darkness.entity;

import com.notasmr.darkness.util.Reference;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class DarknessRenderer extends MobRenderer<DarknessEntity, BipedModel<DarknessEntity>>
{
    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(Reference.MODID, "textures/entity/darkness.png");

    public DarknessRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlayerModel<>(0, false), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(DarknessEntity entity) {
        return TEXTURE;
    }
}