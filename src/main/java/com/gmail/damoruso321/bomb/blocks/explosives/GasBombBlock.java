package com.gmail.damoruso321.bomb.blocks.explosives;

import com.gmail.damoruso321.bomb.blocks.ModBlocks;
import com.gmail.damoruso321.bomb.blocks.gas.GasBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class GasBombBlock extends ExplosiveBlock {
    public GasBombBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void detonate(Level level, BlockPos blockPos) {
        //level.destroyBlock(blockPos, false);

        Block gasBlock = ModBlocks.GAS_BLOCK.get();
        level.setBlockAndUpdate(blockPos, gasBlock.defaultBlockState());
        level.scheduleTick(blockPos, gasBlock, 0);
        level.explode(null, blockPos.getX(), blockPos.getY() + .25f, blockPos.getZ(), 1.0f, Level.ExplosionInteraction.NONE);
    }
}
