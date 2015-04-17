package com.zerren.zedeng.block.tile;

import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.client.tile.MessageTileZE;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.utility.TileCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Created by Zerren on 2/20/2015.
 */
public class TileEntityZE extends TileEntity {

    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;
    protected UUID ownerUUID;
    protected TileCache[] tileCache;
    protected boolean canFaceUpDown;

    public TileEntityZE() {
        orientation = ForgeDirection.SOUTH;
        state = 0;
        customName = "";
        ownerUUID = null;
        tileCache = null;
        canFaceUpDown = false;
    }

    @Override
    public void updateEntity() {}

    @Override
    public boolean canUpdate() {
        return false;
    }

    public void updateCache() {
        tileCache = TileCache.buildCache(worldObj, xCoord, yCoord, zCoord);
    }

    public void information(TileEntityZE tile) {
        System.out.println("Made it this far, Mr " + tile);
    }

    public boolean canFaceUpDown() {
        return canFaceUpDown;
    }

    public ForgeDirection getOrientation() {
        return orientation;
    }

    public ForgeDirection spinLeft(ForgeDirection dir, boolean doSpin) {
        ForgeDirection newDir;
        switch (dir) {
            case NORTH: newDir = ForgeDirection.WEST; break;
            case EAST: newDir = ForgeDirection.NORTH; break;
            case SOUTH: newDir = ForgeDirection.EAST; break;
            case WEST: newDir = ForgeDirection.SOUTH; break;
            default: newDir = getOrientation();
        }
        if (doSpin)
            setOrientation(newDir);
        return newDir;
    }

    public ForgeDirection spinRight(ForgeDirection dir, boolean doSpin) {
        ForgeDirection newDir = spinLeft(dir, false).getOpposite();
        if (doSpin)
            setOrientation(newDir);
        return newDir;
    }

    public void setOrientation(ForgeDirection orientation) {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public short getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void setOwnerUUID(UUID id){
        this.ownerUUID = id;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwner(EntityPlayer player) {
        ownerUUID = player.getPersistentID();
    }

    protected String getStringLocale() {
        return "X:" + xCoord + " Y:" + yCoord + " Z:" + zCoord;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (tag.hasKey(Names.NBT.DIRECTION)) {
            this.orientation = ForgeDirection.getOrientation(tag.getByte(Names.NBT.DIRECTION));
        }
        if (tag.hasKey(Names.NBT.STATE)) {
            this.state = tag.getByte(Names.NBT.STATE);
        }
        if (tag.hasKey(Names.NBT.CUSTOM_NAME)) {
            this.customName = tag.getString(Names.NBT.CUSTOM_NAME);
        }
        if (tag.hasKey(Names.NBT.OWNER_UUID_MOST_SIG) && tag.hasKey(Names.NBT.OWNER_UUID_LEAST_SIG)) {
            this.ownerUUID = new UUID(tag.getLong(Names.NBT.OWNER_UUID_MOST_SIG), tag.getLong(Names.NBT.OWNER_UUID_LEAST_SIG));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setByte(Names.NBT.DIRECTION, (byte) orientation.ordinal());
        tag.setByte(Names.NBT.STATE, state);

        if (this.hasCustomName()) {
            tag.setString(Names.NBT.CUSTOM_NAME, customName);
        }
        if (this.hasOwner()) {
            tag.setLong(Names.NBT.OWNER_UUID_MOST_SIG, ownerUUID.getMostSignificantBits());
            tag.setLong(Names.NBT.OWNER_UUID_LEAST_SIG, ownerUUID.getLeastSignificantBits());
        }
    }

    public boolean hasCustomName() {
        return customName != null && customName.length() > 0;
    }

    public boolean hasOwner() {
        return ownerUUID != null;
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.netHandler.getPacketFrom(new MessageTileZE(this));
    }
}
