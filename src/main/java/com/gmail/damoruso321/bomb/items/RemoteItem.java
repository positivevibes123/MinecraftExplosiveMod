package com.gmail.damoruso321.bomb.items;

import com.gmail.damoruso321.bomb.blockentities.ExplosiveBlockEntity;
import com.gmail.damoruso321.bomb.datacomponents.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public class RemoteItem extends Item {
    public RemoteItem(Properties properties) {
        super(properties);
    }

    /*@Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        InteractionHand hand = context.getHand();
        Player player = context.getPlayer();
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            System.out.println("Using the remote on a block.");
            BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());

            if (blockEntity instanceof ExplosiveBlockEntity) {
                // Store bomb info (id, location, and dimension).

                String levelInfo = level.dimensionType().toString();
                BlockPos pos = blockEntity.getBlockPos();
                String id = ((ExplosiveBlockEntity)blockEntity).getUUID();

                //ItemStack stack = player.getItemInHand(hand);
                stack.set(ModDataComponents.REMOTE_PROPERTIES.get(), new RemoteProperties(pos.getX(), pos.getY(), pos.getZ(), levelInfo, id));
                System.out.println("Data saved to " + stack.getItem().getName(stack) + ":" + stack.get(ModDataComponents.REMOTE_PROPERTIES.get()));
            } else {
                RemoteProperties properties = stack.get(ModDataComponents.REMOTE_PROPERTIES.get());
                System.out.println("Properties: " + properties);
                //System.out.println(properties);
            }
        }

        return InteractionResult.SUCCESS;
    }*/

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            BlockEntity blockEntity = level.getBlockEntity(blockHitResult.getBlockPos());
            //System.out.println("Hit block pos: " + blockHitResult.getBlockPos());

            if (blockEntity instanceof ExplosiveBlockEntity) {
                // If we are clicking on an explosive, save its info to the remote.

                String levelInfo = level.dimensionType().toString();
                BlockPos pos = blockEntity.getBlockPos();
                String id = ((ExplosiveBlockEntity)blockEntity).getUUID();

                //ItemStack stack = player.getItemInHand(hand);
                stack.set(ModDataComponents.REMOTE_PROPERTIES.get(), new RemoteProperties(pos.getX(), pos.getY(), pos.getZ(), levelInfo, id));
                System.out.println("Data saved to " + stack.getItem().getName(stack) + ":" + stack.get(ModDataComponents.REMOTE_PROPERTIES.get()));
            } else {
                // Otherwise, try to detonate an explosive saved in the remote. Fail otherwise

                RemoteProperties properties = stack.get(ModDataComponents.REMOTE_PROPERTIES.get());

                if (!properties.equals(RemoteProperties.DEFAULT)) {
                    BlockPos retrievedPos = new BlockPos(properties.xPos, properties.yPos, properties.zPos);
                    BlockEntity retrievedEntity = level.getBlockEntity(retrievedPos);

                    // Check if we are in the same level, and if an explosive device is there
                    if (level.dimensionType().toString().equals(properties.level) && retrievedEntity instanceof ExplosiveBlockEntity) {
                        // Finally, if the UUID matches the UUID stored in the remote, we can detonate
                        if (((ExplosiveBlockEntity)retrievedEntity).getUUID().equals(properties.id)) {
                            level.destroyBlock(retrievedPos, false);
                        }
                    }
                }
                System.out.println("Properties: " + properties);
                //System.out.println(properties);
            }

            // Detonate stored bomb...
        } else {
            // Swing player arm visually on clientside
            if (!player.swinging) {
                player.swing(hand);
            }
        }

        return InteractionResultHolder.fail(stack);
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
