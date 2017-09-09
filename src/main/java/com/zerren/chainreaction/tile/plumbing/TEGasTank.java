package com.zerren.chainreaction.tile.plumbing;

import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileGasTank;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.NetworkUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 4/13/2015.
 */
public class TEGasTank extends TileEntityCRBase implements IFluidHandler {

    public final FluidTank tank = new FluidTank(ConfigHandler.gasTankVolume * 1000);
    private boolean tankDirty;
    private byte updateCounter;
    public int fluidAmount;

    public TEGasTank() {
        super();
        fluidAmount = 0;
        updateCounter = 0;
        tankDirty = false;
        setCanTick();
    }

    @Override
    public void updateEntity() {
        updateCounter++;

        if (updateCounter >= 60) {
            if (tankDirty && tank.getFluid() != null && tank.getFluidAmount() > 0) {
                fluidAmount = tank.getFluidAmount();
                PacketHandler.INSTANCE.sendToAllAround(new MessageTileGasTank(this, fluidAmount), NetworkUtility.makeTargetPoint(this));
                tankDirty = false;
                this.markDirty();
            }

            updateCounter = 0;
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (canFill(from, resource.getFluid())) {
            used += tank.fill(filling, doFill);
            filling.amount -= used;
        }
        tankDirty = true;
        return used;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        //nothing to drain
        if (resource == null) return null;
        //tank doesn't contain what the drainer wants
        if (!resource.isFluidEqual(tank.getFluid())) return null;

        if (canDrain(from, resource.getFluid())) {
            tankDirty = true;
            return drain(from, resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from == ForgeDirection.UP || from == ForgeDirection.DOWN) {
            tankDirty = true;
            return tank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP) && (tank.getFluid() == null || tank.getFluid().getFluid() == fluid) && fluid.isGaseous();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP) && tank.getFluid() != null && tank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from.ordinal() <= 1) return new FluidTankInfo[]{this.tank.getInfo()};
        return new FluidTankInfo[0];
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

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileGasTank(this, tank.getFluidAmount()));
    }
}
