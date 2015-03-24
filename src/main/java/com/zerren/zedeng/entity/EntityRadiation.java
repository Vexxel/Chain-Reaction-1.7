package com.zerren.zedeng.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by Zerren on 3/12/2015.
 */
public class EntityRadiation extends Entity {

    private int power;

    public EntityRadiation(World world, double x, double y, double z, int power) {
        super(world);
        this.power = power;

        this.renderDistanceWeight = 10.0D;
        this.setSize(0.25F, 0.25F);
    }

    @Override
    protected void entityInit() {

    }

    public int getPower() {
        return power;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }
}
