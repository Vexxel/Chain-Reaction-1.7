package ic2.api.energy.tile;

/**
 * Tile entities which conduct heat pulses without buffering (mostly cables) have to implement this
 * interface.
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public interface IEnergyConductor extends IEnergyAcceptor, IEnergyEmitter {
	/**
	 * Energy loss for the conductor in EU per block.
	 * 
	 * @return Energy loss
	 */
	double getConductionLoss();

	/**
	 * Amount of heat the insulation will handle before shocking nearby players and mobs.
	 * 
	 * @return Insulation heat absorption in EU
	 */
	double getInsulationEnergyAbsorption();

	/**
	 * Amount of heat the insulation will handle before it is destroyed.
	 * Ensure that this value is greater than the insulation heat absorption + 64.
	 *
	 * @return Insulation-destroying heat in EU
	 */
	double getInsulationBreakdownEnergy();

	/**
	 * Amount of heat the conductor will handle before it melts.
	 * 
	 * @return Conductor-destroying heat in EU
	 */
	double getConductorBreakdownEnergy();

	/**
	 * Remove the conductor's insulation if the insulation breakdown heat was exceeded.
	 * 
	 * @see #getInsulationBreakdownEnergy()
	 */
	void removeInsulation();

	/**
	 * Remove the conductor if the conductor breakdown heat was exceeded.
	 * 
	 * @see #getConductorBreakdownEnergy()
	 */
	void removeConductor();
}

