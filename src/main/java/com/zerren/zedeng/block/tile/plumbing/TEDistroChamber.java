package com.zerren.zedeng.block.tile.plumbing;

import com.zerren.zedeng.block.tile.TileEntityZE;
import com.zerren.zedeng.utility.TileCache;
import com.zerren.zedeng.utility.TransferUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 4/9/2015.
 */
public class TEDistroChamber extends TileEntityZE implements IFluidHandler {
    public FluidTank tank = new FluidTank(16000);

    private int updateCounter = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        if (this.tank.getCapacity() > 0) {
            float amount = tank.getFluidAmount();
            float toPush = 500 + (amount * (amount / tank.getCapacity()));

            pushFluids((int)toPush);
        }
        updateCounter++;
        if (updateCounter > 800) {
            updateCache();
            updateCounter = 0;
        }
    }

    private void pushFluids(int toPush) {
        if (tileCache == null) updateCache();

        TransferUtility.splitFluidToConsumers(tank, toPush, tileCache, ForgeDirection.DOWN);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (canFill(from, resource.getFluid())) {
            used += tank.fill(filling, doFill);
            filling.amount -= used;
        }
        return used;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        //nothing to drain
        if (resource == null) return null;
        //tank doesn't contain what the drainer wants
        if (!resource.isFluidEqual(tank.getFluid())) return null;

        if (canDrain(from, resource.getFluid()))
            return drain(from, resource.amount, doDrain);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from != ForgeDirection.DOWN)
            return tank.drain(maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return from == ForgeDirection.DOWN && (tank.getFluid() == null || tank.getFluid().getFluid() == fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from != ForgeDirection.DOWN && tank.getFluid() != null && tank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.tank.readFromNBT(tag.getCompoundTag("tank"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
    }
}
