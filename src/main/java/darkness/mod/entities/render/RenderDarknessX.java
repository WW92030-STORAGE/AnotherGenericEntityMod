package darkness.mod.entities.render;

import darkness.mod.entities.EntityDarknessX;
import darkness.mod.util.Reference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDarknessX extends RenderLiving<EntityDarknessX> {
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entities/darkness.png");

	public RenderDarknessX(RenderManager manager) {
		super(manager, new ModelBiped(), 0.5f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDarknessX e) {
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityDarknessX e, float p_77043_2_, float rotationYaw, float partialTicks) {
		super.applyRotations(e, p_77043_2_, rotationYaw, partialTicks);
	}
}
