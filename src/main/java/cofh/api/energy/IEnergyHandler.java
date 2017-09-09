package cofh.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implement this interface on Tile Entities which should handle heat, generally storing it in one or more internal {@link IEnergyStorage} objects.
 * <p>
 * A reference implementation is provided {@link TileEnergyHandler}.
 *
 * @author King Lemming
 *
 */
public interface IEnergyHandler extends IEnergyProvider, IEnergyReceiver {

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
	@Override
	int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate);

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
	@Override
	int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate);


	/**
	 * Returns the amount of heat currently stored.
	 */
	@Override
	int getEnergyStored(ForgeDirection from);

	/**
	 * Returns the maximum amount of heat that can be stored.
	 */
	@Override
	int getMaxEnergyStored(ForgeDirection from);

}
