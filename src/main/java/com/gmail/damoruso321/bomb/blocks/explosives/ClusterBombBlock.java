package com.gmail.damoruso321.bomb.blocks.explosives;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ClusterBombBlock extends ExplosiveBlock{
    //private static final int RADIUS = 5;
    private static final int NUM_TNT = 8;

    public ClusterBombBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void detonate(Level level, BlockPos blockPos) {
        if (!level.isClientSide()) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

            // Create sphere of explosions...

            /*for (int x = -RADIUS; x <= RADIUS; x+=2) {
                for (int y = -RADIUS; y <= RADIUS; y+=2) {
                    for (int z = -RADIUS; z <= RADIUS; z+=2) {
                        BlockPos explosionPos = blockPos.offset(x, y, z);
                        if (blockPos.distSqr(new Vec3i(explosionPos.getX(), explosionPos.getY(), explosionPos.getZ())) <= RADIUS) {
                            level.explode(null, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 2.0f, Level.ExplosionInteraction.BLOCK);
                        }
                    }
                }
            }*/

            level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.0f, Level.ExplosionInteraction.NONE);

            double angleIncrement = Math.toRadians(360.0 / NUM_TNT);
            double angle = 0;

            for (int i = 0; i < NUM_TNT; i++) {
                PrimedTnt primedtnt = new PrimedTnt(level, (double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5, null);
                level.addFreshEntity(primedtnt);
                double factor = Math.random() * (1.0 - .25) + .25;
                primedtnt.setDeltaMovement(new Vec3(Math.cos(angle) * factor, 1, Math.sin(angle) * factor));
                level.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                angle += angleIncrement;
            }

            //level.gameEvent(null, GameEvent.PRIME_FUSE, primedtnt);
        }
    }
}
