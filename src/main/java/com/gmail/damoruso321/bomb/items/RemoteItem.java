package com.gmail.damoruso321.bomb.items;

import com.gmail.damoruso321.bomb.blockentities.ExplosiveBlockEntity;
import com.gmail.damoruso321.bomb.datacomponents.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RemoteItem extends Item {
    public RemoteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (!level.isClientSide()) {
            System.out.println("Using the remote on a block.");
            BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());

            if (blockEntity instanceof ExplosiveBlockEntity) {
                // Store bomb info (id, location, and dimension).

                String levelName = level.toString();

                System.out.println("Level name: " + levelName);

                ItemStack stack = context.getItemInHand();

                // Testing error here with data component registry object not found...
                if (ModDataComponents.REMOTE_PROPERTIES.isPresent()) {
                    stack.set(ModDataComponents.REMOTE_PROPERTIES.get(), new RemoteProperties(0, 0, 0, levelName, ""));
                } else {
                    System.out.println("Couldn't find data component!");
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            System.out.println("Using the remote in air.");

            // Detonate stored bomb...
        } else {
            // Swing player arm visually on clientside
            if (!player.swinging) {
                player.swing(hand);
            }
        }

        return InteractionResultHolder.fail(itemStack);
    }

    public record RemoteProperties(int xPos, int yPos, int zPos, String level, String id) {
        public static final Codec<RemoteProperties> CODEC = RecordCodecBuilder.create(builder ->
                builder.group(

                        Codec.INT
                                .fieldOf("xPos")
                                .forGetter(RemoteProperties::xPos),

                        Codec.INT
                                .fieldOf("yPos")
                                .forGetter(RemoteProperties::yPos),

                        Codec.INT
                                .fieldOf("zPos")
                                .forGetter(RemoteProperties::zPos),

                        Codec.STRING
                                .fieldOf("level")
                                .forGetter(RemoteProperties::level),

                        Codec.STRING
                                .fieldOf("id")
                                .forGetter(RemoteProperties::id)

                ).apply(builder, RemoteProperties::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, RemoteProperties> NETWORK_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                RemoteProperties::xPos,
                ByteBufCodecs.VAR_INT,
                RemoteProperties::yPos,
                ByteBufCodecs.VAR_INT,
                RemoteProperties::zPos,
                ByteBufCodecs.STRING_UTF8,
                RemoteProperties::level,
                ByteBufCodecs.STRING_UTF8,
                RemoteProperties::id,
                RemoteProperties::new
        );

        public static final RemoteProperties DEFAULT = new RemoteProperties(0, 0, 0, "", "");
    }
}
