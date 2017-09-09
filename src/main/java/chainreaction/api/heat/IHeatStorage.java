package chainreaction.api.heat;

/**
 * Created by Zerren on 4/11/2015.
 * Basically a copy of KingLemming's IEnergyStorage, but for heat.
 *
 *
 */
public interface IHeatStorage {

    /**
     * Gets the arbitrary thermal 'units' that are stored in this tile entity: this is usable heat, unlike wasteHeatUnits
     * @return thermalUnits
     */
    int getHeatStored();

    int getMaxHeatStored();

    /**
     * Adds heat to the storage. Returns quantity of heat that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of heat to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) accepted by the storage.
     */
    int receiveHeat(int maxReceive, boolean simulate);

    /**
     * Removes heat from the storage. Returns quantity of heat that was removed.
     *
     * @param maxExtract
     *            Maximum amount of heat to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) extracted from the storage.
     */
    int extractHeat(int maxExtract, boolean simulate);

}
