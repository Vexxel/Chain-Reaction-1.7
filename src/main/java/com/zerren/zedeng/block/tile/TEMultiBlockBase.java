package com.zerren.zedeng.block.tile;

import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.client.tile.MessageTileMultiblock;
import com.zerren.zedeng.handler.network.client.tile.MessageTileZE;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Created by Zerren on 3/11/2015.
 */
public class TEMultiBlockBase extends TileEntityZE {
    protected UUID masterID;
    protected int masterX, masterY, masterZ;
    protected boolean isMaster;
    public boolean hasMaster;
    protected UUID controllerID;

    public TEMultiBlockBase() {
        super();
        masterID = null;
        isMaster = false;
        controllerID = null;
        hasMaster = false;
    }

    /**
     * If this tile entity has slave tile entities
     * @return
     */
    public boolean isMaster() {
        return isMaster;
    }

    /**
     * Set this tile entity as a maser that has slave tile entities?
     * @param b isMaster
     */
    public void setAsMaster(boolean b) {
        isMaster = b;
    }

    /**
     * Returns the TEMultiBlockBase that controls this tile entity or null if there is no master
     * @return
     */
    public TEMultiBlockBase getCommandingController() {
        if (hasValidMaster() && getMasterUUID() != null) {
            return (TEMultiBlockBase)worldObj.getTileEntity(masterX, masterY, masterZ);
        }
        return null;
    }

    /**
     * Break the multiblock that this tile entity is a part of (gets the commander and sets its master status as false)
     */
    public void invalidateMultiblock() { }

    /**
     * Returns the UUID of this tile entities' master
     * @return UUID
     */
    public UUID getMasterUUID() {
        if (masterID != null) {
            return masterID;
        }
        return null;
    }

    public void setControllerUUID(UUID id) {
        controllerID = id;
    }

    /**
     * If this tile entity is a part of a valid multiblock (master's UUID isn't null, master tile isn't null, and the master tile's UUID equals this tile's UUID)
     * @return
     */
    public boolean hasValidMaster() {
        TEMultiBlockBase masterTile = (TEMultiBlockBase)worldObj.getTileEntity(masterX, masterY, masterZ);

        //has a commanding UUID, the commander isn't null, and the commander's UUID matches this tile's UUID
        return getMasterUUID() != null && masterTile != null && masterTile.getControllerUUID().compareTo(getMasterUUID()) == 0;
    }

    /**
     * Sets this tile entity to belong to the master tile
     * @param id UUID of the master
     * @param x
     * @param y
     * @param z
     */
    public void setController(UUID id, int x, int y, int z) {
        masterID = id;
        masterX = x;
        masterY = y;
        masterZ = z;

        this.markDirty();
    }

    /**
     * Frees this tile entity from a master's control, removing it from the multiblock
     */
    public void removeController() {
        masterID = null;
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;

        this.markDirty();
    }

    /**
     * Returns the array of the x, y, and z of the master's location
     * @return
     */
    public int[] getMasterPos() {
        int[] pos = {masterX, masterY, masterZ};
        return pos;
    }

    /**
     * If this tile entity has a master UUID: if true, this block is a master
     * @return
     */
    public boolean hasControllerID() {
        return controllerID != null;
    }

    /**
     * Returns the master UUID
     * @return UUID of master
     */
    public UUID getControllerUUID() {
        return controllerID;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        masterX = tag.getInteger("masterX");
        masterY = tag.getInteger("masterY");
        masterZ = tag.getInteger("masterZ");
        isMaster = tag.getBoolean("isMaster");

        if (tag.hasKey("masterIDMost") && tag.hasKey("masterIDLeast")) {
            this.masterID = new UUID(tag.getLong("masterIDMost"), tag.getLong("masterIDLeast"));
        }

        if (tag.hasKey("controllerIDMost") && tag.hasKey("controllerIDLeast")) {
            this.controllerID = new UUID(tag.getLong("controllerIDMost"), tag.getLong("controllerIDLeast"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("masterX", masterX);
        tag.setInteger("masterY", masterY);
        tag.setInteger("masterZ", masterZ);
        tag.setBoolean("isMaster", isMaster);

        if (this.hasValidMaster()) {
            tag.setLong("masterIDMost", masterID.getMostSignificantBits());
            tag.setLong("masterIDLeast", masterID.getLeastSignificantBits());
        }

        if (this.hasControllerID()) {
            tag.setLong("controllerIDMost", controllerID.getMostSignificantBits());
            tag.setLong("controllerIDLeast", controllerID.getLeastSignificantBits());
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.netHandler.getPacketFrom(new MessageTileMultiblock(this, isMaster, hasValidMaster()));
    }
}
