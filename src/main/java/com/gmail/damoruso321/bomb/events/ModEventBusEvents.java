package com.gmail.damoruso321.bomb.events;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.blockentities.ExplosiveBlockEntity;
import com.gmail.damoruso321.bomb.particles.GasParticle;
import com.gmail.damoruso321.bomb.particles.ModParticles;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import java.util.UUID;

public class ModEventBusEvents {
    @Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ForgeEvents {
        @SubscribeEvent
        public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
            if (!event.getLevel().isClientSide()) {
                BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());

                // If the block placed is a modded explosive, continue.
                if (blockEntity instanceof ExplosiveBlockEntity) {
                    // Store a unique UUID in the explosive. We do this so remotes can interact with it later.
                    UUID uuid = UUID.randomUUID();
                    ((ExplosiveBlockEntity)blockEntity).storeUUID(uuid.toString());

                    System.out.println("The block placed is a modded explosive. It's UUID is: " + uuid.toString());
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEvents {
        @SubscribeEvent
        public static void registerParticleProviders(final RegisterParticleProvidersEvent event) {
            // Register particle facrtory for custom particles here...
            event.registerSpriteSet(ModParticles.GAS_PARTICLE.get(), GasParticle.Provider::new);
        }
    }
}
