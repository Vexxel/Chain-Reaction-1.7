package com.zerren.chainreaction.tile.mechanism;

import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.tile.TEEnergyProviderBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TERTG extends TEEnergyProviderBase {

    public TERTG() {
        super(ConfigHandler.rtgPower, 12800, ForgeDirection.UP, ForgeDirection.DOWN);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            energyStorage.modifyEnergyStored(RF_GEN_PER_TICK);
            transferEnergyUpDown();
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
        return true;
    }
}
