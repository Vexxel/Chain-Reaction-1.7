package com.zerren.zedeng.block.tile.reactor;

import com.zerren.zedeng.block.tile.TEMultiBlockBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 3/7/2015.
 */
public class TEHeatExchanger extends TEMultiBlockBase implements IFluidHandler {

    private final int tankCapacity = 4000;

    public FluidTank waterToBoil = new FluidTank(this.tankCapacity);
    public FluidTank steam = new FluidTank(this.tankCapacity);

    public FluidTank hotReactorWater = new FluidTank(this.tankCapacity);
    public FluidTank coldReactorWater = new FluidTank(this.tankCapacity);


    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    //Fluid output from tile
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    //Fluid output from tile, not specific
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
        return new FluidTankInfo[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
    }
}
