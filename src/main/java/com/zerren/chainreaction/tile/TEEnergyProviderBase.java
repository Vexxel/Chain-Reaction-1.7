package com.zerren.chainreaction.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import com.zerren.chainreaction.handler.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TEEnergyProviderBase extends TileEntityCRBase implements IEnergyProvider {

    protected EnergyStorage energyStorage = new EnergyStorage(0);
    protected final int RF_GEN_PER_TICK;
    protected final ForgeDirection[] sidesConnectingEnergy;

    public TEEnergyProviderBase(int rfGenPerTick, int eStorage, ForgeDirection... directions) {
        super();
        RF_GEN_PER_TICK = rfGenPerTick;

        energyStorage = new EnergyStorage(eStorage, RF_GEN_PER_TICK * 2);

        if (directions != null)
            sidesConnectingEnergy = directions;
        else
            sidesConnectingEnergy = ForgeDirection.VALID_DIRECTIONS;
    }

    protected void transferEnergyUpDown() {
        for (int i = 0; i < 2; i++) {

            //ForgeDirection is a useful helper class for handling directions.
            int targetY = yCoord + ForgeDirection.getOrientation(i).offsetY;

            TileEntity tile = worldObj.getTileEntity(xCoord, targetY, zCoord);
            if (tile instanceof IEnergyHandler) {

                int maxExtract = energyStorage.getMaxExtract(); //Gets the maximum amount of energy that can be extracted from this tile in one tick.
                int maxAvailable = energyStorage.extractEnergy(maxExtract, true); //Simulates removing "maxExtract" to find out how much energy is actually available.
                int energyTransferred = ((IEnergyHandler) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), maxAvailable, false); //Sends "maxAvailable" to the target tile and records how much energy was accepted.

                energyStorage.extractEnergy(energyTransferred, false);//Extract the energy transferred from the internal storage.
            }
        }
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (from == ForgeDirection.UP || from == ForgeDirection.DOWN) {
            return energyStorage.extractEnergy(RF_GEN_PER_TICK * 2, simulate);
        }
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from == ForgeDirection.DOWN || from == ForgeDirection.UP;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        energyStorage.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        energyStorage.writeToNBT(tag);
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
