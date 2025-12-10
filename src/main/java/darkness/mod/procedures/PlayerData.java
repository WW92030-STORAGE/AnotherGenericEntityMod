package darkness.mod.procedures;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerData {
	public static double px, py, pz;
	public static double ptheta, pphi;
	
	public PlayerData() {
		px = py = pz = ptheta = pphi = 0;
	}
	
	public void getData(Entity entity) {
		PlayerData.px = (double) (entity.posX);
		PlayerData.py = (double) (entity.posY);
		PlayerData.pz = (double) (entity.posZ);
		PlayerData.ptheta = (double) ((-1 * (entity.rotationYaw) - 90 + 720) % 360);
		PlayerData.pphi = (double) (-1 * (entity.rotationPitch));
	//	System.out.println(x(px) + " " + x(py) + " " + x(pz) + " " + x(ptheta) + " " + x(prho));
	}
	
	public static String x(double d) {
		String s = "" + d;
		while (s.length() < 12) s = s + "0";
		s = s.substring(0, 10);
		return s;
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity player = event.player;
			this.getData(player);
		}
	}
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
