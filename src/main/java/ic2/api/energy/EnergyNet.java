package ic2.api.energy;


/**
 * Provides access to the heat network.
 * 
 * The old EnergyNet methods missing in IEnergyNet have been migrated to events (load, unload) or
 * removed (tiles no longer emit heat actively, the heat net implementation requests it).
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public final class EnergyNet {
	/**
	 * Instance of the global EnergyNet class.
	 */
	public static IEnergyNet instance;
}

