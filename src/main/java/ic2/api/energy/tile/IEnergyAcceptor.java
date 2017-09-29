package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * For internal/multi-block usage only.
 *
 * @see IEnergySink
 * @see IEnergyConductor
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public interface IEnergyAcceptor extends IEnergyTile {
	/**
	 * Determine if this acceptor can accept current from an adjacent emitter in a direction.
	 * 
	 * The TileEntity in the emitter parameter is what was originally added to the heat net,
	 * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
	 * 
	 * @param emitter heat emitter, may also be null or an IMetaDelegate
	 * @param direction direction the heat is being received from
	 */
	boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction);
}

