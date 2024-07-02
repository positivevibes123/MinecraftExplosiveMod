package com.gmail.damoruso321.bomb.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GasParticle extends SmokeParticle {
    protected GasParticle(ClientLevel p_107685_, double p_107686_, double p_107687_, double p_107688_, double p_107689_, double p_107690_, double p_107691_, float p_107692_, SpriteSet p_107693_) {
        super(p_107685_, p_107686_, p_107687_, p_107688_, p_107689_, p_107690_, p_107691_, p_107692_, p_107693_);

        // Add some variation in color to make more visually interesting
        float randomGreen = (float)((Math.random() * (1.0f - .5f)) + .5f);

        this.rCol = 0f;
        this.gCol = randomGreen;
        this.bCol = 0f;

        this.quadSize = 1f;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType p_107707_, ClientLevel p_107708_, double p_107709_, double p_107710_, double p_107711_, double p_107712_, double p_107713_, double p_107714_) {
            GasParticle particle = new GasParticle(p_107708_, p_107709_, p_107710_, p_107711_, p_107712_, p_107713_, p_107714_, 1.0F, this.sprites);
            return particle;
        }
    }
}
