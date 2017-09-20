package com.zerren.chainreaction.tile;

import chainreaction.api.heat.HeatStorage;
import chainreaction.api.heat.IHeatHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/22/2015.
 */
public abstract class TEHeatHandlerBase extends TileEntityCRBase implements IHeatHandler {

    protected HeatStorage heatStorage = new HeatStorage(0);
    protected int heatGenPerTick;

    protected final ForgeDirection[] sidesConnectingHeat;

    public TEHeatHandlerBase(int gen, ForgeDirection[] directions) {
        super();
        heatGenPerTick = gen;

        heatStorage = new HeatStorage(10000, 500);

        setCanTick();

        if (directions != null)
            sidesConnectingHeat = directions;
        else
            sidesConnectingHeat = ForgeDirection.VALID_DIRECTIONS;
    }

    protected void transferHeatToOneSide(ForgeDirection dir) {
        int targetX = xCoord + dir.offsetX;
        int targetY = yCoord + dir.offsetY;
        int targetZ = zCoord + dir.offsetZ;

        TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
        if (tile instanceof IHeatHandler) {

            int maxExtract = heatStorage.getMaxExtract(); //Gets the maximum amount of heat that can be extracted from this tile in one tick.
            int maxAvailable = heatStorage.extractHeat(maxExtract, true); //Simulates removing "maxExtract" to find out how much heat is actually available.
            int energyTransferred = ((IHeatHandler) tile).receiveHeat(dir.getOpposite(), maxAvailable, false); //Sends "maxAvailable" to the target tile and records how much heat was accepted.

            heatStorage.extractHeat(energyTransferred, false);//Extract the heat transferred from the internal storage.
        }
    }

    protected void transferHeatToConnectingSides() {
        for (ForgeDirection dir : sidesConnectingHeat){

            //ForgeDirection is a useful helper class for handling directions.
            int targetX = xCoord + dir.offsetX;
            int targetY = yCoord + dir.offsetY;
            int targetZ = zCoord + dir.offsetZ;

            TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
            if (tile instanceof IHeatHandler) {

                int maxExtract = heatStorage.getMaxExtract(); //Gets the maximum amount of heat that can be extracted from this tile in one tick.
                int maxAvailable = heatStorage.extractHeat(maxExtract, true); //Simulates removing "maxExtract" to find out how much heat is actually available.
                int energyTransferred = ((IHeatHandler) tile).receiveHeat(dir.getOpposite(), maxAvailable, false); //Sends "maxAvailable" to the target tile and records how much heat was accepted.

                heatStorage.extractHeat(energyTransferred, false);//Extract the heat transferred from the internal storage.
            }
        }
    }

    @Override
    public int receiveHeat(ForgeDirection from, int maxReceive, boolean simulate) {
        if (isFromValidDirection(from)) {
            return heatStorage.receiveHeat(maxReceive, simulate);
        }
        return 0;
    }

    @Override
    public int extractHeat(ForgeDirection from, int maxExtract, boolean simulate) {
        if (isFromValidDirection(from)) {
            return heatStorage.extractHeat(maxExtract, simulate);
        }
        return 0;
    }

    protected boolean isFromValidDirection(ForgeDirection from) {
        for (ForgeDirection side : sidesConnectingHeat) {
            if (from == side) return true;
        }
        return false;
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
        return isFromValidDirection(from);
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
