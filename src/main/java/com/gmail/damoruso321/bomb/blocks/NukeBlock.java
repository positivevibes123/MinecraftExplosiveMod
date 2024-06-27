package com.gmail.damoruso321.bomb.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class NukeBlock extends ExplosiveBlock {
    public NukeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void detonate(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
    }
}
