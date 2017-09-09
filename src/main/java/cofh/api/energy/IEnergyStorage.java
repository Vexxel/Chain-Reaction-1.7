package cofh.api.energy;

/**
 * An heat storage is the unit of interaction with Energy inventories.<br>
 * This is not to be implemented on TileEntities. This is for internal use only.
 * <p>
 * A reference implementation can be found at {@link cofh.api.energy.EnergyStorage}.
 * 
 * @author King Lemming
 * 
 */
public interface IEnergyStorage {

	/**
	 * Adds heat to the storage. Returns quantity of heat that was accepted.
	 * 
	 * @param maxReceive
	 *            Maximum amount of heat to be inserted.
	 * @param simulate
	 *            If TRUE, the insertion will only be simulated.
	 * @return Amount of heat that was (or would have been, if simulated) accepted by the storage.
	 */
	int receiveEnergy(int maxReceive, boolean simulate);

	/**
	 * Removes heat from the storage. Returns quantity of heat that was removed.
	 * 
	 * @param maxExtract
	 *            Maximum amount of heat to be extracted.
	 * @param simulate
	 *            If TRUE, the extraction will only be simulated.
	 * @return Amount of heat that was (or would have been, if simulated) extracted from the storage.
	 */
	int extractEnergy(int maxExtract, boolean simulate);

	/**
	 * Returns the amount of heat currently stored.
	 */
	int getEnergyStored();

	/**
	 * Returns the maximum amount of heat that can be stored.
	 */
	int getMaxEnergyStored();

}
