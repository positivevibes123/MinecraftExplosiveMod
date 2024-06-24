package com.gmail.damoruso321.bomb.datacomponents;

import com.gmail.damoruso321.bomb.MyMod;
import com.gmail.damoruso321.bomb.items.RemoteItem;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MyMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        eventBus.register(DATA_COMPONENT_TYPES);
    }

    public static final RegistryObject<DataComponentType<RemoteItem.RemoteProperties>> REMOTE_PROPERTIES = registerComponent("remote_properties",
            builder -> builder
                    .persistent(RemoteItem.RemoteProperties.CODEC)
                    .networkSynchronized(RemoteItem.RemoteProperties.NETWORK_CODEC)
                    .cacheEncoding()
    );

    private static <T> RegistryObject<DataComponentType<T>> registerComponent(final String name, final UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
