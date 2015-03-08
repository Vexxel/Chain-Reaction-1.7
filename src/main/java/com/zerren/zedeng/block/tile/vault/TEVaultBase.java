package com.zerren.zedeng.block.tile.vault;

import com.zerren.zedeng.block.tile.TileEntityZE;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.tileentity.MessageTileChest;
import com.zerren.zedeng.handler.network.tileentity.MessageTileVault;
import com.zerren.zedeng.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import java.util.UUID;

/**
 * Created by Zerren on 2/22/2015.
 */
public class TEVaultBase extends TileEntityZE {

    private boolean breakable;
    private UUID masterID;
    private int masterX, masterY, masterZ;

    public TEVaultBase() {
        super();
        masterID = null;
    }

    public boolean isBreakable() {
        return breakable;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    public void setBreakable(boolean b) {
        breakable = b;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        this.markDirty();
    }

    public TEVaultController getCommandingController() {
        if (hasMaster() && getControllerID() != null) {
            return (TEVaultController)worldObj.getTileEntity(masterX, masterY, masterZ);
        }
        return null;
    }

    public void setController(UUID id, int x, int y, int z) {
        masterID = id;
        masterX = x;
        masterY = y;
        masterZ = z;

        this.markDirty();
    }

    public int[] getMasterPos() {
        int[] pos = {masterX, masterY, masterZ};
        return pos;
    }

    public void removeController() {
        masterID = null;
        masterX = 0;
        masterY = 0;
        masterZ = 0;
    }

    public UUID getControllerID() {
        if (masterID != null) {
            return masterID;
        }
        return null;
    }

    public boolean hasMaster() {
        return worldObj.getTileEntity(masterX, masterY, masterZ) != null;
    }

    public void unlockAdjacent(int x, int y, int z, EntityPlayer player) {
        toggleBlock(x - 1, y, z, player);
        toggleBlock(x + 1, y, z, player);

        toggleBlock(x, y - 1, z, player);
        toggleBlock(x, y + 1, z, player);

        toggleBlock(x, y, z - 1, player);
        toggleBlock(x, y, z + 1, player);
    }

    private void toggleBlock(int x, int y, int z, EntityPlayer player) {
        //3 is closed, 4 is open
        int meta = worldObj.getBlockMetadata(x, y, z);

        switch (meta) {
            case 1:
                TEVaultBase controller = (TEVaultBase) worldObj.getTileEntity(x, y, z);
                if (controller != null && controller instanceof TEVaultController) {
                    ((TEVaultController) controller).checkMultiblock(getOwnerUUID(), player);
                }
                break;
            case 3:
                worldObj.setBlockMetadataWithNotify(x, y, z, 4, 2);
                worldObj.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.door_open", 0.5F, worldObj.rand.nextFloat() * 0.25F + 0.5F);
                break;
            case 4:
                worldObj.setBlockMetadataWithNotify(x, y, z, 3, 2);
                worldObj.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.door_close", 0.5F, worldObj.rand.nextFloat() * 0.25F + 0.5F);
                break;
        }

        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        breakable = tag.getBoolean("breakable");

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
        tag.setBoolean("breakable", breakable);

        tag.setInteger("masterX", masterX);
        tag.setInteger("masterY", masterY);
        tag.setInteger("masterZ", masterZ);

        if (this.hasMaster()) {
            tag.setLong("masterIDMost", masterID.getMostSignificantBits());
            tag.setLong("masterIDLeast", masterID.getLeastSignificantBits());
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.netHandler.getPacketFrom(new MessageTileVault(this, isBreakable()));
    }
}