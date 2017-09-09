package chainreaction.api.heat;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/8/2017.
 */
public interface IHeatHandler extends IHeatConnection {

    // merely a convenience interface (remove these methods in 1.8; provided here for back-compat via compiler doing things)

    /**
     * Add heat to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
     *
     * @param from
     *            Orientation the heat is received from.
     * @param maxReceive
     *            Maximum amount of heat to receive.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) received.
     */

    int receiveHeat(ForgeDirection from, int maxReceive, boolean simulate);

    /**
     * Remove heat from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
     *
     * @param from
     *            Orientation the heat is extracted from.
     * @param maxExtract
     *            Maximum amount of heat to extract.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) extracted.
     */
    int extractHeat(ForgeDirection from, int maxExtract, boolean simulate);


    /**
     * Returns the amount of heat currently stored.
     */
    int getHeatStored(ForgeDirection from);

    /**
     * Returns the maximum amount of heat that can be stored.
     */
    int getMaxHeatStored(ForgeDirection from);
}
