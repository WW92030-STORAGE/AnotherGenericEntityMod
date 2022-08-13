package darkness.mod.blocks;

import darkness.mod.entities.DarknessXEntity;
import darkness.mod.entities.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PortalBlock extends Block {

    Block reference0 = Blocks.TORCH;
    public PortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player,
                                 InteractionHand hand, BlockHitResult blockHitResult) {
        // Server: Main Hand & Off Hand
        // Client: Main Hand & Off Hand

        // player.sendSystemMessage(Component.literal("!!!"));

        return super.use(state, level, blockPos, player, hand, blockHitResult);
    }

    double numerator = 5;
    double denominator = 1000;

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rs) {
        super.tick(state, world, pos, rs);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double rng = Math.random() * denominator;
        if (rng >= numerator) return;

        if (world.getLevel().isClientSide()) { // this can't happen
            System.out.println("LOL");
            return;
        }

        System.out.println(world.toString() + " - " + x + " " + y + " " + z);

        Entity en = new DarknessXEntity(ModEntityTypes.DARKNESSX.get(), world);
        en.setPos(x + 0.5, y + 1.0, z + 0.5);
        en.setYRot((float)(Math.random() * 720 * Math.PI));
        world.addFreshEntity(en);
    }
}
