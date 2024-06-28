package com.gmail.damoruso321.bomb.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SpriteSet;

public class GasParticle extends SmokeParticle {
    protected GasParticle(ClientLevel p_107685_, double p_107686_, double p_107687_, double p_107688_, double p_107689_, double p_107690_, double p_107691_, float p_107692_, SpriteSet p_107693_) {
        super(p_107685_, p_107686_, p_107687_, p_107688_, p_107689_, p_107690_, p_107691_, p_107692_, p_107693_);

        this.rCol = 0f;
        this.gCol = 1f;
        this.bCol = 0f;
    }
}
