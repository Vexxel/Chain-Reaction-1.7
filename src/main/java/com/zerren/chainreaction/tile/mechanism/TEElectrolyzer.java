package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.block.IInventoryCR;
import chainreaction.api.block.ISidedInventoryCR;
import chainreaction.api.item.MachineUpgrade;
import chainreaction.api.recipe.ElectrolyzingFluid;
import chainreaction.api.tile.IGuiTileData;
import chainreaction.api.tile.IUpgradeableTile;
import chainreaction.api.tile.UpgradeStorage;
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
public class TEElectrolyzer extends TEEnergyRecieverBase implements IFluidHandler, ISidedInventoryCR, IGuiTileData, IUpgradeableTile {
    private static final int TANK_CAPACITY = 8000;

    private UpgradeStorage upgradeStorage = new UpgradeStorage();

    public final FluidTank inputTank = new FluidTank(TANK_CAPACITY);
    public final FluidTank outputTank1 = new FluidTank(TANK_CAPACITY);
    public final FluidTank outputTank2 = new FluidTank(TANK_CAPACITY);
    private ItemStack[] inventory;
    private int cookTime;
    public boolean hasWork;

    private static final int energyCapacityBase = 25600;

    private int energyRequired;
    private final int energyRequiredBase;

    private int cookTimeRequired;
    private final int cookTimeRequiredBase;

    private boolean upgradesActive;


    public TEElectrolyzer() {
        super(512, energyCapacityBase, TransferUtility.getAllElevationDirections());
        inventory = new ItemStack[5];
        cookTime = 0;
        hasWork = false;

        energyRequired = 128;
        energyRequiredBase = 128;

        cookTimeRequired = 80;
        cookTimeRequiredBase = 80;

        upgradeStorage = new UpgradeStorage();
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

                //if enough time has passed to electrolyze
                if (cookTime >= cookTimeRequired) {
                    electrolyze(true);
                    checkForWork();
                    cookTime = 0;
                }
            }
            else cookTime = 0;

            if (outputTank1.getFluidAmount() > 0)
                TransferUtility.pushFluidsToDirection(this, outputTank1, 200, spinRight(getOrientation(), false));
            if (outputTank2.getFluidAmount() > 0)
                TransferUtility.pushFluidsToDirection(this, outputTank2, 200, spinLeft(getOrientation(), false));

        }
    }

    @Override
    protected void checkForWork() {
        hasWork = electrolyze(false);
    }

    private boolean electrolyze(boolean doWork) {
        if (getInputFluid() == null) return false;
        int amount = inputTank.getFluid().amount;
        FluidStack output1 = ElectrolyzingFluid.getOutput1(inputTank.getFluid().getFluid());
        FluidStack output2 = ElectrolyzingFluid.getOutput2(inputTank.getFluid().getFluid());
        int amountRequired = ElectrolyzingFluid.getInputRequiredAmount(getInputFluid().getFluid());

        //not enough in the input tank
        if (amount < amountRequired) return false;
        //negative amount or zero
        if (amount <= 0) return false;

        //busted shenanigans avoidance
        if (output1 == null || output2 == null) {
            ChainReaction.log.warn("Input electrolyzing fluid has no outputs in electrolyzer at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            inputTank.setFluid(null);
            return false;
        }

        int output1Space = outputTank1.getCapacity() - outputTank1.getFluidAmount();
        int output2Space = outputTank2.getCapacity() - outputTank2.getFluidAmount();

        //no room for either output
        if ((output1Space - output1.amount) < 0) return false;
        if ((output2Space - output2.amount) < 0) return false;

        if (doWork) {
            outputTank1.fill(output1.copy(), true);
            outputTank2.fill(output2.copy(), true);
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
        cookTimeRequired = tag.getInteger("cookTimeRequired");
        energyRequired = tag.getInteger("energyRequired");
        upgradesActive = tag.getBoolean("upgradesActive");

        this.inventory = readNBTItems(tag, this);

        //upgradeStorage.readFromNBT(tag);
        //readUpgradeStorage(tag, upgradeStorage);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        writeNBTFluidTank(tag, inputTank, "Input");
        writeNBTFluidTank(tag, outputTank1, "Output1");
        writeNBTFluidTank(tag, outputTank2, "Output2");

        tag.setInteger("cookTime", cookTime);
        tag.setInteger("cookTimeRequired", cookTimeRequired);
        tag.setInteger("energyRequired", energyRequired);
        tag.setBoolean("upgradesActive", upgradesActive);

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
        //if the fluid is a valid heating fluid and the filling direction is from the back
        return ElectrolyzingFluid.validElectrolyzingFluid(fluid) && from == getOrientation().getOpposite();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from == spinLeft(getOrientation(), false) || from == spinRight(getOrientation(), false);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from == getOrientation().getOpposite()) return new FluidTankInfo[]{this.inputTank.getInfo()};
        else if (from == spinRight(getOrientation(), false)) return new FluidTankInfo[]{this.outputTank1.getInfo()};
        else if (from == spinLeft(getOrientation(), false)) return new FluidTankInfo[]{this.outputTank2.getInfo()};

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
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch (slot) {
            case 0: case 1: return true;
        }
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        switch (slot) {
            case 2: case 3: case 4: return false;
        }
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        switch (slot) {
            case 2: case 3: case 4: return false;
        }
        return true;
    }

    @Override
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
                if (outputTank2.getFluid() != null)
                    outputTank2.getFluid().amount = v;
                break;
            case 7:
                if (inputTank.getFluid() == null) {
                    int[] val = CRMath.unpack2(v);
                    inputTank.setFluid(new FluidStack(val[0], val[1]));
                }
                break;
            case 8:
                if (outputTank1.getFluid() == null) {
                    int[] val = CRMath.unpack2(v);
                    outputTank1.setFluid(new FluidStack(val[0], val[1]));
                }
                break;
            case 9:
                if (outputTank2.getFluid() == null) {
                    int[] val = CRMath.unpack2(v);
                    outputTank2.setFluid(new FluidStack(val[0], val[1]));
                }
                break;
            case 10:
                this.energyStorage.setCapacity(v);
                break;
        }
    }

    @Override
    public void sendGUINetworkData(ContainerCR container, ICrafting iCrafting) {
        iCrafting.sendProgressBarUpdate(container, 0, cookTime);
        iCrafting.sendProgressBarUpdate(container, 1, cookTimeRequired);
        iCrafting.sendProgressBarUpdate(container, 2, energyRequired);
        iCrafting.sendProgressBarUpdate(container, 3, energyStorage.getEnergyStored());

        iCrafting.sendProgressBarUpdate(container, 4, inputTank.getFluidAmount());
        iCrafting.sendProgressBarUpdate(container, 5, outputTank1.getFluidAmount());
        iCrafting.sendProgressBarUpdate(container, 6, outputTank2.getFluidAmount());

        if (inputTank.getFluid() != null)
            iCrafting.sendProgressBarUpdate(container, 7, CRMath.packFluidTank(inputTank));
        if (outputTank1.getFluid() != null)
            iCrafting.sendProgressBarUpdate(container, 8, CRMath.packFluidTank(outputTank1));
        if (outputTank2.getFluid() != null)
            iCrafting.sendProgressBarUpdate(container, 9, CRMath.packFluidTank(outputTank2));

        iCrafting.sendProgressBarUpdate(container, 10, energyStorage.getMaxEnergyStored());
    }

    @SideOnly(Side.CLIENT)
    public int getProgressPercent(int width) {
        return (int)(cookTime > 0 ? (double)cookTime / cookTimeRequired * width : 0);
    }

    @Override
    public MachineUpgrade[] getValidUpgrades() {
        return new MachineUpgrade[] {
                MachineUpgrade.CAPACITY,
                MachineUpgrade.EFFICIENCY,
                MachineUpgrade.OVERCLOCKER,
                MachineUpgrade.RTG
        };
    }

    @Override
    public ItemStack[] getUpgradesInInventory() {
        return new ItemStack[] { inventory[2], inventory[3], inventory[4]};
    }

    @Override
    public void installUpgrades() {
        upgradesActive = !upgradesActive;

        if (upgradesActive) {

            upgradeStorage.upgradeTile(getUpgradesInInventory());

            energyStorage.setCapacity(energyStorage.getMaxEnergyStored() + upgradeStorage.getCapacityMod());
            rfGenPerTickFromRTGMod = upgradeStorage.getRTGMod();
            energyRequired = (int)(energyRequiredBase * (upgradeStorage.getCostMod() <= 0F ? 1F : upgradeStorage.getCostMod()));
            cookTimeRequired = (int)(cookTimeRequiredBase / (upgradeStorage.getSpeedMod() <= 0 ? 1 : upgradeStorage.getSpeedMod()));
        }
        else  {
            upgradeStorage.clear();

            if (energyStorage.getEnergyStored() > energyCapacityBase)  {
                energyStorage.setEnergyStored(energyCapacityBase);
            }

            energyStorage.setCapacity(energyCapacityBase);
            rfGenPerTickFromRTGMod = 0;
            energyRequired = energyRequiredBase;
            cookTimeRequired = cookTimeRequiredBase;
        }
    }

    @Override
    public boolean areUpgradesActive() {
        return upgradesActive;
    }

    @Override
    public UpgradeStorage getUpgradeStorage() {
        return upgradeStorage;
    }
}
