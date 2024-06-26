package com.gmail.damoruso321.bomb.items;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.datacomponents.ModDataComponents;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MyMod.MOD_ID);

    public static final RegistryObject<Item> REMOTE = ITEMS.register("remote", () -> new RemoteItem(new Item.Properties().component(ModDataComponents.REMOTE_PROPERTIES.get(), RemoteItem.RemoteProperties.DEFAULT)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
