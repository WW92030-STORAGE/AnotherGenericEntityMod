package darkness.mod.entities;

import darkness.mod.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DarknessXEntity extends PathfinderMob {
    
    public DarknessXEntity(EntityType<? extends DarknessXEntity> type, Level level) {
        super(type, level);
        ((GroundPathNavigation)this.getNavigation()).setCanPassDoors(true);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, ModEntityTypes.SPEED)
                .add(Attributes.FOLLOW_RANGE, ModEntityTypes.RANGE)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ARMOR, 10);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TemptGoal(this, 1, Ingredient.of(new ItemStack(Blocks.REDSTONE_BLOCK)), false));
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DarknessEntity.class, true));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(4, new HurtByTargetGoal(this, Mob.class));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, DarknessEntity.class, ModEntityTypes.RANGE));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, ModEntityTypes.RANGE));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, DarknessXEntity.class, ModEntityTypes.RANGE));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new LeapAtTargetGoal(this, 0.8F));
        this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
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
        if (src.getEntity() instanceof Player) return false;
        if (src.getEntity() instanceof Arrow) return false;
        if (src.getEntity() instanceof IronGolem) return false;

        super.hurt(src, dmg);

        return true;
    }
    
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void tick() {
        super.tick();
        if (lol) return;
        Level level = this.getLevel();
        Player player = level.getNearestPlayer(this, ModEntityTypes.RANGE);

        if (player == null) return;

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        LivingEntity entity = this;

        // here we go into the death zone

        double px = player.getX();
        double py = player.getY();
        double pz = player.getZ();
        double ptheta = (-1 * player.getYRot() - 90 + 720) % 360;
        double pphi = -1 * player.getXRot();

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
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100, false, false));
        }
        else if (exists) {
            // System.out.println("=====");
            this.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            if (dist3d >= 100) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 3, false, false));
            else this.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    }
}
