package com.gmail.damoruso321.bomb;

import com.gmail.damoruso321.bomb.blockentities.ModBlockEntities;
import com.gmail.damoruso321.bomb.blocks.ModBlocks;
import com.gmail.damoruso321.bomb.datacomponents.ModDataComponents;
import com.gmail.damoruso321.bomb.items.ModItems;

import com.gmail.damoruso321.bomb.sounds.ModSounds;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MyMod.MOD_ID)
public class MyMod {
    public static final String MOD_ID = "bomb";

    public MyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.GAS_BOMB_BLOCK);
            event.accept(ModItems.REMOTE);
        }
    }
}
