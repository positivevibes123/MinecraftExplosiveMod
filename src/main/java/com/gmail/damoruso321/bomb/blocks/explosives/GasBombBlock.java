package com.gmail.damoruso321.bomb.blocks.explosives;

import com.gmail.damoruso321.bomb.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.ticks.TickPriority;

public class GasBombBlock extends ExplosiveBlock {
    public GasBombBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void detonate(Level level, BlockPos blockPos) {
        if (!level.isClientSide()) {
            Block gasBlock = ModBlocks.GAS_BLOCK.get();
            level.setBlockAndUpdate(blockPos, gasBlock.defaultBlockState());
            level.scheduleTick(blockPos, gasBlock, 0, TickPriority.HIGH);
            level.explode(null, blockPos.getX(), blockPos.getY() - 1, blockPos.getZ(), 2.0f, Level.ExplosionInteraction.BLOCK);
        }
    }
}
