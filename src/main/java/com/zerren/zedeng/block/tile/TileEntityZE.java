package com.zerren.zedeng.block.tile;

import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.client.tile.MessageTileZE;
import com.zerren.zedeng.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Created by Zerren on 2/20/2015.
 */
public class TileEntityZE extends TileEntity
{
    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;
    protected UUID ownerUUID;

    public TileEntityZE() {
        orientation = ForgeDirection.SOUTH;
        state = 0;
        customName = "";
        ownerUUID = null;
    }

    @Override
    public void updateEntity() {}

    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public void information(TileEntityZE tile) {
        System.out.println("Made it this far, Mr " + tile);
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

    public void setOrientation(ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public short getState()
    {
        return state;
    }

    public void setState(byte state)
    {
        this.state = state;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
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

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(Names.NBT.DIRECTION))
        {
            this.orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(Names.NBT.DIRECTION));
        }

        if (nbtTagCompound.hasKey(Names.NBT.STATE))
        {
            this.state = nbtTagCompound.getByte(Names.NBT.STATE);
        }

        if (nbtTagCompound.hasKey(Names.NBT.CUSTOM_NAME))
        {
            this.customName = nbtTagCompound.getString(Names.NBT.CUSTOM_NAME);
        }

        if (nbtTagCompound.hasKey(Names.NBT.OWNER_UUID_MOST_SIG) && nbtTagCompound.hasKey(Names.NBT.OWNER_UUID_LEAST_SIG))
        {
            this.ownerUUID = new UUID(nbtTagCompound.getLong(Names.NBT.OWNER_UUID_MOST_SIG), nbtTagCompound.getLong(Names.NBT.OWNER_UUID_LEAST_SIG));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Names.NBT.DIRECTION, (byte) orientation.ordinal());
        nbtTagCompound.setByte(Names.NBT.STATE, state);

        if (this.hasCustomName())
        {
            nbtTagCompound.setString(Names.NBT.CUSTOM_NAME, customName);
        }

        if (this.hasOwner())
        {
            nbtTagCompound.setLong(Names.NBT.OWNER_UUID_MOST_SIG, ownerUUID.getMostSignificantBits());
            nbtTagCompound.setLong(Names.NBT.OWNER_UUID_LEAST_SIG, ownerUUID.getLeastSignificantBits());
        }
    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public boolean hasOwner()
    {
        return ownerUUID != null;
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.netHandler.getPacketFrom(new MessageTileZE(this));
    }


}
