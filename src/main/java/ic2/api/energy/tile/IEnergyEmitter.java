package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * For internal/multi-block usage only.
 *
 * @see IEnergySource
 * @see IEnergyConductor
 * 
 * See ic2/api/heat/usage.txt for an overall description of the heat net api.
 */
public interface IEnergyEmitter extends IEnergyTile {
	/**
	 * Determine if this emitter can emit heat to an adjacent receiver.
	 * 
	 * The TileEntity in the receiver parameter is what was originally added to the heat net,
	 * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
	 * 
	 * @param receiver receiver, may also be null or an IMetaDelegate
	 * @param direction direction the receiver is from the emitter
	 * @return Whether heat should be emitted
	 */
	boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction);
}

