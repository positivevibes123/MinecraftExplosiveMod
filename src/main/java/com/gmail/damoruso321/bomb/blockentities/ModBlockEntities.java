package com.gmail.damoruso321.bomb.blockentities;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExplosiveBlockEntity>> EXPLOSIVE_BLOCK_ENTITY = BLOCK_ENTITIES.register("explosive_block_entity",
            () -> BlockEntityType.Builder.of(ExplosiveBlockEntity::new, ModBlocks.GAS_BOMB_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
