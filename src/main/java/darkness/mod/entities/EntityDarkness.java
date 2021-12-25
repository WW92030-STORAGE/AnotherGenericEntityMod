package darkness.mod.entities;

import darkness.mod.init.EntityInit;
import darkness.mod.procedures.PlayerData;
import darkness.mod.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityDarkness extends EntityMob {
//	EntityMob test = new EntityVillager();
//  EntityMob test2 = new EntityZombie();
	public EntityDarkness(World worldIn) {
		super(worldIn);
	//	((EntityMob)this).setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD, (int) (1)));
		if (EntityInit.PERSISTENCE) enablePersistence();
		((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
	}
	
	@Override
	protected void initEntityAI()
    {
		
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDarknessX.class, false, false));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.2, true));
		this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));
		this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));	
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, EntityInit.RANGE));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityDarknessX.class, EntityInit.RANGE));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityDarkness.class, EntityInit.RANGE));
		this.tasks.addTask(10, new EntityAIWander(this, 1));
		this.tasks.addTask(11, new EntityAISwimming(this));
		this.tasks.addTask(12, new EntityAILeapAtTarget(this, 0.8f));
		this.tasks.addTask(13, new EntityAILookIdle(this));
    }
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EntityInit.SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(EntityInit.RANGE);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource src) {
		return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
	}
	
	@Override
	protected boolean canDespawn() {
		return !EntityInit.PERSISTENCE;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof EntityArrow)
			return false;
		if (source.getImmediateSource() instanceof EntityPlayer) {
			double prob = 1.0 / 16;
			if (Math.random() < prob) Reference.open("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
			return false;
		}
		if (source.getImmediateSource() instanceof EntityPotion)
			return false;
		if (source == DamageSource.FALL)
			return false;
		if (source == DamageSource.CACTUS)
			return false;
		if (source == DamageSource.DROWN)
			return false;
		if (source == DamageSource.LIGHTNING_BOLT)
			return false;
		return super.attackEntityFrom(source, amount);
	}
	
	public static String x(double d) {
		String s = "" + d;
		while (s.length() < 12) s = s + "0";
		s = s.substring(0, 10);
		return s;
	}
	
	// Since probabilities are conditional we must reduce the End population since only 1 other mob spawns naturally and infinitely
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
		int x = (int) this.posX;
		int y = (int) this.posY;
		int z = (int) this.posZ;
		Entity entity = this;
		if (entity.dimension == 1 && Math.random() * 4 >= 1) entity.world.removeEntity(entity);
	//	System.out.println("REMOVED");
		return data; // only because I have to
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		double x = this.posX;
		double y = this.posY;
		double z = this.posZ;
		Entity entity = this;
		
		// here we go into the death zone
		
		double dx = PlayerData.px - x;
		double dy = PlayerData.py - y;
		double dz = PlayerData.pz - z;
		double dist2d = dx * dx + dz * dz;
		double dist3d = dx * dx + dy * dy + dz * dz;
		
		// BEGIN ANGLE CALCS
		
		double theta = Reference.atan(dx, dz) * Reference.DEG;
		theta = (theta + 450) % 360;
		
		double theta2 = theta + 360;
		double theta3 = theta - 360;
		
		double phi = Reference.atan(dist2d, dy) * Reference.DEG - 90.0;
		double phi2 = Reference.atan(dist2d, dy - 1) * Reference.DEG - 90.0;
		
	//	if (Math.abs(dy) < Reference.EPSILON) phi = 0;
	//	if (Math.abs(dy + 1) < Reference.EPSILON) phi2 = 0;
		
	//	System.out.println(x(theta) + " " + x(PlayerData.ptheta));
	//	System.out.println(x(phi) + " " + x(phi2) + " " + x(PlayerData.pphi));
		
		// END ANGLE CALCS
		
		boolean tc1 = Math.abs(theta - PlayerData.ptheta) < 60;
		boolean tc2 = Math.abs(theta2 - PlayerData.ptheta) < 60;
		boolean tc3 = Math.abs(theta3 - PlayerData.ptheta) < 60;
		boolean thetacheck = tc1 || tc2 || tc3;
		
		boolean phicheck = Math.abs(phi - PlayerData.pphi) < 60;
		boolean phicheck2 = Math.abs(phi2 - PlayerData.pphi) < 60;
		
		boolean exists = entity instanceof EntityLivingBase;
		
		if (thetacheck && (phicheck || phicheck2)) {
		//	System.out.println("!!!!!");
			entity.motionX = 0;
			entity.motionZ = 0;
			if (exists) ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 100, false, false));
		}
		else if (exists) {
			if (dist3d > 100) ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 3, false, false));
			else ((EntityLivingBase) entity).removePotionEffect(MobEffects.SPEED);
		}
	}

}
