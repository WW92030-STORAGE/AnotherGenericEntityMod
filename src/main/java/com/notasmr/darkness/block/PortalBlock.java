package com.notasmr.darkness.block;

import com.notasmr.darkness.entity.DarknessXEntity;
import com.notasmr.darkness.entity.ModEntityTypes;
import com.notasmr.darkness.item.ModItems;
import com.notasmr.darkness.util.Reference;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.function.Supplier;

public class PortalBlock extends Block {
    public PortalBlock(Properties p) {
        super(p);
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos blockPos, PlayerEntity player,
                                Hand hand, BlockRayTraceResult brtr) {
        // Server: Main Hand & Off Hand
        // Client: Main Hand & Off Hand

        // player.sendMessage(new StringTextComponent("!!!"), player.getUUID());

        return super.use(state, level, blockPos, player, hand, brtr);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rs) {
        super.tick(state, world, pos, rs);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int rng = rs.nextInt(world.getGameRules().getInt(GameRules.RULE_RANDOMTICKING));
        if (rng > 1) return;

        if (world.getLevel().isClientSide()) { // this can't happen
            System.out.println("LOL");
            return;
        }

        System.out.println(world.toString() + " - " + x + " " + y + " " + z);

        Entity en = new DarknessXEntity(ModEntityTypes.DARKNESSX.get(), world);
        en.setPos(x + 0.5, y + 1.0, z + 0.5);
        en.setYHeadRot((float)(Math.random() * 720 * Math.PI));
        world.addFreshEntity(en);
    }
}