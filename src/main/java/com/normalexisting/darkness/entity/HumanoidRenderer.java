package com.normalexisting.darkness.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.normalexisting.darkness.Reference;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

// Largely based on the humanoid renderer from Twilight Forest because i'm tired of having to blindly navigate the labyrinth of rendering humanoid mobs.

public class HumanoidRenderer<T extends Mob> extends MobRenderer<T, PlayerModel<T>> {
    private ResourceLocation texture;
    private final PlayerModel<T> model;

    public ResourceLocation setTexture(String tex) {
        texture = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "textures/model/" + tex);
        return texture;
    }

    public HumanoidRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlayerModel<>(pContext.bakeLayer(ModelLayers.PLAYER), false), 0.8F);
        this.model = this.getModel();

        setTexture("darkness.png");
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return this.texture;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}