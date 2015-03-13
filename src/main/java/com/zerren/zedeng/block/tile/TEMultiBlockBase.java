package com.zerren.zedeng.block.tile;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Created by Zerren on 3/11/2015.
 */
public class TEMultiBlockBase extends TileEntityZE {
    protected UUID masterID;
    protected int masterX, masterY, masterZ;

    public TEMultiBlockBase() {
        super();
        masterID = null;
    }

    public TEMultiBlockBase getCommandingController() {
        if (hasMaster() && getMasterUUID() != null) {
            return (TEMultiBlockBase)worldObj.getTileEntity(masterX, masterY, masterZ);
        }
        return null;
    }

    public UUID getMasterUUID() {
        if (masterID != null) {
            return masterID;
        }
        return null;
    }

    public boolean hasMaster() {
        return worldObj.getTileEntity(masterX, masterY, masterZ) != null;
    }

    public void setController(UUID id, int x, int y, int z) {
        masterID = id;
        masterX = x;
        masterY = y;
        masterZ = z;

        this.markDirty();
    }

    public void removeController() {
        masterID = null;
        masterX = 0;
        masterY = 0;
        masterZ = 0;
    }

    public int[] getMasterPos() {
        int[] pos = {masterX, masterY, masterZ};
        return pos;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        masterX = tag.getInteger("masterX");
        masterY = tag.getInteger("masterY");
        masterZ = tag.getInteger("masterZ");

        if (tag.hasKey("masterIDMost") && tag.hasKey("masterIDLeast")) {
            this.masterID = new UUID(tag.getLong("masterIDMost"), tag.getLong("masterIDLeast"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("masterX", masterX);
        tag.setInteger("masterY", masterY);
        tag.setInteger("masterZ", masterZ);

        if (this.hasMaster()) {
            tag.setLong("masterIDMost", masterID.getMostSignificantBits());
            tag.setLong("masterIDLeast", masterID.getLeastSignificantBits());
        }
    }
}
