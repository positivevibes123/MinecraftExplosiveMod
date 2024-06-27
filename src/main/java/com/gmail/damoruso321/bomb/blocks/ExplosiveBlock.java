package com.gmail.damoruso321.bomb.blocks;

import com.gmail.damoruso321.bomb.blockentities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExplosiveBlock extends Block implements EntityBlock {
    public ExplosiveBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return ModBlockEntities.EXPLOSIVE_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    public abstract void detonate(Level level, BlockPos blockPos);
}
