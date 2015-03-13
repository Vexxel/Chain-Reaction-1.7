package com.zerren.zedeng.client.fx;

import com.zerren.zedeng.reference.Textures;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Zerren on 3/11/2015.
 */
public class EntityRadiationFX extends EntityFX {

    public static final ResourceLocation particles = Textures.Misc.PARTICLES;

    public EntityRadiationFX(World world, double x, double y, double z) {
        this(world, x, y, z, 0D, 0D, 0D, 10);
    }

    /**
     *
     * @param power Strength of radiation--correlates to how long the particle lasts
     */
    public EntityRadiationFX(World world, double x, double y, double z, double velX, double velY, double velZ, int power) {
        super(world, x, y, z, 0D, 0D, 0D);

        motionX = motionY = motionZ = 0;

        motionX += velX;
        motionY += velY;
        motionZ += velZ;

        particleMaxAge = power;
        particleGravity = 0;

        setParticleTextureIndex(0);

        noClip = true;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
