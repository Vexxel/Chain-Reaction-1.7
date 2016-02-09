package com.zerren.chainreaction.tile;

import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileCR;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.TileCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Created by Zerren on 2/20/2015.
 */
public class TileEntityCRBase extends TileEntity {

    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;
    protected UUID ownerUUID;
    protected TileCache[] tileCache;
    protected boolean canFaceUpDown;

    public TileEntityCRBase() {
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

    /**
     * Quick method for writing tile inventory contents to NBT
     * @param tag
     * @param inv
     */
    protected void writeNBTItems(NBTTagCompound tag, ItemStack[] inv) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < inv.length; ++i) {
            if (inv[i] != null) {
                NBTTagCompound itemtag = new NBTTagCompound();
                itemtag.setShort("Slot", (short) i);
                inv[i].writeToNBT(itemtag);
                nbttaglist.appendTag(itemtag);
            }
        }

        tag.setTag("Items", nbttaglist);
    }

    protected ItemStack[] readNBTItems(NBTTagCompound tag, IInventory tile) {
        ItemStack[] inv = new ItemStack[tile.getSizeInventory()];
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound itemtag = nbttaglist.getCompoundTagAt(i);
            int slot = itemtag.getShort("Slot");

            if (slot >= 0 && slot < inv.length) {
                inv[slot] = ItemStack.loadItemStackFromNBT(itemtag);
            }
        }

        return inv;
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
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileCR(this));
    }
}
