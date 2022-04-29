package darkness.mod.util.handlers;

import darkness.mod.entities.EntityDarkness;
import darkness.mod.entities.EntityDarknessP;
import darkness.mod.entities.EntityDarknessX;
import darkness.mod.entities.render.RenderDarkness;
import darkness.mod.entities.render.RenderDarknessP;
import darkness.mod.entities.render.RenderDarknessX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkness.class, new IRenderFactory<EntityDarkness>() {
			@Override
			public Render<? super EntityDarkness> createRenderFor(RenderManager manager) {
				return new RenderDarkness(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDarknessX.class, new IRenderFactory<EntityDarknessX>() {
			@Override
			public Render<? super EntityDarknessX> createRenderFor(RenderManager manager) {
				return new RenderDarknessX(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDarknessP.class, new IRenderFactory<EntityDarknessP>() {
			@Override
			public Render<? super EntityDarknessP> createRenderFor(RenderManager manager) {
				return new RenderDarknessP(manager);
			}
		});
	}
}
