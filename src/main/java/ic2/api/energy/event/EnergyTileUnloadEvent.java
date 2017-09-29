package ic2.api.energy.event;

import ic2.api.energy.tile.IEnergyTile;

/**
 * Event announcing terminated heat tiles.
 *
 * This event notifies subscribers of unloaded heat tiles, e.g. after getting
 * unloaded through the chunk they are in or after being destroyed by the
 * player or another block pick/destruction mechanism.
 *
 * Every heat tile which wants to get disconnected from the IC2 Energy
 * Network has to either post this event or alternatively call
 * EnergyNet.removeTileEntity().
 *
 * You may use this event to build a static representation of heat tiles for
 * your own heat grid implementation if you need to. It's not required if you
 * always lookup heat paths on demand.
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public class EnergyTileUnloadEvent extends EnergyTileEvent {
	public EnergyTileUnloadEvent(IEnergyTile energyTile1) {
		super(energyTile1);
	}
}

