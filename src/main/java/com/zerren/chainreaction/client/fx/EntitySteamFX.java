package com.zerren.chainreaction.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

/**
 * Created by Zerren on 3/11/2015.
 */
public class EntitySteamFX extends EntityFX {
    float steamParticleScale;

    public EntitySteamFX(World world, double x, double y, double z, double vX, double vY, double vZ, float scale)
    {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);

        this.motionX *= 0.1;
        this.motionY *= 0.1;
        this.motionZ *= 0.1;

        this.motionX += vX / 10;
        this.motionY += vY / 10;
        this.motionZ += vZ / 10;

        this.particleRed = this.particleGreen = this.particleBlue = (float)((Math.random() * 0.2) + 0.8);
        this.particleAlpha = 0.8F;

        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.steamParticleScale = this.particleScale;

        this.particleMaxAge = (int)(2.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int)((float)this.particleMaxAge * scale);
        this.noClip = false;
    }

    public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
        float f6 = ((float)this.particleAge + p_70539_2_) / (float)this.particleMaxAge * 32.0F;

        if (f6 < 0.0F)
        {
            f6 = 0.0F;
        }

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.particleScale = this.steamParticleScale * f6;
        super.renderParticle(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
    }

    public void onUpdate() {

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.motionY += 0.004D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}
