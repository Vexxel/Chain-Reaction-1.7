package com.zerren.zedeng.block.tile.chest;

import com.zerren.zedeng.api.materials.ZedBlocks;
import com.zerren.zedeng.block.tile.TileEntityZE;
import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.client.tile.MessageTileChest;
import com.zerren.zedeng.inventory.ContainerChestZE;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.utility.CoreUtility;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by Zerren on 2/28/2015.
 */
public class TEChest extends TileEntityZE implements IInventory
{
    /**
     * The current angle of the chest lid (between 0 and 1)
     */
    public float lidAngle;

    /**
     * The angle of the chest lid last tick
     */
    public float prevLidAngle;

    /**
     * The number of players currently using this chest
     */
    public int numUsingPlayers;

    /**
     * Server sync counter (once per 20 ticks)
     */
    private int ticksSinceSync;
    private ItemStack[] inventory;
    private boolean locked;
    private String code = "";

    public TEChest(int metaData) {
        super();
        this.state = (byte) metaData;

        if (metaData == 0) {
            inventory = new ItemStack[ContainerChestZE.BRICK_ROWS * ContainerChestZE.BRICK_COLUMNS];
        }
        else if (metaData == 1) {
            inventory = new ItemStack[ContainerChestZE.THAUMIUM_ROWS * ContainerChestZE.THAUMIUM_COLUMNS];
        }
        else if (metaData == 2) {
            inventory = new ItemStack[ContainerChestZE.VOID_ROWS * ContainerChestZE.VOID_COLUMNS];
        }
    }

    public void setChestCode(String code, EntityPlayer player) {
        this.code = code;
        CoreUtility.addChat("Set lock code to: " + code, player);
        this.markDirty();

    }

    public boolean hasChestCode() {
        return code.length() > 0;
    }

    public String getChestCode() {
        return code;
    }

    public void setChestLocked(boolean b) {
        this.locked = b;
    }

    public boolean getChestLocked() {
        return locked;
    }
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    public boolean tryCode(String keyCode, EntityPlayer player, boolean bedrock) {
        if (bedrock) {
            if (!getChestLocked()) setChestLocked(true);
            else setChestLocked(false);
            CoreUtility.addColoredChat("gui.info.keyhole.creative.name", EnumChatFormatting.YELLOW, player);
            worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.Sounds.LOCK_SUCCESS, 1F, 1F);

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            //informPlayers();
            return true;
        }
        if (keyCode == null) return false;

        //System.out.println("Used " + keyCode + " on lock " + code);
        if (!worldObj.isRemote) {
            if (keyCode.contains(code)) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.Sounds.LOCK_SUCCESS, 1F, 1F);
                if (!getChestLocked()) setChestLocked(true);
                else setChestLocked(false);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

                //informPlayers();
                return true;
            }
            else {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.Sounds.LOCK_FAILURE, 1F, 1F);
                CoreUtility.addColoredChat("gui.info.keyhole.deny.name", EnumChatFormatting.YELLOW, player);
                return false;
            }
        }
        this.markDirty();

        return false;
    }

    public void informPlayers() {
        PacketHandler.netHandler.sendToAllAround(new MessageTileChest(this, getChestLocked()), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 128d));
    }
    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (inventory[slotIndex] != null)
        {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }


        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomName() ? this.getCustomName() : CoreUtility.translate("container.chest.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.hasCustomName();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param entityplayer The player we are checking to see if they can use this chest
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }


    @Override
    public void openInventory() {
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ZedBlocks.chest, 1, numUsingPlayers);
    }

    @Override
    public void closeInventory() {
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ZedBlocks.chest, 1, numUsingPlayers);
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (++ticksSinceSync % 20 * 4 == 0)
        {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ZedBlocks.chest, 1, numUsingPlayers);
        }

        prevLidAngle = lidAngle;
        float angleIncrement = 0.1F;
        double adjustedXCoord, adjustedZCoord;

        if (numUsingPlayers > 0 && lidAngle == 0.0F)
        {
            adjustedXCoord = xCoord + 0.5D;
            adjustedZCoord = zCoord + 0.5D;
            worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, Reference.Sounds.CHEST_OPEN, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
        {
            float var8 = lidAngle;

            if (numUsingPlayers > 0)
            {
                lidAngle += angleIncrement;
            }
            else
            {
                lidAngle -= angleIncrement;
            }

            if (lidAngle > 1.0F)
            {
                lidAngle = 1.0F;
            }

            if (lidAngle < 0.5F && var8 >= 0.5F)
            {
                adjustedXCoord = xCoord + 0.5D;
                adjustedZCoord = zCoord + 0.5D;
                worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, Reference.Sounds.CHEST_CLOSE, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (lidAngle < 0.0F)
            {
                lidAngle = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    @Override
    public boolean receiveClientEvent(int eventID, int numUsingPlayers)
    {
        if (eventID == 1)
        {
            this.numUsingPlayers = numUsingPlayers;
            return true;
        }
        else
        {
            return super.receiveClientEvent(eventID, numUsingPlayers);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        code = nbtTagCompound.getString("code");
        locked = nbtTagCompound.getBoolean("locked");

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setString("code", code);
        nbtTagCompound.setBoolean("locked", locked);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.netHandler.getPacketFrom(new MessageTileChest(this, getChestLocked()));
    }
}
