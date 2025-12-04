package com.notasmr.darkness.entity;

import com.notasmr.darkness.util.Reference;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class DarknessXEntity extends EntityCreature {

    public DarknessXEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);

        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setEnterDoors(true);

        this.tasks.addTask(0, new EntityAITempt(this, 0.25, Item.getItemFromBlock(Blocks.redstone_block), false));
        this.tasks.addTask(0, new EntityAIAttackOnCollide(this, DarknessEntity.class, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, DarknessEntity.class, 1, false));
        this.tasks.addTask(4, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.targetTasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 128));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, DarknessXEntity.class, 128));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, DarknessEntity.class, 128));
        this.tasks.addTask(10, new EntityAIWander(this, 1.0));
        this.tasks.addTask(11, new EntityAISwimming(this));
        this.tasks.addTask(13, new EntityAILookIdle(this));
    }

    public boolean isAIEnabled(){
        return true;
    }

    protected void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0F);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10);
    }

    DamageSource[] immunes = {DamageSource.inFire, DamageSource.onFire, DamageSource.lava, DamageSource.inWall, DamageSource.drown,
            DamageSource.starve, DamageSource.cactus, DamageSource.fall, DamageSource.magic, DamageSource.wither, DamageSource.anvil, DamageSource.fallingBlock};

    @Override
    public boolean attackEntityFrom(DamageSource src, float value) {
        Entity e = src.getEntity();
        if (e == null) return false;
        if (e instanceof EntityPlayer) return false;
        return super.attackEntityFrom(src, value);
    }

    private int radius = 144;

    private final boolean DEBUG = false;
    private final boolean DEBUG0 = false;


    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }

        if (DEBUG0) return;
        World level = this.worldObj;
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.posX - radius, 0, this.posZ - radius, this.posX + radius, 256, this.posZ + radius);
        List<Entity> list = level.getEntitiesWithinAABB(EntityPlayer.class, aabb);
        double min = Double.MAX_VALUE;

        if (list.size() <= 0) return;

        EntityPlayer player = (EntityPlayer)list.get(0);
        for (Entity e : list) {
            if (!(e instanceof EntityPlayer)) continue;

            double rsq = this.getDistanceSqToEntity(e);
            if (rsq < min) {
                min = rsq;
                player = (EntityPlayer)e;
            }
        }

        if (player == null) return;

        double x = this.posX;
        double y = this.posY;
        double z = this.posZ;
        EntityLiving entity = this;

// here we go into the death zone

        double px = player.posX;
        double py = player.posY;
        double pz = player.posZ;
        double ptheta = (-1 * player.rotationYaw - 90 + 720) % 360;
        double pphi = -1 * player.rotationPitch;

        if (Math.random() < (1.0 / 1024.0)) System.out.println(ptheta + " " + pphi);

        double dx = px - x;
        double dy = py - y;
        double dz = pz - z;
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

        // END ANGLE CALCS

        boolean tc1 = Math.abs(theta - ptheta) < 60;
        boolean tc2 = Math.abs(theta2 - ptheta) < 60;
        boolean tc3 = Math.abs(theta3 - ptheta) < 60;
        boolean thetacheck = tc1 || tc2 || tc3;

        boolean phicheck = Math.abs(phi - pphi) < 60;
        boolean phicheck2 = Math.abs(phi2 - pphi) < 60;

        boolean exists = this instanceof EntityLiving;

        if (DEBUG) {
            System.out.println((thetacheck && (phicheck || phicheck2)) + " | " + exists);
            return;
        }

        if (thetacheck && (phicheck || phicheck2)) {
            // System.out.println("!!!!!");
            // this.setAIMoveSpeed(0);
            this.addPotionEffect(new PotionEffect(2, 100, 100, true));
        }
        else if (exists) {
            // System.out.println("=====");
            this.removePotionEffect(2);
            if (dist3d >= 100) this.addPotionEffect(new PotionEffect(1, 3, 3, true));
            else this.removePotionEffect(1);
        }
        else {
            this.clearActivePotions();
        }
    }

    public void x(String s, EntityPlayer player) {
        player.addChatComponentMessage(new ChatComponentText(s));
    }

    public String introDialogue[] = {"...", "Hi there.", "Oh, it's you.", "Hello!", "Hi!", "Rawr!", "Hewwo!"};

    protected boolean interact(EntityPlayer player) {
        World world = this.worldObj;
        if (world.isRemote) return false;

        String s = introDialogue[(int)(Math.random() * introDialogue.length)];
        x(s, player);

        return true;
    }


    // Copied directly from EntityMob
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (p_70652_1_ instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
        }

        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                p_70652_1_.setFire(j * 4);
            }

            if (p_70652_1_ instanceof EntityLivingBase)
            {
                EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
            }

            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }

        return flag;
    }
}

