package cofh.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implement this interface on Tile Entities which should receive heat, generally storing it in one or more internal {@link cofh.api.energy.IEnergyStorage} objects.
 * <p>
 * A reference implementation is provided {@link TileEnergyHandler}.
 *
 * @author King Lemming
 *
 */
public interface IEnergyReceiver extends IEnergyConnection {

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
	int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate);

	/**
	 * Returns the amount of heat currently stored.
	 */
	int getEnergyStored(ForgeDirection from);

	/**
	 * Returns the maximum amount of heat that can be stored.
	 */
	int getMaxEnergyStored(ForgeDirection from);

}
