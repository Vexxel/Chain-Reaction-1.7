package com.zerren.chainreaction.tile.plumbing;

import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.TransferUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 4/9/2015.
 */
public class TEDistroChamber extends TileEntityCRBase implements IFluidHandler {
    public FluidTank tank = new FluidTank(16000);

    private int updateCounter;

    public TEDistroChamber() {
        super();
        updateCounter = 0;
        canFaceUpDown = true;
        setCanTick();
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }

        if (this.tank.getCapacity() > 0) {
            float amount = tank.getFluidAmount();
            float toPush = 500 + (amount * (amount / tank.getCapacity()));

            pushFluids((int)toPush);
        }
        updateCounter++;
        if (updateCounter > 1200) {
            updateCache();
            updateCounter = 0;
        }
    }

    private void pushFluids(int toPush) {
        if (tileCache == null) updateCache();

        TransferUtility.splitFluidToCachedConsumers(tank, toPush, tileCache, getOrientation());
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
        if (from != getOrientation())
            return tank.drain(maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return from == getOrientation() && (tank.getFluid() == null || tank.getFluid().getFluid() == fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from != getOrientation() && tank.getFluid() != null && tank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.tank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setTag(Names.NBT.TANK, tank.writeToNBT(new NBTTagCompound()));
    }
}
