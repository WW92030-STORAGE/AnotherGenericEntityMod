package com.notasmr.darkness.entity;

import com.notasmr.darkness.util.Reference;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class DarknessEntity extends MonsterEntity {
    public DarknessEntity(EntityType<? extends DarknessEntity> type, World level) {
        super(type, level);
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, ModEntityTypes.SPEED)
                .add(Attributes.FOLLOW_RANGE, ModEntityTypes.RANGE)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ARMOR, 10);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DarknessXEntity.class, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, MobEntity.class));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.targetSelector.addGoal(6, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, ModEntityTypes.RANGE));
        this.goalSelector.addGoal(7, new LookAtGoal(this, DarknessXEntity.class, ModEntityTypes.RANGE));
        this.goalSelector.addGoal(10, new RandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(11, new SwimGoal(this));
        // this.goalSelector.addGoal(12, new LeapAtTargetGoal(this, 0.8F));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
    }

    protected SoundEvent getAmbientSound() {
        return new SoundEvent(new ResourceLocation(""));
    }

    protected SoundEvent getHurtSound(DamageSource src) {
        return new SoundEvent(new ResourceLocation(""));
    }

    protected SoundEvent getDeathSound() {
        return new SoundEvent(new ResourceLocation(""));
    }

    boolean lol = false;

    public boolean hurt(DamageSource src, float dmg) {
        if (src == DamageSource.FALL) return false;
        if (src == DamageSource.CACTUS) return false;
        if (src == DamageSource.DROWN) return false;
        if (src == DamageSource.LIGHTNING_BOLT) return false;
        if (src.getEntity() instanceof PlayerEntity) return false;
        if (src.getEntity() instanceof ArrowEntity) return false;

        super.hurt(src, dmg);

        return true;
    }

    void depopulate() {
        Entity e = this;
        World level = e.level;
        PlayerEntity p = level.getNearestPlayer(this, 128);
        if (p == null) return;

        ArrayList<Entity> list = Reference.aabb(p, p.getX() - 128, p.getY() - 16, p.getZ() - 128., p.getX() + 128, p.getY() + 16, p.getZ() + 128);

        int cnt = 0;
        for (Entity ee : list) {
            if (ee instanceof DarknessEntity) cnt++;
        }
        if (cnt > Reference.MAX_PER_PLAYER) e.remove();
    }

    public ILivingEntityData finalizeSpawn(IServerWorld l, DifficultyInstance d, SpawnReason r, @Nullable ILivingEntityData i, @Nullable CompoundNBT n) {
        ILivingEntityData data = super.finalizeSpawn(l, d, r, i, n);
        LivingEntity e = this;
        if (e.level.dimension() == World.END && Math.random() * 4 >= 1) e.remove();
        System.out.println("END SPAWN");

        depopulate();

        return data;
    }

    public void tick() {
        super.tick();
        if (Math.random() < 1.0 / 4096.0) depopulate();
        if (lol) return;
        World level = this.level;
        PlayerEntity player = level.getNearestPlayer(this, ModEntityTypes.RANGE);

        if (player == null) return;

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        LivingEntity entity = this;

        // here we go into the death zone

        double px = player.getX();
        double py = player.getY();
        double pz = player.getZ();
        double ptheta = (-1 * player.yHeadRot - 90 + 720) % 360;
        double pphi = -1 * player.xRot;

        // if (Math.random() < 0.1) System.out.println(ptheta + " " + pphi);

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

        boolean exists = this instanceof LivingEntity;

        if (thetacheck && (phicheck || phicheck2)) {
            // System.out.println("!!!!!");
            // this.setNoAi(true);
            this.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 100, false, false));
        }
        else if (exists) {
            // System.out.println("=====");
            this.removeEffect(Effects.MOVEMENT_SLOWDOWN);
            if (dist3d >= 100) this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3, 3, false, false));
            else this.removeEffect(Effects.MOVEMENT_SPEED);
        }
    }
}
