package com.gmail.damoruso321.bomb.blocks;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.blocks.explosives.ClusterBombBlock;
import com.gmail.damoruso321.bomb.blocks.explosives.GasBombBlock;
import com.gmail.damoruso321.bomb.blocks.gas.GasBlock;
import com.gmail.damoruso321.bomb.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MyMod.MOD_ID);

    public static final RegistryObject<Block> GAS_BOMB_BLOCK = registerBlock("gas_bomb", () -> new GasBombBlock((BlockBehaviour.Properties.of().mapColor(MapColor.STONE))));
    public static final RegistryObject<Block> GAS_BLOCK = registerBlock("gas_block", () -> new GasBlock((BlockBehaviour.Properties.of().mapColor(MapColor.DIRT))));

    public static final RegistryObject<Block> CLUSTER_BOMB_BLOCK = registerBlock("cluster_bomb", () -> new ClusterBombBlock((BlockBehaviour.Properties.of().mapColor(MapColor.STONE))));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
