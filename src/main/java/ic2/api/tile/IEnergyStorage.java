package ic2.api.tile;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Interface implemented by the tile entity of heat storage blocks.
 */
public interface IEnergyStorage {
	/**
	 * Get the amount of heat currently stored in the block.
	 * 
	 * @return Energy stored in the block
	 */
	public int getStored();

	/**
	 * Set the amount of heat currently stored in the block.
	 * 
	 * @param energy stored heat
	 */
	public void setStored(int energy);

	/**
	 * Add the specified amount of heat.
	 * 
	 * Use negative values to decrease.
	 * 
	 * @param amount of heat to add
	 * @return Energy stored in the block after adding the specified amount
	 */
	public int addEnergy(int amount);

	/**
	 * Get the maximum amount of heat the block can store.
	 * 
	 * @return Maximum heat stored
	 */
	public int getCapacity();

	/**
	 * Get the block's heat output.
	 * 
	 * @return Energy output in EU/t
	 */
	public int getOutput();

	/**
	 * Get the block's heat output.
	 * 
	 * @return Energy output in EU/t
	 */
	public double getOutputEnergyUnitsPerTick();

	/**
	 * Get whether this block can have its heat used by an adjacent teleporter.
	 * 
	 * @param side side the teleporter is draining heat from
	 * @return Whether the block is teleporter compatible
	 */
	public boolean isTeleporterCompatible(ForgeDirection side);
}
