package darkness.mod.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import darkness.mod.util.Reference;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DarknessXRenderer<T extends DarknessXEntity> extends MobRenderer<T, PlayerModel<T>> {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/model/darkness0.png");;
    private final Model model;

    public DarknessXRenderer(EntityRendererProvider.Context manager) {
        super(manager, new PlayerModel<T>(manager.bakeLayer(ModelLayers.PLAYER), false), 0.6F);
        model = getModel();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return texture;
    }
}
