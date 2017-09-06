package com.zerren.chainreaction.tile.mechanism;

import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.tile.TEEnergyProviderBase;
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
}
