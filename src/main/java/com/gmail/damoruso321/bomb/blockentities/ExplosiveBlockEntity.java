package com.gmail.damoruso321.bomb.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveBlockEntity extends BlockEntity {
    private String uuid;
    private static final String nbtKey = "UUIDkey";

    public ExplosiveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXPLOSIVE_BLOCK_ENTITY.get(), pos, state);
    }

    public void storeUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider p_327783_) {
        super.saveAdditional(nbt, p_327783_);
        nbt.putString(nbtKey, uuid);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider p_327783_) {
        super.loadAdditional(nbt, p_327783_);
        this.uuid = nbt.getString(nbtKey);
    }
}
