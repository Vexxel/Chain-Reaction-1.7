package com.zerren.chainreaction.tile.mechanism;

import chainreaction.api.heat.HeatStorage;
import chainreaction.api.heat.IHeatHandler;
import com.zerren.chainreaction.tile.TEEnergyProviderBase;
import com.zerren.chainreaction.utility.TransferUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TEStirlingEngine extends TEEnergyProviderBase implements IHeatHandler{


    protected HeatStorage heatStorage = new HeatStorage(0);
    protected final ForgeDirection[] sidesConnectingHeat;

    public TEStirlingEngine() {
        super(256, 12800, TransferUtility.getAllSideDirections());
        sidesConnectingHeat = TransferUtility.getAllSideDirections();
        heatStorage = new HeatStorage(10000, 500);

    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            //1 bucket/s = 620 heat/s = 2560 rf/s (128rf/t)
            //31 heat/t received
            int heat = heatStorage.getHeatStored();
            int toConsume = heat > 75 ? 75 : heat;

            heatStorage.modifyHeatStored(-toConsume);
            energyStorage.modifyEnergyStored((int)(toConsume * 3.5));

            transferEnergyToOneSide(getOrientation());
        }
    }

    @Override
    public int receiveHeat(ForgeDirection from, int maxReceive, boolean simulate) {
        if (isFromValidHeatDirection(from)) {
            return heatStorage.receiveHeat(maxReceive, simulate);
        }
        return 0;
    }

    @Override
    public int extractHeat(ForgeDirection from, int maxExtract, boolean simulate) {
        if (isFromValidHeatDirection(from)) {
            return heatStorage.extractHeat(maxExtract, simulate);
        }
        return 0;
    }

    @Override
    public int getHeatStored(ForgeDirection from) {
        return heatStorage.getHeatStored();
    }

    @Override
    public int getMaxHeatStored(ForgeDirection from) {
        return heatStorage.getMaxHeatStored();
    }

    @Override
    public boolean canConnectHeat(ForgeDirection from) {
        return from == getOrientation().getOpposite();
    }

    protected boolean isFromValidHeatDirection(ForgeDirection from) {
        for (ForgeDirection side : sidesConnectingHeat) {
            if (from == side) return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        heatStorage.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        heatStorage.writeToNBT(tag);
    }
}
