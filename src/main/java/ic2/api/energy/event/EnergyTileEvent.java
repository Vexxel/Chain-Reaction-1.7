package ic2.api.energy.event;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.event.world.WorldEvent;

import ic2.api.energy.tile.IEnergyTile;

/**
 * Base class for heat net events, don't use it directly.
 *
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public class EnergyTileEvent extends WorldEvent {
	public final IEnergyTile energyTile;

	public EnergyTileEvent(IEnergyTile energyTile1) {
		super(((TileEntity) energyTile1).getWorldObj());

		if (world == null) throw new NullPointerException("world is null");

		this.energyTile = energyTile1;
	}
}

