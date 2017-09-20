package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.block.IInventoryCR;
import chainreaction.api.heat.HeatStorage;
import chainreaction.api.heat.IHeatHandler;
import chainreaction.api.recipe.ElectrolyzingFluid;
import chainreaction.api.recipe.HeatingFluid;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileCR;
import com.zerren.chainreaction.tile.TEEnergyRecieverBase;
import com.zerren.chainreaction.tile.container.ContainerElectrolyzer;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.TransferUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TEElectrolyzer extends TEEnergyRecieverBase implements IFluidHandler, IInventoryCR {
    public final int tankCapacity = 8000;

    public final FluidTank inputTank = new FluidTank(tankCapacity);
    public final FluidTank outputTank1 = new FluidTank(tankCapacity);
    public final FluidTank outputTank2 = new FluidTank(tankCapacity);
    private ItemStack[] inventory;
    private int cookTime;

    public TEElectrolyzer() {
        super(256, 12800, TransferUtility.getAllElevationDirections());
        inventory = new ItemStack[3];
        cookTime = 0;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (cookTime >= 20) {
                int energy = energyStorage.getEnergyStored();
                if (energy >= 32) {
                    electrolyze();
                }

                electrolyze();
                cookTime = 0;
            }
            if (outputTank1.getFluidAmount() > 0)
                pushFluids(outputTank1, 200, spinLeft(getOrientation(), false));
            if (outputTank2.getFluidAmount() > 0)
                pushFluids(outputTank2, 200, getOrientation().getOpposite());

            cookTime++;
        }
    }

    private void electrolyze() {
        if (getInputFluid() == null) return;
        int amount = inputTank.getFluid().amount;
        FluidStack output1 = ElectrolyzingFluid.getOutput1(inputTank.getFluid().getFluid());
        FluidStack output2 = ElectrolyzingFluid.getOutput2(inputTank.getFluid().getFluid());
        int amountRequired = ElectrolyzingFluid.getInputRequiredAmount(getInputFluid().getFluid());

        //not enough in the input tank
        if (amount < amountRequired) return;
        //negative amount or zero
        if (amount <= 0) return;

        //busted shenanigans avoidance
        if (output1 == null || output2 == null) {
            ChainReaction.log.warn("Input electrolyzing fluid has no outputs in electrolyzer at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            inputTank.setFluid(null);
            return;
        }

        int output1Space = outputTank1.getCapacity() - outputTank1.getFluidAmount();
        int output2Space = outputTank2.getCapacity() - outputTank2.getFluidAmount();

        //no room for either output
        if ((output1Space - output1.amount) < 0) return;
        if ((output2Space - output2.amount) < 0) return;

        outputTank1.fill(output1.copy(), true);
        outputTank2.fill(output2.copy(), true);

        inputTank.drain(amountRequired, true);

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    private void pushFluids(IFluidTank tank, int flowCap, ForgeDirection dir) {
        TileEntity receiver = TransferUtility.getTileAdjacentFromDirection(this, dir);

        FluidStack fluid = tank.drain(flowCap, false);
        if (fluid != null && fluid.amount > 0 && receiver instanceof IFluidHandler) {
            int used = ((IFluidHandler) receiver).fill(dir.getOpposite(), fluid, true);

            if (used > 0) {
                //amount -= used;
                tank.drain(used, true);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    public FluidStack getInputFluid() {
        return inputTank.getFluid();
    }

    public FluidStack getOutput1Fluid() {
        return outputTank1.getFluid();
    }

    public FluidStack getOutput2Fluid() {
        return outputTank2.getFluid();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        readNBTFluidTank(tag, inputTank, "Input");
        readNBTFluidTank(tag, outputTank1, "Output1");
        readNBTFluidTank(tag, outputTank2, "Output2");

        cookTime = tag.getInteger("cookTime");

        this.inventory = readNBTItems(tag, this);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        writeNBTFluidTank(tag, inputTank, "Input");
        writeNBTFluidTank(tag, outputTank1, "Output1");
        writeNBTFluidTank(tag, outputTank2, "Output2");

        tag.setInteger("cookTime", cookTime);

        writeNBTItems(tag, inventory);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from == ForgeDirection.UP;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (canFill(from, resource.getFluid())) {
            used += inputTank.fill(filling, doFill);
            filling.amount -= used;
        }
        if (used > 0 ) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
        return ElectrolyzingFluid.validElectrolyzingFluid(fluid) && from == spinRight(getOrientation(), false);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from == spinLeft(getOrientation(), false) || from == getOrientation().getOpposite();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from == spinRight(getOrientation(), false)) return new FluidTankInfo[]{this.inputTank.getInfo()};
        else if (from == spinLeft(getOrientation(), false)) return new FluidTankInfo[]{this.outputTank1.getInfo()};
        else if (from == getOrientation().getOpposite()) return new FluidTankInfo[]{this.outputTank2.getInfo()};

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
        return this.hasCustomName() ? this.getCustomName() : CoreUtility.translate("container.electrolyzer.name");
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
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    public void getGUINetworkData(int id, int v)
    {
        /*switch (id)
        {
            case 0:
                if (getInputFluid() == null)
                {
                   inputTank.setFluid(new FluidStack(v, 0));
                } else
                {
                    inputTank.getFluid().fluidID = v;
                }
                break;
            case 1:
                if (tank[0].getFluid() == null)
                {
                    tank[0].setFluid(new FluidStack(0, v));
                } else
                {
                    tank[0].getFluid().amount = v;
                }
                break;
            case 2:
                if (tank[1].getFluid() == null)
                {
                    tank[1].setFluid(new FluidStack(v, 0));
                } else
                {
                    tank[1].getFluid().fluidID = v;
                }
                break;
            case 3:
                if (tank[1].getFluid() == null)
                {
                    tank[1].setFluid(new FluidStack(0, v));
                } else
                {
                    tank[1].getFluid().amount = v;
                }
                break;
        }*/
    }

    public void sendGUINetworkData(ContainerElectrolyzer container, ICrafting iCrafting)
    {
        /*iCrafting.sendProgressBarUpdate(container, 0, getInputFluid() != null ? getInputFluid().getFluidID() : 0);
        iCrafting.sendProgressBarUpdate(container, 1, getInputFluid() != null ? getInputFluid().amount : 0);

        iCrafting.sendProgressBarUpdate(container, 2, getOutput1Fluid() != null ? getOutput1Fluid().getFluidID() : 0);
        iCrafting.sendProgressBarUpdate(container, 3, getOutput1Fluid() != null ? getOutput1Fluid().amount : 0);

        iCrafting.sendProgressBarUpdate(container, 4, getOutput2Fluid() != null ? getOutput2Fluid().getFluidID() : 0);
        iCrafting.sendProgressBarUpdate(container, 5, getOutput2Fluid() != null ? getOutput2Fluid().amount : 0);*/
    }

}
