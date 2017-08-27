package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.block.IInventoryCR;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileBloomery;
import com.zerren.chainreaction.item.ItemOres;
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

import java.util.Random;
import java.util.UUID;

/**
 * Created by Zerren on 5/19/2016.
 */
public class TEBloomery extends TEMultiBlockBase implements IInventoryCR {


    protected ItemStack[] inventory = new ItemStack[10];
    public int bloomeryCookTime = 0;
    public int bloomeryBurnTime = 0;
    private int burnCheck = 0;

    private static final int COOK_TIME = 20;
    private static final int BURN_TIME = 20;

    public TEBloomery() {
        super();
    }

    @Override
    public void updateEntity() {

        boolean dirty = false;

        //decrement fuel burn time
        if (this.bloomeryBurnTime > 0) {
            --this.bloomeryBurnTime;

            //stop burning
            if (bloomeryBurnTime <= 0) {
                PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(this, this.isMaster(), true, true, isBurning()), NetworkUtility.makeTargetPoint(this));
            }
        }


        if (!worldObj.isRemote && isMaster()) {

            burnCheck++;
            if (burnCheck >= 20) {
                //not burning and has work
                if (!isBurning() && hasWork(getBloomSize())) {
                    //set burning
                    if (consumeFuel()) {
                        bloomeryBurnTime = BURN_TIME;
                        PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(this, this.isMaster(), true, true, isBurning()), NetworkUtility.makeTargetPoint(this));
                    }
                }
                burnCheck = 0;
            }
            //burning
            if (bloomeryBurnTime != 0) {
                if (hasWork(getBloomSize())) {
                    bloomeryCookTime++;

                    if (bloomeryCookTime >= COOK_TIME) {
                        reduceIron();
                        bloomeryCookTime = 0;
                        dirty = true;
                    }
                }
            }
        }

        if (dirty) {
            this.markDirty();
        }
    }

    private void reduceIron() {
        boolean[] ores = getSlotsWithOres();
        int oreCount = 0;
        for (int i = 0; i < ores.length; i++) {
            if (ores[i]) {
                oreCount++;
                if (inventory[i + 4].stackSize <= 1) inventory[i + 4] = null;
                else inventory[i + 4].stackSize--;
            }
        }
        if (inventory[8] == null) {
            inventory[8] = new ItemStack(CRItems.ores, 1, 0);
        }
        ItemOres.setBloomSize(inventory[8], oreCount + getBloomSize());

        float slag = getSlagPercent(oreCount);
        Random rand = new Random();
        if (rand.nextFloat() < slag) {
            if(inventory[9] == null) {
                inventory[9] = new ItemStack(CRItems.ores, 1, 1);
            }
            else {
                inventory[9].stackSize++;
            }
        }

        this.markDirty();
    }

    private int getBloomSize() {
        return ItemOres.getBloomSize(inventory[8]);
    }

    private boolean consumeFuel() {
        if (hasFuel()){
            for (int i = 0; i < 4; i++) {
                if (inventory[i] != null && inventory[i].getItem() == Items.coal && inventory[i].getItemDamage() == 1) {
                    if (inventory[i].stackSize <= 1) inventory[i] = null;
                    else inventory[i].stackSize--;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasWork(int bloomSize) {
        boolean[] slots = getSlotsWithOres();
        //ores in ore slots
        if (slots[0] || slots[1] || slots[2] || slots[3]) {
            //bloom isn't (and wont be) max size or doesn't exist
            if (inventory[8] == null || bloomSize + getAmountSlotsWithOres() <= ItemOres.BLOOM_MAX) {
                //slag output isn't full
                if (inventory[9] == null || inventory[9].stackSize < 64) {
                    return true;
                }
            }
        }
        return false;
    }

    private float getSlagPercent(int ores) {
        switch (ores) {
            case 1: return 0.1F;
            case 2: return 0.2F;
            case 3: return 0.4F;
            case 4: return 0.9F;
        }
        return 0.0F;
    }

    private boolean[] getSlotsWithOres() {
        boolean[] ores = {false, false, false, false};
        if (hasOres()) {
            for (int i = 0; i < 4; i++) {
                if (CoreUtility.hasDictionaryMatch(inventory[i + 4], "oreIron") && inventory[i + 4].stackSize > 0) ores[i] = true;
            }
        }
        return ores;
    }

    private int getAmountSlotsWithOres() {
        int ores = 0;
        for (boolean ore : getSlotsWithOres()) {
            if (ore) ores++;
        }
        return ores;
    }

    private boolean hasFuel() {
        for (int i = 0; i < 4; i++) {
            if (inventory[i] != null && inventory[i].getItem() == Items.coal && inventory[i].getItemDamage() == 1) return true;
        }
        return false;
    }

    private boolean hasOres() {
        for (int i = 0; i < 4; i++) {
            if (inventory[i + 4] != null && CoreUtility.hasDictionaryMatch(inventory[i + 4], "oreIron")) return true;
        }
        return false;
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
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(tile, tile.isMaster(), true, true, isBurning()), NetworkUtility.makeTargetPoint(this));
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
                        PacketHandler.INSTANCE.sendToAllAround(new MessageTileBloomery(tile, false, false, true, isBurning()), NetworkUtility.makeTargetPoint(this));
                        ChainReaction.proxy.updateTileModel(tile);
                    }
                }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {

            this.inventory = readNBTItems(tag, this);
            this.bloomeryCookTime = tag.getShort("bloomeryCookTime");
            this.bloomeryBurnTime = tag.getShort("bloomeryBurnTime");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {

            writeNBTItems(tag, inventory);
            tag.setShort("bloomeryCookTime", (short)bloomeryCookTime);
            tag.setShort("bloomeryBurnTime", (short)bloomeryBurnTime);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_) {
        return this.bloomeryCookTime * p_145953_1_ / 200;
    }

    public boolean isBurning() {
        return this.bloomeryBurnTime > 0;
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
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileBloomery(this, isMaster, hasValidMaster(), false, isBurning()));
    }
}
