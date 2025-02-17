package com.normalexisting.darkness.entity;

import com.normalexisting.darkness.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class DarknessXEntity extends Monster {
    public DarknessXEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        ((GroundPathNavigation)this.getNavigation()).setCanPassDoors(true);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    void depopulate() {
        Entity e = this;
        Level level = e.level();
        Player p = level.getNearestPlayer(this, 128);
        if (p == null) return;

        ArrayList<Entity> list = Reference.aabb(p, p.getX() - 128, p.getY() - 16, p.getZ() - 128., p.getX() + 128, p.getY() + 16, p.getZ() + 128);

        int cnt = 0;
        for (Entity ee : list) {
            if (ee instanceof DarknessXEntity) cnt++;
        }
        if (cnt > Reference.MAX_PER_PLAYER) e.remove(RemovalReason.DISCARDED);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor sla, DifficultyInstance di, MobSpawnType mst, @Nullable SpawnGroupData sgd) {
        SpawnGroupData sd = super.finalizeSpawn(sla, di, mst, sgd);

        depopulate();
        return sd;
    }


    @Override
    public void tick() {
        super.tick();

        if (Math.random() < 1.0 / 4096.0) depopulate();

        // if (!this.level().isClientSide()) return; // just do it for fucks sake otherwise tick ticks twice per tick

        Level level = this.level();
        Player p = level.getNearestPlayer(this, 128);

        if (p == null) return;

        double dx = p.getX() - getX();
        double dy = p.getY() - getY();
        double dz = p.getZ() - getZ();
        double ptheta = (-1 * p.getYRot() - 90 + 720) % 360;
        double pphi = -1 * p.getXRot();

        double dist2d = dx * dx + dz * dz;
        double dist3d = dist2d + dy * dy;

        dist2d = Math.sqrt(dist2d);
        dist3d = Math.sqrt(dist3d);

        double theta = Reference.atan(dx, dz) * Reference.DEG;
        theta = Reference.rem(theta + 450, 360);

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
            this.setDeltaMovement(0, this.getDeltaMovement().y(), 0);
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100, false, false));
        }
        else if (exists) {
            // System.out.println("=====");
            // this.setNoAi(false);
            this.setSpeed(Reference.SPEED);
            this.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            if (dist3d >= 100) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 3, false, false));
            else this.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    }

    @Override
    public boolean hurt(DamageSource d, float p_19947_) {
        if (d.is(DamageTypes.ARROW)) return false;
        if (d.is(DamageTypes.PLAYER_ATTACK)) return false;
        if (d.is(DamageTypes.MAGIC)) return false;
        if (d.is(DamageTypes.FALL)) return false;
        if (d.is(DamageTypes.CACTUS)) return false;
        if (d.is(DamageTypes.DROWN)) return false;
        if (d.is(DamageTypes.LIGHTNING_BOLT)) return false;

        return super.hurt(d, p_19947_);
    }

    @Override
    protected void registerGoals() {
        /*

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

         */

        Zombie z;

        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, DarknessEntity.class, false));
        // this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1, Ingredient.of(new ItemStack(Blocks.REDSTONE_BLOCK)), false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(3, new HurtByTargetGoal(this, Player.class));
        this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, Reference.RANGE));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, DarknessEntity.class, Reference.RANGE));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, DarknessXEntity.class, Reference.RANGE));

        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, 0.8f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new FloatGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.FOLLOW_RANGE, Reference.RANGE)
                .add(Attributes.MOVEMENT_SPEED, Reference.SPEED)
                .add(Attributes.ARMOR_TOUGHNESS, 10f)
                .add(Attributes.ATTACK_DAMAGE, 10f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    public void x(String s, Player player) {
        player.sendSystemMessage(Component.literal(s));
    }

    public String introDialogue[] = {"...", "Hi there.", "Oh, it's you.", "Hello!", "Hi!", "Rawr!", "Hewwo!"};
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        Level level = this.level();
        if (level.isClientSide()) return InteractionResult.sidedSuccess(this.level().isClientSide);
        try {
            int index = (int)(Math.random() * introDialogue.length);
            x(introDialogue[index], player);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }
}