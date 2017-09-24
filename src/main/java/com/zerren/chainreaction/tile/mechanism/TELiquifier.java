package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.block.IInventoryCR;
import chainreaction.api.recipe.ElectrolyzingFluid;
import chainreaction.api.recipe.LiquifyingFluid;
import chainreaction.api.tile.IGuiTileData;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.tile.TEEnergyRecieverBase;
import com.zerren.chainreaction.tile.container.ContainerCR;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.TransferUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TELiquifier extends TEEnergyRecieverBase implements IFluidHandler, IInventoryCR, IGuiTileData {
    private static final int TANK_CAPACITY = 8000;

    public final FluidTank inputTank = new FluidTank(TANK_CAPACITY);
    public final FluidTank outputTank1 = new FluidTank(TANK_CAPACITY);

    private ItemStack[] inventory;
    private int cookTime;
    public boolean hasWork;
    private int energyRequired;
    private int cookTimeRequired;

    public TELiquifier() {
        super(256, 25600, TransferUtility.getAllSideDirections());
        inventory = new ItemStack[4];
        cookTime = 0;
        hasWork = false;
        energyRequired = 64;
        cookTimeRequired = 40;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {

            int energy = energyStorage.getEnergyStored();

            //enough energy to work and has work to do
            if (energy >= energyRequired && hasWork) {
                //increase the cook time and reduce the energy
                cookTime++;
                energyStorage.modifyEnergyStored(-energyRequired);

                //if enough time has passed to liquify
                if (cookTime >= cookTimeRequired) {
                    liquify(true);
                    checkForWork();
                    cookTime = 0;
                }
            }
            else cookTime = 0;

            if (outputTank1.getFluidAmount() > 0)
                TransferUtility.pushFluidsToDirection(this, outputTank1, 200, spinLeft(getOrientation(), false));

        }
    }

    @Override
    protected void checkForWork() {
        hasWork = liquify(false);
    }

    private boolean liquify(boolean doWork) {
        if (getInputFluid() == null) return false;
        int amount = inputTank.getFluid().amount;
        FluidStack output = LiquifyingFluid.getOutput(inputTank.getFluid().getFluid());
        int amountRequired = LiquifyingFluid.getInputRequiredAmount(getInputFluid().getFluid());

        //not enough in the input tank
        if (amount < amountRequired) return false;
        //negative amount or zero
        if (amount <= 0) return false;

        //busted shenanigans avoidance
        if (output == null) {
            ChainReaction.log.warn("Input liquifying fluid has no outputs in gas liquifier at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            inputTank.setFluid(null);
            return false;
        }

        int output1Space = outputTank1.getCapacity() - outputTank1.getFluidAmount();

        //no room for output
        if ((output1Space - output.amount) < 0) return false;

        if (doWork) {
            outputTank1.fill(output.copy(), true);
            inputTank.drain(amountRequired, true);
        }
        return true;
    }

    public FluidStack getInputFluid() {
        return inputTank.getFluid();
    }

    public FluidStack getOutput1Fluid() {
        return outputTank1.getFluid();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        readNBTFluidTank(tag, inputTank, "Input");
        readNBTFluidTank(tag, outputTank1, "Output");

        cookTime = tag.getInteger("cookTime");
        cookTimeRequired = tag.getInteger("cookTimeRequired");
        energyRequired = tag.getInteger("energyRequired");

        this.inventory = readNBTItems(tag, this);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        writeNBTFluidTank(tag, inputTank, "Input");
        writeNBTFluidTank(tag, outputTank1, "Output");

        tag.setInteger("cookTime", cookTime);
        tag.setInteger("cookTimeRequired", cookTimeRequired);
        tag.setInteger("energyRequired", energyRequired);

        writeNBTItems(tag, inventory);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from == getOrientation().getOpposite();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (canFill(from, resource.getFluid())) {
            used += inputTank.fill(filling, doFill);
            filling.amount -= used;
        }

        return used;
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
        //if the fluid is a valid heating fluid and the filling direction is from the right
        return LiquifyingFluid.validLiquifyingFluid(fluid) && from == spinRight(getOrientation(), false);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from == spinLeft(getOrientation(), false);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from == spinRight(getOrientation(), false)) return new FluidTankInfo[]{this.inputTank.getInfo()};
        else if (from == spinLeft(getOrientation(), false)) return new FluidTankInfo[]{this.outputTank1.getInfo()};

        return new FluidTankInfo[0];
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public void clearInventory() {

    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            if (itemStack.stackSize <= decrementAmount) {
                setInventorySlotContents(slotIndex, null);
            }
            else {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        if (inventory[slotIndex] != null) {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        }
        else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomName() ? this.getCustomName() : CoreUtility.translate("container.liquifier.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.hasCustomName();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getGUINetworkData(int id, int v) {
        switch (id) {
            case 0:
                this.cookTime = v;
                break;
            case 1:
                this.cookTimeRequired = v;
                break;
            case 2:
                this.energyRequired = v;
                break;
            case 3:
                energyStorage.setEnergyStored(v);
                break;
            case 4:
                if (inputTank.getFluid() != null)
                    inputTank.getFluid().amount = v;
                break;
            case 5:
                if (outputTank1.getFluid() != null)
                    outputTank1.getFluid().amount = v;
                break;
            case 6:
                if (inputTank.getFluid() == null) {
                    int[] val = CRMath.unpack2(v);
                    inputTank.setFluid(new FluidStack(val[0], val[1]));
                }
                break;
            case 7:
                if (outputTank1.getFluid() == null) {
                    int[] val = CRMath.unpack2(v);
                    outputTank1.setFluid(new FluidStack(val[0], val[1]));
                }
                break;

        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void sendGUINetworkData(ContainerCR container, ICrafting iCrafting) {
        iCrafting.sendProgressBarUpdate(container, 0, cookTime);
        iCrafting.sendProgressBarUpdate(container, 1, cookTimeRequired);
        iCrafting.sendProgressBarUpdate(container, 2, energyRequired);
        iCrafting.sendProgressBarUpdate(container, 3, energyStorage.getEnergyStored());

        iCrafting.sendProgressBarUpdate(container, 4, inputTank.getFluidAmount());
        iCrafting.sendProgressBarUpdate(container, 5, outputTank1.getFluidAmount());

        if (inputTank.getFluid() != null)
            iCrafting.sendProgressBarUpdate(container, 6, CRMath.pack2(inputTank.getFluid().getFluidID(), inputTank.getFluidAmount()));
        if (outputTank1.getFluid() != null)
            iCrafting.sendProgressBarUpdate(container, 7, CRMath.pack2(outputTank1.getFluid().getFluidID(), outputTank1.getFluidAmount()));
    }

    @SideOnly(Side.CLIENT)
    public int getProgressPercent(int width) {
        return (int)(cookTime > 0 ? (double)cookTime / cookTimeRequired * width : 0);
    }
}
