package com.gmail.damoruso321.bomb.blocks.gas;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;

public class GasBlock extends Block {
    private static final int MAX_SPREAD = 5;
    private static final IntegerProperty SPREAD_COUNT = IntegerProperty.create("spread_count", 0, MAX_SPREAD);

    public GasBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(SPREAD_COUNT, 5)
        );
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(SPREAD_COUNT);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        level.addParticle(ParticleTypes.LARGE_SMOKE, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0, 0.0, 0.0);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        super.tick(blockState, level, blockPos, randomSource);

        // If we have reached the limit in how much we can spread, stop.
        if (blockState.getValue(SPREAD_COUNT) <= 0) {
            return;
        }

        BlockPos down = new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());
        BlockPos up = new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());

        BlockPos left = new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ());
        BlockPos right = new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ());

        BlockPos front = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1);
        BlockPos back = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1);

        // Try to spread a few times...

        for (int i = 0; i < 3; i++) {
            spreadWithProbability(level, blockState, left, randomSource);
            spreadWithProbability(level, blockState, right, randomSource);
            spreadWithProbability(level, blockState, up, randomSource);
            spreadWithProbability(level, blockState, down, randomSource);
            spreadWithProbability(level, blockState, front, randomSource);
            spreadWithProbability(level, blockState, back, randomSource);
        }
    }

    private void spreadWithProbability(ServerLevel level, BlockState currentState, BlockPos blockPos, RandomSource randomSource) {
        int rand = randomSource.nextIntBetweenInclusive(0, 2);

        // Check spread count b/c if we are at initial block, guarantee it expands at least once.
        // This is to prevent gas from not expanding in tight conditions due to lower probability
        if (rand == 0 || currentState.getValue(SPREAD_COUNT) == MAX_SPREAD) {
            if (level.getBlockState(blockPos) == Blocks.AIR.defaultBlockState()) {
                level.setBlockAndUpdate(blockPos, this.defaultBlockState().setValue(SPREAD_COUNT, currentState.getValue(SPREAD_COUNT) - 1));
                level.scheduleTick(blockPos, this, randomSource.nextIntBetweenInclusive(2, 5), TickPriority.HIGH);
            }
        }
    }
}
