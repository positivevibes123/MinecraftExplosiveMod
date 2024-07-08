package com.gmail.damoruso321.bomb.creative;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.blocks.ModBlocks;
import com.gmail.damoruso321.bomb.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MyMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = TABS.register("mod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("Dan's Explosives"))
                    .icon(ModItems.REMOTE.get()::getDefaultInstance)
                    .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    @SubscribeEvent
    public static void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        // Add implementation here... (Get rid of implementation in MyMod.java)

        if (event.getTabKey() == MOD_TAB.getKey()) {
            event.accept(ModItems.REMOTE);
            event.accept(ModBlocks.GAS_BOMB_BLOCK);
        }
    }
}
