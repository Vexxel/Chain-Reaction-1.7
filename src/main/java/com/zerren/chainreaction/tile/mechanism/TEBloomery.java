package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.block.IInventoryCR;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileBloomery;
import com.zerren.chainreaction.reference.MultiblockShape;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NetworkUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.UUID;

/**
 * Created by Zerren on 5/19/2016.
 */
public class TEBloomery extends TEMultiBlockBase implements IInventoryCR {

    public boolean isActive = false;
    protected ItemStack[] inventory = new ItemStack[10];

    public TEBloomery() {
        super();
    }

    @Override
    public void updateEntity() {

        if (!worldObj.isRemote && isMaster()) {
            //if ()
            this.isActive = true;
            //PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(this, isMaster(), hasMaster, true, isActive), NetworkUtility.makeTargetPoint(this));
        }
    }

    public void initiateController(UUID id, EntityPlayer player) {
        if (controllerID == null) {
            setControllerUUID(id);
        }
        if (ownerUUID == null) {
            setOwner(player);
        }

        if (!this.isMaster && !this.hasValidMaster()){
            if (checkMultiblock(player)) {
                buildMultiblock(player);
                ChainReaction.proxy.playSound(this.worldObj, xCoord, yCoord, zCoord, Reference.Sounds.LOCK_SUCCESS, 1.0F, 1.0F);
            }
        }
    }

    private boolean checkMultiblock(EntityPlayer player) {
        MultiblockShape[][][] structure = MultiblockShape.bloomery;
        int counter = 0;

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 4; y++)
                for (int z = 0; z < 3; z++) {
                    TileEntity tile = worldObj.getTileEntity(xCoord + (x - 1), yCoord + (y), zCoord + (z - 1));
                    if (tile instanceof TEMultiBlockBase && (((TEMultiBlockBase) tile).hasValidMaster() || ((TEMultiBlockBase) tile).isMaster()))
                        return false;

                    if (structure[x][y][z].getBlock() == Blocks.air) counter++;

                    else if (worldObj.getBlock(xCoord + (x - 1), yCoord + (y), zCoord + (z - 1)) == structure[x][y][z].getBlock()) {
                        if (worldObj.getBlockMetadata(xCoord + (x - 1), yCoord + (y), zCoord + (z - 1)) == structure[x][y][z].getMetadata()) {
                            counter++;
                        }
                    }
                }
        ChainReaction.log.info("" + counter);
        if (counter == 36) {
            ChainReaction.log.info("Success");
            return true;
        }
        else ChainReaction.log.info("Failure");
        return false;
    }

    private void buildMultiblock(EntityPlayer player) {
        MultiblockShape[][][] structure = MultiblockShape.bloomery;
        this.setAsMaster(true);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 4; y++)
                for (int z = 0; z < 3; z++) {
                    if (structure[x][y][z].getBlock() != CRBlocks.mechanism) continue;

                    TEBloomery tile = (TEBloomery) worldObj.getTileEntity(xCoord + (x - 1), yCoord + (y), zCoord + (z - 1));

                    tile.setController(this.getControllerUUID(), xCoord, yCoord, zCoord);

                    tile.setMultiblockPartNumber((short) ((x * 100) + (y * 10) + (z)));
                    tile.setOrientation(this.getOrientation());
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(tile, tile.isMaster(), true, true, isActive), NetworkUtility.makeTargetPoint(this));
                }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void invalidateMultiblock() {
        TEMultiBlockBase commander = getCommandingController();
        if (commander == null) commander = this;
        commander.setAsMaster(false);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 4; y++)
                for (int z = 0; z < 3; z++) {
                    TEBloomery tile = (TEBloomery) worldObj.getTileEntity(commander.xCoord + (x - 1), commander.yCoord + (y), commander.zCoord + (z - 1));
                    if (tile != null) {
                        tile.removeController();
                        tile.setMultiblockPartNumber((short) -1);
                        PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(tile, false, false, true, isActive), NetworkUtility.makeTargetPoint(this));
                        ChainReaction.proxy.updateTileModel(tile);
                    }
                }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {

            this.inventory = readNBTItems(tag, this);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {

            writeNBTItems(tag, inventory);
        }
    }

    @Override
    public void clearInventory() {
        for (int i = 0; i < getSizeInventory(); i++) {
            inventory[i] = null;
        }
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    @Override
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

    @Override
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

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return CoreUtility.translate("container.bloomery.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot < 4) {
            return stack.getItem() == Items.coal && stack.getItemDamage() == 1;
        }
        if (slot >= 4 && slot < 8) {
            return CoreUtility.hasDictionaryMatch(stack, "oreIron");
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster())
            return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
        return super.getRenderBoundingBox();
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileBloomery(this, isMaster, hasValidMaster(), false, isActive));
    }
}
