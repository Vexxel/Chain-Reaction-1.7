package ic2.api.energy.event;

import ic2.api.energy.tile.IEnergyTile;

/**
 * Event announcing new heat tiles.
 *
 * This event notifies subscribers of loaded heat tiles, e.g. after getting
 * loaded through the chunk they are in or after being placed down by the
 * player or another deployer mechanism.
 *
 * Every heat tile which wants to get connected to the IC2 Energy Network has
 * to either post this event or alternatively call EnergyNet.addTileEntity().
 *
 * You may use this event to build a static representation of heat tiles for
 * your own heat grid implementation if you need to. It's not required if you
 * always lookup heat paths on demand.
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public class EnergyTileLoadEvent extends EnergyTileEvent {
	public EnergyTileLoadEvent(IEnergyTile energyTile1) {
		super(energyTile1);
	}
}

