package com.zerren.zedeng.tile.vault;

import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.utility.CoreUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Created by Zerren on 2/20/2015.
 */
public class TEVaultController extends TEVaultBase implements IInventory {

    private ItemStack[] inventory = new ItemStack[54 * ConfigHandler.vaultPages];

    private boolean allBreakable = true;
    public int page = 0;
    public int selection;
    public final int numPages = ConfigHandler.vaultPages;

    public TEVaultController() {
        super();
    }

    public UUID getControllerUUID() {
        return controllerID;
    }

    public boolean hasControllerID() {
        return controllerID != null;
    }

    public void setPage(int pg) {
        this.page = pg;
    }

    public void playSFXatCore(String sfx, float volume, float pitch) {
        int[] cPos = getCoreBlock();
        worldObj.playSoundEffect((double)cPos[0] + 0.5D, (double)cPos[1] + 0.5D, (double)cPos[2] + 0.5D, sfx, volume, pitch);
    }

    public int[] getCoreBlock() {
        ForgeDirection direction = getOrientation();
        int cX = 0, cZ = 0;

        if (direction == ForgeDirection.NORTH) {
            cX = xCoord;
            cZ = zCoord + 2;
        }
        if (direction == ForgeDirection.EAST) {
            cX = xCoord - 2;
            cZ = zCoord;
        }
        if (direction == ForgeDirection.SOUTH) {
            cX = xCoord;
            cZ = zCoord - 2;
        }
        if (direction == ForgeDirection.WEST) {
            cX = xCoord + 2;
            cZ = zCoord;
        }

        return new int[] {cX, yCoord, cZ};
    }

    public void initiateController(UUID id, EntityPlayer player) {
        if (controllerID == null) {
            controllerID = id;
        }

        //if the controller has an owner
        if (getOwnerUUID() != null) {
            //if the controller's owner isn't the person who activated this
            if (player.getPersistentID().compareTo(getOwnerUUID()) != 0) return;
        }
        //else set the ownerUUID to the person who placed/activated's UUID
        else {
            setOwner(player);
        }
        if (!worldObj.isRemote){
            if (checkMultiblock(this.getOwnerUUID(), player)) {
                //success
                CoreUtility.addColoredChat("gui.info.controller.success1.name", EnumChatFormatting.YELLOW, player);
                CoreUtility.addColoredChat("gui.info.controller.success2.name", EnumChatFormatting.YELLOW, player);
                isMaster = true;
            }
            else {
                //failure
                CoreUtility.addColoredChat("gui.info.controller.failure1.name", EnumChatFormatting.YELLOW, player);
                CoreUtility.addColoredChat("gui.info.controller.failure2.name", EnumChatFormatting.YELLOW, player);
                isMaster = false;
            }
        }
    }

    public boolean checkMultiblock(UUID ownedBy, EntityPlayer player) {
        ForgeDirection direction = getOrientation();

        int cX = 0;
        int cY = yCoord - 2;
        int cZ = 0;

        int counter = 0;
        int cCounter = 0;

        //gets the center bottom block (of the 5x5x5) from a direction check
        if (direction == ForgeDirection.NORTH) {
            //all but z-
            TEVaultBase center = (TEVaultBase) worldObj.getTileEntity(xCoord, cY, zCoord + 2);
            if (center != null) {
                cX = xCoord;
                cZ = zCoord + 2;
            }
            else return false;
        }
        if (direction == ForgeDirection.EAST) {
            //all but x+
            TEVaultBase center = (TEVaultBase) worldObj.getTileEntity(xCoord - 2, cY, zCoord);
            if (center != null) {
                cX = xCoord - 2;
                cZ = zCoord;
            }
            else return false;
        }
        if (direction == ForgeDirection.SOUTH) {
            //all but z+
            TEVaultBase center = (TEVaultBase) worldObj.getTileEntity(xCoord, cY, zCoord - 2);
            if (center != null) {
                cX = xCoord;
                cZ = zCoord - 2;
            }
            else return false;
        }
        if (direction == ForgeDirection.WEST) {
            //all but x-
            TEVaultBase center = (TEVaultBase) worldObj.getTileEntity(xCoord + 2, cY, zCoord);
            if (center != null) {
                cX = xCoord + 2;
                cZ = zCoord;
            }
            else return false;
        }

        TEVaultBase lock = (TEVaultBase) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        if (lock != null && !(lock instanceof TEVaultLock)) {
            if (!worldObj.isRemote)
                CoreUtility.addColoredChat("gui.info.controller.nokeyhole.name", EnumChatFormatting.RED, player);
            return false;
        }
        //scan the 5x5 starting with bottom left centered on the middle bottom
        vaultCheck : for (int x = cX - 2; x < cX + 3; x++) {
            for (int y = cY; y < cY + 5; y++) {
                for (int z = cZ - 2; z < cZ + 3; z++) {
                    TileEntity vault = worldObj.getTileEntity(x, y, z);
                    //make sure the block has a vault tile entity, but don't count other controllers
                    if (vault != null && (vault instanceof TEVaultBase) && !(vault instanceof TEVaultController)) {
                        //check to see if the ownerUUID of the vault controller is the same as every vault block, or if those vault blocks have no ownerUUID
                        if (((TEVaultBase) vault).getOwnerUUID() == null || ((TEVaultBase) vault).getOwnerUUID().compareTo(ownedBy) == 0) {
                            if (((TEVaultBase) vault).getMasterUUID() == null || ((TEVaultBase) vault).getMasterUUID().compareTo(this.getControllerUUID()) == 0) {
                                counter++;
                            }
                            else break vaultCheck;
                        }
                        //container counter--need 27 (3^3)
                        if (vault.getWorldObj().getBlockMetadata(x, y, z) == 5) cCounter++;
                    }
                }
            }
        }
        if (counter >= 124 && cCounter == 27) {
            allBreakable ^= true;

            if (allBreakable) CoreUtility.addColoredChat("gui.info.controller.breakable.name", EnumChatFormatting.YELLOW, player);
            else CoreUtility.addColoredChat("gui.info.controller.unbreakable.name", EnumChatFormatting.YELLOW, player);

            for (int x = cX - 2; x < cX + 3; x++) {
                for (int y = cY; y < cY + 5; y++) {
                    for (int z = cZ - 2; z < cZ + 3; z++) {
                        TileEntity vault = worldObj.getTileEntity(x, y, z);
                        //make sure the block has a vault tile entity
                        if (vault != null && (vault instanceof TEVaultBase)) {
                            //check to see if the ownerName of the vault controller is the same as every vault block, or if those vault blocks have no ownerName
                            if (((TEVaultBase) vault).getOwnerUUID() == null || ((TEVaultBase) vault).getOwnerUUID().compareTo(ownedBy) == 0) {
                                //set the vault block's ownerName to the command block's ownerName and give each block the coords of the controller
                                ((TEVaultBase) vault).setController(controllerID, xCoord, yCoord, zCoord);
                                ((TEVaultBase) vault).setOwnerUUID(ownedBy);
                                ((TEVaultBase) vault).setBreakable(allBreakable);
                                vault.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
                            }
                        }
                    }
                }
            }
        }
        //total count of the 5^3 (125) - controller, and if exactly 3^3 (27) of them are container blocks
        if (counter >= 124 && cCounter == 27) {
            isMaster = true;
            return true;
        }
        else {
            isMaster = false;
            return false;
        }
    }

    public void breakMultiblock() {
        ForgeDirection direction = this.getOrientation();
        int cX = 0;
        int cY = yCoord - 2;
        int cZ = 0;

        System.out.println(isMaster);

        if (!isMaster) return;

        if (direction == ForgeDirection.NORTH) {
            cX = xCoord;
            cZ = zCoord + 2;
        }
        if (direction == ForgeDirection.EAST) {
            cX = xCoord - 2;
            cZ = zCoord;
        }
        if (direction == ForgeDirection.SOUTH) {
            cX = xCoord;
            cZ = zCoord - 2;
        }
        if (direction == ForgeDirection.WEST) {
            cX = xCoord + 2;
            cZ = zCoord;
        }

        int checkcounter = 0;

        for (int x = cX - 2; x < cX + 3; x++) {
            for (int y = cY; y < cY + 5; y++) {
                for (int z = cZ - 2; z < cZ + 3; z++) {
                    TileEntity vault = worldObj.getTileEntity(x, y, z);
                    //make sure the block has a vault tile entity
                    if (vault != null && (vault instanceof TEVaultBase)) {
                        //check to see if the ownerName of the vault controller is the same as every vault block, or if those vault blocks have no ownerName
                        if (((TEVaultBase) vault).getOwnerUUID() == null || ((TEVaultBase) vault).getOwnerUUID().equals(this.ownerUUID)) {
                            //set the vault block's ownerName to the command block's ownerName and give each block the coords of the controller
                            if (!(vault instanceof TEVaultController)) {
                                ((TEVaultBase) vault).setOwnerUUID(null);
                                ((TEVaultBase) vault).removeController();
                            }
                            ((TEVaultBase) vault).setBreakable(true);
                            vault.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
                            checkcounter++;
                        }
                    }
                }
            }
        }
        System.out.println(checkcounter);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("allBreakable", allBreakable);
        tag.setInteger("page", page);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound itemtag = new NBTTagCompound();
                itemtag.setShort("Slot", (short) i);
                this.inventory[i].writeToNBT(itemtag);
                nbttaglist.appendTag(itemtag);
            }
        }

        tag.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.customName);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        allBreakable = tag.getBoolean("allBreakable");
        page = tag.getInteger("page");

        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        if (tag.hasKey("CustomName", 8))
        {
            this.customName = tag.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound itemtag = nbttaglist.getCompoundTagAt(i);
            int slot = itemtag.getShort("Slot");

            if (slot >= 0 && slot < this.inventory.length)
            {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemtag);
            }
        }
    }

    public int getSizeInventory() {
        return 54 * ConfigHandler.vaultPages;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= count) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            }
            else {
                itemstack = this.inventory[slot].splitStack(count);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return CoreUtility.translate("container.vault.name");
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }
}
