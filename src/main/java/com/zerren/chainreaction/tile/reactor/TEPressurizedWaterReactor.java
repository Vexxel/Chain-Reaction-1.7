package com.zerren.chainreaction.tile.reactor;

import chainreaction.api.block.IThermalTile;
import chainreaction.api.reactor.ReactorType;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileMultiblock;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.MultiblockShape;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NetworkUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.UUID;

/**
 * Created by Zerren on 4/23/2015.
 */
public class TEPressurizedWaterReactor extends TEMultiBlockBase implements IInventory, IThermalTile, IFluidHandler {

    private final int tankCapacity = 32000;
    public final FluidTank coolantInletTank = new FluidTank(this.tankCapacity);
    public final FluidTank coolantOutputTank = new FluidTank(this.tankCapacity);

    protected ItemStack[] inventory = new ItemStack[19];

    /**
     * Arbitrary thermal storage used to heat a fluid--10 TU can turn 1mb water to 160mb steam (RF:Steam :: 2:1), 1TU = 32 RF.
     * 5mb of hot coolant = 1TU = 200TU per 1000mb--Exchanger at peak performance consuming hot coolant produces 20TU/t = 640RF/t = 320Steam/t.
     * Each bucket of hot coolant can make 6400RF
     */
    private float thermalUnits;

    /**
     * Arbitrary thermal waste heat storage that is the 'leftover' of a energy conversion--this tile entity does not have a loss in efficiency, because it deals in
     * entirely thermal units. Loss of efficiency occurs when energy is transformed between two different types (eg. Heat <-> electricity, light <-> heat)
     */
    private int wasteHeatUnits;

    private short updateCounter;

    private short controlRodDepth;

    private final ReactorType reactorType;

    public TEPressurizedWaterReactor() {
        super();
        thermalUnits = 0;
        this.reactorType = ReactorType.PWR;
        controlRodDepth = 100;
    }

    @Override
    public void updateEntity() {

        if (!worldObj.isRemote && isMaster()) {
            //do things
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
                MultiblockCost.takeMaterials(player, MultiblockCost.PRESSURIZED_WATER_REACTOR, true);
                ChainReaction.proxy.playSound(this.worldObj, xCoord, yCoord, zCoord, Reference.Sounds.LOCK_SUCCESS, 1.0F, 1.0F);
            }
        }
    }

    public short getControlRodDepth() {
        return this.controlRodDepth;
    }

    /**
     * Depth of control rods. 100 is fully inserted and is effectively "off".
     * @param depth how far the control rods are inserted. 100 = full, 0 = none
     */
    public void setControlRodDepth(short depth)  {
        if (depth > 100) depth = 100;
        else if (depth < 0) depth = 0;

        this.controlRodDepth = depth;
    }

    private boolean checkMultiblock(EntityPlayer player) {
        MultiblockShape[][][] structure = MultiblockShape.pwr;
        int counter = 0;

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 7; y++)
                for (int z = 0; z < 3; z++) {
                    if (worldObj.getBlock(xCoord + (x - 1), yCoord + (y - 3), zCoord + (z - 1)) == structure[x][y][z].getBlock()) {
                        if (worldObj.getBlockMetadata(xCoord + (x - 1), yCoord + (y - 3), zCoord + (z - 1)) == structure[x][y][z].getMetadata()) {
                            counter++;
                        }
                    }
                }

            if (counter == 63) {
                if (!MultiblockCost.takeMaterials(player, MultiblockCost.PRESSURIZED_WATER_REACTOR, false)) return false;

                this.setAsMaster(true);

                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 7; y++)
                        for (int z = 0; z < 3; z++) {
                            TEMultiBlockBase tile = (TEMultiBlockBase) worldObj.getTileEntity(xCoord + (x - 1), yCoord + (y - 3), zCoord + (z - 1));

                            tile.setController(this.getControllerUUID(), xCoord, yCoord, zCoord);

                            tile.setMultiblockPartNumber((short) ((x * 100) + (y * 10) + (z)));
                            tile.setOrientation(this.getOrientation());
                            PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, tile.isMaster(), true, true), NetworkUtility.makeTargetPoint(this));
                        }

            }
        return counter == 63;
    }

    @Override
    public void invalidateMultiblock() {
        TEMultiBlockBase commander = getCommandingController();
        if (commander == null) commander = this;
        commander.setAsMaster(false);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 7; y++)
                for (int z = 0; z < 3; z++) {
                    TEMultiBlockBase tile = (TEMultiBlockBase) worldObj.getTileEntity(commander.xCoord + (x - 1), commander.yCoord + (y - 3), commander.zCoord + (z - 1));
                    if (tile != null) {
                        tile.removeController();
                        tile.setMultiblockPartNumber((short) -1);
                        PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, false, false, true), NetworkUtility.makeTargetPoint(this));
                        ChainReaction.proxy.updateTileModel(tile);
                    }
                }

        //MultiblockCost.refundMaterials(MultiblockCost.PRESSURIZED_WATER_REACTOR, worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public ReactorType getReactorType() {
        return this.reactorType;
    }

    @Override
    public float getThermalUnits() {
        return thermalUnits;
    }
    @Override
    public void setThermalUnits(float units) {
        this.thermalUnits = units;
    }
    @Override
    public int getWasteHeat() {
        return wasteHeatUnits;
    }
    @Override
    public void setWasteHeat(int units) {
        this.wasteHeatUnits = units;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        TEPressurizedWaterReactor commander = (TEPressurizedWaterReactor)getCommandingController();
        short p = this.getMultiblockPartNumber();
        if (p == 31 || p == 130 ||  p == 132 || p == 231) {
            return new FluidTankInfo[] {(commander != null) ? commander.coolantOutputTank.getInfo() : coolantOutputTank.getInfo()};
        }
        else if (p == 11 || p == 110 ||  p == 112 || p == 211) {
            return new FluidTankInfo[] {(commander != null) ? commander.coolantInletTank.getInfo() : coolantInletTank.getInfo()};
        }

        return new FluidTankInfo[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {
            this.coolantInletTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantInput"));
            this.coolantOutputTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantOutput"));

            this.thermalUnits = tag.getFloat(Names.NBT.THERMAL_UNITS);

            this.inventory = readNBTItems(tag, this);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {
            tag.setTag(Names.NBT.TANK + "CoolantInput", coolantInletTank.writeToNBT(new NBTTagCompound()));
            tag.setTag(Names.NBT.TANK + "CoolantOutput", coolantOutputTank.writeToNBT(new NBTTagCompound()));

            tag.setFloat(Names.NBT.THERMAL_UNITS, thermalUnits);

            writeNBTItems(tag, inventory);
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster())
            return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 3, zCoord - 1, xCoord + 2, yCoord + 4, zCoord + 2);
        return super.getRenderBoundingBox();
    }

    @Override
    public int getSizeInventory() {
        return 19;
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
        return CoreUtility.translate("container.pwr.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && p_70300_1_.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
